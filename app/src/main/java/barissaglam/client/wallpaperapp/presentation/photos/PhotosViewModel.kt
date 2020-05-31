package barissaglam.client.wallpaperapp.presentation.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import barissaglam.client.wallpaperapp.base.viewmodel.BaseViewModel
import barissaglam.client.wallpaperapp.data.viewitem.PhotoViewItem
import barissaglam.client.wallpaperapp.domain.usecase.CategoryUseCase
import barissaglam.client.wallpaperapp.domain.usecase.PhotosUseCase
import barissaglam.client.wallpaperapp.presentation.photos.helper.MovieItemPaginationHelper
import barissaglam.client.wallpaperapp.util.Resource
import barissaglam.client.wallpaperapp.view.categoryview.Category
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class PhotosViewModel @Inject constructor(categoryUseCase: CategoryUseCase, private val photosUseCase: PhotosUseCase) : BaseViewModel() {
    /** LiveData for ViewState **/
    private val liveDataViewState = MutableLiveData<PhotosFragmentViewState>()
    val liveDataViewState_: LiveData<PhotosFragmentViewState> = liveDataViewState

    var page = FIRST_PAGE
    var selectedCategoryIndex = 0
    val categoryList = ArrayList<Category>()
    private var paginationHelper = MovieItemPaginationHelper(this)

    init {
        categoryList.addAll(categoryUseCase.getCategories())
    }

    fun getMovieList(): LiveData<PagedList<PhotoViewItem>> {
        val config = PagedList.Config.Builder()
            .setPageSize(30)
            .setInitialLoadSizeHint(30)
            .setPrefetchDistance(5)
            .setEnablePlaceholders(true)
            .build()
        return initPagedListBuilder(config).build()
    }

    private fun initPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, PhotoViewItem> {
        val dataSourceFactory = object : DataSource.Factory<Int, PhotoViewItem>() {
            override fun create(): DataSource<Int, PhotoViewItem> {
                return paginationHelper
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }

    fun getPhotosByQuery(updateViewState: Boolean, block: (PhotosFragmentViewState) -> Unit) {
        photosUseCase.getPhotosByQuery(query = categoryList[selectedCategoryIndex].name, page = page)
            .onEach { response ->
                val viewState = PhotosFragmentViewState(response)
                block(viewState)
                if (updateViewState || response is Resource.Success || response is Resource.Error) {
                    liveDataViewState.value = viewState
                }
                /*
                    Pagination yaparken her sayfa çekildiğinde (1,2,3...) bu fonksiyon çalışacağı için genel sayfadaki loading progressbar 'updateViewState' ilk sayfada true gönderilerek gösterildi.
                    Diğer isteklerde sayfadaki loading progressbar değil de recyclerview içindeki progressbar gösterileceği için 'updateViewState' diğer sayfalarda false gönderilerek
                    Genel sayfadaki progressbar gösterilmemesi sağlandı. Resource error olduğunda error layoutu gösterileceği için ve success olduğunda her yeni gelen data ile işlem yapılacağı için
                    Success ve Error durumlarında da ViewState güncellenmesi sağlandı.
                    Daha iyi bir çözüm bulunabilir :) :)
                 */
            }.launchIn(viewModelScope)
    }

    companion object {
        const val FIRST_PAGE = 1
    }

}