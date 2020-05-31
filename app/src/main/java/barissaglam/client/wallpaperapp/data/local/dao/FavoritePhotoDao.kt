package barissaglam.client.wallpaperapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import barissaglam.client.wallpaperapp.data.local.entity.FavoritePhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritePhotoDao {

    @Insert
    suspend fun addPhotoToFavorite(favoritePhotoEntity: FavoritePhotoEntity)

    @Query("DELETE FROM fav_photos WHERE id=:photoId")
    suspend fun deletePhotoFromFavorite(photoId: String)

    @Query("SELECT * FROM fav_photos ORDER BY code DESC")
    fun getFavoritePhotos(): Flow<List<FavoritePhotoEntity>>

}