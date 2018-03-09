package whiskarek.andrewshkrob.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SearchWidget extends FrameLayout {

    public static final String SEARCH_WIDGET_YANDEX = "ru.yandex.searchplugin.Widget";
    public static final String SEARCH_WIDGET_GOOGLE = "com.google.android.googlequicksearchbox.SearchWidgetProvider";

    private WidgetInfo mWidgetInfo;

    public SearchWidget(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
    }

    public SearchWidget(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchWidget(final Context context) {
        super(context);
    }

    public void setUpWidget(final WidgetInfo widgetInfo) {
        mWidgetInfo = widgetInfo;
        if (mWidgetInfo != null) {
            removeAllViews();
            final int width = FrameLayout.LayoutParams.MATCH_PARENT;
            final int height = FrameLayout.LayoutParams.WRAP_CONTENT;
            final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
            addView(mWidgetInfo.getHostView(), layoutParams);
        }
    }

}
