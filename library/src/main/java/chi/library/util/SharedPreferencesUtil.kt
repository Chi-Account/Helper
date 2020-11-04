package chi.library.util

import android.content.Context
import android.content.SharedPreferences
import chi.library.base.BaseApplication

object SharedPreferencesUtil {

    private const val SHARED_PREFERENCES_NAME = "shared_preferences_util"

    private var sharedPreferences: SharedPreferences? = null

    private fun getSharedPreferences(): SharedPreferences {
        if (sharedPreferences == null) {
            synchronized(javaClass) {
                if (sharedPreferences == null) {
                    sharedPreferences = BaseApplication.context.getSharedPreferences(
                        SHARED_PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                    )
                }
            }
        }
        return sharedPreferences!!
    }

    private fun getEditor() =
        getSharedPreferences().edit()

    fun getAll(): Map<String, *> =
        getSharedPreferences().all

    fun putString(key: String, value: String) =
        getEditor().putString(key, value).commit()

    fun getString(key: String, defValue: String) =
        getSharedPreferences().getString(key, defValue)

    fun putStringSet(key: String, values: Set<String>) =
        getEditor().putStringSet(key, values).commit()

    fun getStringSet(key: String, defValues: Set<String>?) =
        getSharedPreferences().getStringSet(key, defValues)

    fun putInt(key: String, value: Int) =
        getEditor().putInt(key, value).commit()

    fun getInt(key: String, defValue: Int) =
        getSharedPreferences().getInt(key, defValue)

    fun putLong(key: String, value: Long) =
        getEditor().putLong(key, value).commit()

    fun getLong(key: String, defValue: Long) =
        getSharedPreferences().getLong(key, defValue)

    fun putFloat(key: String, value: Float) =
        getEditor().putFloat(key, value).commit()

    fun getFloat(key: String, defValue: Float) =
        getSharedPreferences().getFloat(key, defValue)

    fun putBoolean(key: String, value: Boolean) =
        getEditor().putBoolean(key, value).commit()

    fun getBoolean(key: String, defValue: Boolean) =
        getSharedPreferences().getBoolean(key, defValue)

    fun contains(key: String) =
        getSharedPreferences().contains(key)

    fun remove(key: String) =
        getEditor().remove(key).commit()

    fun clear() =
        getEditor().clear().commit()
}