package barissaglam.client.wallpaperapp.di

import android.content.Context
import barissaglam.client.wallpaperapp.App
import barissaglam.client.wallpaperapp.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        NetworkModule::class,
        FragmentBuildersModule::class,
        ServiceModule::class,
        ViewModelModule::class,
        LocalDataModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AndroidInjector<App>
    }
}