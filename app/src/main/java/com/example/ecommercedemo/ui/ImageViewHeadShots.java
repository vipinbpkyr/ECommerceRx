package com.example.ecommercedemo.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

public class ImageViewHeadShots extends ImageView {

    OnSwipeTouchListener mOnSwipeTouchListener;
    ArrayList<Bitmap> chunkedImages;
    int current = 4;

    public ImageViewHeadShots(Context context) {
        super(context);
    }

    public ImageViewHeadShots(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewHeadShots(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageViewHeadShots(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setImage(Bitmap bitmap) {
        chunkedImages = new ArrayList<Bitmap>(5);
        mOnSwipeTouchListener = new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
//                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();

            }

            public void onSwipeRight(float diff) {
//                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
//                Log.d("OnSwipeTouchListener", "onSwipeRight " + diff);
//                imageView1.setImageBitmap(chunkedImages.get(2));

            }

            @Override
            public void onScrolls(float diffX, float distanceX) {
//                Log.d("OnSwipeTouchListener", "onScrolls " + diffX+ ", "+ distanceX);

                if (distanceX < 0) {
                    if (current < chunkedImages.size() - 1) {
                        current++;
                        setImageBitmap(chunkedImages.get(current));
                    }else{
                        setImageBitmap(chunkedImages.get(0));
                        current = 0;
                    }

                } else {
                    if (current > 0) {
                        current--;
                        setImageBitmap(chunkedImages.get(current));
                    }else{
                        setImageBitmap(chunkedImages.get(chunkedImages.size() - 1));
                        current = chunkedImages.size() - 1;
                    }

                }

                super.onScrolls(diffX, distanceX);
            }

            public void onSwipeLeft(float diff) {
//                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
//                Log.d("OnSwipeTouchListener", "onSwipeLeft " + diff);
//                imageView1.setImageBitmap(chunkedImages.get(1));
            }

            public void onSwipeBottom() {
//                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        };
        setOnTouchListener(mOnSwipeTouchListener);
        splitImage(bitmap, 7);

    }

    private void splitImage(Bitmap bitmap, int chunkNumbers) {
        //For the number of rows and columns of the grid to be displayed
        int rows, cols;

        //For height and width of the small image chunks
        int chunkHeight, chunkWidth;

        //To store all the small image chunks in bitmap format in this list

        //Getting the scaled bitmap of the source image

//        Drawable dra = ContextCompat.getDrawable(getContext(), R.drawable.image);
//        BitmapDrawable drawable = (BitmapDrawable) dra;
//        Bitmap bitmap = drawable.getBitmap();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        rows = cols = chunkNumbers;
        chunkHeight = bitmap.getHeight();
        chunkWidth = bitmap.getWidth() / cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        int xCoord = 0;
        for (int x = 0; x < rows; x++) {
            chunkedImages.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
            xCoord += chunkWidth;
        }

//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        setImageBitmap(chunkedImages.get(3));
//        invalidate();

    }

    public class OnSwipeTouchListener implements OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;
            float scroll = 0f;

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                ImageViewHeadShots.this.getParent().requestDisallowInterceptTouchEvent(false);
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                ImageViewHeadShots.this.getParent().requestDisallowInterceptTouchEvent(true);
                float diffX = e2.getX() - e1.getX();
                float abs = Math.abs(diffX);
                float diff = Math.abs(abs - scroll);

//                Log.d("GestureListener", "onScroll " + e1.getX() +", "+ e2.getX());

                if (diff > 30) {
                    onScrolls(diffX, distanceX);
                    scroll = abs;
                }

//                return super.onScroll(e1, e2, distanceX, distanceY);
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight(diffX);
                            } else {
                                onSwipeLeft(diffX);
                            }
                            result = true;
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                        result = true;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
//                return result;
                return true;
            }
        }

        public void onScrolls(float diffX, float distanceX) {
        }

        public void onSwipeRight(float diff) {
        }

        public void onSwipeLeft(float diff) {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }
    }
    public void loadUrl(String url) {
        Log.d("ImageViewHeadShots", "loadUrl " + url);

//        Glide.with(this).load(R.drawable.ic_loading).into(imageViewLoadingHead);
//        setVisibility(VISIBLE);

        Glide.with(this)
                .asBitmap()
                .load(url)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        Log.d("ImageViewHeadShots", "setData onResourceReady " + resource.getWidth());
                        setImage(resource);

//                        setVisibility(VISIBLE);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Log.d("ImageViewHeadShots", "setData onLoadFailed");
//                        setVisibility(GONE);
                    }
                });
    }

}
