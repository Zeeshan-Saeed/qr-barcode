# Augmented Reality barcode & QR code detector for >= Android 4.0 #

### What is this repository for? ###

* Barcodes and QR codes are detected in the real time camera picture. Depending on a self defined barcode database - barcodes are connected to commands which are responsible for e.g. showing an image via augmented reality (on top of the camera image) or to play a sound. Features include the automatically taking of a photo when a barcode is recognized.
* Version 1.1

### How do I get set up? ###

* IDE: Android Studio (tested with 2.1.2)
* Android SDK
* Dependencies: ZXing (Zebra Crossing) library for barcode detection (automatically included by Gradle)
* Database configuration: Database is located in .tools.BarcodeDatabase.java
* Database usage: the database exists out of a **key (barcode value)** and a **connected command**. The command is handled in .views.OverlayView.java
* Images location: res/drawable | Sounds location: res/raw
* Automatically taking photos: adjustable via settings menu in the GUI
* Make sure the app has the required permission on start, as there is no runtime-check yet! (Camera, external storage)


### Test images which are in the hardcoded database ###
Barcode - connected with the command "right" - shows an image with an arrow to the right

<img src="/testimages/barcode.jpg" alt="Drawing" width="200px"/>

QR code - connected with the command "left" - shows an image with an arrow to the left

<img src="/testimages/qrcode.png" alt="Drawing" width="200px"/>

### Who do I talk to? ###

* Repo owner and developer: android@michaeltroger.com
