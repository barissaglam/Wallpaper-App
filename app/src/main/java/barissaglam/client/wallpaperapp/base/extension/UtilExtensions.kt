package barissaglam.client.wallpaperapp.base.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData


inline fun <T> LiveData<T>.observeNonNull(
    owner: LifecycleOwner,
    crossinline observer: (t: T) -> Unit
) {
    this.observe(owner, androidx.lifecycle.Observer {
        it?.let(observer)
    })
}
