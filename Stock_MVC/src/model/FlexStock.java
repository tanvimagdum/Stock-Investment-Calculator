package model;

public class FlexStock<D> extends Stock{
    D dataThree;

    public D getD() {
        return dataThree;
    }

    public FlexStock(Object newDataOne, Object newDataTwo, D newDataThree) {
        super(newDataOne, newDataTwo);
        this.dataThree = newDataThree;
    }
}
