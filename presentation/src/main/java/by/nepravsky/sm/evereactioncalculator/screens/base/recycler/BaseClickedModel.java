package by.nepravsky.sm.evereactioncalculator.screens.base.recycler;


import by.nepravsky.sm.domain.entity.old.DomainEntity;

public class BaseClickedModel<E extends DomainEntity> {

    private E entity;


    public BaseClickedModel(E entity) {
        this.entity = entity;

    }

    public E getEntity() {
        return entity;
    }

}
