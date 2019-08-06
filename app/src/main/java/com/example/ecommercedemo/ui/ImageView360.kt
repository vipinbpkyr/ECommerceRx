package com.example.ecommercedemo.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class ImageView360 : AppCompatImageView {

    private var mOnSwipeTouchListener: OnSwipeTouchListener? = null
    private var chunkedImages: MutableList<Bitmap>? = null
    private var current = 4

    companion object {
        private const val SWIPE_THRESHOLD = 100
        private const val SWIPE_VELOCITY_THRESHOLD = 100
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setImage(bitmap: Bitmap, count: Int = 7) {
        setBitMaps(splitImage(bitmap, count))
    }

    private fun setBitMaps(chunkedImages: MutableList<Bitmap>){
        this.chunkedImages = chunkedImages
        setImageBitmap(chunkedImages[3])

        mOnSwipeTouchListener = object : OnSwipeTouchListener(context) {
            override fun onSwipeTop() {
                //                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();

            }

            override fun onSwipeRight(diff: Float) {
                //                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
                //                Log.d("OnSwipeTouchListener", "onSwipeRight " + diff);
                //                imageView1.setImageBitmap(chunkedImages.get(2));

            }

            override fun onScrolls(diffX: Float, distanceX: Float) {
                //                Log.d("OnSwipeTouchListener", "onScrolls " + diffX+ ", "+ distanceX);

                if (distanceX < 0) {
                    if (current < chunkedImages.size - 1) {
                        current++
                        setImageBitmap(chunkedImages[current])
                    } else {
                        setImageBitmap(chunkedImages[0])
                        current = 0
                    }

                } else {
                    if (current > 0) {
                        current--
                        setImageBitmap(chunkedImages[current])
                    } else {
                        setImageBitmap(chunkedImages[chunkedImages.size - 1])
                        current = chunkedImages.size - 1
                    }

                }

                super.onScrolls(diffX, distanceX)
            }

            override fun onSwipeLeft(diff: Float) {
                //                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
                //                Log.d("OnSwipeTouchListener", "onSwipeLeft " + diff);
                //                imageView1.setImageBitmap(chunkedImages.get(1));
            }

            override fun onSwipeBottom() {
                //                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        }

        setOnTouchListener(mOnSwipeTouchListener)
    }

    private fun splitImage(bitmap: Bitmap, chunkNumbers: Int): ArrayList<Bitmap> {
        //For the number of rows and columns of the grid to be displayed
        val images = ArrayList<Bitmap>(7)

        val rows: Int
        val cols: Int = chunkNumbers

        //For height and width of the small image chunks
        val chunkHeight: Int = bitmap.height
        val chunkWidth: Int

        //To store all the small image chunks in bitmap format in this list

        //Getting the scaled bitmap of the source image

        //        Drawable dra = ContextCompat.getDrawable(getContext(), R.drawable.image);
        //        BitmapDrawable drawable = (BitmapDrawable) dra;
        //        Bitmap bitmap = drawable.getBitmap();
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.width, bitmap.height, true)
        rows = cols
        chunkWidth = bitmap.width / cols

        //xCoord and yCoord are the pixel positions of the image chunks
        val yCoord = 0
        var xCoord = 0
        for (x in 0 until rows) {
            images.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight))
            xCoord += chunkWidth
        }

        //        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        //        invalidate();

        return images
    }

    fun loadUrl(url: String?) {
        Log.d("ImageViewHeadShots", "loadUrl $url")

        //        Glide.with(this).load(R.drawable.ic_loading).into(imageViewLoadingHead);
        //        setVisibility(VISIBLE);

        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(object : SimpleTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    Log.d("ImageViewHeadShots", "setData onResourceReady " + resource.width)
                    setImage(resource)

                    //                        setVisibility(VISIBLE);
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    Log.d("ImageViewHeadShots", "setData onLoadFailed")
                    //                        setVisibility(GONE);
                }
            })
    }

    fun downLoadMultipleImages() {
        // launch a coroutine in viewModelScope
        GlobalScope.launch(Dispatchers.IO) {
            // slowFetch()
            val list: MutableList<Bitmap>
            val urls = listOf("http://p.imgci.com/db/PICTURES/CMS/128400/128483.1.jpg","http://p.imgci.com/db/PICTURES/CMS/289000/289002.1.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/222900/222915.jpg","http://p.imgci.com/db/PICTURES/CMS/263700/263705.20.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/263700/263704.1.jpg","http://p.imgci.com/db/PICTURES/CMS/233200/233209.5.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/263700/263702.1.jpg")
            list = getBitMaps(urls)
            Timber.e("downLoadMultipleImages ${list.size} $list ")

            chunkedImages = list
            setImageBitmap(chunkedImages!![3])

        }

    }

    private fun getBitMaps(urls: List<String>): MutableList<Bitmap> {
        val list = mutableListOf<Bitmap>()
        urls.forEach { list.add(getBitmapFromUrl1(it)) }

        return list
    }

    private fun getBitmapFromUrl1(imageUrl: String): Bitmap {
        Timber.e("getBitmapFromUrl1 $imageUrl")
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        val `is` = connection.inputStream
        return BitmapFactory.decodeStream(`is`)
    }

    private open inner class OnSwipeTouchListener(ctx: Context) : OnTouchListener {

        private val gestureDetector: GestureDetector

        init {
            gestureDetector = GestureDetector(ctx, GestureListener())
        }

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            return gestureDetector.onTouchEvent(event)
        }

        open fun onScrolls(diffX: Float, distanceX: Float) {}

        open fun onSwipeRight(diff: Float) {}

        open fun onSwipeLeft(diff: Float) {}

        open fun onSwipeTop() {}

        open fun onSwipeBottom() {}

        private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
            internal var scroll = 0f

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                this@ImageView360.parent.requestDisallowInterceptTouchEvent(false)
                return true
            }

            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                this@ImageView360.parent.requestDisallowInterceptTouchEvent(true)
                val diffX = e2.x - e1.x
                val abs = Math.abs(diffX)
                val diff = Math.abs(abs - scroll)

                //                Log.d("GestureListener", "onScroll " + e1.getX() +", "+ e2.getX());

                if (diff > 30) {
                    onScrolls(diffX, distanceX)
                    scroll = abs
                }

                //                return super.onScroll(e1, e2, distanceX, distanceY);
                return true
            }

            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                var result = false
                try {
                    val diffY = e2.y - e1.y
                    val diffX = e2.x - e1.x
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight(diffX)
                            } else {
                                onSwipeLeft(diffX)
                            }
                            result = true
                        }
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom()
                        } else {
                            onSwipeTop()
                        }
                        result = true
                    }
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

                //                return result;
                return true
            }

        }

    }

}