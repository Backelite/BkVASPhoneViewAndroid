package com.backelite.android.bkvasphoneviewandroidapp.matchers

import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import android.widget.TextView
import com.backelite.android.bkvasphoneviewandroid.VASPhoneView
import com.backelite.android.bkvasphoneviewandroidapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Created by jean-baptistevincey on 10/12/2017.
 */
object VASPhoneViewPhoneNumberMatcher {

    /**
     * Returns a matcher that matches [VASPhoneView]s based on text property value.
     *
     * @param stringMatcher [Matcher] of [String] with text to match
     */
    fun withPhoneNumber(stringMatcher: Matcher<String>): Matcher<View> {

        return object : BoundedMatcher<View, VASPhoneView>(VASPhoneView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("with phone number: ")
                stringMatcher.describeTo(description)
            }

            public override fun matchesSafely(vasPhoneView: VASPhoneView): Boolean {
                return stringMatcher.matches(vasPhoneView.findViewById<TextView>(R.id.vasphoneview_phonenumber).text.toString())
            }
        }
    }
}