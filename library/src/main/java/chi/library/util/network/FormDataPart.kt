package chi.library.util.network

import okhttp3.MediaType
import java.io.File

data class FormDataPart(
    val name: String,
    val filename: String?,
    val file: File,
    val mediaType: MediaType?
)