package barissaglam.client.wallpaperapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("type") val type: String?,
    @SerializedName("title") val title: String?
)