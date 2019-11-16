package by.nepravsky.sm.evereactioncalculator.screens.main;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import by.nepravsky.sm.evereactioncalculator.R;
import by.nepravsky.sm.evereactioncalculator.screens.base.activity.BaseMVVMActivity;
import by.nepravsky.sm.evereactioncalculator.databinding.ActivityMainBinding;
import by.nepravsky.sm.evereactioncalculator.screens.settings.SettingsActivity;


public class MainActivity extends BaseMVVMActivity<MainViewModel,
        ActivityMainBinding,
        MainRouter> {

    @Override
    public MainViewModel provideViewModel() {
        return ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainRouter provideRouter() {
        return new MainRouter(this);
    }

    public static long pressedTime;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AutoCompleteTextView reactions = findViewById(R.id.reaction);
        ArrayAdapter<String> reactionAdapter = new ArrayAdapter<>(this,
                R.layout.item_list_simple_text,
                getResources().getStringArray(R.array.reactions));
        reactions.setAdapter(reactionAdapter);

        Spinner spinner = findViewById(R.id.systems);
        ArrayAdapter<String> systemList = new ArrayAdapter<>(this, R.layout.item_list_simple_text, getResources()
        .getStringArray(R.array.regions));
        spinner.setAdapter(systemList);

        binding.productList.setLayoutManager(new LinearLayoutManager(this));
        binding.reaction.setOnEditorActionListener(doneEvent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.settings){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        if (item.getItemId() == R.id.about){
            router.startAboutFragment();
        }
        return super.onOptionsItemSelected(item);
    }

    private final AutoCompleteTextView.OnEditorActionListener doneEvent = new AutoCompleteTextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if (actionId == EditorInfo.IME_ACTION_DONE){
                viewModel.searchOnClick();

            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {

        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        }else {

            if (viewModel.productsCach.size() > 1) {
                viewModel.productsCach.removeLast();
                viewModel.setReactionInfo(viewModel.productsCach.getLast());

            }
        }

        pressedTime = System.currentTimeMillis();
    }


}
