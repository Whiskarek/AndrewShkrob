package whiskarek.andrewshkrob.activity.launcher.fragment.desktop;

import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.LauncherActivity;
import whiskarek.andrewshkrob.view.adapter.MenuViewPagerAdapter;
import whiskarek.andrewshkrob.widget.SearchWidget;
import whiskarek.andrewshkrob.widget.WidgetInfo;

import static whiskarek.andrewshkrob.widget.SearchWidget.SEARCH_WIDGET_GOOGLE;
import static whiskarek.andrewshkrob.widget.SearchWidget.SEARCH_WIDGET_YANDEX;

public class DesktopFragment extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_desktop, container, false);

        final SearchWidget searchWidget = view.findViewById(R.id.desktop_screen_search_widget);
        searchWidget.setUpWidget(loadWidget(
                ((LauncherActivity) getActivity()).appWidgetManager(),
                ((LauncherActivity) getActivity()).appWidgetHost()
        ));

        LauncherExecutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                final WidgetInfo widgetInfo = loadWidget(
                        ((LauncherActivity) getActivity()).appWidgetManager(),
                        ((LauncherActivity) getActivity()).appWidgetHost()
                );

                LauncherExecutors.getInstance().mainThreadIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        searchWidget.setUpWidget(widgetInfo);
                    }
                });
            }
        });

        final ViewPager viewPager = view.findViewById(R.id.desktop_screen_holder);
        viewPager.setAdapter(new MenuViewPagerAdapter(getFragmentManager(), Arrays.asList((Fragment) Screen.getScreen(1), Screen.getScreen(2))));

        return view;
    }

    @Nullable
    private WidgetInfo loadWidget(final AppWidgetManager appWidgetManager,
                                  final AppWidgetHost appWidgetHost) {

        final List<AppWidgetProviderInfo> widgetList = appWidgetManager.getInstalledProviders();

        for (AppWidgetProviderInfo info : widgetList) {
            Log.d("Launcher", info.toString());
        }

        AppWidgetProviderInfo widgetProvider = null;
        for (AppWidgetProviderInfo info : widgetList) {
            //To get the google search box
            if (info.provider.getClassName()
                    .equals(SEARCH_WIDGET_YANDEX)) {
                widgetProvider = info;
                break;
            }
        }

        if (widgetProvider == null) {
            for (AppWidgetProviderInfo info : widgetList) {
                //To get the google search box
                if (info.provider.getClassName()
                        .equals(SEARCH_WIDGET_GOOGLE)) {
                    widgetProvider = info;
                    break;
                }
            }
        }

        if (widgetProvider != null) {
            final int widgetId = appWidgetHost.allocateAppWidgetId();

            appWidgetManager.bindAppWidgetIdIfAllowed(widgetId, widgetProvider.provider);

            final AppWidgetHostView widgetHostView =
                    appWidgetHost.createView(getContext(), widgetId, widgetProvider);
            final AppWidgetProviderInfo widgetProviderInfo =
                    appWidgetManager.getAppWidgetInfo(widgetId);



            widgetHostView.setAppWidget(widgetId, widgetProviderInfo);

            return new WidgetInfo(widgetHostView, widgetId);
        }

        return null;
    }

}
