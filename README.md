# SwipeableCards

[![](https://jitpack.io/v/nihad92/SwipeableCards.svg)](https://jitpack.io/#nihad92/SwipeableCards)

Library for creating Tinder-like swipe cards effect using RecyclerView

![Demo gif](https://i.imgur.com/yg0F8V3.gif)

### Features 
Library provides three Helper classes for creating tinder-like swipe card effect with recycler view 
 * SwipeableTouchHelperCallback responsible for creating swipe effect and sending callbacks on card swiped
 * ItemTouchHelper for animating layouts
 * SwipeableLayoutManager used for ordrering views in recycler view
 
### Download

Add to your project gradle

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Download via Gradle:
```
dependencies {
  compile 'com.github.nihad92:SwipeableCards:{latestvesion}'
}
```
 
### Example

First create SwipeableTouchHelperCallback
```
adapter = new OurRecyclerviewAdapter();
SwipeableTouchHelperCallback swipeableTouchHelperCallback = 
  new SwipeableTouchHelperCallback(new OnItemSwiped() {
        //Called after swiping view, place to remove top item from your recyclerview adapter
        @Override public void onItemSwiped() {
          adapter.removeTopItem();
        }
        
        @Override public void onItemSwipedLeft() {

        }

        @Override public void onItemSwipedRight() {

        }
      });
```

Create ItemTouchHelper and attach to RecyclerView

**NOTE: You have to use ItemTouchHelper from swipeable.com.layoutmanager.touchelper**
```
ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeableTouchHelperCallback);
itemTouchHelper.attachToRecyclerView(recyclerView);
```

Add Layout manager and adapter to recycler view

```
recyclerView.setLayoutManager(new SwipeableLayoutManager());
recyclerView.setAdapter(adapter = new ListAdapter());
```

### Customization
Easly change angle of card rotation, deck scaling percentage, translate cards in deck, animation duration or visible cards in deck
```
swipeableLayoutManager
  .setAngle(10)
  .setAnimationDuratuion(500)
  .setMaxShowCount(3)
  .setScaleGap(0.1f)
  .setTransYGap(0);
  ```
  
### Licence
```
Copyright (C) 2018

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

