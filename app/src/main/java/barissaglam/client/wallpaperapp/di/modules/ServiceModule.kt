package barissaglam.client.wallpaperapp.di.modules

import barissaglam.client.wallpaperapp.service.PhotoDownloadService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ServiceModule {
    @get:ContributesAndroidInjector
    val contributePhotoDownloadService: PhotoDownloadService
}