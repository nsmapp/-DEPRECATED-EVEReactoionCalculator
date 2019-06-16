package by.nepravsky.sm.evereactioncalculator.screens.main.recycler;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import by.nepravsky.sm.domain.entity.FormulaComponent;
import by.nepravsky.sm.evereactioncalculator.databinding.ItemProductBinding;
import by.nepravsky.sm.evereactioncalculator.screens.base.recycler.BaseViewHolder;

public class ReactionsViewHolder extends BaseViewHolder<FormulaComponent, ReactionsViewModel, ItemProductBinding> {


    public ReactionsViewHolder(ItemProductBinding binding, ReactionsViewModel viewModel) {
        super(binding, viewModel);
    }

    public static ReactionsViewHolder init(ViewGroup wg, ReactionsViewModel vm){

        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(wg.getContext()), wg, false);
        return new ReactionsViewHolder(binding, vm);
    }
}
