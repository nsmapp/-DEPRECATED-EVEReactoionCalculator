package by.nepravsky.sm.evereactioncalculator.screens.base.activity;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable;

    public void addCompositeDisposable(Disposable disposable){

        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null){
            compositeDisposable.clear();
        }
    }
}
