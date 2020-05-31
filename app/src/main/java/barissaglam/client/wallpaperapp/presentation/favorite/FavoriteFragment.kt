package barissaglam.client.wallpaperapp.presentation.favorite

import androidx.navigation.fragment.findNavController
import barissaglam.client.wallpaperapp.R
import barissaglam.client.wallpaperapp.base.extension.observeNonNull
import barissaglam.client.wallpaperapp.base.view.BaseFragment
import barissaglam.client.wallpaperapp.databinding.FragmentFavoriteBinding
import barissaglam.client.wallpaperapp.presentation.favorite.adapter.FavoriteAdapter

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_favorite
    override val classTypeOfViewModel: Class<FavoriteViewModel> = FavoriteViewModel::class.java

    private val adapter: FavoriteAdapter by lazy { FavoriteAdapter() }

    override fun init() {
        binding.recyclerView.adapter = adapter
    }

    override fun setupClickListeners() {
        binding.imageButtonClose.setOnClickListener { findNavController().popBackStack() }
        adapter.onPhotoItemClick = { findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToPhotoDetailFragment(photoId = it.id)) }
    }

    override fun setUpViewModelStateObservers() {
        viewModel.favoriteListLiveData_.observeNonNull(viewLifecycleOwner, { viewState ->
            binding.viewState = viewState
            adapter.submitList(viewState.getData())
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.onPhotoItemClick = null
    }
}