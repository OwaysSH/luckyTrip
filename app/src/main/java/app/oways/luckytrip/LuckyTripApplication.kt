package app.oways.luckytrip

import com.akexorcist.localizationactivity.ui.LocalizationApplication
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class LuckyTripApplication: LocalizationApplication() {

    override fun getDefaultLanguage(): Locale {

        return Locale.getDefault()
    }
}