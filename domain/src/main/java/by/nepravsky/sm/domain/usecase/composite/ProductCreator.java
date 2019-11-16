package by.nepravsky.sm.domain.usecase.composite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.Formula;
import by.nepravsky.sm.domain.entity.FormulaComponent;
import by.nepravsky.sm.domain.entity.Material;
import by.nepravsky.sm.domain.entity.MaterialPrice;
import by.nepravsky.sm.domain.entity.Product;
import by.nepravsky.sm.domain.entity.Tax;
import by.nepravsky.sm.domain.executors.PostExecutionThread;
import by.nepravsky.sm.domain.usecase.BaseUseCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.FormulaCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.MaterialMapCase;
import by.nepravsky.sm.domain.usecase.downlevel.caseinterface.MaterialPriceMapCase;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class ProductCreator extends BaseUseCase {

    private FormulaCase formulaCase;
    private MaterialMapCase materialCase;
    private MaterialPriceMapCase priceCase;

    @Inject
    public ProductCreator(PostExecutionThread postExecutionThread,
                          FormulaCase formulaCase,
                          MaterialMapCase materialCase,
                          MaterialPriceMapCase priceCase) {
        super(postExecutionThread);
        this.formulaCase = formulaCase;
        this.materialCase = materialCase;
        this.priceCase = priceCase;
    }

    public Observable<Product> get(String itemName, final int regionId, final long run, final Tax tax){

        return formulaCase
                .get(itemName)
                .subscribeOn(executionThread)
                .observeOn(postExecutionThread)
                .flatMap(new Function<Formula, Observable<Product>>() {
                    @Override
                    public Observable<Product> apply(final Formula formula) throws Exception {
                        return priceCase.get(formula.getIdList(), regionId)
                                .flatMap(new Function<Map<String, MaterialPrice>, Observable<Product>>() {
                                    @Override
                                    public Observable<Product> apply(final Map<String, MaterialPrice> priceMap) throws Exception {
                                        return materialCase.get(formula.getIdList())
                                                .flatMap(new Function<Map<String, Material>, Observable<Product>>() {
                                                    @Override
                                                    public Observable<Product> apply(Map<String, Material> materialMap) throws Exception {

                                                        Product product = createProduct(formula,
                                                                materialMap,
                                                                priceMap,
                                                                run,
                                                                tax);
                                                        return Observable.just(product);
                                                    }
                                                });
                                    }
                                });
                    }
                });



    }

    private Product createProduct(Formula formula,
                                  Map<String, Material> materialMap,
                                  Map<String, MaterialPrice> priceMap,
                                  long runs,
                                  Tax tax){

        double materialSell = 0;
        double materialBuy = 0;
        double materialVol = 0;
        long reactionTime = formula.getTime() * runs;
        List<FormulaComponent> componentsList = new ArrayList<>();

        componentsList.addAll(formula.getProductList());
        componentsList.addAll(formula.getMaterialList());

        for (FormulaComponent fc: componentsList) {

            MaterialPrice matPrices;
            Material material;

            fc.setQuantity(fc.getQuantity() * runs);

            matPrices = priceMap.get(fc.getId());
            material = materialMap.get(fc.getId());

            double sell = matPrices.getSell() * fc.getQuantity();
            double buy = matPrices.getBuy() * fc.getQuantity() * tax.getBrokersFee();
            double vol = material.getVol() * fc.getQuantity();

            if (!fc.isProduct()){
                materialBuy = materialBuy + buy * tax.getReactionTax();
                materialSell = materialSell + sell * tax.getReactionTax();
                materialVol = materialVol + vol ;
            }

            fc.setMaterialInfoAndPrice(vol, material.getName(), buy, sell, runs);

        }

        return new Product(reactionTime,
                componentsList,
                materialSell,
                materialBuy,
                materialVol);
    }


}
