package gr.fellow.fellow_traveller.usecase.firabase

import android.net.Uri
import gr.fellow.fellow_traveller.domain.FellowDataSource

class UploadPictureFirebaseUseCase(
    private val dataSource: FellowDataSource
) {
    suspend operator fun invoke(uri: Uri, userId: String) =
        dataSource.updatePictureFirebase(uri, userId)

}