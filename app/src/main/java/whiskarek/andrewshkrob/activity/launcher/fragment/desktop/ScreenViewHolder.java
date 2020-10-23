package whiskarek.andrewshkrob.activity.launcher.fragment.desktop;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.model.viewholder.ScreenViewHolderModel;

public class ScreenViewHolder extends RecyclerView.ViewHolder implements ScreenViewHolderModel{

    private final ImageView mCellIcon;
    private final TextView mCellName;

    public ScreenViewHolder(final View view) {
        super(view);
        mCellIcon = view.findViewById(R.id.launcher_app_icon);
        mCellName = view.findViewById(R.id.launcher_app_name);
    }

    @Override
    public ImageView getCellIcon() {
        return mCellIcon;
    }

    @Override
    public TextView getCellName() {
        return mCellName;
    }

}
