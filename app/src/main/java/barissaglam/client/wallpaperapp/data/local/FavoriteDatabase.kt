package barissaglam.client.wallpaperapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import barissaglam.client.wallpaperapp.data.local.dao.FavoritePhotoDao
import barissaglam.client.wallpaperapp.data.local.entity.FavoritePhotoEntity

@Database(entities = [FavoritePhotoEntity::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract fun getFavoritePhotoDao(): FavoritePhotoDao
}