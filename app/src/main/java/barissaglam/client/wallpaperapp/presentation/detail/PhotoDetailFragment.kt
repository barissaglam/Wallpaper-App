package barissaglam.client.wallpaperapp.presentation.detail

import android.app.WallpaperManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.view.drawToBitmap
import androidx.navigation.fragment.findNavController
import barissaglam.client.wallpaperapp.R
import barissaglam.client.wallpaperapp.base.extension.observeNonNull
import barissaglam.client.wallpaperapp.base.extension.orFalse
import barissaglam.client.wallpaperapp.base.view.BaseFragment
import barissaglam.client.wallpaperapp.data.enum.PermissionType
import barissaglam.client.wallpaperapp.data.enum.SnackBarType.ERROR
import barissaglam.client.wallpaperapp.data.enum.SnackBarType.SUCCESS
import barissaglam.client.wallpaperapp.databinding.FragmentPhotoDetailBinding
import barissaglam.client.wallpaperapp.service.PhotoDownloadService
import barissaglam.client.wallpaperapp.util.PermissionManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItems

class PhotoDetailFragment : BaseFragment<FragmentPhotoDetailBinding, PhotoDetailViewModel>() {
    override val layoutResourceId: Int = R.layout.fragment_photo_detail
    override val classTypeOfViewModel: Class<PhotoDetailViewModel> = PhotoDetailViewModel::class.java

    private val wallpaperManager by lazy { WallpaperManager.getInstance(requireContext()) }

    override fun setUpViewModelStateObservers() {
        viewModel.liveDataViewState_.observeNonNull(viewLifecycleOwner, { viewState ->
            binding.viewState = viewState
        })
    }

    override fun initStartRequest() {
        viewModel.getPhotoDetail()
    }

    override fun setupClickListeners() {
        binding.imageButtonClose.setOnClickListener { findNavController().popBackStack() }
        binding.imageButtonFavorite.setOnClickListener { onFavoriteButtonClicked() }
        binding.layoutFooter.buttonDownload.setOnClickListener { startDownloadService() }
        binding.layoutFooter.buttonSet.setOnClickListener { setWallpaper() }
    }

    private fun onFavoriteButtonClicked() {
        if (binding.viewState?.isFavorite().orFalse()) viewModel.deleteFavorite()
        else viewModel.addFavorite()
    }

    /**
     * R.array.wallpaper_download_options
     * 0 = Small
     * 1 = Regular
     * 2 = Full
     * 3 = Raw
     */
    private fun startDownloadService() {
        if (PermissionManager.checkPermission(requireContext(), PermissionType.WRITE_EXTERNAL_STORAGE)) {
            MaterialDialog(requireContext()).show {
                listItems(R.array.wallpaper_download_options) { _, index, _ ->
                    activity?.startService(Intent(activity, PhotoDownloadService::class.java).apply
                    { putExtra(PhotoDownloadService.EXTRA_PHOTO_URL, getImageUrl(index)) })
                    dismiss()
                }
                title(text = "Select Photo Size")
                cancelable(false)
                negativeButton(text = "CANCEL")
            }
        } else
            PermissionManager.requestPermission(this, PermissionType.WRITE_EXTERNAL_STORAGE)
    }

    private fun getImageUrl(index: Int): String? {
        return when (index) {
            DOWNLOAD_SMALL -> binding.viewState?.getData()?.smallImageUrl
            DOWNLOAD_REGULAR -> binding.viewState?.getData()?.regularImageUrl
            DOWNLOAD_FULL -> binding.viewState?.getData()?.fullImageUrl
            DOWNLOAD_RAW -> binding.viewState?.getData()?.rawImageUrl
            else -> ""
        }
    }

    private fun setWallpaper() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        showProgress()
        Thread(Runnable {
            try {
                val bitmap = binding.imageViewPhoto.drawToBitmap()
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM)
                showSnackBar(message = getString(R.string.wallpaper_applied), type = SUCCESS)
            } catch (e: Exception) {
                showSnackBar(message = getString(R.string.error_unknown), type = ERROR)
            }
            dismissProgress()
        }).start()
    } else
        showSnackBar(message = getString(R.string.not_supported_set_wallpaper), type = ERROR)


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PermissionManager.RC_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDownloadService()
            } else {
                showSnackBar(message = getString(R.string.error_no_permission), type = ERROR)
            }
        }
    }


    companion object {
        /* Download options indexes*/
        private const val DOWNLOAD_SMALL = 0
        private const val DOWNLOAD_REGULAR = 1
        private const val DOWNLOAD_FULL = 2
        private const val DOWNLOAD_RAW = 3
    }
}