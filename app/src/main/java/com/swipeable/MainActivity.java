package com.swipeable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import swipeable.com.layoutmanager.OnItemSwiped;
import swipeable.com.layoutmanager.SwipeableLayoutManager;
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback;
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper;

public class MainActivity extends AppCompatActivity {

  private ListAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    adapter = new ListAdapter();
    final RecyclerView recyclerView = findViewById(R.id.recycler_view);
    SwipeableTouchHelperCallback swipeableTouchHelperCallback =
        new SwipeableTouchHelperCallback(new OnItemSwiped() {
          @Override public void onItemSwiped() {
            adapter.removeTopItem();
          }

          @Override public void onItemSwipedLeft() {
            Log.e("SWIPE", "LEFT");
          }

          @Override public void onItemSwipedRight() {
            Log.e("SWIPE", "RIGHT");
          }

          @Override public void onItemSwipedUp() {
            Log.e("SWIPE", "UP");
          }

          @Override public void onItemSwipedDown() {
            Log.e("SWIPE", "DOWN");
          }
        }) {
          @Override
          public int getAllowedSwipeDirectionsMovementFlags(RecyclerView.ViewHolder viewHolder) {
            return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
          }
        };
    final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
    itemTouchHelper.attachToRecyclerView(recyclerView);
    recyclerView.setLayoutManager(new SwipeableLayoutManager().setAngle(10)
        .setAnimationDuratuion(450)
        .setMaxShowCount(3)
        .setScaleGap(0.1f)
        .setTransYGap(0));
    recyclerView.setAdapter(adapter = new ListAdapter());

    AppCompatButton button = findViewById(R.id.swipe);
    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        itemTouchHelper.swipe(recyclerView.findViewHolderForAdapterPosition(0), ItemTouchHelper.DOWN);
      }
    });
  }
}
