package by.nepravsky.sm.evereactioncalculator.screens.main;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import by.nepravsky.sm.evereactioncalculator.R;
import by.nepravsky.sm.evereactioncalculator.screens.base.activity.BaseMVVMActivity;
import by.nepravsky.sm.evereactioncalculator.databinding.ActivityMainBinding;


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
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.reactions));

        reactions.setAdapter(reactionAdapter);

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
                viewModel.setAllReactionInfo(viewModel.productsCach.getLast());

            }
        }

        pressedTime = System.currentTimeMillis();
    }


}
