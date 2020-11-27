package hu.bme.caffshare.domain

import hu.bme.caffshare.data.network.NetworkDataSource
import hu.bme.caffshare.domain.model.DomainComment
import javax.inject.Inject

class CommentInteractor @Inject constructor(
    private val networkDataSource: NetworkDataSource
) {
    suspend fun deleteComment(commentId: String): Boolean =
        networkDataSource.deleteComment(commentId)

    suspend fun getCommentsForCaff(caffId: String): List<DomainComment>? =
        networkDataSource.getCommentsForCaff(caffId)

    suspend fun addComment(caffId: String, comment: String): Boolean =
        networkDataSource.addComment(caffId, comment)
}