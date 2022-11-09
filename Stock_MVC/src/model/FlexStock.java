package model;

public class FlexStock<D> extends Stock{
    D dataThree;

    public D getT() {
        return dataThree;
    }

    public FlexStock(Object newDataOne, Object newDataTwo, D newDataThree) {
        super(newDataOne, newDataTwo);
        this.dataThree = newDataThree;
    }
}
