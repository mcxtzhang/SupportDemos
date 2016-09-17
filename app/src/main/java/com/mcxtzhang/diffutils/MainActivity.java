package com.mcxtzhang.diffutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TestBean> mDatas;
    private RecyclerView mRv;
    private DiffAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new DiffAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new TestBean("张旭童1", "Android", R.drawable.pic1));
        mDatas.add(new TestBean("张旭童2", "Java", R.drawable.pic2));
        mDatas.add(new TestBean("张旭童3", "背锅", R.drawable.pic3));
        mDatas.add(new TestBean("张旭童4", "手撕产品", R.drawable.pic4));
        mDatas.add(new TestBean("张旭童5", "手撕测试", R.drawable.pic5));
    }

    /**
     * 模拟刷新操作
     *
     * @param view
     */
    public void onRefresh(View view) {
        try {
            List<TestBean> newDatas = new ArrayList<>();
            for (TestBean bean : mDatas) {
                newDatas.add(bean.clone());//clone一遍旧数据 ，模拟刷新操作
            }
            newDatas.add(new TestBean("赵子龙", "帅", R.drawable.pic6));//模拟新增数据
            newDatas.get(0).setDesc("Android+");
            newDatas.get(0).setPic(R.drawable.pic7);//模拟修改数据
            TestBean testBean = newDatas.get(1);//模拟数据位移
            newDatas.remove(testBean);
            newDatas.add(testBean);

            //新宠
            //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, newDatas), true);
            //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，轻松成为文艺青年
            diffResult.dispatchUpdatesTo(mAdapter);
            //别忘了将新数据给Adapter
            mDatas = newDatas;
            mAdapter.setDatas(mDatas);

            //mAdapter.notifyDataSetChanged();//以前普通青年的我们只能这样，现在我们是文艺青年了，有新宠了

        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}
