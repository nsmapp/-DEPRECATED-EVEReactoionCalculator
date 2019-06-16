package by.nepravsky.sm.evereactioncalculator.screens.base.recycler;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import by.nepravsky.sm.evereactioncalculator.BR;
import by.nepravsky.sm.domain.entity.DomainEntity;


public class BaseViewHolder<E extends DomainEntity,
        VM extends BaseViewModel<E>,
        B extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private E entity;
    private B binding;
    private VM viewModel;
    private int position;

    public BaseViewHolder(B binding, VM viewModel) {
        super(binding.getRoot());
        this.binding = binding;
        this.viewModel = viewModel;
        binding.setVariable(BR.viewModel, viewModel);
    }

    public void bind(E entity, int position) {
        viewModel.setEntity(entity , position);
        binding.executePendingBindings();
        this.position = position;
    }

    public VM getViewModel() {
        return viewModel;
    }

}
