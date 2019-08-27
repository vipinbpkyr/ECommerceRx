package com.example.ecommercedemo.ui

import android.app.Activity
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.example.ecommercedemo.ui.gyro.DeviceOrientation
import com.example.ecommercedemo.util.getBitmapFromAsset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import kotlin.math.roundToInt

class ImageView360 : AppCompatImageView {

    private var previousPosition = 0
    private var auto: Boolean = true
    private var mOnSwipeTouchListener: OnSwipeTouchListener? = null
    private var chunkedImages: MutableList<Bitmap>? = null
    private var current = 0

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

    private fun setBitMaps(chunkedImages: MutableList<Bitmap>) {
        this.chunkedImages = chunkedImages
        setImageBitmap(chunkedImages[0])

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
                scrollImage(distanceX > 0)

                if (distanceX > 0) {



                } else {


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

    private fun scrollImage(b: Boolean) {
            if(b){
                if (current < chunkedImages!!.size - 1) {
                    current++
                    setImageBitmap(chunkedImages?.get(current))
                } else {
                    setImageBitmap(chunkedImages?.get(0))
                    current = 0
                }
            }else{
                if (current > 0) {
                    current--
                    setImageBitmap(chunkedImages?.get(current))
                } else {
                    setImageBitmap(chunkedImages?.get(chunkedImages!!.size - 1))
                    current = chunkedImages!!.size - 1
                }
            }


    }

    fun autoRotate() {

        if (auto) {
            Handler().postDelayed({
                setImageBitmap(chunkedImages?.get(current))

                if (current < chunkedImages!!.size - 1) {
                    current++
                } else current = 0

                autoRotate()

            }, 100)
        }
    }

    fun loadFromAssets() {
        val images = ArrayList<Bitmap>(7)
        for (i in 0..35) {
            getBitmapFromAsset(context, "$i.jpeg")?.let { images.add(it) }
//            images.add(getBitmapFromAsset(context, "$i.jpg"))
        }

        setBitMaps(images)

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
            val urls = listOf(
                "http://p.imgci.com/db/PICTURES/CMS/128400/128483.1.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/289000/289002.1.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/222900/222915.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/263700/263705.20.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/263700/263704.1.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/233200/233209.5.jpg",
                "http://p.imgci.com/db/PICTURES/CMS/263700/263702.1.jpg"
            )
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
                auto = false
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

    fun setLifeCycleOwner(context: Context, owner: LifecycleOwner){
        var mSensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
        var accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        var magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        var deviceOrientation = object:DeviceOrientation(){
            override fun onRoll(roll: Float) {
                Log.d("ImageView360", "onRoll roll $roll")

                var pos:Int = roll.roundToInt() /10
                Log.d("ImageView360", "onRoll pos $pos")
                if (previousPosition != pos) {
                    previousPosition = pos
//                    scrollImage(pos > 0)
                    var newPos = 0f
                    if (pos < 0) {
                        newPos = (chunkedImages!!.size - 1) + ((pos / 36f) * (chunkedImages!!.size - 1))
                    }else if(pos == 0){
                        newPos = 0f

                    }else{
                        newPos = ((pos / 36f) * (chunkedImages!!.size - 1))

                    }
                    Log.d("ImageView360", "onRoll newPos $newPos")
                    current = newPos.toInt()
                    setImageBitmap(chunkedImages?.get(current))

                }
            }
        }
        owner.lifecycle.addObserver(MyObserver(mSensorManager, magnetometer, accelerometer, deviceOrientation))

    }

    class MyObserver (var mSensorManager: SensorManager,var magnetometer: Sensor,
                      var accelerometer: Sensor, var deviceOrientation: DeviceOrientation): LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun connectListener() {
            Log.d("ImageView360", "connectListener")
            mSensorManager.registerListener(
                deviceOrientation.getEventListener(),
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
            mSensorManager.registerListener(
                deviceOrientation.getEventListener(),
                magnetometer,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun disconnectListener() {
            Log.d("ImageView360", "connectListener")
            mSensorManager.unregisterListener(deviceOrientation.eventListener)

        }
    }

}