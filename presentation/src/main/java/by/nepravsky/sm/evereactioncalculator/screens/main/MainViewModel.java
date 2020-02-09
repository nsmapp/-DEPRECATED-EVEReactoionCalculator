package by.nepravsky.sm.evereactioncalculator.screens.main;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import by.nepravsky.sm.domain.entity.presentation.FormulaName;
import by.nepravsky.sm.domain.entity.presentation.FormulaTypes;
import by.nepravsky.sm.domain.entity.presentation.ItemPres;
import by.nepravsky.sm.domain.entity.presentation.ReactionPres;
import by.nepravsky.sm.domain.usecase.GetFormulaNamesUseCase;
import by.nepravsky.sm.domain.usecase.reactor.FullReactionUseCase;
import by.nepravsky.sm.domain.usecase.reactor.SingleReactionUseCase;
import by.nepravsky.sm.domain.utils.TimeConverter;
import by.nepravsky.sm.evereactioncalculator.R;
import by.nepravsky.sm.evereactioncalculator.app.App;
import by.nepravsky.sm.evereactioncalculator.screens.base.activity.BaseViewModel;
import by.nepravsky.sm.evereactioncalculator.screens.base.recycler.BaseClickedModel;
import by.nepravsky.sm.evereactioncalculator.screens.main.recycler.ReactionsAdapter;
import by.nepravsky.sm.evereactioncalculator.utils.ErrorMessage;
import by.nepravsky.sm.domain.utils.NumberValidator;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

import static by.nepravsky.sm.evereactioncalculator.utils.IDs.ALL_FORMULS;
import static by.nepravsky.sm.evereactioncalculator.utils.IDs.BOOSTER_FORM;
import static by.nepravsky.sm.evereactioncalculator.utils.IDs.COMPOSITE_FORM;
import static by.nepravsky.sm.evereactioncalculator.utils.IDs.FULLERITE_FORM;

public class MainViewModel extends BaseViewModel<MainRouter> {

    public ObservableInt spinnerId = new ObservableInt();
    public ObservableField<String> reaction = new ObservableField<>("");
    public ObservableField<String> runs = new ObservableField<>("1");
    public ObservableField<String> reactionTax = new ObservableField<>("0.1");

    public ObservableField<String> productName= new ObservableField<>();
    public ObservableField<String> productVolume= new ObservableField<>();
    public ObservableField<String> productQuantity = new ObservableField<>();
    public ObservableField<String> productSell = new ObservableField<>();
    public ObservableField<String> productBuy = new ObservableField<>();
    public ObservableField<String> reactionTime = new ObservableField<>();
    public ObservableField<String> reactionCost = new ObservableField<>();
    public ObservableField<String> materialSell = new ObservableField<>();
    public ObservableField<String> materialBuy = new ObservableField<>();
    public ObservableField<String> materialVol = new ObservableField<>();
    public ObservableField<String> productUrl = new ObservableField<>("14");

    public ObservableInt isVisableSettings = new ObservableInt(View.GONE);
    public ObservableInt isVisableInformation = new ObservableInt(View.VISIBLE);
    public ObservableBoolean isFullChain = new ObservableBoolean(false);

    public ObservableBoolean isAll = new ObservableBoolean(true);
    public ObservableBoolean isBoosters = new ObservableBoolean(false);
    public ObservableBoolean isFullerites = new ObservableBoolean(false);
    public ObservableBoolean isComposite = new ObservableBoolean(false);

    private int systemId  = 10000002;
    public ReactionsAdapter adapter = new ReactionsAdapter();
    public LinkedList<ReactionPres> productsCach = new LinkedList<>();


    @Override
    public void runInject() {
        App.getAppComponent().runInject(this);
    }

    @Inject public SingleReactionUseCase singleReaction;
    @Inject public FullReactionUseCase fullReaction;
    @Inject public GetFormulaNamesUseCase formulaNamesUseCase;
    @Inject public TimeConverter timeConverter;
    @Inject public ErrorMessage errorMessage;
    @Inject public Resources resources;

    public FormulaTypes formulaTypes = new FormulaTypes(
            new ArrayList<FormulaName>(),
            new ArrayList<FormulaName>(),
            new ArrayList<FormulaName>()
    );

    public MainViewModel() {

        addCompositeDisposable(
                formulaNamesUseCase
                        .getFormulaTypes()
                        .subscribe(new Consumer<FormulaTypes>() {
                            @Override
                            public void accept(FormulaTypes types) throws Exception {
                                formulaTypes = types;
                                int formType = router.getFormulaState();

                                if(formType == BOOSTER_FORM){
                                    enabledBoosters();
                                }else { if(formType == FULLERITE_FORM){
                                    enabledFullerite();
                                }else { if(formType == COMPOSITE_FORM){
                                    enabledComposite();
                                }else { enabledAll(); }}}
                            }
                        })
        );

        addCompositeDisposable(
                adapter.observItemClick()
                        .subscribe(new Consumer<BaseClickedModel<ItemPres>>() {
                            @Override
                            public void accept(BaseClickedModel<ItemPres> itemPresModel) throws Exception {
                                loadReaction(itemPresModel.getEntity().getName());
                            }
                        })
        );
    }

