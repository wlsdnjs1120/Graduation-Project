# Graduation-Project

# Motion Recognition

***

## Project Description

Motion Recognition is a function that checks the movements of users using a smartphone camera and it is implemented using the posenet of tensorflowlite. 
Existing posenets used CPUs to measure the body's major joints, and furthermore, the accuracy of recognizing joints when the user moves was reduced. 
Therefore, we used GPU instead of CPU to accurately recognize joints even if the user moves, and we were able to implement joint measurement speed faster. 
In addition, 15 joints are measured for head, neck, background, left-right shoulder, left-right elbow, left-right hip, left-right knee, left-right angle.
And we implemented the function with kotlin.

![image](https://user-images.githubusercontent.com/57340671/116256022-c454ba80-a7ad-11eb-9281-4ae2a6af1ea4.png)
![image](https://user-images.githubusercontent.com/57340671/116256026-c61e7e00-a7ad-11eb-9f81-b6de84de258e.png)

***

## Purpose of the Project

### 1. Purpose of the project

As more and more people are doing home training due to Corona, 
we implemented the above function to exercise more efficiently. 
If you perform this function when you exercise, 
the camera shows the main joints of your body and the lines connecting them 
so that you can make sure that you are in the right position for the exercise.


### 2. What problems can you solve?

When performing this function during exercise, 
it recognizes the 15 main joints of the body 
(head, neck, background, left-right shoulder, left-right elbow, left-right hip, left-right knee, left-right angle) 
and displays the motion lines that connect the joints on the screen.


### 3. Why is this project useful?

We developed a home training app, 
so it can be used more conveniently because it is developed with a smartphone camera that installs the app.
Also, unlike other apps that tell you the exercise time and exercise list, 
it can be more useful because you can check the exercise posture yourself.


### 4. What kind of people would like to use this project?

Due to Corona, more people are doing home training at home. 
If they use this function to exercise, I think they will be able to exercise more effectively.


### 5. How does this project work?

Key Class Descriptions
* AutoFitFrameLayout & AutoFitTextureView: Show Surface View to match display screen of user’s device
``` Kotlin
fun setAspectRatio(
    width: Int,
    height: Int
  ) {
    if (width < 0 || height < 0) {
      throw IllegalArgumentException("Size cannot be negative.")
    }
    mRatioWidth = width
    mRatioHeight = height
    requestLayout()
  }

  override fun onMeasure(
    widthMeasureSpec: Int,
    heightMeasureSpec: Int
  ) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    val width = View.MeasureSpec.getSize(widthMeasureSpec)
    val height = View.MeasureSpec.getSize(heightMeasureSpec)
    if (0 == mRatioWidth || 0 == mRatioHeight) {
      setMeasuredDimension(width, height)
    } else {
      if (width < height * mRatioWidth / mRatioHeight) {
        setMeasuredDimension(width, width * mRatioHeight / mRatioWidth)
      } else {
        setMeasuredDimension(height * mRatioWidth / mRatioHeight, height)
      }
    }
```

* Camera2BasicFragment, CameraActivity, DimensionExtension.kt : Classes for using camera
``` Kotlin
override fun onResume() {
    super.onResume()
    if (!OpenCVLoader.initDebug()) {
      OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback)
    } else {
      mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS)
    }
  }
```
``` Kotlin
 /**
     * Configures the necessary [android.graphics.Matrix] transformation to `textureView`. This
     * method should be called after the camera preview size is determined in setUpCameraOutputs and
     * also the size of `textureView` is fixed.
     *
     * @param viewWidth  The width of `textureView`
     * @param viewHeight The height of `textureView`
     */
    private fun configureTransform(
            viewWidth: Int,
            viewHeight: Int
    ) {
        val activity = activity
        if (null == textureView || null == previewSize || null == activity) {
            return
        }
        val rotation = activity.windowManager.defaultDisplay.rotation
        val matrix = Matrix()
        val viewRect = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        val bufferRect = RectF(0f, 0f, previewSize!!.height.toFloat(), previewSize!!.width.toFloat())
        val centerX = viewRect.centerX()
        val centerY = viewRect.centerY()
        if (Surface.ROTATION_90 == rotation || Surface.ROTATION_270 == rotation) {
            bufferRect.offset(centerX - bufferRect.centerX(), centerY - bufferRect.centerY())
            matrix.setRectToRect(viewRect, bufferRect, Matrix.ScaleToFit.FILL)
            val scale = Math.max(
                    viewHeight.toFloat() / previewSize!!.height,
                    viewWidth.toFloat() / previewSize!!.width
            )
            matrix.postScale(scale, scale, centerX, centerY)
            matrix.postRotate((90 * (rotation - 2)).toFloat(), centerX, centerY)
        } else if (Surface.ROTATION_180 == rotation) {
            matrix.postRotate(180f, centerX, centerY)
        }
        textureView!!.setTransform(matrix)
    }
```

* DrawView.kt : Classes for draw body joint line & point
``` Kotlin
private val mColorArray = intArrayOf(
            resources.getColor(R.color.color_top, null),    // 0
            resources.getColor(R.color.color_neck, null),   // 1
            resources.getColor(R.color.color_l_shoulder, null), // 2
            resources.getColor(R.color.color_l_elbow, null),    // 3
            resources.getColor(R.color.color_l_wrist, null),    // 4
            resources.getColor(R.color.color_r_shoulder, null), //5
            resources.getColor(R.color.color_r_elbow, null),     //6
            resources.getColor(R.color.color_r_wrist, null),    //7
            resources.getColor(R.color.color_l_hip, null),  //8
            resources.getColor(R.color.color_l_knee, null), //9
            resources.getColor(R.color.color_l_ankle, null),    //10
            resources.getColor(R.color.color_r_hip, null),  //11
            resources.getColor(R.color.color_r_knee, null), //12
            resources.getColor(R.color.color_r_ankle, null),    //13
            resources.getColor(R.color.color_background, null)  //14
    )

fun setDrawPoint(
            point: Array<FloatArray>,
            ratio: Float
    ) {
        mDrawPoint.clear()

        var tempX: Float
        var tempY: Float
        for (i in 0..13) {
            tempX = point[0][i] / ratio / mRatioX
            tempY = point[1][i] / ratio / mRatioY
            mDrawPoint.add(PointF(tempX, tempY))
        }
    }
    
override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mDrawPoint.isEmpty()) return
        var prePointF: PointF? = null
        mPaint.color = 0xff6fa8dc.toInt()
        val p1 = mDrawPoint[1]
        for ((index, pointF) in mDrawPoint.withIndex()) {
            if (index == 1) continue
            when (index) {
                //0-1
                0 -> {
                    canvas.drawLine(pointF.x, pointF.y, p1.x, p1.y, mPaint)
                }
                // 1-2, 1-5, 1-8, 1-11
                2, 5, 8, 11 -> {
                    canvas.drawLine(p1.x, p1.y, pointF.x, pointF.y, mPaint)
                }
                else -> {
                    if (prePointF != null) {
                        mPaint.color = 0xff6fa8dc.toInt()
                        canvas.drawLine(prePointF.x, prePointF.y, pointF.x, pointF.y, mPaint)
                    }
                }
            }
            prePointF = pointF
        }

        for ((index, pointF) in mDrawPoint.withIndex()) {
            mPaint.color = mColorArray[index]
            canvas.drawCircle(pointF.x, pointF.y, circleRadius, mPaint)
        }
    }
```
* ImageClassifier, ImageClassifierFloatInception : Classes for get image and classify, run training model
``` Kotlin
private fun convertBitmapToByteBuffer(bitmap: Bitmap) {
    if (imgData == null) {
      return
    }
    imgData!!.rewind()
    bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
    // Convert the image to floating point.
    var pixel = 0
    val startTime = SystemClock.uptimeMillis()
    for (i in 0 until imageSizeX) {
      for (j in 0 until imageSizeY) {
        val v = intValues[pixel++]
        addPixelValue(v)
      }
    }
    val endTime = SystemClock.uptimeMillis()
    Log.d(
        TAG,"Timecost to put values into ByteBuffer: " + Long.toString(endTime - startTime)
    )
  }
  
fun get_point_by_id(keypoints: DoubleArray, id: Int): DoubleArray? {
        val keypoint = DoubleArray(2)
        try {
            keypoint[0] = keypoints[2 * id]
            keypoint[1] = keypoints[2 * id + 1]
        } catch (e: java.lang.Exception) {
            return null
        }
        return keypoint
    }

 private fun get_angle(p1: DoubleArray, p2: DoubleArray, p3: DoubleArray): Double {
        // angle = arcos(p1-p2-p3)
        var ang = -1.0
        val x2 = p2[0]
        val y2 = p2[1]
        val x3 = p3[0]
        val y3 = p3[1]
        val x1 = p1[0]
        val y1 = p1[1]
        val temp1 = (x3 - x2) * (x1 - x2) + (y3 - y2) * (y1 - y2)
        val temp2 = Math.sqrt(((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2)) * ((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)))
        ang = Math.acos(temp1 / temp2)
        ang = ang / Math.PI * 180
        return ang
    }
protected fun get_distance(p1: DoubleArray, p2: DoubleArray): Double {
        val x2 = p2[0]
        val y2 = p2[1]
        val x1 = p1[0]
        val y1 = p1[1]
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2))
    }
```

Other Descriptions
* Use the processed camera image as input and output about the key point
* The detected key points are indexed as part IDs with a reliability score between 0.0 and 1.0
* The reliability score indicates the probability that a key point will exist at that location

***

## How to Start a Project

### 1. Prerequisites required to install and use a project

To use your smartphone's camera, you must add the code below to the Manifest file in your Android studio.

\<uses-permission android:name="android.permission.CAMERA" />

\<uses-feature android:name="android.hardware.camera" />

\<uses-feature android:name="android.hardware.camera.autofocus" />

There were code to allow the use of cameras, can execute functions on the camera is operating properly.


### 2. How to install and use

* Download the Motion Recognition code from github.
* Run code through Android studio
* Connect your smartphone using Android OS to your notebook
* Run the project to verify that it works well on your smartphone.

***

## External Resource Information

### 1. Tensorflow Lite

![image](https://user-images.githubusercontent.com/57340671/116255993-bc951600-a7ad-11eb-8e6b-fa1dadcc39bb.png)

Using the posenet demo of tensorflowlite, 
we used an open source that recognizes the main joints of the body. 
However, there was a problem that the recognition was slow because of the CPU 
and the user could not recognize the joints of the body when moving.
Therefore, we used GPU to speed up recognition and implemented major joints by modifying them.

### 2. Open CV

![image](https://user-images.githubusercontent.com/57340671/116255950-b0a95400-a7ad-11eb-81b8-4a6e095e98fd.png)

This enabled camera to be used in android studio and successfully implemented.
Unlike the open source that used the existing rear camera, 
we have used the front camera to allow users to check and shoot with their smartphones, 
and also improved image quality.
