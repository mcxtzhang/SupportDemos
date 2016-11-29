package com.mcxtzhang.diffutils.sortedlist;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mcxtzhang.diffutils.R;

import java.util.ArrayList;
import java.util.List;

public class SortedListActivity extends AppCompatActivity {

    private SortedList<TestSortBean> mDatas;
    private RecyclerView mRv;
    private SortedAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_list);

        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SortedAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);


        initData();


        mDatas.beginBatchedUpdates();
        mAdapter.setDatas(mDatas);
        mDatas.endBatchedUpdates();
    }

    private void initData() {
        mDatas = new SortedList<>(TestSortBean.class, new SortedListCallback(mAdapter));
        mDatas.add(new TestSortBean(10, "Android", R.drawable.pic1));
        mDatas.add(new TestSortBean(10, "Android", R.drawable.pic1));
        mDatas.add(new TestSortBean(2, "Java", R.drawable.pic2));
        mDatas.add(new TestSortBean(30, "背锅", R.drawable.pic3));
        mDatas.add(new TestSortBean(4, "手撕产品", R.drawable.pic4));
        mDatas.add(new TestSortBean(50, "手撕测试", R.drawable.pic5));
    }

    /**
     * 模拟刷新操作
     *
     * @param view
     */
    public void onRefresh(View view) {

        //add 内部会自动调用  mCallback.onInserted(index, 1); ->notifyItemRangeInserted(index,1);
        //也就是说我们add一次 它就刷新一次，没有batch操作，有点low

        /*mDatas.add(new TestSortBean(26, "帅", R.drawable.pic6));//模拟新增数据
        mDatas.add(new TestSortBean(27, "帅", R.drawable.pic6));//模拟新增数据*/

        // 如果想batch 就必须用addAll()操作，感觉这算一个限制。
        //addAll 也分两种
        //第一种 以可变参数addAll
        //mDatas.addAll(new TestSortBean(26, "帅", R.drawable.pic6),new TestSortBean(27, "帅", R.drawable.pic6));
        //第二种 集合形式
/*
        List<TestSortBean> temp = new ArrayList<>();
        temp.add(new TestSortBean(26, "帅", R.drawable.pic6));
        temp.add(new TestSortBean(28, "帅", R.drawable.pic6));
        mDatas.addAll(temp);
*/


        List<TestSortBean> newDatas = new ArrayList<>();
        for (int i = 0; i < mDatas.size(); i++) {
            try {
                newDatas.add(mDatas.get(i).clone());//clone一遍旧数据 ，模拟刷新操作
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        newDatas.add(new TestSortBean(29, "帅", R.drawable.pic6));//模拟新增数据
        newDatas.get(0).setName("Android+");
        newDatas.get(0).setIcon(R.drawable.pic7);//模拟修改数据
        TestSortBean testBean = newDatas.get(1);//模拟数据位移
        newDatas.remove(testBean);
        newDatas.add(testBean);
        mDatas.clear();
        mDatas.addAll(newDatas);



/*        new Thread(new Runnable() {
            @Override
            public void run() {
                //每次add都会计算一次 放在子线程中
                //放在子线程中计算
                mDatas.add(new TestSortBean(26, "帅", R.drawable.pic6));//模拟新增数据
                mDatas.get(0).setName("Android+");
                mDatas.get(0).setIcon(R.drawable.pic7);//模拟修改数据
                TestSortBean testBean = mDatas.get(1);//模拟数据位移
                mDatas.remove(testBean);
                mDatas.add(testBean);
                Message message = mHandler.obtainMessage(H_CODE_UPDATE);
                message.sendToTarget();
            }
        }).start();*/
        //mAdapter.notifyDataSetChanged();//以前普通青年的我们只能这样，现在我们是文艺青年了，有新宠了

    }

    private static final int H_CODE_UPDATE = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case H_CODE_UPDATE:
                    mDatas.beginBatchedUpdates();
                    mAdapter.setDatas(mDatas);
                    mDatas.endBatchedUpdates();
                    break;
            }
        }
    };
}
