package barissaglam.client.wallpaperapp.domain.usecase

import barissaglam.client.wallpaperapp.data.repository.LocalRepository
import barissaglam.client.wallpaperapp.view.categoryview.Category
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    fun getCategories(): ArrayList<Category> {
        return localRepository.getCategories()
    }
}