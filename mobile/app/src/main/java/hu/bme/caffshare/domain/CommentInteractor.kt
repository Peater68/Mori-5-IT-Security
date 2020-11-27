package hu.bme.caffshare.domain

import hu.bme.caffshare.data.network.NetworkDataSource
import javax.inject.Inject

class CommentInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun deleteComment(commentId: String) =
        networkDataSource.deleteComment(commentId)

    suspend fun getCommentsForCaff(caffId: String) =
        networkDataSource.getCommentsForCaff(caffId)

    suspend fun addComment(caffId: String, comment: String) =
        networkDataSource.addComment(caffId, comment)
}