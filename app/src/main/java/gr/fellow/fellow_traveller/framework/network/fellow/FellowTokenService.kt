package gr.fellow.fellow_traveller.framework.network.fellow

import gr.fellow.fellow_traveller.data.BaseResponse
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccessRefreshTokenResponse
import gr.fellow.fellow_traveller.framework.network.fellow.request.RefreshTokenRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface FellowTokenService {

    @POST("auth/refresh_token")
    suspend fun refreshToken(
        @Body refreshToken: RefreshTokenRequest
    ): Response<BaseResponse<AccessRefreshTokenResponse>>

}

