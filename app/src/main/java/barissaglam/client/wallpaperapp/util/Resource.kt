package barissaglam.client.wallpaperapp.util


/**
 * I got this class from https://github.com/muratcanbur/ProjectX/blob/master/app/src/main/java/co/icanteach/projectx/common/Resource.kt
 */

sealed class Resource<out T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error(val exception: Throwable) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()
}

inline fun <T, R> Resource<T>.map(transform: (T) -> R): Resource<R> {
    return when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Error -> Resource.Error(exception)
        is Resource.Loading -> Resource.Loading
        is Resource.Empty -> Resource.Empty
    }
}

enum class State {
    LOADING,
    SUCCESS,
    ERROR,
    EMPTY,
    NONE
}