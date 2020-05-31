package barissaglam.client.wallpaperapp.view.categoryview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import barissaglam.client.wallpaperapp.R
import barissaglam.client.wallpaperapp.databinding.ItemCategoryViewBinding


class CategoryView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : HorizontalScrollView(context, attrs) {

    private var listener: CategoryClickListener? = null
    private var radioGroup: RadioGroup
    private var startIndex = 0
    private val categoryList = ArrayList<Category>()

    init {
        View.inflate(context, R.layout.layout_category_view, this)
        this.radioGroup = findViewById(R.id.radioGroup)
    }

    private fun initCategoryView(categoryList: ArrayList<Category>) {
        this.categoryList.addAll(categoryList)
        if (categoryList.isNotEmpty()) {
            for ((index, category) in categoryList.withIndex()) {
                addItem(category, index)
            }
        }
    }

    private fun addItem(category: Category, index: Int) {
        val item = DataBindingUtil.inflate<ItemCategoryViewBinding>(LayoutInflater.from(context), R.layout.item_category_view, this, false)
        item.apply {
            viewState = CategoryViewItem(category, index)
            radioButton.id = index
            radioButton.isChecked = index == startIndex
            radioButton.setOnClickListener { listener?.onCategoryClick(category, index) }
        }
        radioGroup.addView(item.root)
    }

    private fun setClickListener(listener: CategoryClickListener) {
        this.listener = listener
    }

    private fun setStartIndex(index: Int) {
        this.startIndex = index
    }


    class Builder {
        private val categoryList = ArrayList<Category>()
        private lateinit var view: CategoryView
        private lateinit var categoryClickListener: CategoryClickListener
        private var startIndex: Int = 0

        fun view(view: CategoryView): Builder {
            this.view = view
            return this
        }

        fun categoryList(categoryList: ArrayList<Category>): Builder {
            this.categoryList.addAll(categoryList)
            return this
        }

        fun listener(categoryClickListener: CategoryClickListener): Builder {
            this.categoryClickListener = categoryClickListener
            return this
        }

        fun startIndex(startIndex: Int): Builder {
            this.startIndex = startIndex
            return this
        }


        fun build() {
            view.setClickListener(categoryClickListener)
            view.setStartIndex(startIndex)
            view.initCategoryView(categoryList)
        }
    }

    interface CategoryClickListener {
        fun onCategoryClick(category: Category, index: Int)
    }
}