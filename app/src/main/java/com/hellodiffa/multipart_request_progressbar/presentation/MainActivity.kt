package com.hellodiffa.multipart_request_progressbar.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.hellodiffa.multipart_request_progressbar.R
import com.hellodiffa.multipart_request_progressbar.remote.result.ResultState
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val PICK_IMAGE = 100;
    private val RC_READ_FILE = 999;

    private val viewModel by inject<MainViewModel>()
    private lateinit var selectedImage: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setView()
        observeData()
        onClicked()
    }

    private fun setView() {
        hideProgress()
    }

    private fun onClicked() {
        btnChooseImage.setOnClickListener {
            readPermission()
        }
        btnUploadImage.setOnClickListener {
            showProgress()
            viewModel.uploadFile(selectedImage, applicationContext)
        }

        btnUploadWithoutProgress.setOnClickListener {
            viewModel.uploadFileWithoutProgress(selectedImage, applicationContext)
            hideProgress()
        }
    }

    private fun observeData() {
        viewModel.result.observe(this, Observer {
            when (it.status) {
                ResultState.Status.SUCCESS -> {
                    Toast.makeText(this, "Upload has been successed", Toast.LENGTH_SHORT).show()
                    hideProgress()
                    resetView()
                }
                ResultState.Status.ERROR -> {
                    Toast.makeText(this, it.error?.message, Toast.LENGTH_SHORT).show()
                    hideProgress()
                }
                ResultState.Status.LOADING -> {
                    if (it.data != null) {
                        val progress = (100 * it.data)
                        progressBar.progress = progress.toInt()
                        tvProgress.text = "${progress.toInt()}% "
                    }
                }
            }
        })
    }

    private fun resetView() {
        Glide.with(this)
            .load(resources.getDrawable(R.drawable.ic_image_black_24dp))
            .into(imgView)

        btnUploadImage.isEnabled  = false
        btnUploadWithoutProgress.isEnabled = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { showThumbnail(it) }
        }
    }

    private fun showThumbnail(data: Uri) {
        selectedImage = data
        Glide
            .with(this)
            .load(selectedImage)
            .into(imgView)
        btnUploadWithoutProgress.isEnabled = true
        btnUploadImage.isEnabled = true
    }

    private fun readPermission() {
        val perms = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            gotoGallery()
        } else {
            ActivityCompat.requestPermissions(
                this,
                perms,
                RC_READ_FILE
            )
        }
    }

    private fun gotoGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_READ_FILE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gotoGallery()
            } else {
                Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
        tvProgress.visibility = View.GONE
        tvProgress.text = ""
        progressBar.progress = 0
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
        tvProgress.visibility = View.VISIBLE
    }

}
