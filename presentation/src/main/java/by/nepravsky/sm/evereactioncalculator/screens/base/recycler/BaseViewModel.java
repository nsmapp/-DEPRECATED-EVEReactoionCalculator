package by.nepravsky.sm.evereactioncalculator.screens.base.recycler;

import androidx.lifecycle.ViewModel;
import by.nepravsky.sm.domain.entity.old.DomainEntity;


public abstract class BaseViewModel<E extends DomainEntity> extends ViewModel {

    private E entity;

    public abstract void setEntity(E entity, int position);

    public abstract void onItemClick();

    protected E getEntity() {
        return entity;
    }

    public void setEntity(E entity) {
        this.entity = entity;
    }
}
