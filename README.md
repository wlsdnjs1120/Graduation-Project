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

## Purpose and Purpose of the Project

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

Key Class Description
* AutoFitFrameLayout & AutoFitTextureView: Show Surface View to match display screen of user’s device
* Camera2BasicFragment, CameraActivity, DimensionExtension.kt : Classes for using camera
* DrawView.kt : Classes for draw body joint line & point
* ImageClassifier, ImageClassifierFloatInception : Classes for get image and classify, run training model

Other Descriptions
* Use the processed camera image as input and output about the key point
* The detected key points are indexed as part IDs with a reliability score between 0.0 and 1.0
* The reliability score indicates the probability that a key point will exist at that location

***

## How to Start a Project

### 1. Prerequisites required to install and use a project

To use your smartphone's camera, you must add the code below to the Manifest file in your Android studio.
<uses-permission android:name="android.permission.CAMERA" />
<uses-feature android:name="android.hardware.camera" />
<uses-feature android:name="android.hardware.camera.autofocus" />
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
