package by.nepravsky.sm.data.database.repositoryImpl;

import android.util.Log;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import by.nepravsky.sm.data.database.AppDatabase;
import by.nepravsky.sm.data.database.entity.FormulaDBE;
import by.nepravsky.sm.data.database.entity.FormulaNameDBE;
import by.nepravsky.sm.data.database.entity.ReactionItemDBE;
import by.nepravsky.sm.domain.entity.Formula;
import by.nepravsky.sm.domain.entity.ReactionItem;
import by.nepravsky.sm.domain.entity.presentation.FormulaName;
import by.nepravsky.sm.domain.repositories.FormulaRepositories;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class FormulaRepoImpl implements FormulaRepositories {


    private AppDatabase database;
    private Gson gson = new Gson();

    @Inject
    public FormulaRepoImpl(AppDatabase database) {
        this.database = database;
    }

    @Override
    public Single<Formula> getFormula(final String name) {
        return database.getFormulaDAO()
                .getFormuls(name)
                .map(new Function<FormulaDBE, Formula>() {
                    @Override
                    public Formula apply(FormulaDBE formulaDBE) throws Exception {

                        return makeFormula(formulaDBE);
                    }
                });
    }

    @Override
    public Single<List<Formula>> getAllFormuls() {
        return database.getFormulaDAO()
                .getAllFormuls()
                .map(new Function<List<FormulaDBE>, List<Formula>>() {
                    @Override
                    public List<Formula> apply(List<FormulaDBE> formulaDBEList) throws Exception {

                        List<Formula> formulaList = new ArrayList<>();
                        for (FormulaDBE formulaDBE : formulaDBEList){
                            formulaList.add(makeFormula(formulaDBE));
                        }

                        return formulaList;
                    }
                });
    }

    @Override
    public Single<List<FormulaName>> getBoosters() {
        return database.getFormulaDAO()
                .getBoostersNames()
                .map(new Function<List<FormulaNameDBE>, List<FormulaName>>() {
                    @Override
                    public List<FormulaName> apply(List<FormulaNameDBE> list) throws Exception {
                        List<FormulaName> foemulas = new ArrayList<FormulaName>();
                        for (FormulaNameDBE i : list){
                            foemulas.add(makeFormulaName(i));
                        }
                        return foemulas;
                    }
                });
    }

    @Override
    public Single<List<FormulaName>> getFullerites() {
        return database.getFormulaDAO()
                .getFulleriteNames()
                .map(new Function<List<FormulaNameDBE>, List<FormulaName>>() {
                    @Override
                    public List<FormulaName> apply(List<FormulaNameDBE> list) throws Exception {
                        List<FormulaName> foemulas = new ArrayList<FormulaName>();
                        for (FormulaNameDBE i : list){
                            foemulas.add(makeFormulaName(i));
                        }
                        return foemulas;
                    }
                });
    }

    @Override
    public Single<List<FormulaName>> getAllComposites() {
        return database.getFormulaDAO()
                .getCompositeNames()
                .map(new Function<List<FormulaNameDBE>, List<FormulaName>>() {
                    @Override
                    public List<FormulaName> apply(List<FormulaNameDBE> list) throws Exception {
                        List<FormulaName> foemulas = new ArrayList<FormulaName>();
                        for (FormulaNameDBE i : list){
                            foemulas.add(makeFormulaName(i));
                        }
                        return foemulas;
                    }
                });
    }


    private FormulaName makeFormulaName(FormulaNameDBE dbe){
        return new FormulaName(
                        dbe.getId(),
                        dbe.getName());
    }
    private Formula makeFormula(FormulaDBE formulaDBE){

        ReactionItemDBE[] materialItems =
                mapToReactionItemDBE(formulaDBE.getMaterial());
        ReactionItemDBE[] productItemDBES =
                mapToReactionItemDBE(formulaDBE.getProduct());

        ReactionItem products = mapToReactionItems(productItemDBES).get(0);
        List<ReactionItem> materials = mapToReactionItems(materialItems);

        int time = Integer.parseInt(formulaDBE.getTime());

        return new Formula(
                formulaDBE.getId(),
                formulaDBE.getEn(),
                materials,
                products,
                time);
    }

    private ReactionItemDBE[] mapToReactionItemDBE(String jsonArrayItems){
        return gson.fromJson(jsonArrayItems, ReactionItemDBE[].class);
    }

    private List<ReactionItem> mapToReactionItems(ReactionItemDBE[] itemList){

        List<ReactionItem> reactionItemList = new ArrayList<>();
        for (ReactionItemDBE item: itemList){
            reactionItemList.add(
                    new ReactionItem(
                            item.getQuantity(),
                            item.getTypeID()
                    )
            );
        }
        return reactionItemList;
    }


}
