package com.app.videopreloading.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.videopreloading.R
import com.app.videopreloading.callback.HomeScreenCallback
import com.app.videopreloading.ui.main.MainActivity.Companion.videosList
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment(), View.OnClickListener {
    private var homeScreenCallback: HomeScreenCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonPlayVideo1.setOnClickListener(this)
        buttonPlayVideo2.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            homeScreenCallback = context as HomeScreenCallback
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDetach() {
        super.onDetach()
        homeScreenCallback = null
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.buttonPlayVideo1 -> {
                homeScreenCallback?.openVideoPlayScreen(videosList[0])
            }
            R.id.buttonPlayVideo2 -> {
                homeScreenCallback?.openVideoPlayScreen(videosList[1])
            }
        }
    }
}