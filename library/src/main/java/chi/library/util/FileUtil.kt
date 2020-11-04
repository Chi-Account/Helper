package chi.library.util

import android.os.Environment
import chi.library.base.BaseApplication
import java.io.File

fun getDownloadsDirectory(): File? =
    BaseApplication.context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.apply {
        if (isFile) {
            delete()
        }
        if (!exists()) {
            mkdirs()
        }
    }

fun getDownloadsPath(): String? =
    getDownloadsDirectory()?.absolutePath

fun deleteFile(file: File) =
    if (file.exists()) {
        if (file.isFile) {
            file.delete()
        } else {
            file.deleteRecursively()
        }
    } else {
        false
    }

fun clearDirectory(file: File): Boolean =
    if (deleteFile(file)) {
        file.mkdirs()
    } else {
        false
    }