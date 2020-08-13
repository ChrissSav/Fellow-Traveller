package gr.fellow.fellow_traveller.utils

import android.content.SharedPreferences
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.google.gson.JsonParser
import gr.fellow.fellow_traveller.data.BaseApiException
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.ErrorResponseModel
import okhttp3.ResponseBody

private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
    val editor = edit()
    operation(editor)
    editor.apply()
}

operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Access PreferencesHelper to implement this kind of operation")
    }
}


inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when (T::class) {
        String::class -> getString(key, defaultValue as? String) as T?
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T?
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T?
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T?
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?
        else -> throw UnsupportedOperationException("Access PreferencesHelper to implement this kind of operation")
    }
}


class ConnectivityHelper(private val connectivityManager: ConnectivityManager) {

    fun checkInternetConnection(): Boolean {
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }
}

fun getModelFromResponseErrorBody(errorBody: ResponseBody?): ErrorResponse {
    return try {
        val mJsonString = errorBody!!.string()
        val parser = JsonParser()
        val mJson = parser.parse(mJsonString)
        val errorResponseModel =
            Gson().fromJson<ErrorResponseModel>(mJson, ErrorResponseModel::class.java)
        ErrorResponse(errorResponseModel.detail.statusCode, errorResponseModel.detail.message)
    } catch (e: Exception) {
        throw BaseApiException(SOMETHING_WORNG)
    }

}

