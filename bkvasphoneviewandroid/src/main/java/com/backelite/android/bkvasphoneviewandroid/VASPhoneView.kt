package com.backelite.android.bkvasphoneviewandroid

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.support.annotation.IdRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.TypefaceSpan
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

    private var arialAllowed: Boolean = false
    private var fee: String = ""

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
        (0..indexCount)
                .map { typedArray.getIndex(it) }
                .forEach {
                    when (it) {
                        R.styleable.VasPhoneView_vasPhoneViewSize -> setVASPhoneViewSize(VASPhoneViewSize.fromCode(typedArray.getInt(it, 0)))

                        R.styleable.VasPhoneView_vasPhoneViewStyle -> setVASPhoneViewStyle(VASPhoneViewStyle.fromCode(typedArray.getInt(it, 0)))

                        R.styleable.VasPhoneView_vasPhoneViewPhoneNumber -> setVASPhoneViewPhoneNumber(typedArray.getString(it))

                        R.styleable.VasPhoneView_vasPhoneViewFee -> setVASPhoneViewFeeText(typedArray.getString(it))

                        R.styleable.VasPhoneView_vasArialAllowed -> setVASPhoneViewArialAllowed(typedArray.getBoolean(it, false))
                    }
                }
        typedArray.recycle()
    }

    fun setVASPhoneViewSize(vasPhoneViewSize: VASPhoneViewSize) {
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

    fun setVASPhoneViewStyle(vasPhoneViewStyle: VASPhoneViewStyle) {
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
        //do not set fee text
    }

    fun setVASPhoneViewFeeText(fee: String) {
        this.fee = fee
        setupVASPhoneViewFee(fee, arialAllowed)
    }

    fun setVASPhoneViewArialAllowed(arialAllowed: Boolean) {
        this.arialAllowed = arialAllowed
        if (feeTextView.text != null) {
            setupVASPhoneViewFee(fee, arialAllowed)
        }
    }

    private fun setupVASPhoneViewFee(fee: String, arialAllowed: Boolean) {
        //arial il allowed, otherwise roboto bold, used for the fee amount
        val boldFont: Typeface = if (arialAllowed) getNonNullFont(context, R.font.arial_bold) else getNonNullFont(context, R.font.roboto_bold)
        //exo font for the rest
        val exoFont: Typeface = getNonNullFont(context, R.font.exo_bold)

        //formatted text
        val chargeableText: String = context.getString(R.string.vasphoneview_fee_text_chargeable, fee)

        //index of the text before the fee amount, to be formatted in exo font
        val chargeableTextFirstPartIndex: Int = context.getString(R.string.vasphoneview_fee_text_chargeable).indexOf("%")
        //index of the fee amount end (fee should contains the fee amount followed by a / and a duration), to be formatted in roboto / arial
        val feeAmountIndex: Int = if (fee.indexOf("/") != -1) chargeableTextFirstPartIndex + fee.indexOf("/") else chargeableTextFirstPartIndex + fee.length
        //the end of the text will be formatted in exo font

        val spannableStringBuilder = SpannableStringBuilder(chargeableText)
        spannableStringBuilder.setSpan(CustomTypefaceSpan("", exoFont), 0, chargeableTextFirstPartIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableStringBuilder.setSpan(CustomTypefaceSpan("", boldFont), chargeableTextFirstPartIndex, feeAmountIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableStringBuilder.setSpan(CustomTypefaceSpan("", exoFont), feeAmountIndex, chargeableText.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        feeTextView.text = spannableStringBuilder
    }

    fun setVASPhoneViewPhoneNumber(phoneNumber: String) {
        phoneNumberTextView.text = formatPhoneNumber(phoneNumber)
    }

    //first digit on its own, then digits grouped 3 by 3
    private fun formatPhoneNumber(phoneNumber: String): String {
        val stringBuilder = StringBuilder(phoneNumber.replace(" ", ""))
        if (stringBuilder.length == 10) {
            stringBuilder.insert(1, " ")
            stringBuilder.insert(5, " ")
            stringBuilder.insert(9, " ")
        }
        return stringBuilder.toString()
    }

    private fun getNonNullFont(context: Context, idRes: Int): Typeface {
        return ResourcesCompat.getFont(context, idRes) ?: Typeface.DEFAULT
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

    private inner class CustomTypefaceSpan(family: String, private val newType: Typeface) : TypefaceSpan(family) {

        override fun updateDrawState(ds: TextPaint) {
            applyCustomTypeFace(ds, newType)
        }

        override fun updateMeasureState(paint: TextPaint) {
            applyCustomTypeFace(paint, newType)
        }

        private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
            val old: Typeface = paint.typeface
            val oldStyle: Int = old.style

            val fake = oldStyle and tf.style.inv()
            if (fake and Typeface.BOLD != 0) {
                paint.isFakeBoldText = true
            }

            if (fake and Typeface.ITALIC != 0) {
                paint.textSkewX = -0.25f
            }

            paint.typeface = tf
        }
    }
}