package com.backelite.android.bkvasphoneviewandroid

/**
 * Created by jean-baptistevincey on 27/11/2017.
 */
enum class VASPhoneViewStyle(val code: Int) {
    STANDARD(0),
    FREE(1),
    CHARGEABLE(2);

    companion object {
        fun fromCode(code: Int): VASPhoneViewStyle {
            for (f in values()) {
                if (f.code == code) {
                    return f
                }
            }
            return FREE
        }
    }
}