    public void searchOnClick(){
        router.closeKeyboard();
        router.dismisDropDownMenu();
        loadReaction(reaction.get());
    }


    private void loadReaction(final String reactionName){

        if(validationData() ){

            showProgressBar();
            double tax = Double.parseDouble(reactionTax.get());

            switch (spinnerId.get()){
                case 0:
                    systemId = 10000002; //Jita
                    break;
                case 1:
                    systemId = 10000030; //Rens
                    break;
                case 2:
                    systemId = 10000042; //Hek
                    break;
                case 3:
                    systemId = 10000043; //Amarr
                    break;
                case 4:
                    systemId = 10000032; //Dodixie
                    break;
            }

            int run = Integer.parseInt(runs.get());

            if (isFullChain.get()){
                fullReaction.get(reactionName, systemId, run, tax)
                        .subscribe(new Observer<ReactionPres>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                addCompositeDisposable(d);
                            }

                            @Override
                            public void onNext(ReactionPres reactionPres) {
                                disableProgressBar();
                                setReactionInformation(reactionPres);
                            }

                            @Override
                            public void onError(Throwable e) {
                                disableProgressBar();
                                router.showToast(errorMessage.getErrorMessage(e.toString()));
                            }
                            @Override
                            public void onComplete() {}
                        });
            }else {
                singleReaction.get(reactionName, systemId, run, tax)
                        .subscribe(new Observer<ReactionPres>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                addCompositeDisposable(d);
                            }
                            @Override
                            public void onNext(ReactionPres reactionPres) {
                                disableProgressBar();
                                setReactionInformation(reactionPres);
                            }
                            @Override
                            public void onError(Throwable e) {
                                disableProgressBar();
                                router.showToast(errorMessage.getErrorMessage(e.toString()));
                            }
                            @Override
                            public void onComplete() {}
                        });
            }


        }
    }



    public void setReactionInformation(ReactionPres reactionPres){
        productName.set(reactionPres.getProduct().getName());
        productUrl.set(String.valueOf(reactionPres.getProduct().getId()));
        productBuy.set(reactionPres.getProduct().getBuyPriceString());
        productSell.set(reactionPres.getProduct().getSellPriceString());
        productQuantity.set(String.valueOf(reactionPres.getProduct().getQuantity()));
        productVolume.set(reactionPres.getProduct().getVolumeString());
        adapter.setEntity(reactionPres.getMaterialList());
        reactionCost.set(reactionPres.getReactiomCost());
        materialBuy.set(reactionPres.getMaterialBuyPrice());
        materialSell.set(reactionPres.getMaterialSellPrice());
        materialVol.set(reactionPres.getMatVolume());
        reactionTime.set(timeConverter.calculateDMHE(reactionPres.getReactionTime()));
        productsCach.addLast(reactionPres);
    }

    private boolean validationData(){

        if (TextUtils.isEmpty(reaction.get())){
            router.showToast(resources.getString(R.string.reaction_name_not_filled));
            return false;
        }
        if (TextUtils.isEmpty(runs.get())){
            router.showToast(resources.getString(R.string.runs_not_filled));
            return false;
        }

        if (!NumberValidator.isDouble(reactionTax.get())){
            router.showToast(resources.getString(R.string.reaction_tax_not_correct));
            return false;
        }

        return true;
    }

    public void showDropDownMenu(){
        router.showDropDownMenu();
    }

    public void showSettings(){
        if (isVisableSettings.get() == View.GONE){
            isVisableSettings.set(View.VISIBLE);
        }else {
            isVisableSettings.set(View.GONE);
        }
    }

    public void showReactionInfo(){
        if (isVisableInformation.get() == View.GONE){
            isVisableInformation.set(View.VISIBLE);
        }else {
            isVisableInformation.set(View.GONE);
        }
    }

    public void enabledAll(){
        isBoosters.set(false);
        isComposite.set(false);
        isFullerites.set(false);
        isAll.set(true);
        router.initAutoCompliteAdapter(formulaTypes.getAll());
        router.saveFormulaState(ALL_FORMULS);
    }

    public void enabledBoosters(){
        isAll.set(false);
        isFullerites.set(false);
        isComposite.set(false);
        isBoosters.set(true);
        router.initAutoCompliteAdapter(formulaTypes.getBoosters());
        router.saveFormulaState(BOOSTER_FORM);
    }

    public void enabledComposite(){
        isAll.set(false);
        isFullerites.set(false);
        isBoosters.set(false);
        isComposite.set(true);
        router.initAutoCompliteAdapter(formulaTypes.getComposite());
        router.saveFormulaState(COMPOSITE_FORM);
    }

    public void enabledFullerite(){
        isAll.set(false);
        isBoosters.set(false);
        isComposite.set(false);
        isFullerites.set(true);
        router.initAutoCompliteAdapter(formulaTypes.getFullerides());
        router.saveFormulaState(FULLERITE_FORM);

    }
}
