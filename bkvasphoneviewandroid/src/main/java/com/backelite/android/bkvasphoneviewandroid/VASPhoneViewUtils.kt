package com.backelite.android.bkvasphoneviewandroid

import android.support.annotation.VisibleForTesting

/**
 * Created by jean-baptistevincey on 05/12/2017.
 */
class VASPhoneViewUtils {

    //format phone number, first digit on its own, then digits grouped 3 by 3
    @VisibleForTesting
    internal fun formatPhoneNumber(phoneNumber: String): String {
        val stringBuilder = StringBuilder(phoneNumber.replace(" ", ""))
        if (stringBuilder.length == 10) {
            stringBuilder.insert(1, " ")
            stringBuilder.insert(5, " ")
            stringBuilder.insert(9, " ")
        }
        return stringBuilder.toString()
    }
}