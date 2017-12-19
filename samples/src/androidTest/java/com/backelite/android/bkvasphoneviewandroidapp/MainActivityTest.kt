package com.backelite.android.bkvasphoneviewandroidapp

import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasAction
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import android.util.TypedValue
import com.backelite.android.bkvasphoneviewandroidapp.matchers.VASPhoneViewColorMatcher
import com.backelite.android.bkvasphoneviewandroidapp.matchers.VASPhoneViewFeeTextMatcher
import com.backelite.android.bkvasphoneviewandroidapp.matchers.VASPhoneViewHeightMatcher
import com.backelite.android.bkvasphoneviewandroidapp.matchers.VASPhoneViewPhoneNumberMatcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by jean-baptistevincey on 06/12/2017.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var intentsTestRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun vasPhoneViewTest_StyleFree() {
        onView(withId(R.id.vasphoneview_free_small))
                .check(matches(isDisplayed()))

        //test phonenumber text color and fee background color matching #78b41e
        val freeColor = "#78b41e"
        onView(withId(R.id.vasphoneview_free_small))
                .check(matches(VASPhoneViewColorMatcher.withColor(Matchers.equalTo(Color.parseColor(freeColor)))))

        //test fee text matching "Service & appel\ngratuits"
        val freeFeeText = "Service & appel\ngratuits"
        onView(withId(R.id.vasphoneview_free_small))
                .check(matches(VASPhoneViewFeeTextMatcher.withFeeText(Matchers.equalTo(freeFeeText))))
    }

    @Test
    fun vasPhoneViewTest_StyleStandard() {
        onView(withId(R.id.vasphoneview_standard_small))
                .check(matches(isDisplayed()))

        //test phonenumber text color and fee background color matching #91919b
        val standardColor = "#91919b"
        onView(withId(R.id.vasphoneview_standard_small))
                .check(matches(VASPhoneViewColorMatcher.withColor(Matchers.equalTo(Color.parseColor(standardColor)))))

        //test fee text matching "Servicegratuit \n+ prix appel"
        val standardFeeText = "Service gratuit\n+ prix appel"
        onView(withId(R.id.vasphoneview_standard_small))
                .check(matches(VASPhoneViewFeeTextMatcher.withFeeText(Matchers.equalTo(standardFeeText))))
    }

    @Test
    fun vasPhoneViewTest_StyleChargeableWithFee() {
        onView(withId(R.id.vasphoneview_chargeable_small))
                .check(matches(isDisplayed()))

        //test phonenumber text color and fee background color matching #a50f78
        val chargeableColor = "#a50f78"
        onView(withId(R.id.vasphoneview_chargeable_small))
                .check(matches(VASPhoneViewColorMatcher.withColor(Matchers.equalTo(Color.parseColor(chargeableColor)))))

        //test fee text matching "Service 0,8€ / min\n+ prix appel"
        val chargeableFeeText = "Service 0,8€ / min\n+ prix appel"
        onView(withId(R.id.vasphoneview_chargeable_small))
                .check(matches(VASPhoneViewFeeTextMatcher.withFeeText(Matchers.equalTo(chargeableFeeText))))
    }

    @Test
    fun vasPhoneViewTest_PhoneNumber() {
        onView(withId(R.id.vasphoneview_chargeable_small))
                .check(matches(isDisplayed()))

        //test formatted phonenumber matching "0 825 123 456"
        val phoneNumber = "0 825 123 456"
        onView(withId(R.id.vasphoneview_chargeable_small))
                .check(matches(VASPhoneViewPhoneNumberMatcher.withPhoneNumber(Matchers.equalTo(phoneNumber))))
    }

    @Test
    fun vasPhoneViewTest_SizeSmall() {
        onView(withId(R.id.vasphoneview_free_small))
                .check(matches(isDisplayed()))

        //test borders matching height 22dp
        val bordersHeightSmall = dpToPx(22, InstrumentationRegistry.getContext().resources)
        onView(withId(R.id.vasphoneview_free_small))
                .check(matches(VASPhoneViewHeightMatcher.withHeight(Matchers.equalTo(bordersHeightSmall))))
    }

    @Test
    fun vasPhoneViewTest_SizeBig() {
        onView(withId(R.id.vasphoneview_free_big))
                .check(matches(isDisplayed()))

        //test borders matching height 34dp
        val bordersHeightBig = dpToPx(34, InstrumentationRegistry.getContext().resources)
        onView(withId(R.id.vasphoneview_free_big))
                .check(matches(VASPhoneViewHeightMatcher.withHeight(Matchers.equalTo(bordersHeightBig))))
    }

    @Test
    fun onVASPhoneViewClickTest_DialOnClick() {
        onView(withId(R.id.vasphoneview_free_small)).perform(click())
        //test intent with action ACTION_DIAL
        intended(allOf(
                hasAction(equalTo(Intent.ACTION_DIAL))
        ))
    }

    private fun dpToPx(dp: Int, resources: Resources): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }

}