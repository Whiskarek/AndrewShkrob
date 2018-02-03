package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.view.SquareView;

public class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {
        private final View mItemView;
        private final SquareView mItemColorView;
        private final TextView mItemColorName;

        GridHolder(final View view) {
            super(view);
            mItemView = view;
            mItemColorView = (SquareView) view.findViewById(R.id.grid_item_color_view);
            mItemColorName = view.findViewById(R.id.grid_item_color_name);
        }

        View getItemView() {
            return mItemView;
        }

        View getItemColorView() {
            return mItemColorView;
        }

        TextView getItemColorName() {
            return mItemColorName;
        }
    }

}
