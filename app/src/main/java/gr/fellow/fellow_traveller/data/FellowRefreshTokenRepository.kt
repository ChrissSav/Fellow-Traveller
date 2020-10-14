package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.framework.network.fellow.request.AccessRefreshTokenResponse
import retrofit2.Response

interface FellowRefreshTokenRepository {
    suspend fun refreshToken(token: String): Response<AccessRefreshTokenResponse>
}