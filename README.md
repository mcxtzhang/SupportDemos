博文传送门：
http://blog.csdn.net/zxt0601/article/details/52562770
---
DiffUtil是support-v7:24.2.0中的新工具类，它用来比较两个数据集，寻找出旧数据集-》新数据集的最小变化量。
说到数据集，相信大家知道它是和谁相关的了，就是我的最爱，RecyclerView。
就我使用的这几天来看，它**最大的用处就是在RecyclerView刷新时**，不再无脑`mAdapter.notifyDataSetChanged()`。
以前无脑`mAdapter.notifyDataSetChanged()`有两个缺点：

 1. 不会触发RecyclerView的动画（删除、新增、位移、change动画）
 2. 性能较低，毕竟是无脑的刷新了一遍整个RecyclerView , 极端情况下：新老数据集一模一样，效率是最低的。

使用DiffUtil后，改为如下代码：
```
DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallBack(mDatas, newDatas), true);
diffResult.dispatchUpdatesTo(mAdapter);
```
它会自动计算新老数据集的差异，并根据差异情况，自动调用以下四个方法

```
adapter.notifyItemRangeInserted(position, count);
adapter.notifyItemRangeRemoved(position, count);
adapter.notifyItemMoved(fromPosition, toPosition);
adapter.notifyItemRangeChanged(position, count, payload);

```
显然，这个四个方法在执行时都是伴有RecyclerView的动画的，且都是定向刷新方法，刷新效率蹭蹭的上升了。
老规矩，先上图，

图一是无脑`mAdapter.notifyDataSetChanged()`的效果图，可以看到刷新交互很生硬，Item突然的出现在某个位置：
![这里写图片描述](http://img.blog.csdn.net/20160917133116779)



图二是使用DiffUtils的效果图，最明显的是有插入、移动Item的动画：
![这里写图片描述](http://img.blog.csdn.net/20160917133139138)

转成GIF有些渣，下载文末Demo运行效果更佳哦。


##本文将包含且不仅包含以下内容：
1 先介绍DiffUtil的简单用法，实现刷新时的“**增量更新**”效果。（“增量更新”是我自己的叫法）

2 DiffUtil的高级用法，在某项Item只有内容(data)变化，位置(position)未变化时，完成**部分更新**（官方称之为**Partial bind**，部分绑定）。

3 了解到 RecyclerView.Adapter还有`public void onBindViewHolder(VH holder, int position, List<Object> payloads) `方法，并掌握它。

4 在子线程中计算DiffResult，在主线程中刷新RecyclerView。

5 少部分人不喜欢的**notifyItemChanged()导致Item白光一闪**的动画 如何去除。

6 DiffUtil部分类、方法 官方注释的汉化
