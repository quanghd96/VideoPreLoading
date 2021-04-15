package com.app.videopreloading.ui.main

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.app.videopreloading.MyApp.Companion.cacheDataSourceFactory
import com.app.videopreloading.R
import com.app.videopreloading.callback.HomeScreenCallback
import com.app.videopreloading.utility.Constants
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.cache.CacheWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
    HomeScreenCallback {

    private val cachingJobs: ArrayList<CacheWriter> = arrayListOf()

    companion object {
        val videosList: ArrayList<String> = arrayListOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        videosList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
        videosList.add("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4")
        preCacheVideo(videosList)
    }

    override fun openVideoPlayScreen(videoUrl: String) {
        val bundle = Bundle()
        bundle.putString(Constants.VIDEO_URL, videoUrl)

        findNavController(R.id.container).navigate(
            R.id.action_mainFragment_to_playerFragment, bundle
        )
    }

    private fun preCacheVideo(videosList: ArrayList<String>) {
        for (videoUrl in videosList) {
            val videoUri = Uri.parse(videoUrl)
            val dataSpec = DataSpec(videoUri)

            GlobalScope.launch(Dispatchers.IO) {
                val cacheWriter =
                    CacheWriter(
                        cacheDataSourceFactory.createDataSource(),
                        dataSpec,
                        true,
                        null,
                        null
                    )
                cacheWriter.cache()
                cachingJobs.add(cacheWriter)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        cachingJobs.forEach { it.cancel() }
    }
}
