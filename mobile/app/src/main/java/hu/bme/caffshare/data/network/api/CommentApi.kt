package hu.bme.caffshare.data.network.api


import hu.bme.caffshare.data.network.model.CommentDTO
import hu.bme.caffshare.data.network.model.CommentUploadDTO
import retrofit2.Response
import retrofit2.http.*

interface CommentApi {
    /**
     *
     *
     * Responses:
     *  - 200: OK
     *
     * @param commentId
     * @return [Unit]
     */
    @DELETE("api/comments/{commentId}")
    suspend fun deleteComment(@Path("commentId") commentId: kotlin.String): Response<Unit>

    /**
     * Your GET endpoint
     * Returns comments of the specified document.
     * Responses:
     *  - 200: OK
     *
     * @param caffId
     * @return [kotlin.collections.List<CommentDTO>]
     */
    @GET("api/documents/{caffId}/comments")
    suspend fun getComments(@Path("caffId") caffId: kotlin.String): Response<kotlin.collections.List<CommentDTO>>

    /**
     *
     * Add a comment to the specified document.
     * Responses:
     *  - 201: Created
     *
     * @param caffId
     * @param commentUploadDTO  (optional)
     * @return [CommentDTO]
     */
    @POST("api/documents/{caffId}/comments")
    suspend fun postComment(
        @Path("caffId") caffId: kotlin.String,
        @Body commentUploadDTO: CommentUploadDTO? = null
    ): Response<CommentDTO>
}
