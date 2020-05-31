package barissaglam.client.wallpaperapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import barissaglam.client.wallpaperapp.base.viewmodel.BaseViewModel
import barissaglam.client.wallpaperapp.domain.usecase.FavoriteUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val useCase: FavoriteUseCase) : BaseViewModel() {
    private val favoriteListLiveData = MutableLiveData<FavoriteFragmentViewState>()
    val favoriteListLiveData_: LiveData<FavoriteFragmentViewState> = favoriteListLiveData

    init {
        useCase.getFavoriteList()
            .onEach {
                favoriteListLiveData.value = FavoriteFragmentViewState(it)
            }
            .launchIn(viewModelScope)
    }
}