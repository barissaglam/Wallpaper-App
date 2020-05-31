package barissaglam.client.wallpaperapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: String?,
    @SerializedName("updated_at") val updatedAt: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("first_name") val first_name: String?,
    @SerializedName("last_name") val last_name: String?,
    @SerializedName("portfolio_url") val portfolio_url: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("links") val links: Link
)