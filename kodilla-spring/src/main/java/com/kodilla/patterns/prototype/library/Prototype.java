package com.kodilla.patterns.prototype.library;

public abstract class Prototype<T> implements Cloneable {
    @Override
    public T clone() throws CloneNotSupportedException {
        return (T) super.clone();
    }
}
