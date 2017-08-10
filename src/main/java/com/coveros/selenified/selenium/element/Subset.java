package com.coveros.selenified.selenium.element;

public interface Subset {

    public abstract int attribute(String attribute);

    public abstract int classs(String classs);

    public abstract int text(String text);

    public abstract int value(String value);

    public abstract int selectOption(String selectOption);

    public abstract int selectValue(String selectValue);
}