package nest.pdog.com.ramotion.cardslider

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import timber.log.Timber


class MyLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    var totalHeight: Int = 0

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        detachAndScrapAttachedViews(recycler)


        totalHeight = 0;
        (0 until itemCount).forEach {
            var view = recycler!!.getViewForPosition(it);
            addView(view);
            //我们自己指定ItemView的尺寸。
            measureChildWithMargins(view, 0, 0);
            var width = getDecoratedMeasuredWidth(view);
            var height = getDecoratedMeasuredHeight(view);
            var mTmpRect = Rect();
            calculateItemDecorationsForChild(view, mTmpRect);

            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
            //包括ItemDecorator设置的距离。
            layoutDecorated(view, 0, totalHeight, width, totalHeight + height);
            totalHeight += height;
        }
    }

    override fun canScrollVertically(): Boolean {
        return true
    }

    var verticalScrollOffset = 0

    override fun scrollVerticallyBy(dy: Int, recycler: RecyclerView.Recycler?, state: RecyclerView.State?): Int {
        Timber.d("itemCount = ${itemCount}")
        Timber.d("childCount = ${childCount}")

        //列表向下滚动dy为正，列表向上滚动dy为负，这点与Android坐标系保持一致。
        //实际要滑动的距离
        var travel = dy;

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {
            travel = -verticalScrollOffset;
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;

        // 调用该方法通知view在y方向上移动指定距离
        offsetChildrenVertical(-travel);

        return travel;
    }

    fun getVerticalSpace(): Int {
        return height - paddingBottom - paddingTop
    }
}
