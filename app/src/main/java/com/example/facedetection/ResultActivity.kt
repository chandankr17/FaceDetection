package com.example.facedetection

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class ResultActivity : AppCompatActivity() {

    private lateinit var tvSmile: TextView
    private lateinit var tvLeftEye: TextView
    private lateinit var tvRightEye: TextView
    private lateinit var pbSmile: ProgressBar
    private lateinit var pbLeftEye: ProgressBar
    private lateinit var pbRightEye: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvSmile = findViewById(R.id.tvSmile)
        tvLeftEye = findViewById(R.id.tvLeftEye)
        tvRightEye = findViewById(R.id.tvRightEye)
        pbSmile = findViewById(R.id.pbSmile)
        pbLeftEye = findViewById(R.id.pbLeftEye)
        pbRightEye = findViewById(R.id.pbRightEye)

        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnCamera = findViewById<Button>(R.id.btnCamera)

        btnHome.setOnClickListener { finishAffinity() }
        btnCamera.setOnClickListener { finish() }

        val bitmap = BitmapHolder.bitmap
        if (bitmap != null) {
            detectFace(bitmap)
        } else {
            tvSmile.text = "No image"
            tvLeftEye.text = "No image"
            tvRightEye.text = "No image"
        }
    }

    private fun detectFace(bitmap: Bitmap) {
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
                    tvSmile.text = "No Face"
                    tvLeftEye.text = "No Face"
                    tvRightEye.text = "No Face"
                    pbSmile.progress = 0
                    pbLeftEye.progress = 0
                    pbRightEye.progress = 0
                } else {
                    val face = faces[0]
                    val smile = face.smilingProbability?.times(100)?.toInt() ?: 0
                    val leftEye = face.leftEyeOpenProbability?.times(100)?.toInt() ?: 0
                    val rightEye = face.rightEyeOpenProbability?.times(100)?.toInt() ?: 0

                    tvSmile.text = "$smile%"
                    tvLeftEye.text = "$leftEye%"
                    tvRightEye.text = "$rightEye%"

                    pbSmile.progress = smile
                    pbLeftEye.progress = leftEye
                    pbRightEye.progress = rightEye
                }
            }
            .addOnFailureListener {
                tvSmile.text = "Failed"
                tvLeftEye.text = "Failed"
                tvRightEye.text = "Failed"
                pbSmile.progress = 0
                pbLeftEye.progress = 0
                pbRightEye.progress = 0
            }
    }
}