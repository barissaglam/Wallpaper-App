package barissaglam.client.wallpaperapp.data.viewitem

data class PhotoDetailViewItem(
    val id: String,
    val photoOwnerName: String,
    val photoOwnerProfile: String,
    val license: String,
    val source: String,
    val rawImageUrl: String,
    val fullImageUrl: String,
    val regularImageUrl: String,
    val smallImageUrl: String,
    val thumbImageUrl: String
) {
}