package barissaglam.client.wallpaperapp.presentation.photos

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import barissaglam.client.wallpaperapp.R
import barissaglam.client.wallpaperapp.base.extension.observeNonNull
import barissaglam.client.wallpaperapp.base.view.BaseFragment
import barissaglam.client.wallpaperapp.databinding.FragmentPhotosBinding
import barissaglam.client.wallpaperapp.presentation.photos.adapter.PhotosAdapter
import barissaglam.client.wallpaperapp.view.categoryview.Category
import barissaglam.client.wallpaperapp.view.categoryview.CategoryView

class PhotosFragment : BaseFragment<FragmentPhotosBinding, PhotosViewModel>(), CategoryView.CategoryClickListener {
    override val layoutResourceId: Int = R.layout.fragment_photos
    override val classTypeOfViewModel: Class<PhotosViewModel> = PhotosViewModel::class.java

    private val photosAdapter: PhotosAdapter by lazy { PhotosAdapter() }

    override fun init() {
        setupRecyclerView()
        setupCategoryView()
    }

    override fun setUpViewModelStateObservers() {
        viewModel.liveDataViewState_.observeNonNull(viewLifecycleOwner) { viewState ->
            binding.viewState = viewState
        }
        runOnlyFirstInit { observePhotosData() }
    }

    private fun observePhotosData() {
        viewModel.getMovieList().observeNonNull(viewLifecycleOwner) {
            photosAdapter.submitList(it)
        }
    }

    override fun setupClickListeners() {
        photosAdapter.onPhotoItemClick = { photo ->
            findNavController().navigate(PhotosFragmentDirections.actionPhotosFragmentToPhotoDetailFragment(photoId = photo.id))
        }
        binding.imageButtonFavorite.setOnClickListener {
            findNavController().navigate(PhotosFragmentDirections.actionPhotosFragmentToFavoriteFragment())
        }
    }

    private fun setupCategoryView() {
        CategoryView.Builder()
            .view(binding.categoryView)
            .categoryList(viewModel.categoryList)
            .listener(this@PhotosFragment)
            .startIndex(viewModel.selectedCategoryIndex)
            .build()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            (layoutManager as GridLayoutManager).spanSizeLookup = photosAdapter.spanSizeLookup
            adapter = photosAdapter
            itemAnimator = null
        }
    }

    override fun onCategoryClick(category: Category, index: Int) {
        viewModel.selectedCategoryIndex = index
        observePhotosData()
    }

    override fun onDestroy() {
        photosAdapter.onPhotoItemClick = null
        super.onDestroy()
    }
}