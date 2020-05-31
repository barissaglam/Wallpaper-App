package barissaglam.client.wallpaperapp.base.extension

import android.content.Context
import androidx.fragment.app.Fragment

fun Fragment.runContextNotNull(block: (Context) -> Unit) {
    this.context?.let {
        block(it)
    }
}