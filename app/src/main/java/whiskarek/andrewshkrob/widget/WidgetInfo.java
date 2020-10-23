package whiskarek.andrewshkrob.widget;

import android.appwidget.AppWidgetHostView;

import whiskarek.andrewshkrob.model.widget.WidgetInfoModel;

public class WidgetInfo implements WidgetInfoModel{

    private final AppWidgetHostView mWidgetHostView;

    private final int mWidgetId;

    public WidgetInfo(final AppWidgetHostView widgetHostView, final int widgetId) {
        mWidgetHostView = widgetHostView;
        mWidgetId = widgetId;
    }

    @Override
    public AppWidgetHostView getHostView() {
        return mWidgetHostView;
    }

    @Override
    public int getWidgetId() {
        return mWidgetId;
    }
}
