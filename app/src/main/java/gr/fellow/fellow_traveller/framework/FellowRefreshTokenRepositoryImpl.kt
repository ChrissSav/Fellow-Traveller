package gr.fellow.fellow_traveller.framework

import gr.fellow.fellow_traveller.data.FellowRefreshTokenRepository
import gr.fellow.fellow_traveller.framework.network.fellow.FellowTokenService
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccessRefreshTokenResponse
import gr.fellow.fellow_traveller.framework.network.fellow.request.RefreshTokenRequest
import retrofit2.Response

class FellowRefreshTokenRepositoryImpl
constructor(
    private val fellowTokenService: FellowTokenService
) : FellowRefreshTokenRepository {

    override suspend fun refreshToken(token: String): Response<AccessRefreshTokenResponse> {
        return fellowTokenService.refreshToken(RefreshTokenRequest(token))
    }

}