package nest.pdog.com.ramotion.echelon;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class EchelonLayoutManager extends RecyclerView.LayoutManager {

    private static final String TAG = "EchelonLayoutManager";

    private int mItemViewHeight;
    private int mScrollOffset;

    public EchelonLayoutManager(Context context) {
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
            RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0 || state.isPreLayout()) return;
        removeAndRecycleAllViews(recycler);

        mItemViewHeight = (int) (getHorizontalSpace() * 0.46f);
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset), getItemCount() * mItemViewHeight);

        layoutChild(recycler);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int pendingScrollOffset = mScrollOffset + dy;
        mScrollOffset = Math.min(Math.max(mItemViewHeight, mScrollOffset + dy), getItemCount() * mItemViewHeight);
        layoutChild(recycler);
        return mScrollOffset - pendingScrollOffset + dy;
    }


    @Override
    public boolean canScrollVertically() {
        return true;
    }


    private void layoutChild(RecyclerView.Recycler recycler) {
        if (getItemCount() == 0) return;
        int bottomItemPosition = (int) Math.floor(mScrollOffset / mItemViewHeight);
        int remainSpace = getVerticalSpace() - mItemViewHeight;

        int bottomItemVisibleHeight = mScrollOffset % mItemViewHeight;
        final float offsetPercentRelativeToItemView = bottomItemVisibleHeight * 1.0f / mItemViewHeight;

        ArrayList<ItemViewInfo> layoutInfos = new ArrayList<>();
        for (int i = bottomItemPosition - 1, j = 1; i >= 0; i--, j++) {
            double maxOffset = (getVerticalSpace() - mItemViewHeight) / 2 * Math.pow(0.8, j);
            int start = (int) (remainSpace - offsetPercentRelativeToItemView * maxOffset);
            float positonOffset = offsetPercentRelativeToItemView;
            float layoutPercent = start * 1.0f / getVerticalSpace();
            ItemViewInfo info = new ItemViewInfo(start, 1, positonOffset, layoutPercent);
            layoutInfos.add(0, info);
            remainSpace = (int) (remainSpace - maxOffset);
            if (remainSpace <= 0) {
                info.setTop((int) (remainSpace + maxOffset));
                info.setPositionOffset(0);
                info.setLayoutPercent(info.getTop() / getVerticalSpace());
                break;
            }
        }

        if (bottomItemPosition < getItemCount()) {
            final int start = getVerticalSpace() - bottomItemVisibleHeight;
            layoutInfos.add(new ItemViewInfo(start, 1.0f, bottomItemVisibleHeight * 1.0f / mItemViewHeight, start * 1.0f / getVerticalSpace())
                .setIsBottom());
        } else {
            bottomItemPosition = bottomItemPosition - 1;//99
        }

        int layoutCount = layoutInfos.size();
        final int startPos = bottomItemPosition - (layoutCount - 1);
        final int endPos = bottomItemPosition;
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            View childView = getChildAt(i);
            int pos = getPosition(childView);
            if (pos > endPos || pos < startPos) {
                removeAndRecycleView(childView, recycler);
            }
        }

        detachAndScrapAttachedViews(recycler);
    }


    /**
     * 获取RecyclerView的显示高度
     */
    public int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    /**
     * 获取RecyclerView的显示宽度
     */
    public int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

}

