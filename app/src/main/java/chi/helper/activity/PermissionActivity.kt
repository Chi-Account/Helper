package chi.helper.activity

import chi.library.base.BasePermissionActivity
import chi.library.util.LogUtil

class PermissionActivity : BasePermissionActivity() {

    override fun getNecessaryPermissions(): Array<out String> = arrayOf(
        android.Manifest.permission.CALL_PHONE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    override fun onPermissionsGranted() {
        LogUtil.i("onPermissionsGranted")
    }

    override fun onPermissionsDenied() {
        LogUtil.i("onPermissionsDenied")
    }
}