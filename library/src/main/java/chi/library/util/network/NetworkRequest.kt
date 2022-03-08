package chi.library.util.network

import android.os.Handler
import android.os.Looper
import chi.library.util.LogUtil
import okhttp3.*
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class NetworkRequest constructor(url: String) {

    companion object {

        private const val TAG = "NetworkRequest"

        @JvmStatic
        private var client = OkHttpClient()

        @JvmStatic
        private var baseUrl = ""

        private val handler = Handler(Looper.getMainLooper())

        fun init(client: OkHttpClient, baseUrl: String) {
            NetworkRequest.client = client
            NetworkRequest.baseUrl = baseUrl
        }

        fun url(url: String) = NetworkRequest(baseUrl + url)
    }

    private val messageList = ArrayList<String>()

    init {
        messageList.add("Url: $url")
    }

    private val requestBuilder = Request.Builder()
        .url(url)

    private var call: Call? = null

    private var callback: NetworkCallback? = null

    fun header(name: String, value: String): NetworkRequest {
        messageList.add("Header: $name = $value")
        requestBuilder.header(name, value)
        return this
    }

    fun addHeader(name: String, value: String): NetworkRequest {
        messageList.add("Header: $name = $value")
        requestBuilder.addHeader(name, value)
        return this
    }

    fun get(): NetworkRequest {
        messageList.add("Get")
        requestBuilder.get()
        return this
    }

    fun post(body: RequestBody): NetworkRequest {
        messageList.add("Post")
        requestBuilder.post(body)
        return this
    }

    fun postJson(json: String): NetworkRequest {
        messageList.add("Json: $json")
        requestBuilder.post(json.toRequestBody(MEDIA_TYPE_JSON))
        return this
    }

    fun postFormBody(params: Map<String, String>): NetworkRequest {
        val formBodyBuilder = FormBody.Builder()
        for ((key, value) in params) {
            messageList.add("Form body: $key = $value")
            formBodyBuilder.add(key, value)
        }
        requestBuilder.post(formBodyBuilder.build())
        return this
    }

    fun postMultipartBody(
        formDataPartList: List<FormDataPart>,
        params: Map<String, String>? = null,
    ): NetworkRequest {
        val multipartBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
        if (params != null) {
            for ((key, value) in params) {
                messageList.add("Form data part: $key = $value")
                multipartBodyBuilder.addFormDataPart(key, value)
            }
        }
        for (formDataPart in formDataPartList) {
            messageList.add("Form data part: $formDataPart")
            multipartBodyBuilder.addFormDataPart(
                formDataPart.name,
                formDataPart.filename,
                formDataPart.file.asRequestBody(formDataPart.mediaType)
            )
        }
        requestBuilder.post(multipartBodyBuilder.build())
        return this
    }

    fun callback(callback: NetworkCallback): NetworkRequest {
        this.callback = callback
        return this
    }

    fun execute(): NetworkRequest {
        call = client.newCall(requestBuilder.build()).apply {
            onLoading(true)
            enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        this@NetworkRequest.onSuccess(response.body?.string() ?: "")
                    } else {
                        this@NetworkRequest.onFailure(IOException("Response code ${response.code}"))
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    this@NetworkRequest.onFailure(e)
                }
            })
        }
        return this
    }

    fun cancel() {
        call?.cancel()
    }

    private fun onLoading(visible: Boolean) {
        try {
            callback?.onLoading(visible)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onSuccess(response: String) {
        handler.post {
            onLoading(false)
            try {
                callback?.onSuccess(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            setNull()
            messageList.add("Success: $response")
            showLog()
        }
    }

    private fun onFailure(exception: Exception) {
        handler.post {
            onLoading(false)
            try {
                callback?.onFailure(exception)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            setNull()
            messageList.add("Failure: ${exception.message}")
            showLog()
        }
    }

    private fun showLog() {
        for (message in messageList) {
            LogUtil.i(TAG, message)
        }
    }

    private fun setNull() {
        call = null
        callback = null
    }
}