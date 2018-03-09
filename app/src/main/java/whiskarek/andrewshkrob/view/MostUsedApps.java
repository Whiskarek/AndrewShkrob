package whiskarek.andrewshkrob.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;

public class MostUsedApps extends LinearLayout {

    private int mAppAmount;
    private List<View> mApps;

    public MostUsedApps(final Context context) {
        super(context);

        init(context);
    }

    public MostUsedApps(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public MostUsedApps(final Context context, @Nullable final AttributeSet attrs,
                        final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    public void setAppAmount(final int appAmount) {
        mAppAmount = appAmount;

    }

    private void init(final Context context) {
        mApps = new ArrayList<>();
    }

    private View createAppView(final Context context) {
        final View view = inflate(context, R.layout.most_used_item, this);
    }
}
