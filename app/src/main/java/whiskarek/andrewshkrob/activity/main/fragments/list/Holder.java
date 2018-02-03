package whiskarek.andrewshkrob.activity.main.fragments.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.view.CircleView;

public class Holder {

    static class ListHolder extends RecyclerView.ViewHolder {
        private final CircleView mItemColorView;
        private final TextView mItemColorName;
        private final TextView mItemRandomText;

        ListHolder(final View view) {
            super(view);
            mItemColorView = (CircleView) view.findViewById(R.id.list_item_color_view);
            mItemColorName = (TextView) view.findViewById(R.id.list_item_color_name);
            mItemRandomText = (TextView) view.findViewById(R.id.list_item_random_text);
        }

        CircleView getItemColorView() {
            return mItemColorView;
        }

        TextView getItemColorName() {
            return mItemColorName;
        }

        TextView getItemRandomText() {
            return mItemRandomText;
        }
    }

}
