package com.backelite.android.bkvasphoneviewandroid

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout


/**
 * Created by jean-baptistevincey on 04/10/2017.
 */

class VASPhoneView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    init {
        init()
        initAttrs(context, attrs)
    }

    fun init() {
        inflate(context, R.layout.vasphoneview, this)
    }

    fun initAttrs(context: Context, attrs: AttributeSet? = null) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VasPhoneView)

        val indexCount: Int = typedArray.indexCount
        for (i in 0..indexCount) {
            val attr = typedArray.getIndex(i)
            when (attr) {
                R.styleable.VasPhoneView_VasPhoneViewSize -> {
                    //FIXME put in methods
                    val vasPhoneViewSize: VASPhoneViewSize = VASPhoneViewSize.fromOrdinal(typedArray.getInt(attr, 0))
                    if (vasPhoneViewSize == VASPhoneViewSize.SMALL) {
                        //TODO
                    } else {
                        //normal by default
                        //TODO
                    }
                }

                R.styleable.VasPhoneView_VasPhoneViewStyle -> {
                    //FIXME put in methods
                    val vasPhoneViewStyle: VASPhoneViewStyle = VASPhoneViewStyle.fromOrdinal(typedArray.getInt(attr, 0))
                    if (vasPhoneViewStyle == VASPhoneViewStyle.STANDARD) {
                        //TODO
                    } else if (vasPhoneViewStyle == VASPhoneViewStyle.CHARGEABLE) {
                        //TODO
                    } else {
                        //free by default
                        //TODO
                    }
                }
            }
        }
        typedArray.recycle()
    }

    enum class VASPhoneViewSize(val code: Int) {
        NORMAL(0),
        SMALL(1);

        companion object {
            fun fromOrdinal(code: Int): VASPhoneViewSize {
                for (f in values()) {
                    if (f.ordinal == code) {
                        return f
                    }
                }
                return NORMAL
            }
        }
    }

    enum class VASPhoneViewStyle(val code: Int) {
        FREE(0),
        STANDARD(1),
        CHARGEABLE(2);

        companion object {
            fun fromOrdinal(code: Int): VASPhoneViewStyle {
                for (f in values()) {
                    if (f.ordinal == code) {
                        return f
                    }
                }
                return FREE
            }
        }
    }
}