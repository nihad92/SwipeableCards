package swipeable.com.layoutmanager;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper;

public class SwipeableTouchHelperCallback extends ItemTouchHelper.Callback {

  private final OnItemSwiped onItemSwiped;

  public SwipeableTouchHelperCallback(OnItemSwiped onItemSwiped) {
    super();
    this.onItemSwiped = onItemSwiped;
  }

  @Override
  public final int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN);
  }

  public final float getThreshold(RecyclerView.ViewHolder viewHolder) {
    return viewHolder.itemView.getWidth() * 0.9f;
  }

  @Override public final boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
      RecyclerView.ViewHolder target) {
    return false;
  }

  @Override public final void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    if (direction == ItemTouchHelper.LEFT) {
      onItemSwiped.onItemSwipedLeft();
      onItemSwiped.onItemSwiped();
    } else if (direction == ItemTouchHelper.RIGHT) {
      onItemSwiped.onItemSwipedRight();
      onItemSwiped.onItemSwiped();
    }
  }

  @Override public final float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
    return 0.5f;
  }

  @Override
  public final long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx,
      float animateDy) {
    return ((SwipeableLayoutManager)recyclerView.getLayoutManager()).getAnimationDuratuion();
  }

  @Override
  public final void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
      float dX, float dY, int actionState, boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    double swipValue = Math.sqrt(dX * dX + dY * dY);
    double fraction = swipValue / getThreshold(viewHolder);
    if (fraction > 1) {
      fraction = 1;
    }
    SwipeableLayoutManager swipeableLayoutManager =
        (SwipeableLayoutManager) recyclerView.getLayoutManager();

    int childCount = recyclerView.getChildCount();
    if (viewHolder.getAdapterPosition() == 0) {
      viewHolder.itemView.setRotation(swipeableLayoutManager.getAngle() * (dX/recyclerView.getMeasuredWidth()));
    }

    for (int i = 0; i < childCount; i++) {
      View child = recyclerView.getChildAt(i);
      int level = childCount - i - 1;

      if (level > 0) {
        child.setScaleX((float) (1 - swipeableLayoutManager.getScaleGap() * level
            + fraction * swipeableLayoutManager.getScaleGap()));

        if (level < swipeableLayoutManager.getMaxShowCount() - 1) {
          child.setScaleY((float) (1 - swipeableLayoutManager.getScaleGap() * level
              + fraction * swipeableLayoutManager.getScaleGap()));
          child.setTranslationY((float) (swipeableLayoutManager.getTransYGap() * level
              - fraction * swipeableLayoutManager.getTransYGap()));
        }
      }
    }
  }

  @Override public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    super.clearView(recyclerView, viewHolder);
    viewHolder.itemView.setRotation(0);
  }
}
