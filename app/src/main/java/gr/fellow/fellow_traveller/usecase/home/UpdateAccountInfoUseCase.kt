package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource

class UpdateAccountInfoUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(firstName: String, lastName: String, picture: String?, messengerLink: String?, aboutMe: String?) =
        dataSource.updateAccount(firstName, lastName, picture, messengerLink, aboutMe)


}