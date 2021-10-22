package gr.fellow.fellow_traveller.framework

import android.content.SharedPreferences
import android.util.Log
import gr.fellow.fellow_traveller.data.UnauthorizedException
import gr.fellow.fellow_traveller.framework.network.fellow.FellowTokenService
import gr.fellow.fellow_traveller.framework.network.fellow.auth.RefreshTokenRequest
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_ACCESS_TOKEN
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_REFRESH_TOKEN
import gr.fellow.fellow_traveller.utils.set
import okhttp3.Interceptor
import okhttp3.Response
import java.net.HttpURLConnection

class TokenInterceptor
constructor(
    private val sharedPreferences: SharedPreferences,
    private val fellowTokenService: FellowTokenService
) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val originalRequest = chain.request()
            if (!(originalRequest.url.encodedPath.contains("/signup") && originalRequest.method == "POST")
                && !(originalRequest.url.encodedPath.contains("/signin") && originalRequest.method == "POST")
                && !(originalRequest.url.encodedPath.contains("/check-email") && originalRequest.method == "POST")
                && !(originalRequest.url.encodedPath.contains("/verify-account") && originalRequest.method == "GET")
                && !(originalRequest.url.encodedPath.contains("/refresh-token") && originalRequest.method == "POST")
                && !(originalRequest.url.encodedPath.contains("/forgot-password") && originalRequest.method == "POST")
                && !(originalRequest.url.encodedPath.contains("/reset-password") && originalRequest.method == "POST")
            ) {
                var token = sharedPreferences.getString(PREFS_AUTH_ACCESS_TOKEN, "").toString()

                val authenticationRequest = originalRequest.newBuilder()
                    .addHeader("Authorization", "Bearer $token").build()
                val initialResponse = chain.proceed(authenticationRequest)

                return if (initialResponse.code == HttpURLConnection.HTTP_UNAUTHORIZED || initialResponse.code == HttpURLConnection.HTTP_FORBIDDEN) {
                    initialResponse.close()
                    handleForbiddenResponse()
                    token = sharedPreferences.getString(PREFS_AUTH_ACCESS_TOKEN, "").toString()
                    val newAuthenticationRequest = originalRequest.newBuilder()
                        .addHeader("Authorization", "Bearer $token").build()
                    chain.proceed(newAuthenticationRequest)
                } else {
                    initialResponse
                }
            } else {
                return chain.proceed(originalRequest)
            }
        }
    }

    private fun handleForbiddenResponse() {
        val refreshToken = sharedPreferences.getString(PREFS_AUTH_REFRESH_TOKEN, "").toString()
        val call = fellowTokenService.refreshToken(RefreshTokenRequest(refreshToken))
        val response = call.execute()
        if (response.isSuccessful) {
            sharedPreferences[PREFS_AUTH_ACCESS_TOKEN] = response.body()?.data?.accessToken.toString()
            sharedPreferences[PREFS_AUTH_REFRESH_TOKEN] = response.body()?.data?.refreshToken.toString()
            Log.i("TokenInterceptor", "save new")
        } else {
            throw UnauthorizedException()
        }
    }

}


