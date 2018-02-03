package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class OffsetItemDecoration extends RecyclerView.ItemDecoration {

    private final int mOffset;

    public OffsetItemDecoration(final int offset) {
        mOffset = offset;
    }

    @Override
    public void getItemOffsets(final Rect outRect, final View view, final RecyclerView parent,
                               final RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mOffset, mOffset, mOffset, mOffset);
    }
}
