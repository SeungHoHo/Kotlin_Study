package com.seungho.android.myapplication.movieinformationex

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.icu.text.DecimalFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.seungho.android.myapplication.movieinformationex.data.retrofit.MovieModel
import com.seungho.android.myapplication.movieinformationex.databinding.ActivityDetailMovieBinding
import com.seungho.android.myapplication.movieinformationex.util.GlideApp

class DetailMovieActivity: AppCompatActivity() {
    private val binding by lazy { ActivityDetailMovieBinding.inflate(layoutInflater) }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        updateUI()
        requestWriteStoragePermission()

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.videoImageView.setOnClickListener {
            showVideoLink()
        }

        binding.moviePosterImageView.setOnLongClickListener {

            alertDownloadImage()

            return@setOnLongClickListener (true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI() { //UI 업데이트하기
        val data = intent.getSerializableExtra("MovieModel") as MovieModel
        GlideApp
            .with(this)
            .load(data.stillCut)
            .into(binding.stillCutImageView)
        binding.movieTitleTextView.text = data.title
        GlideApp
            .with(this)
            .load(data.poster)
            .into(binding.moviePosterImageView)
        binding.movieDateTextView.text = "${data.date} 개봉"
        binding.movieGerneTextView.text = "장르 : ${data.gerne}"
        binding.movieTimeTextView.text = "상영시간 : ${data.time}분"
        binding.movieGradeTextView.text = "등급 : ${data.grade}세 관람가"
        binding.movieRateTextView.text = "평점 : ${data.rate} / 10"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val myFormatter = DecimalFormat("###,###")
            binding.movieWatcherTextView.text = "누적 관객 : ${myFormatter.format(data.watcher)}명"
        }
        binding.movieDirectorTextView.text = "감독 : ${data.director}"
        binding.movieActorTextView.text = "출연 배우 : ${data.actor}"
        binding.synopsisTextView.text = data.synopsis
        GlideApp
            .with(this)
            .load(data.stillCut)
            .into(binding.videoImageView)
    }

    private fun showVideoLink() { //AlertDialog1
        val data = intent.getSerializableExtra("MovieModel") as MovieModel
        val builder = AlertDialog.Builder(this)
        builder.setTitle("예고편 링크 이동")
        builder.setMessage("'예' 버튼을 누르면 예고편 웹사이트로 이동됩니다.")
        builder.setPositiveButton("예") { _, _ ->
            val intent2 = Intent(Intent.ACTION_VIEW, Uri.parse(data.video))
            startActivity(intent2)
        }
        builder.setNegativeButton("아니요") { _, _ -> }
        builder.show()
    }

    override fun onRequestPermissionsResult( //권한 요청에 대한 결과 받기
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode === REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty()) {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) {
                        val builder = AlertDialog.Builder(this) //AlertDialog2
                        builder.setTitle("권한 거부됨")
                        builder.setMessage("권한을 거부하여 이전 화면으로 이동되었습니다.")
                        builder.setPositiveButton("확인") { _, _ ->
                            finish()
                        }
                        builder.setCancelable(false)
                        builder.show()
                    }
                }
            }
        }
    }

    private fun requestWriteStoragePermission() { //권한 요청하기
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION
        )
    }

    private fun alertDownloadImage() { //AlertDialog3
        val data = intent.getSerializableExtra("MovieModel") as MovieModel
        val builder = AlertDialog.Builder(this)
        builder.setTitle("이미지 다운로드")
        builder.setMessage("이미지를 다운로드 하시겠습니까?")
        builder.setPositiveButton("예") { dialog, _ ->
            downloadPhoto(data.poster)
            dialog.dismiss()
        }
        builder.setNegativeButton("아니요") { _, _ -> }
        builder.show()
    }
    private fun downloadPhoto(photoUrl: String) { //포스터 다운로드
        photoUrl

        Glide.with(this)
            .asBitmap() //비트맵으로 만들기
            .load(photoUrl)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(
                object : CustomTarget<Bitmap>(SIZE_ORIGINAL, SIZE_ORIGINAL) {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        saveBitmapToMediaStore(resource)

                        Toast.makeText(this@DetailMovieActivity, "다운로드 완료", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        Toast.makeText(this@DetailMovieActivity, "다운로드 중....", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        Toast.makeText(this@DetailMovieActivity, "다운로드 실패...", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) = Unit
                })
    }

    private fun saveBitmapToMediaStore(bitmap: Bitmap) {
        val fileName = "${System.currentTimeMillis()}.jpg" //파일이름
        val resolver = applicationContext.contentResolver
        val imageCollectionUri =
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Images.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }
        val imageDetails = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        val imageUri = resolver.insert(imageCollectionUri, imageDetails)

        imageUri ?: return

        resolver.openOutputStream(imageUri).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            imageDetails.clear()
            imageDetails.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(imageUri, imageDetails, null, null)
        }
    }

    companion object {
        private const val REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 101
    }
}