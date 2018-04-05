package com.swipeable;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListItem> {
  private List<Integer> items = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

  @NonNull @Override public ListItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ListItem(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.dummy_list_item, parent, false));
  }

  @Override public void onBindViewHolder(@NonNull ListItem holder, int position) {
    holder.bind(items.get(position).intValue());
  }

  @Override public int getItemCount() {
    return items.size();
  }

  public List<Integer> getItems() {
    return items;
  }

  public void removeTopItem() {
    items.remove(0);
    notifyDataSetChanged();
  }
}
