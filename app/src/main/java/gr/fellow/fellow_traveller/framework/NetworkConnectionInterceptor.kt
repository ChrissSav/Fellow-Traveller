package gr.fellow.fellow_traveller.framework

import gr.fellow.fellow_traveller.data.NoInternetException
import gr.fellow.fellow_traveller.utils.ConnectivityHelper
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    private val connectivityHelper: ConnectivityHelper
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!connectivityHelper.checkInternetConnection())
            throw NoInternetException()
        return chain.proceed(chain.request())
    }

}

