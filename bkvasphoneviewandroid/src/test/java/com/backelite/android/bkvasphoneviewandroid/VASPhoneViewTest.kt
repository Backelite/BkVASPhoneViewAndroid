package com.backelite.android.bkvasphoneviewandroid

import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

/**
 * Created by jean-baptistevincey on 04/12/2017.
 */
class VASPhoneViewUtilsTest {

    private lateinit var vasPhoneViewUtils: VASPhoneViewUtils

    @Before
    fun setup() {
        vasPhoneViewUtils = VASPhoneViewUtils()
    }

    @Test
    fun formatPhoneNumberTest() {
        val formattedNumber: String = vasPhoneViewUtils.formatPhoneNumber("08 25 12 34 56")
        formattedNumber shouldEqual "0 825 123 456"
    }

}