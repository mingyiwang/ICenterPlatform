package com.icenter.el.client.transfer;

import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Category {

    private String userName;
    private int age;
    private double length;
    private String sex;
    private Category category;
    private List<Category> children;
    private Map<String,Category> childMap;
    private Queue<Category> childQueue;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public List<Category> getChildren() {
        return children;
    }
    public void setChildren(List<Category> children) {
        this.children = children;
    }
    public Map<String, Category> getChildMap() {
        return childMap;
    }
    public void setChildMap(Map<String, Category> childMap) {
        this.childMap = childMap;
    }
    public Queue<Category> getChildQueue() {
        return childQueue;
    }
    public void setChildQueue(Queue<Category> childQueue) {
        this.childQueue = childQueue;
    }
    public double getLength() {
        return length;
    }
    public void setLength(double length) {
        this.length = length;
    }

}
