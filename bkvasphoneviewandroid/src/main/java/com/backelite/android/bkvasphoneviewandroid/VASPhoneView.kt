/*
 * Copyright 2017 Backelite.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.backelite.android.bkvasphoneviewandroid

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.support.annotation.DimenRes
import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.content.res.ResourcesCompat
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.style.TypefaceSpan
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
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
) : ConstraintLayout(context, attrs, defStyle) {

    private val TAG: String = "VASPhoneView"

    private val vasPhoneViewUtils = VASPhoneViewUtils()

    private val bordersView: View by bind(R.id.vasphoneview_borders)
    private val phoneNumberTextView: TextView by bind(R.id.vasphoneview_phonenumber)
    private val feeBackgroundView: View by bind(R.id.vasphoneview_fee_background)
    private val feeTriangleView: VASFeeTriangle by bind(R.id.vasphoneview_fee_triangle)
    private val feeTextView: TextView by bind(R.id.vasphoneview_fee)

    private var phoneNumber: String = ""
    private var arialAllowed: Boolean = false
    private var feeAmount: String = ""

    init {
        init()
        initAttrs(context, attrs)
    }

    /**
     * init inflating layout
     */
    private fun init() {
        inflate(context, R.layout.vasphoneview, this)
    }

    /**
     * initAttrs handling xml attributes
     */
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

                        R.styleable.VasPhoneView_vasPhoneViewFee -> setVASPhoneViewFeeAmount(typedArray.getString(it))

                        R.styleable.VasPhoneView_vasPhoneViewArialAllowed -> setVASPhoneViewArialAllowed(typedArray.getBoolean(it, false))

                        R.styleable.VasPhoneView_vasPhoneViewDialOnClick -> setVASPhoneViewDialOnClick(typedArray.getBoolean(it, false))
                    }
                }
        typedArray.recycle()
    }

    //region VASPhoneView size

    /**
     * setVASPhoneViewSize can be used to set VASPhoneViewSize programmatically (either small or big)
     */
    fun setVASPhoneViewSize(vasPhoneViewSize: VASPhoneViewSize) {
        when (vasPhoneViewSize) {
            VASPhoneViewSize.SMALL -> setupVASPhoneViewSizeSmall()

            VASPhoneViewSize.BIG -> setupVASPhoneViewSizeBig()
        }
    }

    private fun setupVASPhoneViewSizeSmall() {
        setupVASPhoneView(R.dimen.vasphoneview_fee_height_small,
                R.dimen.vasphoneview_borders_height_small,
                R.dimen.vasphoneview_margin_small,
                R.dimen.vasphoneview_margin_border_right_small,
                R.dimen.vasphoneview_phonenumber_fontsize_small,
                R.dimen.vasphoneview_fee_fontsize_small,
                R.dimen.vasphoneview_triangle_height_small,
                R.dimen.vasphoneview_triangle_width_small)
    }

    private fun setupVASPhoneViewSizeBig() {
        setupVASPhoneView(R.dimen.vasphoneview_fee_height_big,
                R.dimen.vasphoneview_borders_height_big,
                R.dimen.vasphoneview_margin_big,
                R.dimen.vasphoneview_margin_border_right_big,
                R.dimen.vasphoneview_phonenumber_fontsize_big,
                R.dimen.vasphoneview_fee_fontsize_big,
                R.dimen.vasphoneview_triangle_height_big,
                R.dimen.vasphoneview_triangle_width_big)
    }

    private fun setupVASPhoneView(@DimenRes feeHeightRes: Int,
                                  @DimenRes bordersHeightRes: Int,
                                  @DimenRes marginRes: Int,
                                  @DimenRes marginBorderRightRes: Int,
                                  @DimenRes phoneNumberFontSizeRes: Int,
                                  @DimenRes feeFontSizeRes: Int,
                                  @DimenRes triangleHeightRes: Int,
                                  @DimenRes triangleWidthRes: Int) {

        val margin: Int = resources.getDimensionPixelSize(marginRes)

        //borders
        bordersView.layoutParams.height = resources.getDimensionPixelSize(bordersHeightRes)

        //fee background
        feeBackgroundView.layoutParams.height = resources.getDimensionPixelSize(feeHeightRes)
        (feeBackgroundView.layoutParams as ConstraintLayout.LayoutParams).setMargins(0, 0, resources.getDimensionPixelSize(marginBorderRightRes), 0)

        //phone number textview
        (phoneNumberTextView.layoutParams as ConstraintLayout.LayoutParams).setMargins(margin, 0, 0, 0)
        phoneNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(phoneNumberFontSizeRes))

        //fee textview
        (feeTextView.layoutParams as ConstraintLayout.LayoutParams).setMargins(margin, 0, margin, 0)
        feeTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(feeFontSizeRes))

        //fee triangle
        (feeTriangleView.layoutParams as ConstraintLayout.LayoutParams).setMargins(margin, 0, 0, 0)
        feeTriangleView.layoutParams.height = resources.getDimensionPixelSize(triangleHeightRes)
        feeTriangleView.layoutParams.width = resources.getDimensionPixelSize(triangleWidthRes)

    }
    //endregion

    //region VASPhoneView style

    /**
     * setVASPhoneViewSize can be used to set VASPhoneViewStyle programmatically (either free, standard or chargeable)
     */
    fun setVASPhoneViewStyle(vasPhoneViewStyle: VASPhoneViewStyle) {
        when (vasPhoneViewStyle) {
            VASPhoneViewStyle.STANDARD -> setupVASPhoneViewStyleStandard()

            VASPhoneViewStyle.FREE -> setupVASPhoneViewStyleFree()

            VASPhoneViewStyle.CHARGEABLE -> setupVASPhoneViewStyleChargeable()

        }
    }

    private fun setupVASPhoneViewStyleStandard() {
        val color: Int = ContextCompat.getColor(context, R.color.vasphoneview_color_fee_type_standard)
        phoneNumberTextView.setTextColor(color)
        feeBackgroundView.setBackgroundColor(color)
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
        //do not set fee text, will be set with the fee amount
    }
    //endregion

    //region VASPhoneView phone number

    fun setVASPhoneViewPhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber.replace(" ", "")
        phoneNumberTextView.text = vasPhoneViewUtils.formatPhoneNumber(phoneNumber)
    }

    //endregion

    //region VASPhoneView fee amount

    fun setVASPhoneViewFeeAmount(feeAmount: String) {
        this.feeAmount = feeAmount
        setupVASPhoneViewAmount(feeAmount, arialAllowed)
    }

    private fun setupVASPhoneViewAmount(feeAmount: String, arialAllowed: Boolean) {
        //arial il allowed, otherwise roboto bold, used for the fee amount
        val boldFont: Typeface = if (arialAllowed) getNonNullFont(context, R.font.arial_bold) else getNonNullFont(context, R.font.roboto_bold)
        //exo font for the rest
        val exoFont: Typeface = getNonNullFont(context, R.font.exo_bold)

        //formatted text
        val chargeableText: String = context.getString(R.string.vasphoneview_fee_text_chargeable, feeAmount)

        //index of the text before the fee amount, to be formatted in exo font
        val chargeableTextFirstPartIndex: Int = context.getString(R.string.vasphoneview_fee_text_chargeable).indexOf("%")
        //index of the fee amount end to be formatted in roboto / arial
        val feeAmountIndex: Int = chargeableTextFirstPartIndex + feeAmount.length
        //the end of the text will be formatted in exo font

        val spannableStringBuilder = SpannableStringBuilder(chargeableText)
        spannableStringBuilder.setSpan(CustomTypefaceSpan("", exoFont), 0, chargeableTextFirstPartIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableStringBuilder.setSpan(CustomTypefaceSpan("", boldFont), chargeableTextFirstPartIndex, feeAmountIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        spannableStringBuilder.setSpan(CustomTypefaceSpan("", exoFont), feeAmountIndex, chargeableText.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)

        feeTextView.text = spannableStringBuilder
    }

    //endregion

    //region VASPhoneView arial allowed

    fun setVASPhoneViewArialAllowed(arialAllowed: Boolean) {
        this.arialAllowed = arialAllowed
        if (feeTextView.text != null) {
            setupVASPhoneViewAmount(feeAmount, arialAllowed)
        }
        phoneNumberTextView.typeface = getNonNullFont(context, R.font.arial_bold)
    }

    //endregion

    //region VASPhoneView dial on click

    fun setVASPhoneViewDialOnClick(dialOnClick: Boolean) {
        if (dialOnClick) {
            setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + phoneNumber)
                if (intent.resolveActivity(context.packageManager) != null) {
                    startActivity(context, intent, null)
                }
            }

        }
    }

    //endregion

    private fun getNonNullFont(context: Context, idRes: Int): Typeface {
        return ResourcesCompat.getFont(context, idRes) ?: Typeface.DEFAULT
    }

    /**
     * CustomTypefaceSpan allows to use custom fonts on TypefaceSpan
     */
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