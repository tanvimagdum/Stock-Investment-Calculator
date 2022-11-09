package model;

public class FlexStock<S,F,D> extends Stock<S,F>{
    D dataThree;

    public D getD() {
        return dataThree;
    }

    public FlexStock(S newDataOne, F newDataTwo, D newDataThree) {
        super(newDataOne, newDataTwo);
        this.dataThree = newDataThree;
    }
}
