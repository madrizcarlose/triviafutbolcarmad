package com.example.triviafutbol.ui.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import com.example.triviafutbol.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SoundManager {

    private var appContext: Context? = null
    private var soundPool: SoundPool? = null
    private var bgPlayer: MediaPlayer? = null

    private var whistleId = 0
    private var cheerId = 0
    private var clickId = 0

    private var isWhistleLoaded = false
    private var isCheerLoaded = false
    private var isClickLoaded = false

    private val _isMuted = MutableStateFlow(false)
    val isMuted: StateFlow<Boolean> = _isMuted.asStateFlow()

    fun init(context: Context) {
        if (appContext != null) return
        appContext = context.applicationContext
        val ctx = appContext ?: return

        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(6)
                .setAudioAttributes(audioAttributes)
                .build()

            soundPool?.setOnLoadCompleteListener { _, sampleId, status ->
                if (status == 0) {
                    when (sampleId) {
                        whistleId -> isWhistleLoaded = true
                        cheerId -> isCheerLoaded = true
                        clickId -> isClickLoaded = true
                    }
                }
            }

            whistleId = soundPool?.load(ctx, R.raw.whistle, 1) ?: 0
            cheerId = soundPool?.load(ctx, R.raw.cheer, 1) ?: 0
            clickId = soundPool?.load(ctx, R.raw.click, 1) ?: 0

            initStadiumAmbient(ctx)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initStadiumAmbient(context: Context) {
        try {
            bgPlayer = MediaPlayer.create(
                context,
                R.raw.stadium_ambient,
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build(),
                (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager).generateAudioSessionId()
            )?.apply {
                isLooping = true
                setVolume(0.2f, 0.2f)
            }
            if (!_isMuted.value) {
                bgPlayer?.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun toggleMute() {
        _isMuted.value = !_isMuted.value
        if (_isMuted.value) {
            bgPlayer?.pause()
        } else {
            if (bgPlayer?.isPlaying == false) {
                bgPlayer?.start()
            }
        }
    }

    fun playIntro() {
        if (_isMuted.value) return
        try {
            playFallback(R.raw.intro)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playWhistle() {
        if (_isMuted.value) return
        if (isWhistleLoaded && soundPool != null) {
            soundPool?.play(whistleId, 1.0f, 1.0f, 1, 0, 1.0f)
        } else {
            playFallback(R.raw.whistle)
        }
    }

    fun playCheer() {
        if (_isMuted.value) return
        if (isCheerLoaded && soundPool != null) {
            soundPool?.play(cheerId, 1.0f, 1.0f, 1, 0, 1.0f)
        } else {
            playFallback(R.raw.cheer)
        }
    }

    fun playClick() {
        if (_isMuted.value) return
        if (isClickLoaded && soundPool != null) {
            soundPool?.play(clickId, 0.7f, 0.7f, 1, 0, 1.0f)
        } else {
            playFallback(R.raw.click)
        }
    }

    private fun playFallback(resId: Int) {
        val ctx = appContext ?: return
        try {
            val mp = MediaPlayer.create(
                ctx,
                resId,
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build(),
                (ctx.getSystemService(Context.AUDIO_SERVICE) as AudioManager).generateAudioSessionId()
            )
            mp?.setOnCompletionListener { player ->
                player.release()
            }
            mp?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
