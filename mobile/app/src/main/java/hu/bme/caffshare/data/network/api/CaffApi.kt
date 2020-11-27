package hu.bme.caffshare.data.network.api

import hu.bme.caffshare.data.network.model.CaffDetailsDTO
import hu.bme.caffshare.data.network.model.CaffSumDTO
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface CaffApi {
    /**
     *
     * Delete caff file by id
     * Responses:
     *  - 204: No Content
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Internal Server Error
     *
     * @param caffId
     * @return [Unit]
     */
    @DELETE("api/caffs/{caffId}")
    suspend fun deleteCaffById(@Path("caffId") caffId: kotlin.String): Response<Unit>

    /**
     *
     * Download preview image of caff or the caff file depends on query param
     * Responses:
     *  - 200: OK
     *  - 400: Bad Request
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 404: Not Found
     *  - 500: Internal Server Error
     *
     * @param caffId
     * @param type
     * @return [ResponseBody]
     */
    @Streaming
    @GET("api/caffs/{caffId}/download")
    suspend fun downloadPreviewOrCaffFile(
        @Path("caffId") caffId: kotlin.String,
        @Query("type") type: kotlin.String
    ): Response<ResponseBody>

    /**
     * Your GET endpoint
     * Get all caff wit summarized details
     * Responses:
     *  - 200: OK
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Internal Server Error
     *
     * @return [kotlin.collections.List<CaffSumDTO>]
     */
    @GET("api/caffs")
    suspend fun getAllCaffs(): Response<kotlin.collections.List<CaffSumDTO>>

    /**
     * Your GET endpoint
     * Get caff details by document id
     * Responses:
     *  - 200: OK
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 404: Not Found
     *  - 500: Internal Server Error
     *
     * @param caffId
     * @return [CaffDetailsDTO]
     */
    @GET("api/caffs/{caffId}")
    suspend fun getCaffDetailsById(@Path("caffId") caffId: kotlin.String): Response<CaffDetailsDTO>

    /**
     *
     * Upload caff file
     * Responses:
     *  - 204: No Content
     *  - 400: Bad Request
     *  - 401: Unauthorized
     *  - 403: Forbidden
     *  - 500: Internal Server Error
     *
     * @param file  (optional)
     * @return [Unit]
     */
    @Multipart
    @POST("api/caffs")
    suspend fun uploadCaff(@Part file: MultipartBody.Part): Response<Unit>

}
