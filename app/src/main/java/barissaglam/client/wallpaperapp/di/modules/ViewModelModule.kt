package barissaglam.client.wallpaperapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import barissaglam.client.wallpaperapp.di.ViewModelFactory
import barissaglam.client.wallpaperapp.di.ViewModelKey
import barissaglam.client.wallpaperapp.presentation.detail.PhotoDetailViewModel
import barissaglam.client.wallpaperapp.presentation.favorite.FavoriteViewModel
import barissaglam.client.wallpaperapp.presentation.photos.PhotosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @get:Binds
    val ViewModelFactory.viewModelFactory: ViewModelProvider.Factory

    @get:IntoMap
    @get:Binds
    @get:ViewModelKey(PhotosViewModel::class)
    val PhotosViewModel.bindsPhotosViewModel: ViewModel

    @get:IntoMap
    @get:Binds
    @get:ViewModelKey(PhotoDetailViewModel::class)
    val PhotoDetailViewModel.bidsPhotoDetailViewModel: ViewModel

    @get:IntoMap
    @get:Binds
    @get:ViewModelKey(FavoriteViewModel::class)
    val FavoriteViewModel.bidsFavoriteViewModel: ViewModel

}