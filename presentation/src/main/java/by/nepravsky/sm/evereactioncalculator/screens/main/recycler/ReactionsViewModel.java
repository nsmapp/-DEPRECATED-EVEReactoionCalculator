package by.nepravsky.sm.evereactioncalculator.screens.main.recycler;


import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import by.nepravsky.sm.domain.entity.presentation.ItemPres;
import by.nepravsky.sm.evereactioncalculator.screens.base.recycler.BaseClickedModel;
import by.nepravsky.sm.evereactioncalculator.screens.base.recycler.BaseViewModel;
import io.reactivex.subjects.PublishSubject;

public class ReactionsViewModel extends BaseViewModel<ItemPres> {

    public ObservableField<String> typeName = new ObservableField<>("");
    public ObservableField<String> vol = new ObservableField<>("");
    public ObservableField<String> sell = new ObservableField<>("");
    public ObservableField<String> buy = new ObservableField<>("");
    public ObservableField<String> quantity = new ObservableField<>("");

    public ObservableField<String> reactionComponent = new ObservableField<>("material");
    public ObservableBoolean isProduct = new ObservableBoolean(false);

    public ObservableField<String> imgUrl = new ObservableField<>("0");

    private PublishSubject<BaseClickedModel<ItemPres>> positionCLick;

    @Override
    public void setEntity(ItemPres entity, int position) {

        setEntity(entity);

        imgUrl.set(String.valueOf(entity.getId()));
        typeName.set(entity.getName());
        sell.set(entity.getSellPriceString());
        buy.set(entity.getBuyPriceString());
        vol.set(entity.getVolumeString());
        quantity.set(String.valueOf(entity.getQuantity()));
    }

    public ReactionsViewModel(PublishSubject<BaseClickedModel<ItemPres>> positionCLick) {
        this.positionCLick = positionCLick;
    }

    @Override
    public void onItemClick() {
        positionCLick.onNext(new BaseClickedModel<>(getEntity()));
    }





}
