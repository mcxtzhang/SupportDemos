package com.mcxtzhang.diffutils.sortedlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.SortedList;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mcxtzhang.diffutils.R;

/**
 * SortedListDemo
 */
public class SortedListActivity extends AppCompatActivity {
    /**
     * 数据源替换为SortedList，
     * 以前可能会用ArrayList。
     */
    private SortedList<TestSortBean> mDatas;
    private RecyclerView mRv;
    private SortedAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_list);

        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        //★以前构建Adapter时，一般会将data也一起传入，现在有变化
        mAdapter = new SortedAdapter(this, null);
        mRv.setAdapter(mAdapter);


        initData();


        //mDatas.beginBatchedUpdates();
        mAdapter.setDatas(mDatas);
        //mDatas.endBatchedUpdates();
    }

    private void initData() {
        //★SortedList初始化的时候，要将Adapter传进来。所以先构建Adapter，再构建SortedList
        mDatas = new SortedList<>(TestSortBean.class, new SortedListCallback(mAdapter));
        mDatas.add(new TestSortBean(10, "Android", R.drawable.pic1));
        //★注意这里有一个重复的字段 会自动去重的。
        mDatas.add(new TestSortBean(10, "Android重复", R.drawable.pic1));
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

        mDatas.add(new TestSortBean(26, "温油对待产品", R.drawable.pic6));//模拟新增
        mDatas.add(new TestSortBean(12, "小马可以来点赞了", R.drawable.pic6));//模拟新增
        mDatas.add(new TestSortBean(2, "Python", R.drawable.pic6));//add进去 重复的会自动修改

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


        //刷新时，服务器给我们的一般都是一个List
        //直接addAll 要先clear， 会闪屏
/*        List<TestSortBean> newDatas = new ArrayList<>();
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
        mDatas.addAll(newDatas);*/


        new Thread(new Runnable() {
            @Override
            public void run() {
                //每次add都会计算一次 想放在子线程中
                //然而这是肯定不行的，上文提过，每次add 会自动 mAdapter.notifyItemRangeInserted(position, count);
                //这一点就不如DiffUtil啦。
                //android.view.ViewRootImpl$CalledFromWrongThreadException: Only the original thread that created a view hierarchy can touch its views.
                /*mDatas.add(new TestSortBean(26, "帅", R.drawable.pic6));//模拟新增数据
                mDatas.add(new TestSortBean(27, "帅", R.drawable.pic6));//模拟新增数据*/
            }
        }).start();
    }
}
