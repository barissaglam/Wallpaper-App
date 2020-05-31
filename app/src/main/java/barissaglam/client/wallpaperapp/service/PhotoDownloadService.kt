package barissaglam.client.wallpaperapp.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import barissaglam.client.wallpaperapp.R
import barissaglam.client.wallpaperapp.domain.usecase.PhotoDownloadUseCase
import barissaglam.client.wallpaperapp.util.Resource
import barissaglam.client.wallpaperapp.util.SingleMediaScanner
import dagger.android.DaggerIntentService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import javax.inject.Inject

class PhotoDownloadService : DaggerIntentService("PhotoDownloadService") {
    @Inject
    lateinit var useCase: PhotoDownloadUseCase

    private lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var notificationManager: NotificationManager

    override fun onHandleIntent(intent: Intent?) {
        intent?.extras?.let { bundle ->
            val imageUrl = bundle.getString(EXTRA_PHOTO_URL, "")
            setupNotification()
            startDownload(imageUrl)
        }
    }

    private fun setupNotification() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH).apply {
                setSound(null, null)
                enableVibration(false)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_hd_black_24dp)
            .setContentTitle(applicationContext.getString(R.string.app_name))
            .setContentText(getString(R.string.photo_downloading))
            .setDefaults(0)
            .setAutoCancel(true)
    }

    private fun startDownload(imageUrl: String) {
        if (imageUrl.isNotEmpty()) {
            useCase.downloadImage(imageUrl).onEach {
                when (it) {
                    is Resource.Loading -> {
                        notificationManager.notify(0, notificationBuilder.build())
                    }
                    is Resource.Success -> {
                        writeResponseOnOutputStream(it.data)
                    }
                    is Resource.Error -> {
                        onDownloadComplete(getString(R.string.error_unknown))
                    }
                }
            }.launchIn(GlobalScope)
        }
    }

    private fun writeResponseOnOutputStream(body: ResponseBody) {
        try {
            var readingData: Int
            val fileReader = ByteArray(1024 * 4)
            val fileSize = body.contentLength()
            val inputStream: InputStream = body.byteStream()
            val outputStream = getOutputStream()
            var downloadedData: Long = 0

            while (inputStream.read(fileReader).also { readingData = it } != -1) {
                downloadedData += readingData.toLong()
                val progress = ((downloadedData * 100).toDouble() / fileSize.toDouble()).toInt()
                updateNotification(progress)
                outputStream?.write(fileReader, 0, readingData)
            }
            onDownloadComplete(getString(R.string.download_success))
            outputStream?.flush()
            outputStream?.close()
            inputStream.close()
        } catch (exception: Exception) {
            onDownloadComplete(getString(R.string.error_unknown))
        }
    }

    private fun getOutputStream(): OutputStream? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + DIR_NAME
            val myDir = File(directory)
            if (!myDir.exists()) {
                myDir.mkdirs()
            }

            val file = File(myDir, UUID.randomUUID().toString() + FILE_EXTENSION)
            SingleMediaScanner(applicationContext, file)
            FileOutputStream(file)
        } else {
            val resolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, UUID.randomUUID().toString() + FILE_EXTENSION)
                put(MediaStore.MediaColumns.MIME_TYPE, "images/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, (Environment.DIRECTORY_DCIM).toString() + DIR_NAME)
            }
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)?.let {
                resolver.openOutputStream(it)
            }
        }
    }

    private fun updateNotification(currentProgress: Int) {
        notificationBuilder.setProgress(100, currentProgress, false)
        notificationBuilder.setContentText(getString(R.string.downloaded, currentProgress.toString()))
        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun onDownloadComplete(message: String) {
        notificationManager.cancel(0)
        notificationBuilder.setProgress(0, 0, false)
        notificationBuilder.setContentText(message)
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        notificationManager.cancel(0)
        notificationManager.notify(0, notificationBuilder.build())
    }


    companion object {
        const val EXTRA_PHOTO_URL = "EXTRA_PHOTO_URL"
        private const val CHANNEL_ID = "0"
        private const val CHANNEL_NAME = "PHOTO_DOWNLOAD"
        private const val DIR_NAME = "/Wallpaper_App"
        private const val FILE_EXTENSION = ".jpg"
    }
}

