/*
 * Copyright (C) 2018
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    return makeMovementFlags(0,
        viewHolder.getAdapterPosition() != 0 ? 0 : getAllowedDirectionsMovementFlags(viewHolder));
  }

  public int getAllowedSwipeDirectionsMovementFlags() {
    return ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
  }

  public int getAllowedDirectionsMovementFlags(RecyclerView.ViewHolder viewHolder) {
    return getAllowedDirectionsMovementFlags();
  }

  public int getAllowedDirectionsMovementFlags() {
    return ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
  }

  public final float getThreshold(RecyclerView.ViewHolder viewHolder) {
    return viewHolder.itemView.getWidth() * 0.9f;
  }

  @Override
  public final boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
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

  @Override public final long getAnimationDuration(RecyclerView recyclerView, int animationType,
      float animateDx, float animateDy) {
    return ((SwipeableLayoutManager) recyclerView.getLayoutManager()).getAnimationDuratuion();
  }

  @Override public final void onChildDraw(Canvas c, RecyclerView recyclerView,
      RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
      boolean isCurrentlyActive) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    double swipValue = Math.sqrt(dX * dX + dY * dY);
    double fraction = swipValue / getThreshold(viewHolder);
    fraction = Math.min(1, fraction);

    if (viewHolder instanceof OnItemSwipePercentageListener) {
      ((OnItemSwipePercentageListener)viewHolder).onItemSwipePercentage(
          Math.max(-1, Math.min(1, dX / recyclerView.getMeasuredWidth())));
    }

    SwipeableLayoutManager swipeableLayoutManager =
        (SwipeableLayoutManager) recyclerView.getLayoutManager();

    int childCount = recyclerView.getChildCount();
    if (viewHolder.getAdapterPosition() == 0) {
      viewHolder.itemView.setRotation(
          swipeableLayoutManager.getAngle() * (dX / recyclerView.getMeasuredWidth()));
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

  @Override
  public final void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    super.clearView(recyclerView, viewHolder);
    viewHolder.itemView.setRotation(0);
  }
}
