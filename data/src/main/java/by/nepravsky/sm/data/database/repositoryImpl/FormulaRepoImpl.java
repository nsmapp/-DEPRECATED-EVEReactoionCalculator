package by.nepravsky.sm.data.database.repositoryImpl;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import by.nepravsky.sm.data.database.AppDatabase;
import by.nepravsky.sm.data.database.entity.FormulaDBE;
import by.nepravsky.sm.data.database.entity.ProductionItem;
import by.nepravsky.sm.domain.entity.Formula;
import by.nepravsky.sm.domain.entity.FormulaComponent;
import by.nepravsky.sm.domain.repositories.FormulaRepositories;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

public class FormulaRepoImpl implements FormulaRepositories {


    private AppDatabase database;

    @Inject
    public FormulaRepoImpl(AppDatabase database) {
        this.database = database;
    }

    @Override
    public Observable<Formula> getFormula(final String name) {
        return database.getFormulaDAO()
                .getFormuls(name)
                .toObservable()
                .map(new Function<FormulaDBE, Formula>() {
                    @Override
                    public Formula apply(FormulaDBE formulaDBE) throws Exception {

                        List<FormulaComponent> products = new ArrayList<>();
                        List<FormulaComponent> materials= new ArrayList<>();

                        Gson gson = new Gson();

                        ProductionItem[] materialItems =
                                gson.fromJson(formulaDBE.getMaterial(), ProductionItem[].class);
                        ProductionItem[] productionItems =
                                gson.fromJson(formulaDBE.getProduct(), ProductionItem[].class);

                        for (ProductionItem m: materialItems){
                            materials.add(new FormulaComponent(
                                    String.valueOf(m.getTypeID()),
                                    m.getQuantity(),
                                    false)
                            );
                        }

                        for (ProductionItem p: productionItems){
                            products.add(new FormulaComponent(
                                    String.valueOf(p.getTypeID()),
                                    p.getQuantity(),
                                    true)
                            );
                        }

                        int time = Integer.parseInt(formulaDBE.getTime());

                        return new Formula(materials, products, time);
                    }
                });
    }


}
