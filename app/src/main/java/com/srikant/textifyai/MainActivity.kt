package com.srikant.textifyai

import android.R
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieDrawable
import com.srikant.textifyai.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tts: TextToSpeech

    // Language map for Spinner
    private val languageMap = hashMapOf(
        "English (UK)" to Locale.UK,
        "English (US)" to Locale.US,
        "Hindi" to Locale("hi", "IN"),
        "Marathi" to Locale("mr", "IN"),
        "Odia" to Locale("or", "IN"),
        "Bengali" to Locale("bn", "IN"),
        "Tamil" to Locale("ta", "IN"),
        "Telugu" to Locale("te", "IN"),
        "Gujarati" to Locale("gu", "IN"),
        "Kannada" to Locale("kn", "IN")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize TextToSpeech
        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts.language = Locale.UK // Default language
            }
        }

        // Play Animation Continuously
        binding.laSplashAnimation.apply {
            visibility = View.VISIBLE
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }

        // Populate Spinner with language options
        val adapter = ArrayAdapter(
            this,
            R.layout.simple_spinner_dropdown_item,
            languageMap.keys.toList()
        )
        binding.spinnerLang.adapter = adapter

        // Handle Button Click
        binding.speakbtns.setOnClickListener {
            val text = binding.getTextt.text.toString()
            val selectedLang = binding.spinnerLang.selectedItem.toString()

            if (text.isEmpty()) {
                Toast.makeText(this, "Please enter text", Toast.LENGTH_SHORT).show()
            } else {
                val selectedLocale = languageMap[selectedLang]
                val result = tts.setLanguage(selectedLocale)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(this, "Selected Language Not Supported", Toast.LENGTH_SHORT).show()
                } else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }
        }
    }

    // Shutdown TTS and Stop Animation on Activity Destroy
    override fun onDestroy() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
        binding.laSplashAnimation.cancelAnimation() // Stop animation
        super.onDestroy()
    }
}
