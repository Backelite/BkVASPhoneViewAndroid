package com.backelite.android.bkvasphoneviewandroid

/**
 * Created by jean-baptistevincey on 27/11/2017.
 */
enum class VASPhoneViewSize(val code: Int) {
    SMALL(0),
    BIG(1);

    companion object {
        fun fromCode(code: Int): VASPhoneViewSize {
            for (f in values()) {
                if (f.code == code) {
                    return f
                }
            }
            return BIG
        }
    }
}