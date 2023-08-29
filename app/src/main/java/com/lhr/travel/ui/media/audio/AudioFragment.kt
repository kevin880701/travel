package com.lhr.travel.ui.media.audio

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhr.travel.R
import com.lhr.travel.data.audio.AudioResponse
import com.lhr.travel.databinding.FragmentAudioBinding
import com.lhr.travel.recyclerViewAdapter.AudioAdapter
import com.lhr.travel.ui.main.MainViewModel.Companion.currentLanguage
import com.lhr.travel.ui.media.MediaViewModel
import com.lhr.travel.ui.media.MediaViewModel.Companion.currentMediaId
import com.lhr.travel.ui.media.MediaViewModel.Companion.isPlaying
import com.lhr.travel.ui.media.MediaViewModel.Companion.previousMediaId
import java.io.IOException

class AudioFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentAudioBinding
    lateinit var viewModel: AudioViewModel
    lateinit var audioAdapter: AudioAdapter
    lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_audio, container, false
        )
        val view: View = binding!!.root
        mediaPlayer = MediaPlayer()
        audioAdapter = AudioAdapter(this.requireContext())

        initRecyclerView(binding.recyclerAudio)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            AudioViewModelFactory(requireActivity().application)
        )[AudioViewModel::class.java]

        viewModel.isProgressVisible.observe(viewLifecycleOwner) { isLoading -> // 當isDialogLoading改變時觸發Dialog
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE // 讀取資料時顯示轉圈
                binding.recyclerAudio.visibility = View.INVISIBLE
                binding.constraintMediaBar.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE // 讀取資料完成隱藏轉圈
                binding.recyclerAudio.visibility = View.VISIBLE
                binding.constraintMediaBar.visibility = View.VISIBLE
            }
        }

        currentLanguage.observe(viewLifecycleOwner) { newIds ->
            // 語言變更時執行的動作
            loadAudioApiData()
        }

        loadAudioApiData()

        currentMediaId.observe(viewLifecycleOwner, Observer { newMediaId ->
            if(newMediaId != null && audioAdapter != null) {
                prepareAudio()
                audioAdapter.notifyItemChanged(previousMediaId.value!!)
                audioAdapter.notifyItemChanged(newMediaId)
            }
            if(isPlaying.value!!){
                playAudio()
            }
            if(newMediaId != null) {
                previousMediaId.value = newMediaId
            }
        })

        isPlaying.observe(viewLifecycleOwner, Observer { newMediaId ->
            if(isPlaying.value!!){
                binding.imagePlay.visibility = View.INVISIBLE
                binding.imagePause.visibility = View.VISIBLE
            }else{
                binding.imagePlay.visibility = View.VISIBLE
                binding.imagePause.visibility = View.INVISIBLE

            }
            if(currentMediaId.value != null) {
                audioAdapter.notifyItemChanged(currentMediaId.value!!)
            }
        })

        binding.imagePrevious.setOnClickListener(this)
        binding.imagePlay.setOnClickListener(this)
        binding.imagePause.setOnClickListener(this)
        binding.imageNext.setOnClickListener(this)
    }

    fun initRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    this.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            adapter = audioAdapter
        }
    }

    fun loadAudioApiData() {
        viewModel.getAudioListObserver()
            .observe(viewLifecycleOwner, Observer<AudioResponse?> {
                if (it != null) {
                    //更新adapter
                    audioAdapter.listData = it.data
                    audioAdapter.notifyDataSetChanged()
                    // 初始化mediaPlayer
                    currentMediaId.value = 0
                    binding.textMediaTitle.text = it.data[currentMediaId.value!!].title
                } else {
                    Toast.makeText(this.context, "Error in fetching data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        viewModel.fetchAudioApi()
    }

    fun playAudio() {
        try {
            mediaPlayer?.setOnPreparedListener {
                // 語音準備完成
                mediaPlayer?.start()
            }
            mediaPlayer?.start()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun pauseAudio() {
        try {
            mediaPlayer?.pause()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun prepareAudio(){
        binding.textMediaTitle.text = audioAdapter.listData[currentMediaId.value!!].title
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(audioAdapter.listData[currentMediaId.value!!].url)
        mediaPlayer?.prepareAsync()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imagePrevious -> {
                isPlaying.value = true
                currentMediaId.value = currentMediaId.value?.minus(1)
                if (currentMediaId.value != null && currentMediaId.value!! < 0) {
                    currentMediaId.value = audioAdapter.listData.size - 1
                }
            }
            R.id.imagePlay -> {
                isPlaying.value = true
                playAudio()
            }

            R.id.imagePause -> {
                isPlaying.value = false
                pauseAudio()
            }

            R.id.imageNext -> {
                isPlaying.value = true
                currentMediaId.value = currentMediaId.value?.plus(1)
                if (currentMediaId.value != null && currentMediaId.value!! >= audioAdapter.listData.size) {
                    currentMediaId.value = 0
                }
            }
        }
    }

}