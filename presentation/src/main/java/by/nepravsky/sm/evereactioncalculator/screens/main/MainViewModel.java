package by.nepravsky.sm.evereactioncalculator.screens.main;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import java.util.LinkedList;
import javax.inject.Inject;
import by.nepravsky.sm.domain.entity.presentation.ItemPres;
import by.nepravsky.sm.domain.entity.presentation.ReactionPres;
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
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainViewModel extends BaseViewModel<MainRouter> {

    public ObservableInt spinnerId = new ObservableInt();
    public ObservableField<String> reaction = new ObservableField<>();
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

    private int systemId  = 10000002;
    public ReactionsAdapter adapter = new ReactionsAdapter();
    public LinkedList<ReactionPres> productsCach = new LinkedList<>();


    @Override
    public void runInject() {
        App.getAppComponent().runInject(this);
    }

    @Inject public SingleReactionUseCase singleReaction;
    @Inject public FullReactionUseCase fullReaction;
    @Inject public TimeConverter timeConverter;
    @Inject public ErrorMessage errorMessage;
    @Inject public Resources resources;

    public MainViewModel() {

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

}
