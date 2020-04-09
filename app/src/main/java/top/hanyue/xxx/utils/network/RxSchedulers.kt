package top.hanyue.xxx.utils.network

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Buzz on 2019/4/23.
 * Email :lmx2060918@126.com
 */
object RxSchedulers {

    /**
     * 线程切换
     */
    fun <T> threadIo(): FlowableTransformer<T, T> {
        return FlowableTransformer { flowable ->
            flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 数据处理
     */
    fun <T> objectTransformer(): FlowableTransformer<ResponseWrapper<T>, T> {
        return FlowableTransformer { upstream ->
            upstream.flatMap { tResultData: ResponseWrapper<T> ->
                if (tResultData.code == 200) {
                    return@flatMap Flowable.just(tResultData.data)
                }
                return@flatMap Flowable.error<T> { RxException.ParamsException(tResultData.msg) }
            }
        }
    }

    /**
     * {"msg":"ok","code":200,"data":"修改成功"}
     */
    fun <String> stringTransformer(): FlowableTransformer<ResponseWrapper<String>, String> {
        return FlowableTransformer { upstream ->
            upstream.flatMap { tResultData: ResponseWrapper<String> ->
                if (tResultData.code == 200) {
                    return@flatMap Flowable.just(tResultData.data)
                }
                return@flatMap Flowable.error<String> { RxException.ParamsException(tResultData.msg) }
            }
        }
    }
}




