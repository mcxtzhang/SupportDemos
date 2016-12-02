package com.mcxtzhang.diffutils.sortedlist;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

/**
 * 介绍：比较规则Callback。
 * 和DiffUtil.Callback。写法套路一毛一样。
 * 而且比DiffUtil.Callback简单。
 * 因为不用传数据集进来，每次直接给你Item比较。
 *
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

    /**
     * 把它当成equals 方法就好
     */
    @Override
    public int compare(TestSortBean o1, TestSortBean o2) {
        return o1.getId() - o2.getId();
    }

    /**
     * 和DiffUtil方法一致，不再赘述
     */
    @Override
    public boolean areItemsTheSame(TestSortBean item1, TestSortBean item2) {
        return item1.getId() == item2.getId();
    }
    /**
     * 和DiffUtil方法一致，不再赘述
     */
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
