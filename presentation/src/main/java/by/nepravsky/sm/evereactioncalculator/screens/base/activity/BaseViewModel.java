package by.nepravsky.sm.evereactioncalculator.screens.base.activity;

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<R extends BaseRouter> extends ViewModel {

    protected R router;

    private CompositeDisposable compositeDisposable;
    public ObservableInt progressBar = new ObservableInt(View.GONE);
    public BaseViewModel() {
        runInject();
    }


    protected abstract void runInject();

    protected void addCompositeDisposable(Disposable disposable){

        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public void showProgressBar(){
        progressBar.set(View.VISIBLE);
    }

    public void disableProgressBar(){
        progressBar.set(View.GONE);
    }

    public void addRouter(R router) {
        this.router = router;
    }

    public void removeRouter() {
        router = null;
    }
}
