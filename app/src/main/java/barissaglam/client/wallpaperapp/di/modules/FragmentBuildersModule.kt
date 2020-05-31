package barissaglam.client.wallpaperapp.di.modules

import barissaglam.client.wallpaperapp.presentation.detail.PhotoDetailFragment
import barissaglam.client.wallpaperapp.presentation.favorite.FavoriteFragment
import barissaglam.client.wallpaperapp.presentation.photos.PhotosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuildersModule {

    @get:ContributesAndroidInjector
    val contributePhotosFragment: PhotosFragment

    @get:ContributesAndroidInjector
    val contributePhotoDetailFragment: PhotoDetailFragment

    @get:ContributesAndroidInjector
    val contributeFavoriteFragment: FavoriteFragment


}
