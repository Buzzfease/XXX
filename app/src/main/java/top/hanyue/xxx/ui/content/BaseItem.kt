package top.hanyue.xxx.ui.content

import com.chad.library.adapter.base.entity.MultiItemEntity

class BaseItem<T>(private val itemType: Int, private val data: T) : MultiItemEntity {

    override fun getItemType(): Int {
        return itemType
    }

    fun getData(): T {
        return data
    }

    companion object {
        //Enter item type here
    }
}