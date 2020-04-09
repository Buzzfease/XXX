package top.hanyue.xxx.utils.network

import android.accounts.NetworkErrorException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
object RxException {

    private const val NETWORK_EXCEPTION = "网络未连接,请先连接网络"
    private const val CONNECTION_TIMEOUT = "网络连接超时，请检查您的网络状态，稍后重试"
    private const val CONNECT_EXCEPTION = "网络连接异常，请检查您的网络状态"
    private const val UNKNOWN_HOST_EXCEPTION = "网络异常，请检查您的网络状态"


    fun getMessage(t: Throwable): String {
        return when (t) {
            is SocketTimeoutException -> CONNECTION_TIMEOUT
            is ConnectException -> CONNECT_EXCEPTION
            is UnknownHostException -> UNKNOWN_HOST_EXCEPTION
            is NetworkErrorException -> NETWORK_EXCEPTION
            else -> t.message.toString()
        }
    }

    class ParamsException(var msg:String) :Exception() {

        override val message: String? get() = msg
    }

    class LoginNoAccountException(var msg:String) :Exception() {

        override val message: String? get() = msg
    }

    class AccountAlreadyExistException(var msg:String) :Exception() {

        override val message: String? get() = msg
    }
}
