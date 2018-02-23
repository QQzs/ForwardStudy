package com.zs.project.listener

import android.view.View

/**
 *
Created by zs
Date：2018年 01月 25日
Time：17:14
—————————————————————————————————————
About:
—————————————————————————————————————
 */
interface ItemLongClickListener {
    fun onItemLongClick(position : Int , data : Any , view : View)
}