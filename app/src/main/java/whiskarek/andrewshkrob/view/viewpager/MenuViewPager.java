package whiskarek.andrewshkrob.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MenuViewPager extends ViewPager {

    public MenuViewPager(final Context context) {
        super(context);
    }

    public MenuViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(final MotionEvent ev){

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    protected boolean canScroll(final View v, final boolean checkV, final int dx,
                                final int x, final int y) {
        if (v != this && v instanceof ViewPager) {
            return true;
        }
        return super.canScroll(v, checkV, dx, x, y);
    }

}
