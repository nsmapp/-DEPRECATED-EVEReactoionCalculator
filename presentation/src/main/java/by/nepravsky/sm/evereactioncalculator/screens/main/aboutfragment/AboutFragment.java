package by.nepravsky.sm.evereactioncalculator.screens.main.aboutfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import by.nepravsky.sm.evereactioncalculator.R;

public class AboutFragment extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getDialog()).setTitle(R.string.about);
        return inflater.inflate(R.layout.fragment_about, container, false);
    }


}
