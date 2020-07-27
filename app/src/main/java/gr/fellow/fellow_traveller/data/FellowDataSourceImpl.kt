package gr.fellow.fellow_traveller.data

import gr.fellow.fellow_traveller.domain.FellowDataSource
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCheckRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.LoginRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import retrofit2.Response

class FellowDataSourceImpl(
    private val repository: FellowRepository

) : FellowDataSource {

    override suspend fun checkUserPhone(phone: String): ResultWrapper<StatusHandleResponse> {
        return repository.checkField(AccountCheckRequest("phone", phone))
    }

    override suspend fun checkUserEmail(email: String): ResultWrapper<StatusHandleResponse> {
        return repository.checkField(AccountCheckRequest("email", email))
    }

    override suspend fun registerUser(firstName: String,lastName: String, email: String,password: String, phone: String
    ): ResultWrapper<UserInfoResponse> {
        return repository.registerUser(AccountCreateRequest(firstName,lastName,email, password, phone))
    }

    override suspend fun loginUser( username: String,  password: String): ResultWrapper<UserLoginResponse> {
        return  repository.loginUser(LoginRequest(username,password))
    }
}