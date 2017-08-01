package io.supercharge.shimmerlayout;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class ShimmerLayout extends FrameLayout {

    private static final int DEFAULT_ANIMATION_DURATION = 1500;
    private static final int DEFAULT_ANGLE = 20;
    private static final int MIN_ANGLE_VALUE = 0;
    private static final int MAX_ANGLE_VALUE = 30;

    private int maskOffsetX;
    private Rect maskRect;
    private Paint maskPaint;
    private ValueAnimator maskAnimator;

    private Bitmap localAvailableBitmap;
    private Bitmap localMaskBitmap;
    private Bitmap destinationBitmap;
    private Bitmap sourceMaskBitmap;
    private Canvas canvasForRendering;

    private boolean isAnimationStarted;
    private boolean autoStart;
    private int shimmerAnimationDuration;
    private int shimmerColor;
    private int shimmerAngle;

    public ShimmerLayout(Context context) {
        this(context, null);
    }

    public ShimmerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShimmerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setWillNotDraw(false);

        maskPaint = new Paint();
        maskPaint.setAntiAlias(true);
        maskPaint.setDither(true);
        maskPaint.setFilterBitmap(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ShimmerLayout,
                0, 0);

        try {
            shimmerAngle = a.getInteger(R.styleable.ShimmerLayout_shimmer_angle, DEFAULT_ANGLE);
            shimmerAnimationDuration = a.getInteger(R.styleable.ShimmerLayout_shimmer_animation_duration, DEFAULT_ANIMATION_DURATION);
            shimmerColor = a.getColor(R.styleable.ShimmerLayout_shimmer_color, getColor(R.color.shimmer_color));
            autoStart = a.getBoolean(R.styleable.ShimmerLayout_shimmer_auto_start, false);
        } finally {
            a.recycle();
        }

        setShimmerAngle(shimmerAngle);
        if (autoStart && getVisibility() == VISIBLE) {
            startShimmerAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        resetShimmering();
        super.onDetachedFromWindow();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!isAnimationStarted || getWidth() <= 0 || getHeight() <= 0) {
            super.dispatchDraw(canvas);
        } else {
            dispatchDrawUsingBitmap(canvas);
        }
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == VISIBLE) {
            if (autoStart) {
                startShimmerAnimation();
            }
        } else {
            stopShimmerAnimation();
        }
    }

    public void startShimmerAnimation() {
        if (isAnimationStarted) {
            return;
        }

        if (getWidth() == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    removeGlobalLayoutListener(this);
                    startShimmerAnimation();
                }
            });

            return;
        }

        Animator animator = getShimmerAnimation();
        animator.start();
        isAnimationStarted = true;
    }

    public void stopShimmerAnimation() {
        resetShimmering();
    }

    public void setShimmerColor(int shimmerColor) {
        this.shimmerColor = shimmerColor;
        resetIfStarted();
    }

    public void setShimmerAnimationDuration(int durationMillis) {
        this.shimmerAnimationDuration = durationMillis;
        resetIfStarted();
    }

    /**
     * Set the angle of the shimmer effect in clockwise direction in degrees.
     * The angle must be between {@value #MIN_ANGLE_VALUE} and {@value #MAX_ANGLE_VALUE}.
     *
     * @param angle The angle to be set
     */
    public void setShimmerAngle(int angle) {
        if (angle < MIN_ANGLE_VALUE || MAX_ANGLE_VALUE < angle) {
            throw new IllegalArgumentException(String.format("shimmerAngle value must be between %d and %d",
                    MIN_ANGLE_VALUE,
                    MAX_ANGLE_VALUE));
        }
        this.shimmerAngle = angle;
        resetIfStarted();
    }

    private void resetIfStarted() {
        if (isAnimationStarted) {
            resetShimmering();
            startShimmerAnimation();
        }
    }

    private void dispatchDrawUsingBitmap(Canvas canvas) {
        super.dispatchDraw(canvas);

        localAvailableBitmap = getDestinationBitmap();
        if (localAvailableBitmap == null) {
            return;
        }

        if (canvasForRendering == null) {
            canvasForRendering = new Canvas(localAvailableBitmap);
        }

        drawMask(canvasForRendering);
        canvas.save();
        canvas.clipRect(maskOffsetX, 0, maskOffsetX + maskRect.width(), getHeight());
        canvas.drawBitmap(localAvailableBitmap, 0, 0, null);
        canvas.restore();

        localAvailableBitmap = null;
    }

    private void drawMask(Canvas renderCanvas) {
        localMaskBitmap = getSourceMaskBitmap();
        if (localMaskBitmap == null) {
            return;
        }

        renderCanvas.save();
        renderCanvas.clipRect(maskOffsetX, 0,
                maskOffsetX + localMaskBitmap.getWidth(),
                getHeight());

        super.dispatchDraw(renderCanvas);
        renderCanvas.drawBitmap(localMaskBitmap, maskOffsetX, 0, maskPaint);

        renderCanvas.restore();

        localMaskBitmap = null;
    }

    private void resetShimmering() {
        if (maskAnimator != null) {
            maskAnimator.end();
            maskAnimator.removeAllUpdateListeners();
        }

        maskAnimator = null;
        isAnimationStarted = false;

        releaseBitMaps();
    }

    private void releaseBitMaps() {
        if (sourceMaskBitmap != null) {
            sourceMaskBitmap.recycle();
            sourceMaskBitmap = null;
        }

        if (destinationBitmap != null) {
            destinationBitmap.recycle();
            destinationBitmap = null;
        }

        canvasForRendering = null;
    }

    private Bitmap getDestinationBitmap() {
        if (destinationBitmap == null) {
            destinationBitmap = createBitmap(getWidth(), getHeight());
        }

        return destinationBitmap;
    }

    private Bitmap getSourceMaskBitmap() {
        if (sourceMaskBitmap != null) {
            return sourceMaskBitmap;
        }

        int width = maskRect.width();
        int height = getHeight();

        final int edgeColor = reduceColorAlphaValueToZero(shimmerColor);
        LinearGradient gradient = new LinearGradient(
                -maskRect.left, 0,
                width + maskRect.left, 0,
                new int[]{edgeColor, shimmerColor, shimmerColor, edgeColor},
                new float[]{0.25F, 0.47F, 0.53F, 0.75F},
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(gradient);

        sourceMaskBitmap = createBitmap(width, height);
        Canvas canvas = new Canvas(sourceMaskBitmap);
        canvas.rotate(shimmerAngle, width / 2, height / 2);
        canvas.drawRect(-maskRect.left, maskRect.top, width + maskRect.left, maskRect.bottom, paint);

        return sourceMaskBitmap;
    }

    private Animator getShimmerAnimation() {
        if (maskAnimator != null) {
            return maskAnimator;
        }

        if (maskRect == null) {
            maskRect = calculateMaskRect();
        }

        final int animationToX = getWidth();
        final int animationFromX = -animationToX;
        final int shimmerBitmapWidth = maskRect.width();
        final int shimmerAnimationFullLength = animationToX - animationFromX;

        maskAnimator = ValueAnimator.ofFloat(0.0F, 1.0F);
        maskAnimator.setDuration(shimmerAnimationDuration);
        maskAnimator.setRepeatCount(ObjectAnimator.INFINITE);

        final float[] value = new float[1];
        maskAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value[0] = (Float) animation.getAnimatedValue();
                maskOffsetX = ((int) (animationFromX + shimmerAnimationFullLength * value[0]));

                if (maskOffsetX + shimmerBitmapWidth >= 0) {
                    invalidate();
                }
            }
        });

        return maskAnimator;
    }

    private Bitmap createBitmap(int width, int height) {
        try {
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError e) {
            System.gc();
            return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
    }

    private int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getColor(id);
        } else {
            //noinspection deprecation
            return getResources().getColor(id);
        }
    }

    private void removeGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        } else {
            getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        }
    }

    private int reduceColorAlphaValueToZero(int actualColor) {
        return Color.argb(0, Color.red(actualColor), Color.green(actualColor), Color.blue(actualColor));
    }

    private Rect calculateMaskRect() {
        if (shimmerAngle == 0) {
            return new Rect((int) (getWidth() * 0.25), 0, (int) (getWidth() * 0.75), getHeight());
        }

        int top = 0;
        int right = (int) (getWidth() * 0.75);
        int center = (int) (getHeight() * 0.5);
        Point originalTopRight = new Point(right, top);
        Point originalCenterRight = new Point(right, center);

        Point rotatedTopRight = rotatePoint(originalTopRight, shimmerAngle, getWidth() / 2, getHeight() / 2);
        Point rotatedCenterRight = rotatePoint(originalCenterRight, shimmerAngle, getWidth() / 2, getHeight() / 2);
        Point rotatedIntersection = getTopIntersection(rotatedTopRight, rotatedCenterRight);
        int halfMaskHeight = distanceBetween(rotatedCenterRight, rotatedIntersection);

        int paddingVertical = (getHeight() / 2) - halfMaskHeight;
        int paddingHorizontal = (getWidth() - rotatedIntersection.x);

        return new Rect(paddingHorizontal, paddingVertical, getWidth() - paddingHorizontal, getHeight() - paddingVertical);
    }

    /**
     * Finds the intersection of the line and the top of the canvas
     *
     * @param p1 First point of the line of which the intersection with the canvas should be determined
     * @param p2 Second point of the line of which the intersection with the canvas should be determined
     * @return The point of intersection
     */
    private Point getTopIntersection(Point p1, Point p2) {
        double x1 = p1.x;
        double x2 = p2.x;
        double y1 = -p1.y;
        double y2 = -p2.y;
        // slope-intercept form of the line represented by the two points
        double m = (y2 - y1) / (x2 - x1);
        double b = y1 - m * x1;
        // The intersection with the line represented by the top of the canvas
        int x = (int) ((0 - b) / m);
        int y = 0;
        return new Point(x, y);
    }

    private Point rotatePoint(Point point, float degrees, float cx, float cy) {
        float[] pts = new float[2];
        pts[0] = point.x;
        pts[1] = point.y;

        Matrix transform = new Matrix();
        transform.setRotate(degrees, cx, cy);
        transform.mapPoints(pts);

        return new Point((int) pts[0], (int) pts[1]);
    }

    private int distanceBetween(Point p1, Point p2) {
        return (int) Math.ceil(Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2)));
    }
}
