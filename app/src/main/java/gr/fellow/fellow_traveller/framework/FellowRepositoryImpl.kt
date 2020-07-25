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
import gr.fellow.fellow_traveller.framework.network.fellow.response.StatusHandleResponse
import gr.fellow.fellow_traveller.framework.network.fellow.response.UserInfoResponse
import gr.fellow.fellow_traveller.set
import gr.fellow.fellow_traveller.utils.PREFS_AUTH_TOKEN

class FellowRepositoryImpl(
    private val context: Context,
    private val service: FellowService,
    private val connectivityHelper: ConnectivityHelper,
    private val sharedPrefs: SharedPreferences
) : FellowRepository {


    override suspend fun checkField(accountCheckRequest: AccountCheckRequest): ResultWrapper<StatusHandleResponse> =
        networkCall(connectivityHelper) {
            service.checkIfAccountInfoExist(accountCheckRequest).handleToCorrectFormat()
        }


    override suspend fun registerUser(registerUserRequest: AccountCreateRequest): ResultWrapper<UserInfoResponse> =
        networkCall(connectivityHelper) {
            val temp = service.registerUser(registerUserRequest)
            val res = temp.handleToCorrectFormat()
            if (res is ResultWrapper.Error)
                when (res.error.code) {
                    200 -> res.error.msg =
                        context.resources.getString(R.string.ERROR_PHONE_ALREADY_EXISTS)
                    201 -> res.error.msg =
                        context.resources.getString(R.string.ERROR_EMAIL_ALREADY_EXISTS)
                    else -> res.error.msg =
                        context.resources.getString(R.string.ERROR_API_UNREACHABLE)
                }
            else{
                sharedPrefs[PREFS_AUTH_TOKEN] = temp.headers()["Set-Cookie"]?.split(";".toRegex())?.toTypedArray()?.get(0)
            }
            res
        }

}