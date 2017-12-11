package com.backelite.android.bkvasphoneviewandroidapp.matchers

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
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
object VASPhoneViewColorMatcher {

    /**
     * Returns a matcher that matches [VASPhoneView]s based on text property value.
     *
     * @param colorMatcher [Matcher] of [Int] with text to match
     */
    fun withColor(colorMatcher: Matcher<Int>): Matcher<View> {

        return object : BoundedMatcher<View, VASPhoneView>(VASPhoneView::class.java) {

            override fun describeTo(description: Description) {
                description.appendText("with color: ")
                colorMatcher.describeTo(description)
            }

            public override fun matchesSafely(vasPhoneView: VASPhoneView): Boolean {

                val feeBackgroundView: View = vasPhoneView.findViewById(R.id.vasphoneview_fee_background)
                val backgroundColorDrawable: Drawable = feeBackgroundView.background
                var backgroundColor = 0
                if (backgroundColorDrawable is ColorDrawable) {
                    backgroundColor = backgroundColorDrawable.color
                }

                val phoneNumberView: TextView = vasPhoneView.findViewById(R.id.vasphoneview_phonenumber)
                val textColor: Int = phoneNumberView.currentTextColor

                return colorMatcher.matches(backgroundColor) && colorMatcher.matches(textColor)
            }
        }
    }
}