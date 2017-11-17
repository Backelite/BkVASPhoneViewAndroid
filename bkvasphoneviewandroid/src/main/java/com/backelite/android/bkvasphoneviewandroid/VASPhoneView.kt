package com.backelite.android.bkvasphoneviewandroid

import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

/**
 * Created by jean-baptistevincey on 04/10/2017.
 */

fun <T : View> View.bind(@IdRes idRes: Int): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE) { findViewById<T>(idRes) }
}

class VASPhoneView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val TAG: String = "VASPhoneView"

    private val bordersView: View by bind(R.id.vasphoneview_borders)
    private val phoneNumberTextView: TextView by bind(R.id.vasphoneview_phonenumber)
    private val feeBackgroundView: View by bind(R.id.vasphoneview_fee_background)
    private val feeTriangleView: VASFeeTriangle by bind(R.id.vasphoneview_fee_triangle)
    private val feeTextView: TextView by bind(R.id.vasphoneview_fee)

    init {
        init()
        initAttrs(context, attrs)
    }

    private fun init() {
        inflate(context, R.layout.vasphoneview, this)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet? = null) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VasPhoneView)

        val indexCount: Int = typedArray.indexCount
        for (i in 0..indexCount) {
            val attr = typedArray.getIndex(i)
            when (attr) {
                R.styleable.VasPhoneView_vasPhoneViewSize -> setupVASPhoneViewSize(VASPhoneViewSize.fromCode(typedArray.getInt(attr, 0)))

                R.styleable.VasPhoneView_vasPhoneViewStyle -> setupVASPhoneViewStyle(VASPhoneViewStyle.fromCode(typedArray.getInt(attr, 0)))

                R.styleable.VasPhoneView_vasPhoneViewPhoneNumber -> setupVASPhoneViewPhoneNumber(typedArray.getString(attr))
            }
        }
        typedArray.recycle()
    }

    private fun setupVASPhoneViewSize(vasPhoneViewSize: VASPhoneViewSize) {
        when (vasPhoneViewSize) {
            VASPhoneViewSize.SMALL -> setupVASPhoneViewSizeSmall()

            VASPhoneViewSize.BIG -> setupVASPhoneViewSizeBig()
        }
    }

    private fun setupVASPhoneViewSizeSmall() {
        //TODO
    }

    private fun setupVASPhoneViewSizeBig() {
        //nothing to do, big by default
    }

    private fun setupVASPhoneViewStyle(vasPhoneViewStyle: VASPhoneViewStyle) {
        when (vasPhoneViewStyle) {
            VASPhoneViewStyle.STANDARD -> setupVASPhoneViewStyleStandard()

            VASPhoneViewStyle.FREE -> setupVASPhoneViewStyleFree()

            VASPhoneViewStyle.CHARGEABLE -> setupVASPhoneViewStyleChargeable()

        }
    }

    private fun setupVASPhoneViewStyleStandard() {
        feeTextView.setText(R.string.vasphoneview_fee_text_standard)
    }

    private fun setupVASPhoneViewStyleFree() {
        val color: Int = ContextCompat.getColor(context, R.color.vasphoneview_color_fee_type_free)
        phoneNumberTextView.setTextColor(color)
        feeBackgroundView.setBackgroundColor(color)
        feeTextView.setText(R.string.vasphoneview_fee_text_free)
    }

    private fun setupVASPhoneViewStyleChargeable() {
        val color: Int = ContextCompat.getColor(context, R.color.vasphoneview_color_fee_type_chargeable)
        phoneNumberTextView.setTextColor(color)
        feeBackgroundView.setBackgroundColor(color)
        feeTextView.text = context.getString(R.string.vasphoneview_fee_text_chargeable, "0€ / min")
    }

    private fun setupVASPhoneViewPhoneNumber(phoneNumber: String) {
        //TODO format phone number
        phoneNumberTextView.text = phoneNumber
    }

    enum class VASPhoneViewSize(val code: Int) {
        BIG(0),
        SMALL(1);

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
}