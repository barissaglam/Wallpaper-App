package barissaglam.client.wallpaperapp.di.modules

import android.content.Context
import androidx.room.Room
import barissaglam.client.wallpaperapp.data.local.FavoriteDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(context: Context): FavoriteDatabase = Room.databaseBuilder(context, FavoriteDatabase::class.java, "local_database").build()

    @Provides
    @Singleton
    fun provideFavoritePhotoDao(db: FavoriteDatabase) = db.getFavoritePhotoDao()
}