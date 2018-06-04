package liquidate.morn.com.ramotion.cardslider

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import timber.log.Timber


class MyLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        // 先把所有的View先从RecyclerView中detach掉，然后标记为"Scrap"状态，表示这些View处于可被重用状态(非显示中)。
        // 实际就是把View放到了Recycler中的一个集合中。
        detachAndScrapAttachedViews(recycler);
        calculateChildrenSite(recycler!!);
        Timber.d("onlayout")
    }

    var totalHeight = 0

    private fun calculateChildrenSite(recycler: RecyclerView.Recycler) {

        totalHeight = 0;
        Timber.d("totalHeight = ${totalHeight}")
        for (i  in 0 until  itemCount){
            // 遍历Recycler中保存的View取出来
            val view = recycler.getViewForPosition(i);
            addView(view); // 因为刚刚进行了detach操作，所以现在可以重新添加
            measureChildWithMargins(view, 0, 0); // 通知测量view的margin值
            var width = getDecoratedMeasuredWidth (view); // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
            var height = getDecoratedMeasuredHeight (view);

            var mTmpRect = Rect();
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
            calculateItemDecorationsForChild(view, mTmpRect);

            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
            //包括ItemDecorator设置的距离。
            layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
            totalHeight += height;
        }
    }


}
