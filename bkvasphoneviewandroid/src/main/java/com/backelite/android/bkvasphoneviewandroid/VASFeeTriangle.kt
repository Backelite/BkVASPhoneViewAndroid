package com.backelite.android.bkvasphoneviewandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by jean-baptistevincey on 12/10/2017.
 */
class VASFeeTriangle @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyle: Int = 0
) : View(context, attrs, defStyle) {

    val color: Int = Color.WHITE
    val paint: Paint = Paint()
    val triangle: Path = Path()

    override fun onFinishInflate() {
        super.onFinishInflate()

        this.paint.color = this.color
        this.paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        this.triangle.moveTo(0f, 0f)
        this.triangle.lineTo(width.toFloat(), (height / 2).toFloat())
        this.triangle.lineTo(0f, height.toFloat())
        this.triangle.lineTo(0f, 0f)

        canvas?.drawPath(this.triangle, this.paint)
    }

}