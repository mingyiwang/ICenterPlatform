package com.icenter.el.client.transfer;

import com.icenter.core.client.rest.convert.JSONConvertible;
import java.util.ArrayList;
import java.util.List;

public class Test implements JSONConvertible {

    private int integer = 0;
    private boolean booleanValue = false;
    private String stringValue = "Empty";
    private double doubleValue = 0d;
    private long longValue     = 0l;
    private byte byteValue     = 1;
    private char charValue     = 'a';
    private float floatValue   = 1f;
    private short shortValue   = 0;
    private Test2 testValue    = new Test2();
    private List<Test2> testListValue = new ArrayList<Test2>();

    public int getInteger() {
        return integer;
    }
    public void setInteger(int integer) {
        this.integer = integer;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }
    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public long getLongValue() {
        return longValue;
    }

    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }

    public byte getByteValue() {
        return byteValue;
    }

    public void setByteValue(byte byteValue) {
        this.byteValue = byteValue;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }

    public short getShortValue() {
        return shortValue;
    }

    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

    public Test2 getTestValue() {
        return testValue;
    }

    public void setTestValue(Test2 testValue) {
        this.testValue = testValue;
    }

    public List<Test2> getTestListValue() {
        return testListValue;
    }

    public void setTestListValue(List<Test2> testListValue) {
        this.testListValue = testListValue;
    }

}
