package top.hanyue.xxx.utils.network

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.*

/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
interface ResApi {

    /**
     * 接口形式
     */
    @GET("/app/test/test/test")
    fun test(@Header("Authorization") auth:String?, @Query("test") query:String): Flowable<ResponseWrapper<List<Objects>>>

}