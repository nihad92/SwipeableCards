package swipeable.com.layoutmanager;

import android.support.v7.widget.RecyclerView;

public interface OnItemSwiped {
  void onItemSwiped();
  void onItemSwipedLeft();
  void onItemSwipedRight();
}
