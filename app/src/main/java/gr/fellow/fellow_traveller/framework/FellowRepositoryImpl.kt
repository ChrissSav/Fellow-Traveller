package gr.fellow.fellow_traveller.framework

import android.content.Context
import android.content.SharedPreferences
import gr.fellow.fellow_traveller.ConnectivityHelper
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.data.FellowRepository
import gr.fellow.fellow_traveller.data.ResultWrapper
import gr.fellow.fellow_traveller.framework.network.fellow.FellowService
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCheckRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.AccountCreateRequest
import gr.fellow.fellow_traveller.framework.network.fellow.request.LoginRequest
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserLoginResponse
import gr.fellow.fellow_traveller.set
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN

class FellowRepositoryImpl(
    private val service: FellowService,
    private val connectivityHelper: ConnectivityHelper,
    private val sharedPrefs: SharedPreferences
) : FellowRepository {


    override suspend fun checkField(accountCheckRequest: AccountCheckRequest): ResultWrapper<StatusHandleResponse> =
        networkCall(connectivityHelper) {
            service.checkIfAccountInfoExist(accountCheckRequest).handleToCorrectFormat()
        }


    override suspend fun registerUser(registerUserRequest: AccountCreateRequest): ResultWrapper<UserLoginResponse> =
        networkCall(connectivityHelper) {
            val res = service.registerUser(registerUserRequest)
            if (res.isSuccessful)
                sharedPrefs[PREFS_AUTH_TOKEN] =
                    res.headers()["Set-Cookie"]?.split(";".toRegex())?.toTypedArray()?.get(0)
            res.handleToCorrectFormat()
        }

    override suspend fun loginUser(loginRequest: LoginRequest): ResultWrapper<UserLoginResponse> =
        networkCall(connectivityHelper)
        {
            val res = service.userLogin(loginRequest)
            if (res.isSuccessful)
                sharedPrefs[PREFS_AUTH_TOKEN] =
                    res.headers()["Set-Cookie"]?.split(";".toRegex())?.toTypedArray()?.get(0)
            res.handleToCorrectFormat()
        }

}