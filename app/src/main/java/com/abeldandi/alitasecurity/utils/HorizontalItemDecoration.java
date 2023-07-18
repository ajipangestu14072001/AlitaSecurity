package com.abeldandi.alitasecurity.utils;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public HorizontalItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position

        if (position != 0)
            outRect.left = space;

        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;
    }
}

