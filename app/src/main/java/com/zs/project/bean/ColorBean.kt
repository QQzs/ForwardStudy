package com.zs.project.bean

/**
 * Created by zs
 * Date：2018年 02月 08日
 * Time：17:04
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

class ColorBean {

    var index: Int = 0

    var isChoose: Boolean = false

    var iconId: Int = 0

    var name: String? = null

    constructor(index: Int) {
        this.index = index
    }

    constructor(index: Int, id: Int, name: String) {
        this.index = index
        this.iconId = id
        this.name = name
    }
}
