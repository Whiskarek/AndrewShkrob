package whiskarek.andrewshkrob.activity.launcher.fragment.desktop;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.database.dao.ApplicationDao;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;
public class ScreenAdapter extends RecyclerView.Adapter<ScreenViewHolder> {

    private static final int CELL_AMOUNT = 25;

    private final Context mContext;

    private List<ApplicationEntity> mShortcutList;

    public ScreenAdapter(final Context context) {
        mContext = context;

        mShortcutList = new ArrayList<>(CELL_AMOUNT);

        for (int i = 0; i < CELL_AMOUNT; i++) {
            mShortcutList.add(null);
        }
    }

    @Override
    public ScreenViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_item, parent, false);

        final ScreenViewHolder viewHolder = new ScreenViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (mShortcutList.get(adapterPosition) != null) {
                        mContext.startActivity(mShortcutList.get(adapterPosition).getIntent());

                        LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                final ApplicationDao appDao =
                                        ((LauncherApplication) mContext.getApplicationContext())
                                                .getDatabase()
                                                .applicationDao();
                                appDao.setLaunchAmount(
                                        mShortcutList.get(adapterPosition).getPackageName(),
                                        mShortcutList.get(adapterPosition).getLaunchAmount() + 1
                                );
                            }
                        });
                    }
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ScreenViewHolder holder, int position) {
        final ApplicationEntity app = mShortcutList.get(position);
        if (app == null) {
            return;
        }

        holder.getCellName().setText(app.getLabel());
        holder.getCellIcon().setBackground(app.getIcon());
    }

    @Override
    public int getItemCount() {
        return mShortcutList.size();
    }

    public void setData(final Map<Integer, ApplicationEntity> shortcutList) {
        for (int i = 0; i < CELL_AMOUNT; i++) {
            if (shortcutList.containsKey(i)) {
                mShortcutList.set(i, shortcutList.get(i));
            } else {
                mShortcutList.set(i, null);
            }
        }

        notifyDataSetChanged();
    }
}
