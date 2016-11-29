package com.mcxtzhang.diffutils.sortedlist;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/29.
 */

public class TestSortBean implements Cloneable{
    private int id;
    private String name;
    private int icon;

    //仅写DEMO 用 实现克隆方法
    @Override
    public TestSortBean clone() throws CloneNotSupportedException {
        TestSortBean bean = null;
        try {
            bean = (TestSortBean) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public String toString() {
        return "TestSortBean{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public TestSortBean(int id, String name, int icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public TestSortBean setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestSortBean setName(String name) {
        this.name = name;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    public TestSortBean setIcon(int icon) {
        this.icon = icon;
        return this;
    }
}
