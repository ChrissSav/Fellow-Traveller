package gr.fellow.fellow_traveller.framework.network.fellow

import gr.fellow.fellow_traveller.data.BaseResponse
import gr.fellow.fellow_traveller.framework.network.fellow.auth.AccessRefreshTokenResponse
import gr.fellow.fellow_traveller.framework.network.fellow.auth.RefreshTokenRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FellowTokenService {

    @POST("auth/refresh-token")
    fun refreshToken(
        @Body refreshToken: RefreshTokenRequest
    ): Call<BaseResponse<AccessRefreshTokenResponse>>

}

