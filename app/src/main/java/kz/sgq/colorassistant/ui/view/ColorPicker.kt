package kz.sgq.colorassistant.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kz.sgq.colorassistant.R

class ColorPicker : View {
    private var lightnessView: LightnessView? = null
    private var saturationView: SaturationView? = null

    private val COLORS = intArrayOf(-0xffff01, -0xff0001, -0xff0100, -0x100, -0x10000, -0xff01, -0xffff01)
    private var translationOffset = 0f
    private var colorWheelThickness = 0
    private var colorPointerRadius = 0
    private var colorPointerHaloRadius = 0
    private var colorWheelRadius = 0
    private var colorCenterRadius = 0
    private var centerColor = 0
    private var firstColorWheelRadius = 0
    private var firstColorCenterRadius = 0
    private var colorPointerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var colorPointerHaloPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var colorWheelPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var colorCenterPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val colorWheelRectangle = RectF()
    private val colorCenterRectangle = RectF()
    private var color = 0
    private var oldSelectedListenerColor = 0
    private var userMoving = false
    private var angle = -Math.PI / 2
    private var mSlopX = 0f
    private var mSlopY = 0f
    private var touchColorWheelEnabled = true

    constructor(context: Context?) : super(context) {
        initConstructor(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initConstructor(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initConstructor(attrs, defStyleAttr)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val intrinsicSize = 2 * (firstColorWheelRadius + colorPointerHaloRadius)
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

        val min = Math.min(width, height)
        setMeasuredDimension(min, min)
        translationOffset = min * 0.5f

        colorWheelRadius = min / 2 - colorWheelThickness - colorPointerHaloRadius
        colorWheelRectangle.set(
                -colorWheelRadius.toFloat(),
                -colorWheelRadius.toFloat(),
                colorWheelRadius.toFloat(),
                colorWheelRadius.toFloat()
        )

        colorCenterRadius = (firstColorCenterRadius.toFloat() * (
                colorWheelRadius.toFloat() / firstColorWheelRadius
                )).toInt()
        colorCenterRectangle.set(
                -colorCenterRadius.toFloat(),
                -colorCenterRadius.toFloat(),
                colorCenterRadius.toFloat(),
                colorCenterRadius.toFloat()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val pointerPosition = calcPointPosition()

        canvas?.translate(translationOffset, translationOffset)

        canvas?.drawOval(colorWheelRectangle, colorWheelPaint)

        canvas?.drawCircle(pointerPosition[0], pointerPosition[1],
                colorPointerHaloRadius.toFloat(), colorPointerHaloPaint)

        canvas?.drawCircle(pointerPosition[0], pointerPosition[1],
                colorPointerRadius.toFloat(), colorPointerPaint)

        canvas?.drawOval(colorCenterRectangle, colorCenterPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        val x = event!!.x - translationOffset
        val y = event.y - translationOffset

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val pointerPosition = calcPointPosition()
                if (x >= pointerPosition[0] - colorPointerHaloRadius
                        && x <= pointerPosition[0] + colorPointerHaloRadius
                        && y >= pointerPosition[1] - colorPointerHaloRadius
                        && y <= pointerPosition[1] + colorPointerHaloRadius) {
                    mSlopX = x - pointerPosition[0]
                    mSlopY = y - pointerPosition[1]
                    userMoving = true
                    invalidate()
                } else if (Math.sqrt((x * x + y * y).toDouble()) <= colorWheelRadius + colorPointerHaloRadius
                        && Math.sqrt((x * x + y * y).toDouble()) >= colorWheelRadius - colorPointerHaloRadius
                        && touchColorWheelEnabled) {
                    userMoving = true
                    invalidate()
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                    return false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (userMoving) {
                    angle = Math.atan2((y - mSlopY).toDouble(), (x - mSlopX).toDouble())
                    calcColor()
                    colorPointerPaint.color = color
                    setColor(color)
                    colorPointerHaloPaint.color = centerColor
                    colorPointerHaloPaint.alpha = 0x50
                    setCenterColor(centerColor)
                    invalidate()
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                    return false
                }
            }
            MotionEvent.ACTION_UP -> {
                userMoving = false
                if (centerColor != oldSelectedListenerColor) {
                    oldSelectedListenerColor = centerColor
                }
                invalidate()
            }
            MotionEvent.ACTION_CANCEL -> {
                if (centerColor != oldSelectedListenerColor) {
                    oldSelectedListenerColor = centerColor
                }
            }
        }
        return true
    }

    private fun initConstructor(attrs: AttributeSet?, defStyleAttr: Int) {
        val resources = context.resources
        val typedArray: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.ColorPicker,
                defStyleAttr,
                0
        )

        colorWheelThickness = typedArray.getDimensionPixelSize(
                R.styleable.ColorPicker_color_wheel_thickness,
                resources.getDimensionPixelSize(R.dimen.color_wheel_thickness)
        )

        colorPointerRadius = typedArray.getDimensionPixelSize(
                R.styleable.ColorPicker_color_pointer_radius,
                resources.getDimensionPixelSize(R.dimen.color_pointer_radius)
        )

        colorPointerHaloRadius = typedArray.getDimensionPixelSize(
                R.styleable.ColorPicker_color_center_halo_radius,
                resources.getDimensionPixelSize(R.dimen.color_pointer_halo_radius)
        )

        colorWheelRadius = typedArray.getDimensionPixelSize(
                R.styleable.ColorPicker_color_wheel_radius,
                resources.getDimensionPixelSize(R.dimen.color_wheel_radius)
        )

        colorCenterRadius = typedArray.getDimensionPixelSize(
                R.styleable.ColorPicker_color_center_radius,
                resources.getDimensionPixelSize(R.dimen.color_center_radius)
        )

        firstColorWheelRadius = colorWheelRadius
        firstColorCenterRadius = colorCenterRadius

        typedArray.recycle()

        colorPointerPaint.color = calcColor()
        colorPointerHaloPaint.color = calcColor()
        colorPointerHaloPaint.alpha = 0x50

        colorWheelPaint.shader = SweepGradient(0f, 0f, COLORS, null)
        colorWheelPaint.style = Paint.Style.STROKE
        colorWheelPaint.strokeWidth = colorWheelThickness.toFloat()

        colorCenterPaint.color = calcColor()
    }

    private fun calcPointPosition(): FloatArray = floatArrayOf(
            (colorWheelRadius * Math.cos(angle)).toFloat(),
            (colorWheelRadius * Math.sin(angle)).toFloat()
    )

    private fun average(a: Int, b: Int, c: Float): Int = a + Math.round(c * (b - a))

    private fun calcColor(): Int {
        var unit = (angle / (2 * Math.PI)).toFloat()

        if (unit < 0) {
            unit += 1f
        }

        if (unit <= 0) {
            color = COLORS[0]
            centerColor = COLORS[0]
            return COLORS[0]
        } else if (unit >= 1) {
            color = COLORS[COLORS.size - 1]
            centerColor = COLORS[COLORS.size - 1]
            return COLORS[COLORS.size - 1]
        }

        var p = unit * (COLORS.size - 1)
        val i = p.toInt()
        p -= i.toFloat()

        val c0 = COLORS[i]
        val c1 = COLORS[i + 1]
        val a = average(Color.alpha(c0), Color.alpha(c1), p)
        val r = average(Color.red(c0), Color.red(c1), p)
        val g = average(Color.green(c0), Color.green(c1), p)
        val b = average(Color.blue(c0), Color.blue(c1), p)

        color = Color.argb(a, r, g, b)
        centerColor = Color.argb(a, r, g, b)
        return Color.argb(a, r, g, b)
    }

    private fun setColor(color: Int){
        lightnessView?.setColor(color)
        saturationView?.setColor(color)
    }

    fun setCenterColor(color: Int) {
        centerColor = color
        colorCenterPaint.color = centerColor
        invalidate()
    }

    fun addLightnessView(lightnessView: LightnessView){
        this.lightnessView = lightnessView
        this.lightnessView?.addColorPicker(this)
        this.lightnessView?.setColor(color)
    }

    fun addSaturationView(saturationView: SaturationView){
        this.saturationView = saturationView
        this.saturationView?.addColorPicker(this)
        this.saturationView?.setColor(color)
    }
}