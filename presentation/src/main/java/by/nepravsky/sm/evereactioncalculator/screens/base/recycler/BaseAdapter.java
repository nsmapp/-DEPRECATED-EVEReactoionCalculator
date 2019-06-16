package by.nepravsky.sm.evereactioncalculator.screens.base.recycler;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import by.nepravsky.sm.domain.entity.DomainEntity;
import io.reactivex.subjects.PublishSubject;

public abstract class BaseAdapter<E extends DomainEntity,
        VM extends BaseViewModel<E>> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<E> entityList = new ArrayList<>();

    public void setEntity(List<E> entityList) {

        this.entityList = entityList;
        notifyDataSetChanged();
    }

    private boolean isClickedEnabled = true;
    private  PublishSubject<BaseClickedModel<E>> clickEventSubject = PublishSubject.create();

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder<E, VM, ?> baseHolder = (BaseViewHolder<E, VM, ?>) holder;
        baseHolder.bind(entityList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull final RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        if (isClickedEnabled){
            final BaseViewHolder<E, VM, ?> baseHolder = (BaseViewHolder<E, VM, ?>) holder;
            baseHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = baseHolder.getAdapterPosition();
                    if (position >= 0){
                        clickEventSubject.onNext(new BaseClickedModel<E>((E) entityList.get(position)));
                        baseHolder.getViewModel().onItemClick();
                    }
                }
            });
        }
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if(isClickedEnabled){
            holder.itemView.setOnClickListener(null);
        }
    }

    public void setClickedEnabled(boolean clickedEnabled) {
        isClickedEnabled = clickedEnabled;
    }


}

