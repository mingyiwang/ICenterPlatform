package com.icenter.el.client.rest.transfer;

import com.icenter.core.client.rest.convert.JSONConvertible;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Test2 testValue;
    private List<Test2> testListValue;
    private Map<Test2, Integer> mapValue;
    private Map<String, String> mapStringValue;
    private Integer[] emptyValue = new Integer[2];
    private int[] testInt = new int[0];
    private double[] testDouble;
    private boolean[] testBoolean;
    private float[] testFloat;
    private long[] testLong;
    private short[] testShort;
    private byte[] testByte;
    private String[] testString;

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

    public Map<Test2, Integer> getMapValue() {
        return mapValue;
    }

    public void setMapValue(Map<Test2, Integer> mapValue) {
        this.mapValue = mapValue;
    }

    public Integer[] getEmptyValue() {
        return emptyValue;
    }

    public void setEmptyValue(Integer[] emptyValue) {
        this.emptyValue = emptyValue;
    }

    public int[] getTestInt() {
        return testInt;
    }

    public void setTestInt(int[] testInt) {
        this.testInt = testInt;
    }

    public double[] getTestDouble() {
        return testDouble;
    }

    public void setTestDouble(double[] testDouble) {
        this.testDouble = testDouble;
    }

    public boolean[] getTestBoolean() {
        return testBoolean;
    }

    public void setTestBoolean(boolean[] testBoolean) {
        this.testBoolean = testBoolean;
    }

    public float[] getTestFloat() {
        return testFloat;
    }

    public void setTestFloat(float[] testFloat) {
        this.testFloat = testFloat;
    }

    public long[] getTestLong() {
        return testLong;
    }

    public void setTestLong(long[] testLong) {
        this.testLong = testLong;
    }

    public short[] getTestShort() {
        return testShort;
    }

    public void setTestShort(short[] testShort) {
        this.testShort = testShort;
    }

    public byte[] getTestByte() {
        return testByte;
    }

    public void setTestByte(byte[] testByte) {
        this.testByte = testByte;
    }

    public String[] getTestString() {
        return testString;
    }

    public void setTestString(String[] testString) {
        this.testString = testString;
    }

    public Map<String, String> getMapStringValue() {
        return mapStringValue;
    }

    public void setMapStringValue(Map<String, String> mapStringValue) {
        this.mapStringValue = mapStringValue;
    }


}
