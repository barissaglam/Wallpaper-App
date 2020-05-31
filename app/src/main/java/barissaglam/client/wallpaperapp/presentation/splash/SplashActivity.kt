package barissaglam.client.wallpaperapp.presentation.splash

import android.os.Bundle
import barissaglam.client.wallpaperapp.R
import barissaglam.client.wallpaperapp.presentation.home.HomeActivity
import dagger.android.support.DaggerAppCompatActivity

class SplashActivity : DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        HomeActivity.start(this)
        supportFinishAfterTransition()

    }

}