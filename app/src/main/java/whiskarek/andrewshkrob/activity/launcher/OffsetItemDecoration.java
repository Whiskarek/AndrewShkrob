package whiskarek.andrewshkrob.activity.launcher;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OffsetItemDecoration extends RecyclerView.ItemDecoration {

    private final int mOffset;

    public OffsetItemDecoration(int offset) {
        mOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mOffset, mOffset, mOffset, mOffset);
    }
}
