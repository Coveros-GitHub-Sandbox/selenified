package com.coveros.selenified.selenium.element;

import com.coveros.selenified.output.OutputFile;

public class Subset {

    // this will be the name of the file we write all commands out to
    protected OutputFile file;

    // what element are we trying to interact with on the page
    protected Element element;

    // a class used to determine if elements are selects or tables
    protected Helper helper;

    // constants
    protected static final String EXPECTED = "Expected to find ";
    protected static final String CLASS = "class";

    protected static final String NOTINPUT = " is not an input on the page";

    protected static final String VALUE = " has the value of <b>";
    protected static final String TEXT = " has the text of <b>";
    protected static final String HASVALUE = " contains the value of <b>";
    protected static final String HASTEXT = " contains the text of <b>";
    protected static final String ONLYVALUE = ", only the values <b>";
    protected static final String CLASSVALUE = " has a class value of <b>";
}