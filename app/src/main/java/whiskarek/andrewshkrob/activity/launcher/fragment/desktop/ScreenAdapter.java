package whiskarek.andrewshkrob.activity.launcher.fragment.desktop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.database.entity.DesktopCellEntity;

public class ScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private List<DesktopCellEntity> mDesktopCellList;

    public ScreenAdapter(final Context context) {
        mContext = context;
        mDesktopCellList = new ArrayList<>();
    }



}
