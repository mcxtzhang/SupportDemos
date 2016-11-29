package com.mcxtzhang.diffutils.sortedlist;

import android.content.Context;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.diffutils.R;

/**
 * 介绍：
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/29.
 */

public class SortedAdapter extends RecyclerView.Adapter<SortedAdapter.VH> {
    private final static String TAG = "zxt";
    private SortedList<TestSortBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public SortedAdapter(Context mContext,SortedList<TestSortBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(SortedList<TestSortBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public SortedAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SortedAdapter.VH(mInflater.inflate(R.layout.item_diff, parent, false));
    }

    @Override
    public void onBindViewHolder(final SortedAdapter.VH holder, final int position) {
        TestSortBean bean = mDatas.get(position);
        holder.tv1.setText(bean.getName());
        holder.tv2.setText(bean.getId() + "");
        holder.iv.setImageResource(bean.getIcon());
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    class VH extends RecyclerView.ViewHolder {
        TextView tv1, tv2;
        ImageView iv;

        public VH(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }
}

