package chi.library.util.network

interface NetworkCallback {

    fun onLoading(visible: Boolean) {}

    fun onSuccess(response: String) {}

    fun onFailure(exception: Exception) {}
}