package top.hanyue.xxx.utils.network

/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
data class ResponseWrapper<T>(var code: Int, var data: T?, var msg: String)