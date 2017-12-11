package com.backelite.android.bkvasphoneviewandroidapp.matchers

import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import com.backelite.android.bkvasphoneviewandroid.VASPhoneView
import com.backelite.android.bkvasphoneviewandroidapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Created by jean-baptistevincey on 11/12/2017.
 */
object VASPhoneViewHeightMatcher {

    /**
     * Returns a matcher that matches [VASPhoneView]s based on text property value.
     *
     * @param intMatcher [Matcher] of [String] with text to match
     */
    fun withHeight(intMatcher: Matcher<Int>): Matcher<View> {

        return object : BoundedMatcher<View, VASPhoneView>(VASPhoneView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("with height: ")
                intMatcher.describeTo(description)
            }

            public override fun matchesSafely(vasPhoneView: VASPhoneView): Boolean {
                return intMatcher.matches(vasPhoneView.findViewById<View>(R.id.vasphoneview_borders).height)
            }
        }
    }
}