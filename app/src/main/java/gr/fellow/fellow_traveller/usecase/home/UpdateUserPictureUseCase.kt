package gr.fellow.fellow_traveller.usecase.home

import gr.fellow.fellow_traveller.domain.FellowDataSource

class UpdateUserPictureUseCase(
    private val dataSource: FellowDataSource
) {

    suspend operator fun invoke(picture: String?) =
        dataSource.updatePicture(picture)


}