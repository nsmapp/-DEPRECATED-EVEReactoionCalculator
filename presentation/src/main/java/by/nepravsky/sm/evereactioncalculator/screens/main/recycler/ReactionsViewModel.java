package by.nepravsky.sm.evereactioncalculator.screens.main.recycler;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import by.nepravsky.sm.domain.entity.old.FormulaComponent;
import by.nepravsky.sm.evereactioncalculator.screens.base.recycler.BaseClickedModel;
import by.nepravsky.sm.evereactioncalculator.screens.base.recycler.BaseViewModel;
import io.reactivex.subjects.PublishSubject;

public class ReactionsViewModel extends BaseViewModel<FormulaComponent> {

    public ObservableField<String> typeName = new ObservableField<>("");
    public ObservableField<String> vol = new ObservableField<>("");
    public ObservableField<String> sell = new ObservableField<>("");
    public ObservableField<String> buy = new ObservableField<>("");
    public ObservableField<String> quantity = new ObservableField<>("");

    public ObservableField<String> reactionComponent = new ObservableField<>("material");
    public ObservableBoolean isProduct = new ObservableBoolean(false);

    public ObservableField<String> imgUrl = new ObservableField<>("");

    private PublishSubject<BaseClickedModel<FormulaComponent>> positionCLick;

    @Override
    public void setEntity(FormulaComponent entity, int position) {

        setEntity(entity);

        imgUrl.set("https://imageserver.eveonline.com/Type/" + entity.getId() + "_64.png");
        typeName.set(entity.getName());
        sell.set(String.format(Locale.getDefault(),"%,3.2f", entity.getSell()) + " ISK");
        buy.set(String.format(Locale.getDefault(),"%,3.2f", entity.getBuy()) + " ISK");
        vol.set(String.format(Locale.getDefault(),"%,3.2f", entity.getVol()) + " m\u00b3");
        quantity.set(entity.getQuantity() + " unit");
        isProduct.set(entity.isProduct());

        if (entity.isProduct()){
            reactionComponent.set("product");
        }

    }

    public ReactionsViewModel(PublishSubject<BaseClickedModel<FormulaComponent>> positionCLick) {
        this.positionCLick = positionCLick;
    }

    @Override
    public void onItemClick() {
        positionCLick.onNext(new BaseClickedModel<>(getEntity()));
    }

    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView iv, String url) {
        Picasso.get()
                .load(url)
                .into(iv);
    }



}
