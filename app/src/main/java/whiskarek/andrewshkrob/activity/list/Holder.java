package whiskarek.andrewshkrob.activity.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;

public class Holder {

    static class LinearHolder extends RecyclerView.ViewHolder {
        private final View mListImage;
        private final TextView mListMainText;
        private final TextView mListSecondText;

        LinearHolder(final View view) {
            super(view);

            mListImage = view.findViewById(R.id.list_image);
            mListMainText = view.findViewById(R.id.list_main_text);
            mListSecondText = view.findViewById(R.id.list_second_text);
        }

        View getListImage() {
            return mListImage;
        }

        TextView getListMainText() {
            return mListMainText;
        }

        TextView getListSecondText() {
            return mListSecondText;
        }
    }

}
