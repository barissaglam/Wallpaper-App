package barissaglam.client.wallpaperapp.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import barissaglam.client.wallpaperapp.base.viewmodel.BaseViewModel
import barissaglam.client.wallpaperapp.data.viewitem.PhotoDetailViewItem
import barissaglam.client.wallpaperapp.domain.usecase.FavoriteUseCase
import barissaglam.client.wallpaperapp.domain.usecase.PhotoDetailUseCase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoDetailViewModel @Inject constructor(
    private val useCase: PhotoDetailUseCase,
    private val favoriteUseCase: FavoriteUseCase
) : BaseViewModel() {
    private val liveDataViewState = MutableLiveData<PhotoDetailFragmentViewState>()
    val liveDataViewState_: LiveData<PhotoDetailFragmentViewState> = liveDataViewState

    private lateinit var id: String

    override fun handleIntent(extras: Bundle) {
        val args = PhotoDetailFragmentArgs.fromBundle(extras)
        this.id = args.photoId
    }

    fun getPhotoDetail() {
        combine(
            favoriteUseCase.getFavoriteList(),
            useCase.getPhotoDetail(id = id),
            ::photoDetailPageCombiner
        )
            .onEach { liveDataViewState.value = it }
            .launchIn(viewModelScope)
    }

    fun addFavorite() {
        runIfDataIsNonNull { photoDetailViewItem ->
            viewModelScope.launch {
                favoriteUseCase.addFavorite(photoDetailViewItem)
            }
        }
    }

    fun deleteFavorite() {
        runIfDataIsNonNull { photoDetailViewItem ->
            viewModelScope.launch {
                favoriteUseCase.deleteFavorite(photoDetailViewItem.id)
            }
        }
    }

    private fun runIfDataIsNonNull(block: (PhotoDetailViewItem) -> Unit) {
        liveDataViewState.value?.getData()?.let {
            block(it)
        }
    }
}