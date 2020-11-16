package chi.library.base

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

abstract class BasePermissionActivity : BaseActivity() {

    companion object {
        const val REQUEST_CODE_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val necessaryPermissions = getNecessaryPermissions()
        val flagRequest = necessaryPermissions.any {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (flagRequest) {
            ActivityCompat.requestPermissions(
                this,
                necessaryPermissions,
                REQUEST_CODE_PERMISSION
            )
        } else {
            onPermissionsGranted()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                val flagGranted = grantResults.all {
                    it == PackageManager.PERMISSION_GRANTED
                }
                if (flagGranted) {
                    onPermissionsGranted()
                } else {
                    onPermissionsDenied()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    protected abstract fun getNecessaryPermissions(): Array<out String>

    protected abstract fun onPermissionsGranted()

    protected abstract fun onPermissionsDenied()
}