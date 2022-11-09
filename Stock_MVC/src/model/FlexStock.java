package model;

public class FlexStock<T> extends Stock{
    T dataThree;

    public T getT() {
        return dataThree;
    }

    public FlexStock(Object newDataOne, Object newDataTwo, T newDataThree) {
        super(newDataOne, newDataTwo);
        this.dataThree = newDataThree;
    }
}
