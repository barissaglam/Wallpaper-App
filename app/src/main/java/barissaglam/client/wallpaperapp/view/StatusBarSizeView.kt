package barissaglam.client.wallpaperapp.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import barissaglam.client.wallpaperapp.util.ThemeUtil

class StatusBarSizeView : View {

    companion object {

        // status bar saved size
        var heightSize: Int = 0
    }

    constructor(context: Context) :
        super(context) {
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet?) :
        super(context, attrs) {
        this.init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr) {
        this.init()
    }

    private fun init() {
        heightSize = ThemeUtil.getStatusBarHeight(context)
        post {
            applyHeight(heightSize)
        }
    }

    private fun applyHeight(height: Int) {
        val lp = this.layoutParams
        lp.height = height
        this.layoutParams = lp
    }

}