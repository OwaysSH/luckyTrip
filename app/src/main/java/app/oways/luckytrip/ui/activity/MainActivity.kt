package app.oways.luckytrip.ui.activity

import android.os.Bundle
import app.oways.luckytrip.R
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : LocalizationActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}