/**
 * Copied from NevedomskyMobyDev
 * https://github.com/nevack/NevedomskyMobDev
 */

package whiskarek.andrewshkrob.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import whiskarek.andrewshkrob.R;

public class PageIndicatorView extends View {

    private static final int RADIUS = 6/*dp*/;

    private int mCurrentPage;
    private int mPageCount;

    private Paint mActiveDotColor;
    private Paint mInactiveDotColor;

    private int mDotRadius;
    private int MDotPadding;
    private int mWidth;

    private int previousIndicatorPosition;

    public PageIndicatorView(final Context context) {
        this(context, null);
    }

    public PageIndicatorView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageIndicatorView(final Context context, @Nullable final AttributeSet attrs,
                             final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        mWidth = (MDotPadding * (mPageCount - 1)) + (mDotRadius * 2 * mPageCount);
        int mHeight = mDotRadius * 2;

        // Try for a mWidth based on our minimum
        int minWidth = getPaddingLeft() + getPaddingRight() + mWidth;
        int width = resolveSizeAndState(minWidth, widthMeasureSpec, 0);

        // Whatever the mWidth ends up being, ask for a mHeight that would let the pie
        // get as big as it can
        int minHeight = getPaddingBottom() + getPaddingTop() + mHeight;
        int height = resolveSizeAndState(minHeight, heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        int left = (canvas.getWidth() - mWidth) / 2;
        int top = canvas.getHeight() / 2;

        for (int i = 0; i < mPageCount; i++) {
            canvas.drawCircle(
                    left + MDotPadding * i + mDotRadius * 2 * i + mDotRadius,
                    top,
                    mDotRadius,
                    mInactiveDotColor
            );
        }

        canvas.drawCircle(
                left + previousIndicatorPosition,
                top,
                mDotRadius,
                mActiveDotColor
        );
    }

    private void init() {
        float density = getResources().getDisplayMetrics().density;
        mDotRadius = Math.round(RADIUS * density);
        MDotPadding = mDotRadius * 2;

        previousIndicatorPosition = mDotRadius;

        mActiveDotColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mActiveDotColor.setColor(getResources().getColor(R.color.colorAccent));

        mInactiveDotColor = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInactiveDotColor.setColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    public void setCurrentPage(@IntRange(from = 1) final int mCurrentPage) {
        setCurrentPage(mCurrentPage, true);
    }

    public void setCurrentPage(@IntRange(from = 1) final int currentPage, final boolean animated) {
        this.mCurrentPage = currentPage;

        if (animated) {
            int next = MDotPadding * (this.mCurrentPage - 1) + mDotRadius * 2 * (this.mCurrentPage - 1) + mDotRadius;

            ValueAnimator animator = ValueAnimator.ofInt(previousIndicatorPosition, next);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(final ValueAnimator animation) {
                    previousIndicatorPosition = (int) animation.getAnimatedValue();
                    invalidate();
                }
            });


            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(250); //milliseconds
            animator.start();
        } else invalidate();
    }

    public void setPageCount(@IntRange(from = 1) final int mPageCount) {
        this.mPageCount = mPageCount;
        invalidate();
        requestLayout();
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

}
