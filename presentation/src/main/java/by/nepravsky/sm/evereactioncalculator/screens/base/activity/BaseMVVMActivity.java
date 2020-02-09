package by.nepravsky.sm.evereactioncalculator.screens.base.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import by.nepravsky.sm.evereactioncalculator.BR;

public abstract class BaseMVVMActivity<
        VM extends BaseViewModel,
        B extends ViewDataBinding,
        R extends BaseRouter> extends BaseActivity{

    protected VM viewModel;
    public B binding;
    protected R router;

    public abstract VM provideViewModel();

    public abstract int provideLayoutId();

    public abstract R provideRouter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = provideViewModel();
        binding = DataBindingUtil.setContentView(this, provideLayoutId());
        binding.setVariable(BR.viewModel, viewModel);

        router = provideRouter();

    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.addRouter(router);
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewModel.removeRouter();
    }


}
