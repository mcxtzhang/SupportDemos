package com.mcxtzhang.diffutils.sortedlist;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/29.
 */

public class SortedListCallback extends SortedListAdapterCallback<TestSortBean> {
    /**
     * Creates a {@link SortedList.Callback} that will forward data change events to the provided
     * Adapter.
     *
     * @param adapter The Adapter instance which should receive events from the SortedList.
     */
    public SortedListCallback(RecyclerView.Adapter adapter) {
        super(adapter);
    }

    @Override
    public int compare(TestSortBean o1, TestSortBean o2) {
        return o1.getId() - o2.getId();
    }

    @Override
    public boolean areItemsTheSame(TestSortBean item1, TestSortBean item2) {
        return item1.getId() == item2.getId();
    }

    @Override
    public boolean areContentsTheSame(TestSortBean oldItem, TestSortBean newItem) {
        //默认相同 有一个不同就是不同
        if (oldItem.getId() != newItem.getId()) {
            return false;
        }
        if (oldItem.getName().equals(newItem.getName())) {
            return false;
        }
        if (oldItem.getIcon() != newItem.getIcon()) {
            return false;
        }
        return true;
    }


}
