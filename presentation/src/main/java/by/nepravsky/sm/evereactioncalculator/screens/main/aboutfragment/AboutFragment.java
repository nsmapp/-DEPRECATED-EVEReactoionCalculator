package by.nepravsky.sm.evereactioncalculator.screens.main.aboutfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
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
        View v = inflater.inflate(R.layout.fragment_about, container, false);

        TextView policy = v.findViewById(R.id.policy);
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri privacyPolicyURL = Uri.parse(getResources().getString(R.string.privacy_policy_url));
                Intent urlIntent = new Intent(Intent.ACTION_VIEW, privacyPolicyURL);
                startActivity(urlIntent);
            }
        });

        return v;
    }


}
