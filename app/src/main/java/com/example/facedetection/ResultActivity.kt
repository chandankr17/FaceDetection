package com.example.facedetection

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvResult = findViewById<TextView>(R.id.tvResult)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnCamera = findViewById<Button>(R.id.btnCamera)

        btnHome.setOnClickListener {
            finishAffinity() // go back to splash/home
        }

        btnCamera.setOnClickListener {
            finish() // back to CameraActivity
        }

        val bitmap = intent.getParcelableExtra<Bitmap>("imageBitmap")
        if (bitmap != null) {
            detectFace(bitmap, tvResult)
        } else {
            tvResult.text = "No image found"
        }
    }

    private fun detectFace(bitmap: Bitmap, tvResult: TextView) {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val detector = FaceDetection.getClient(options)
        val image = InputImage.fromBitmap(bitmap, 0)

        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isEmpty()) {
                    tvResult.text = "No Face Detected"
                } else {
                    val sb = StringBuilder()
                    var i = 1
                    for (face in faces) {
                        sb.append("Face $i:\n")
                        sb.append("Smile: ${face.smilingProbability?.times(100)?.toInt()}%\n")
                        sb.append("Left Eye Open: ${face.leftEyeOpenProbability?.times(100)?.toInt()}%\n")
                        sb.append("Right Eye Open: ${face.rightEyeOpenProbability?.times(100)?.toInt()}%\n\n")
                        i++
                    }
                    tvResult.text = sb.toString()
                }
            }
            .addOnFailureListener {
                tvResult.text = "Face Detection Failed"
            }
    }
}