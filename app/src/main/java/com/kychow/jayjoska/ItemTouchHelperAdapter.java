package com.kychow.jayjoska;

/**
 * Created by Karena Chow on 8/1/18.
 */
public interface ItemTouchHelperAdapter {

    boolean onItemMove(int fromPosition, int toPosition);
    void onItemDismiss(int position);
}
