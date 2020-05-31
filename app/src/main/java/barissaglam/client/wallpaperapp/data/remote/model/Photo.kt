package barissaglam.client.wallpaperapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("id") val id: String?,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("promoted_at") val promotedAt: String?,
    @SerializedName("width") val width: Int?,
    @SerializedName("height") val height: Int?,
    @SerializedName("color") val color: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("alt_description") val alt_description: String?,
    @SerializedName("urls") val urls: Url?,
    @SerializedName("links") val links: Link?,
    @SerializedName("likes") val likes: Int?,
    @SerializedName("user") val user: User?,
    @SerializedName("tags") val tags: List<Tag>?
)