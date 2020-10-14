package gr.fellow.fellow_traveller.framework

import android.content.SharedPreferences
import android.util.Log
import gr.fellow.fellow_traveller.framework.network.fellow.FellowTokenService
import gr.fellow.fellow_traveller.framework.network.fellow.request.RefreshTokenRequest
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response


class TokenInterceptor
constructor(
    private val sharedPreferences: SharedPreferences,
    private val fellowTokenService: FellowTokenService
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val newRequest = request.newBuilder()
        val token = sharedPreferences.getString(PREFS_AUTH_TOKEN, "").toString()

        if (
            (request.url.encodedPath.contains("/signup") && request.method == "post")
            ||
            (request.url.encodedPath.contains("/signin") && request.method == "post")
            ||
            (request.url.encodedPath.contains("/check_email") && request.method == "post")
            ||
            (request.url.encodedPath.contains("/verify_account") && request.method == "get")
            ||
            (request.url.encodedPath.contains("/refresh_token") && request.method == "post")
        ) {
            return chain.proceed(newRequest.build())
        } else {

        }

        if (true) {

            runBlocking {
                val response = fellowTokenService.refreshToken(RefreshTokenRequest(token))
                if (response.isSuccessful) {
                    Log.i("makis", "takis____________________________________________________________")
                } else {
                    Log.i("makis", "eroor____________________________________________________________")
                }
            }
        }
        if (token.length > 10)
            newRequest.addHeader("Authorization", "Bearer $token")
        return chain.proceed(newRequest.build())
    }

}