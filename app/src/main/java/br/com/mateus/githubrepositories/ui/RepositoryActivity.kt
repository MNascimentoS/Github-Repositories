package br.com.mateus.githubrepositories.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.mateus.githubrepositories.BuildConfig
import br.com.mateus.githubrepositories.R
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute


class RepositoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
        if (!BuildConfig.DEBUG) {
            AppCenter.start(
                application, BuildConfig.APPCENTER_KEY,
                Analytics::class.java, Crashes::class.java, Distribute::class.java
            )
        }
    }

    companion object {
        const val CARD_TRANSITION = "card"
    }

}