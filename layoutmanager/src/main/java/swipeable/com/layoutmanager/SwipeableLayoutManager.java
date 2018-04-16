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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class SwipeableLayoutManager extends RecyclerView.LayoutManager {
  private int maxShowCount = 3;
  private float scaleGap = 0.1f;
  private int transYGap = 0;
  private int angle = 10;
  private long animationDuratuion = 500;

  @Override public RecyclerView.LayoutParams generateDefaultLayoutParams() {
    return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT);
  }

  @Override public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
    detachAndScrapAttachedViews(recycler);
    int itemCount = getItemCount();
    if (itemCount < 1) {
      return;
    }

    int startPosition = Math.min(maxShowCount, itemCount) - 1;
    startPosition = startPosition > 0 ? startPosition : 0;

    for (int position = startPosition; position >= 0; position--) {
      View view = recycler.getViewForPosition(position);
      addView(view);
      measureChildWithMargins(view, 0, 0);
      int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
      int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
      layoutDecorated(view, widthSpace / 2, heightSpace / 2,
          widthSpace / 2 + getDecoratedMeasuredWidth(view),
          heightSpace / 2 + getDecoratedMeasuredHeight(view));
      if (position > 0) {
        view.setScaleX(1 - scaleGap * position);
        if (position < maxShowCount - 1) {
          view.setTranslationY(transYGap * position);
          view.setScaleY(1 - scaleGap * position);
        } else {
          view.setTranslationY(transYGap * (position - 1));
          view.setScaleY(1 - scaleGap * (position - 1));
        }
      }
    }
  }

  public float getScaleGap() {
    return scaleGap;
  }

  public int getMaxShowCount() {
    return maxShowCount;
  }

  public int getTransYGap() {
    return transYGap;
  }

  public int getAngle() {
    return angle;
  }

  public long getAnimationDuratuion() {
    return animationDuratuion;
  }

  /**
   * max views rendered under recycler view
   *
   * @param maxShowCount default value 3
   */
  public SwipeableLayoutManager setMaxShowCount(int maxShowCount) {
    this.maxShowCount = Math.max(maxShowCount, 1);
    return this;
  }

  /**
   * Percentage of scaling views behind top view
   *
   * @param scaleGap min value = 0 max value = 1 default value = 0.1
   */
  public SwipeableLayoutManager setScaleGap(float scaleGap) {
    this.scaleGap = Math.min(Math.max(0,scaleGap), 1);
    return this;
  }

  /**
   * Represents value in used to translate center of views behind top view  and create nice card
   * stack effect
   *
   * @param transYGap default value 0
   */
  public SwipeableLayoutManager setTransYGap(int transYGap) {
    this.transYGap = transYGap;
    return this;
  }

  /**
   * Angle in degres used for rotation of top view while swiping left or right
   * @param angle
   * @return
   */
  public SwipeableLayoutManager setAngle(int angle) {
    this.angle = angle;
    return this;
  }

  /**
   * Animation duration after swiping view
   * @param animationDuratuion
   * @return
   */
  public SwipeableLayoutManager setAnimationDuratuion(long animationDuratuion) {
    this.animationDuratuion = Math.max(1,animationDuratuion);
    return this;
  }




}
