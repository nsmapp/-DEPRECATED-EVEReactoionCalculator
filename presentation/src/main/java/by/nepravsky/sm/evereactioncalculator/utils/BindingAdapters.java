package by.nepravsky.sm.evereactioncalculator.utils;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

public class BindingAdapters {

    @BindingAdapter({"app:url"})
    public static void loadImage(ImageView iv, String url) {
        Picasso.get()
                .load("https://imageserver.eveonline.com/Type/" + url + "_128.png")
                .into(iv);
    }
}
