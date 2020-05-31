package barissaglam.client.wallpaperapp.di.modules

import barissaglam.client.wallpaperapp.presentation.home.HomeActivity
import barissaglam.client.wallpaperapp.presentation.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @get:ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    val bindHomeActivity: HomeActivity

    @get:ContributesAndroidInjector
    val bindSplashActivity: SplashActivity
}