package com.helha.saintseya;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Joueur {
    private final DoubleProperty x = new SimpleDoubleProperty(500);
    private final DoubleProperty y = new SimpleDoubleProperty(500);

    public void setPosition(double newX, double newY) {
        this.x.set(newX);
        this.y.set(newY);
    }

    public DoubleProperty xProperty() { return x; }
    public DoubleProperty yProperty() { return y; }
}
