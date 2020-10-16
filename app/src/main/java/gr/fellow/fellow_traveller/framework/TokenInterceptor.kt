package gr.fellow.fellow_traveller.framework

import android.content.SharedPreferences
import android.util.Log
import gr.fellow.fellow_traveller.data.FellowRefreshTokenRepository
import gr.fellow.fellow_traveller.data.UnauthorizedException
import gr.fellow.fellow_traveller.utils.*
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor
constructor(
    private val sharedPreferences: SharedPreferences,
    private val fellowRefreshTokenRepository: FellowRefreshTokenRepository
) : Interceptor {

    private var gettingRefreshToken = false

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val firstRequest = request.newBuilder()
        val newRequest = request.newBuilder()

        if (!(request.url.encodedPath.contains("/signup") && request.method == "POST")
            && !(request.url.encodedPath.contains("/signin") && request.method == "POST")
            && !(request.url.encodedPath.contains("/check_email") && request.method == "POST")
            && !(request.url.encodedPath.contains("/verify_account") && request.method == "GET")
            && !(request.url.encodedPath.contains("/refresh_token") && request.method == "POST")
        ) {

            var token = sharedPreferences.getString(PREFS_AUTH_ACCESS_TOKEN, "").toString()
            newRequest.addHeader("Authorization", "Bearer $token")
            Log.i("TokenInterceptor", "first")

            val response = chain.proceed(newRequest.build())

            if (response.code == 401) {
                handleForbiddenResponse()
                Log.i("TokenInterceptor", "re try")

                token = sharedPreferences.getString(PREFS_AUTH_ACCESS_TOKEN, "").toString()
                newRequest.removeHeader("Authorization")
                newRequest.addHeader("Authorization", "Bearer $token")
                chain.proceed(firstRequest.build())
            }
        }
        return chain.proceed(newRequest.build())
    }

    private fun handleForbiddenResponse() {
        Log.i("TokenInterceptor", "handleForbiddenResponse")
        runBlocking {
            val refreshToken = sharedPreferences.getString(PREFS_AUTH_REFRESH_TOKEN, "").toString()
            val response = fellowRefreshTokenRepository.refreshToken(refreshToken)
            if (response.isSuccessful) {
                sharedPreferences[PREFS_AUTH_ACCESS_TOKEN] = response.body()?.data?.accessToken.toString()
                sharedPreferences[PREFS_AUTH_REFRESH_TOKEN] = response.body()?.data?.refreshToken.toString()
                Log.i("TokenInterceptor", "save new")

            } else {
                throw UnauthorizedException()
            }
            gettingRefreshToken = false
        }
    }

}