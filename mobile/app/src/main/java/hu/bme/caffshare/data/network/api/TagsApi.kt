package hu.bme.caffshare.data.network.api

import hu.bme.caffshare.data.network.model.TagDTO
import retrofit2.Response
import retrofit2.http.GET

interface TagsApi {
    /**
     * Your GET endpoint
     * Return tags.
     * Responses:
     *  - 200: OK
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *
     * @return [kotlin.collections.List<TagDTO>]
     */
    @GET("api/tags")
    suspend fun getTags(): Response<kotlin.collections.List<TagDTO>>

}
