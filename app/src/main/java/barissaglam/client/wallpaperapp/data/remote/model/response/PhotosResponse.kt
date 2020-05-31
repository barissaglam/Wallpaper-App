package barissaglam.client.wallpaperapp.data.remote.model.response

import barissaglam.client.wallpaperapp.data.remote.model.Photo
import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: ArrayList<Photo>
)