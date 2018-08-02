/*
 * Copyright 2018 Vlad Weber-Pflaumer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kz.sgq.colorassistant.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.HSLConverter

class SaturationView : View {
    private var colorPicker: ColorPicker? = null

    private var posToSVFactor = 0f
    private var barThickness = 0
    private var barLength = 0
    private var preferredBarLength = 0
    private var barPointerRadius = 0
    private var barPointerHaloRadius = 0
    private var barPointerPosition = 0
    private var color = 0
    private var colorCurrent = 0
    private var movingPointer = false
    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barPaintHalo = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barPointerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barPointerHaloPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barRect = RectF()
    private val barRectHalo = RectF()
    private val mHSVColor = FloatArray(3)
    private var shader: Shader? = null
    private var shaderHalo: Shader? = null

    constructor(context: Context?) : super(context) {
        initConstructor(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initConstructor(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initConstructor(attrs, defStyleAttr)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initConstructor(attrs: AttributeSet?, defStyleAttr: Int) {
        val resources = context.resources
        val typedArray: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ColorPicker,
                defStyleAttr,
                0
        )

        barThickness = typedArray.getDimensionPixelSize(
                R.styleable.ColorBar_bar_thickness,
                resources.getDimensionPixelSize(R.dimen.bar_thickness))
        barLength = typedArray.getDimensionPixelSize(R.styleable.ColorBar_bar_length,
                resources.getDimensionPixelSize(R.dimen.bar_length))
        preferredBarLength = barLength
        barPointerRadius = typedArray.getDimensionPixelSize(
                R.styleable.ColorBar_bar_pointer_radius,
                resources.getDimensionPixelSize(R.dimen.bar_pointer_radius))
        barPointerHaloRadius = typedArray.getDimensionPixelSize(
                R.styleable.ColorBar_bar_pointer_halo_radius,
                resources.getDimensionPixelSize(R.dimen.bar_pointer_halo_radius))

        typedArray.recycle()

        barPaint.shader = shader
        barPaintHalo.shader = shaderHalo
        barPaintHalo.alpha = 0x50
        barPointerPosition = barPointerHaloRadius
        barPointerPaint.color = -0x7e0100
        barPointerHaloPaint.color = -0x7e0100
        barPointerHaloPaint.alpha = 0x50
        posToSVFactor = 1 / barLength.toFloat()

        setColor(Color.BLUE)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val barPointerRadius = barPointerHaloRadius * 2
        val intrinsicSize = preferredBarLength + (barPointerHaloRadius * 2)
        val lengthMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val lengthSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val length = when (lengthMode) {
            MeasureSpec.EXACTLY -> lengthSize
            MeasureSpec.AT_MOST -> Math.min(intrinsicSize, lengthSize)
            MeasureSpec.UNSPECIFIED -> intrinsicSize
            else -> 0
        }

        barLength = length - barPointerRadius
        setMeasuredDimension(barPointerRadius, (barLength + barPointerRadius))
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawRoundRect(barRectHalo, 5f, 5f, barPaintHalo)

        canvas?.drawRoundRect(barRect, 5f, 5f, barPaint)

        canvas?.drawCircle(
                barPointerHaloRadius.toFloat(),
                barPointerPosition.toFloat(),
                barPointerHaloRadius.toFloat(),
                barPointerHaloPaint
        )

        canvas?.drawCircle(
                barPointerHaloRadius.toFloat(),
                barPointerPosition.toFloat(),
                barPointerRadius.toFloat(),
                barPointerPaint
        )
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                movingPointer = true

                if (event.y >= barPointerHaloRadius &&
                        event.y <= barPointerHaloRadius + barLength) {
                    barPointerPosition = Math.round(event.y)
                    colorPicker?.setPositionSaturation(barPointerPosition)
                    calcColor(Math.round(event.y) - barPointerHaloRadius)
                    barPointerPaint.color = color
                    barPointerHaloPaint.color = color
                    barPointerHaloPaint.alpha = 0x50

                    invalidate()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (movingPointer) {
                    if (event.y >= barPointerHaloRadius &&
                            event.y <= barPointerHaloRadius + barLength) {
                        barPointerPosition = Math.round(event.y)
                        colorPicker?.setPositionSaturation(barPointerPosition)
                        calcColor(Math.round(event.y) - barPointerHaloRadius)
                        barPointerPaint.color = color
                        barPointerHaloPaint.color = color
                        barPointerHaloPaint.alpha = 0x50

                        invalidate()
                    } else if (event.y < barPointerHaloRadius) {
                        barPointerPosition = barPointerHaloRadius
                        colorPicker?.setPositionSaturation(barPointerPosition)
                        calcColor(Math.round(event.y) - barPointerHaloRadius)
                        color = colorCurrent
                        barPointerPaint.color = color
                        barPointerHaloPaint.color = color
                        barPointerHaloPaint.alpha = 0x50

                        invalidate()
                    } else if (event.y > barPointerHaloRadius + barLength) {
                        barPointerPosition = barPointerHaloRadius + barLength
                        colorPicker?.setPositionSaturation(barPointerPosition)
                        calcColor(Math.round(event.y) - barPointerHaloRadius)
                        color = HSLConverter.getSaturation(colorCurrent, 0f)
                        barPointerPaint.color = color
                        barPointerHaloPaint.color = color
                        barPointerHaloPaint.alpha = 0x50

                        invalidate()
                    }
                }
            }
            MotionEvent.ACTION_UP -> movingPointer = false
        }

        return true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        barLength = h - barPointerHaloRadius * 2
        barRect.set((barPointerHaloRadius - barThickness / 2).toFloat(),
                barPointerHaloRadius.toFloat(),
                (barPointerHaloRadius + barThickness / 2).toFloat(),
                (barLength + barPointerHaloRadius).toFloat())

        barRectHalo.set((barPointerHaloRadius - barThickness / 2).toFloat() - 2f,
                barPointerHaloRadius.toFloat() - 2f,
                (barPointerHaloRadius + barThickness / 2).toFloat() + 2f,
                (barLength + barPointerHaloRadius).toFloat() + 2f)
    }

    private fun calcColor(i: Int) {
        var j = i
        if (i < 0) {
            j = 0
        } else if (i > barLength) {
            j = barLength
        }
        color = HSLConverter.getSaturation(colorCurrent, 1 - (posToSVFactor * j))
        colorPicker?.setSaturation(1 - (posToSVFactor * j))
    }

    fun setPosition(position: Int) {
        barPointerPosition = position

        invalidate()
    }

    fun setColor(color: Int) {
        Color.colorToHSV(color, mHSVColor)
        val colorWithSaturation = HSLConverter.getSaturation(color, 0f)
        this.colorCurrent = color
        shader = LinearGradient(
                barPointerHaloRadius.toFloat(),
                0f,
                barThickness.toFloat(),
                (barLength + barPointerHaloRadius).toFloat(),
                intArrayOf(color, colorWithSaturation),
                null,
                Shader.TileMode.CLAMP
        )
        shaderHalo = LinearGradient(
                barPointerHaloRadius.toFloat(),
                0f,
                barThickness.toFloat(),
                (barLength + barPointerHaloRadius).toFloat(),
                intArrayOf(color, Color.alpha(color), colorWithSaturation),
                null,
                Shader.TileMode.CLAMP
        )
        barPaint.shader = shader
        barPaintHalo.shader = shaderHalo
        barPaintHalo.alpha = 0x50
        calcColor(barPointerPosition)
        barPointerPaint.color = this.color
        barPointerHaloPaint.color = this.color
        barPointerHaloPaint.alpha = 0x50

        invalidate()
    }

    fun getPosition(): Int = barPointerPosition

    fun addColorPicker(colorPicker: ColorPicker) {
        this.colorPicker = colorPicker
    }
}