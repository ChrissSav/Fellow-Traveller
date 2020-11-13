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
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock


class TokenInterceptor
constructor(
    private val sharedPreferences: SharedPreferences,
    private val fellowTokenService: FellowTokenService
) : Interceptor {

    companion object {
        val lock: Lock = ReentrantLock()
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        var response: Response?
        val request = chain.request()
        val firstRequest = request.newBuilder()

        if (!(request.url.encodedPath.contains("/signup") && request.method == "POST")
            && !(request.url.encodedPath.contains("/signin") && request.method == "POST")
            && !(request.url.encodedPath.contains("/check_email") && request.method == "POST")
            && !(request.url.encodedPath.contains("/verify_account") && request.method == "GET")
            && !(request.url.encodedPath.contains("/refresh_token") && request.method == "POST")
            && !(request.url.encodedPath.contains("/forgot_password") && request.method == "POST")
            && !(request.url.encodedPath.contains("/reset_password") && request.method == "POST")
        ) {
            var token = sharedPreferences.getString(PREFS_AUTH_ACCESS_TOKEN, "").toString()


            // Add token to request
            if (token.length > 10)
                firstRequest.addHeader("Authorization", "Bearer $token")
            // Execute Request
            response = chain.proceed(firstRequest.build())

            if (response.code == HttpURLConnection.HTTP_UNAUTHORIZED) {

                if (lock.tryLock()) {
                    Log.i("TokenInterceptor", "refresh token thread holds the lock");
                    handleForbiddenResponse()
                    token = sharedPreferences.getString(PREFS_AUTH_ACCESS_TOKEN, "").toString()
                    firstRequest.removeHeader("Authorization")
                    firstRequest.addHeader("Authorization", "Bearer $token")
                    Log.i("TokenInterceptor", "refresh token finished. release lock");
                    lock.unlock();
                    return chain.proceed(firstRequest.build())
                } else {
                    lock.lock(); // this will block the thread until the thread that is refreshing
                    // the token will call .unlock() method
                    handleForbiddenResponse()
                    token = sharedPreferences.getString(PREFS_AUTH_ACCESS_TOKEN, "").toString()
                    firstRequest.removeHeader("Authorization")
                    firstRequest.addHeader("Authorization", "Bearer $token")
                    lock.unlock();
                    return chain.proceed(firstRequest.build())
                }
            }
            return response
        } else {
            return chain.proceed(firstRequest.build())
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


