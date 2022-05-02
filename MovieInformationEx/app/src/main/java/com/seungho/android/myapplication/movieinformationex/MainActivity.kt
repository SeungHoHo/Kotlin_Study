package com.seungho.android.myapplication.movieinformationex

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.opengl.Visibility
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.seungho.android.myapplication.movieinformationex.adapter.PosterAdapter
import com.seungho.android.myapplication.movieinformationex.data.repository.MovieRepository
import com.seungho.android.myapplication.movieinformationex.databinding.ActivityMainBinding
import com.seungho.android.myapplication.movieinformationex.data.retrofit.MovieModel
import com.seungho.android.myapplication.viewmodel.MainViewModel
import com.seungho.android.myapplication.viewmodel.MainViewModelFactory
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var viewModel: MainViewModel

    var isSortedDate : Boolean = true
    var isSortedWatcher : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getMovie()

        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(true)

        if(!isNetworkAvailable(this)){ //네트워크 꺼져있으면 어플 재시작
            val builder = AlertDialog.Builder(this) //AlertDialog2
            builder.setMessage("네트워크 연결을 확인 후 다시 시도해주세요")
            builder.setPositiveButton("확인") { _, _ ->
                ActivityCompat.finishAffinity(this)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                exitProcess(0)
            }
            builder.setCancelable(false)
            builder.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortedWatcher -> {
                if (isSortedWatcher) {
                    Toast.makeText(this, "이미 관객수 순으로 정렬되어 있습니다", Toast.LENGTH_SHORT).show()
                    return false
                } else {
                    Toast.makeText(this, "관객수으로 정렬하기 적용됨", Toast.LENGTH_SHORT).show()
                    isSortedWatcher = true
                    isSortedDate = false
                    getMovie()
                }

            }

            R.id.sortedDate -> {
                if (isSortedDate) {
                    Toast.makeText(this, "이미 개봉순으로 정렬되어 있습니다", Toast.LENGTH_SHORT).show()
                    return false
                } else {
                    Toast.makeText(this, "개봉순으로 정렬하기 적용됨", Toast.LENGTH_SHORT).show()
                    isSortedDate = true
                    isSortedWatcher = false
                    getMovie()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getMovie() {
        val movieRepository = MovieRepository()
        val viewModelFactory = MainViewModelFactory(movieRepository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getMovieAPI()
        viewModel.myResponse.observe(this, Observer { it ->
            if(it.isSuccessful) {
                if (isSortedDate) {
                        updateUI(it.body()?.movies!!.sortedWith(compareBy { it.date }).reversed())
                }
                if (isSortedWatcher) {
                        updateUI(it.body()?.movies!!.sortedWith(compareBy { it.watcher}).reversed())
                }
            } else {
                Toast.makeText(this, "데이터 불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(listData: List<MovieModel>) {
        val posterAdapter = PosterAdapter(listData , this)
        binding.recyclerView.adapter = posterAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)
    }

    private fun isNetworkAvailable(context: Context): Boolean { //인터넷 상태 확인
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw      = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false

            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected ?: false
        }
    }
}