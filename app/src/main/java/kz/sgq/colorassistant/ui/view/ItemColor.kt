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
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kz.sgq.colorassistant.R
import kz.sgq.colorassistant.ui.util.ColorConverter

class ItemColor : View {
    private var color = Color.BLUE
    private var valueLightness = 0f
    private var valueSaturation = 0f
    private var positionLightness = 0
    private var positionSaturation = 0
    private var itemPointerRadius = 0
    private var itemPointerHaloRadius = 0
    private var firstItemPointerRadius = 0
    private var firstItemPointerHaloRadius = 0
    private var translationOffset = 0f
    private var angle = -Math.PI / 2
    private var itemPointerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var itemPointerHaloPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var enableHalo = false
    private var boolLightness = false
    private var position: FloatArray? = null
    private var movingPointer = false
    private var positionX = 0f
    private var positionY = 0f
    private var positionCurrent = 0f
    private var clickColor: OnClickListener? = null
    private var itemListener: OnItemListener? = null
    private var deleteIndex: Int = 0
    private var min = 0
    private var act = false
    private var scroll = false
    private var actY = 0f
    private var invisibilityHalo = 0x50
    private var deleting = false
    private var moveItem = true

    interface OnClickListener{

        fun onClick()
    }

    interface OnItemListener{

        fun onInfo(color: Int)

        fun onDelete(index: Int)
    }

    object ItemColor {
        var boolDelete = false
    }

    constructor(context: Context?) : super(context) {

        initConstructor(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

        initConstructor(attrs, 0)
    }

    constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {

        initConstructor(attrs, defStyleAttr)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.apply {
            translate(translationOffset, translationOffset)

            if (enableHalo) drawCircle(
                    positionX,
                    positionCurrent,
                    itemPointerHaloRadius.toFloat(),
                    itemPointerHaloPaint
            )

            drawCircle(
                    positionX,
                    positionCurrent,
                    itemPointerRadius.toFloat(),
                    itemPointerPaint
            )
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val intrinsicSize = (firstItemPointerHaloRadius + itemPointerHaloRadius)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> Math.min(intrinsicSize, widthSize)
            MeasureSpec.UNSPECIFIED -> intrinsicSize
            else -> 0
        }
        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> Math.min(intrinsicSize, heightSize)
            MeasureSpec.UNSPECIFIED -> intrinsicSize
            else -> 0
        }

        min = Math.min(width, height)

        setMeasuredDimension(min + (min / 4), ((min * 0.5f) * 2 + min).toInt())

        translationOffset = min * 0.5f
        positionX = (translationOffset - (min / 4)) / 2
        positionY = min * 0.5f
        positionCurrent = positionY
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (scroll)
            return true

        parent.requestDisallowInterceptTouchEvent(true)

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {

                if ((positionY <= event.y) &&
                        ((positionY + (itemPointerHaloRadius * 2)) >= event.y)) {
                    actY = event.y

                    if (moveItem)
                        movingPointer = true

                    clickColor?.onClick()
                }
            }

            MotionEvent.ACTION_MOVE -> {

                if (movingPointer) {
                    val delDistension = (positionY * 0.5)
                    val actTopOne = (positionY - (actY - event.y)) + delDistension
                    val actBottomOne = positionY + itemPointerHaloRadius * 2
                    val actBottomTwo = ((positionY + itemPointerHaloRadius * 2) +
                            (event.y - actY)) - delDistension
                    val actBottomThree = ((positionY + itemPointerHaloRadius * 2) +
                            (event.y - actY))
                    positionCurrent = when {
                        (actY > event.y) &&
                                ((positionY - (actY - event.y)) >= 0) -> {
                            positionY - (actY - event.y)
                        }
                        (actY < event.y) &&
                                (actBottomThree < ((min * 0.5f) * 2) + min) &&
                                ItemColor.boolDelete -> {
                            positionY + (event.y - actY)
                        }
                        else -> positionCurrent
                    }

                    act = if ((positionY >= event.y) || (positionY >= actTopOne)) {
                        deleting = false

                        true
                    } else if ((actBottomOne <= event.y) || (actBottomOne <= actBottomTwo)) {
                        deleting = true

                        true
                    } else false

                    invalidate()
                }
            }

            MotionEvent.ACTION_UP -> {
                if (!deleting && act) {
                    positionCurrent = positionY

                    itemListener?.onInfo(color)
                    invalidate()
                } else if (deleting && act && ItemColor.boolDelete) {
                    positionCurrent = positionY

                    invalidate()
                    itemListener?.onDelete(deleteIndex)
                } else {
                    positionCurrent = positionY

                    Toast.makeText(context, "Cancel", Toast.LENGTH_SHORT).show()
                    invalidate()
                }

                movingPointer = false
            }
        }

        return true
    }

    fun setOnItemColorListener(itemListener: OnItemListener) {
        this.itemListener = itemListener
    }

    fun setColor(color: Int) {
        this.color = color
        itemPointerPaint.color = color
        itemPointerHaloPaint.color = color
        itemPointerHaloPaint.alpha = invisibilityHalo

        invalidate()
    }

    fun setScroll(scroll: Boolean) {
        this.scroll = scroll
    }

    fun setDeleteIndex(index: Int) {
        deleteIndex = index
    }

    fun setMoveItem(moveItem: Boolean) {
        this.moveItem = moveItem
    }

    fun getColorHex(): String = ColorConverter.getHex(color)

    fun setEnable(bool: Boolean) {
        this.enableHalo = bool

        invalidate()
    }

    fun setPosition(position: FloatArray) {
        this.position = position
    }

    fun getPosition(): FloatArray? = position

    fun getLightness(): Float = valueLightness

    fun getBoolLightness(): Boolean = boolLightness

    fun getSaturation(): Float = valueSaturation

    fun setLightness(value: Float) {
        valueLightness = value
    }

    fun setBoolLightness(bool: Boolean) {
        boolLightness = bool
    }

    fun setSaturation(value: Float) {
        valueSaturation = value
    }

    fun setPositionLightness(position: Int) {
        positionLightness = position
    }

    fun setPositionSaturation(position: Int) {
        positionSaturation = position
    }

    fun getPositionLightness(): Int = positionLightness

    fun getPositionSaturation(): Int = positionSaturation

    fun setAngle(double: Double) {
        angle = double
    }

    fun getAngle(): Double = angle

    fun setOnClickItemColorListener(clickColor: OnClickListener) {
        this.clickColor = clickColor
    }

    private fun initConstructor(attrs: AttributeSet?, defStyleAttr: Int) {
        val resources = context.resources
        val typedArray: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ItemColor,
                defStyleAttr,
                0
        )
        itemPointerRadius = typedArray.getDimensionPixelSize(
                R.styleable.ItemColor_item_center_radius,
                resources.getDimensionPixelSize(R.dimen.item_pointer_radius)
        )
        itemPointerHaloRadius = typedArray.getDimensionPixelSize(
                R.styleable.ItemColor_item_center_halo_radius,
                resources.getDimensionPixelSize(R.dimen.item_pointer_halo_radius)
        )

        typedArray.recycle()

        firstItemPointerRadius = itemPointerRadius
        firstItemPointerHaloRadius = itemPointerHaloRadius
        itemPointerPaint.color = Color.BLUE
        itemPointerHaloPaint.color = Color.BLUE
        itemPointerHaloPaint.alpha = invisibilityHalo
    }
}