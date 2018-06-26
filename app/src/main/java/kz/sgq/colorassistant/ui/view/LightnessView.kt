package kz.sgq.colorassistant.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kz.sgq.colorassistant.R

class LightnessView : View {
    private var posToSVFactor = 0f
    private var svToPosFactor = 0f
    private var barThickness = 0
    private var barLength = 0
    private var preferredBarLength = 0
    private var barPointerRadius = 0
    private var barPointerHaloRadius = 0
    private var barPointerPosition = 0
    private var color = 0
    private var movingPointer = false
    private val barPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barPointerPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barPointerHaloPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val barRect = RectF()
    private val mHSVColor = FloatArray(3)
    private var shader: Shader? = null

    constructor(context: Context?) : super(context) {
        initConstructor(null, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initConstructor(attrs, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initConstructor(attrs, defStyleAttr)
    }

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
        barPointerPosition = (barLength / 2) + barPointerHaloRadius
        barPointerPaint.color = -0x7e0100
        barPointerHaloPaint.color = -0x7e0100
        barPointerHaloPaint.alpha = 0x50
        posToSVFactor = 1 / (barLength.toFloat() / 2)
        svToPosFactor = (barLength.toFloat() / 2) / 1

        setColor(Color.BLUE)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val barPointerRadius = barPointerHaloRadius * 2
        val intrinsicSize = preferredBarLength + (barPointerHaloRadius*2)
        var measureSpec = 0
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
        canvas?.drawRect(barRect, barPaint)

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

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)

        when (event?.action){
            MotionEvent.ACTION_DOWN -> {
                movingPointer = true

                if (event.y >= barPointerHaloRadius &&
                        event.y <= barPointerHaloRadius + barLength) {
                    barPointerPosition = Math.round(event.y)
                    calcColor(Math.round(event.y))
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
                        calcColor(Math.round(event.y))
                        barPointerPaint.color = color
                        barPointerHaloPaint.color = color
                        barPointerHaloPaint.alpha = 0x50
                        invalidate()
                    } else if (event.y < barPointerHaloRadius) {
                        barPointerPosition = barPointerHaloRadius
                        color = Color.BLACK
                        barPointerPaint.color = color
                        barPointerHaloPaint.color = color
                        barPointerHaloPaint.alpha = 0x50
                        invalidate()
                    } else if (event.y > barPointerHaloRadius + barLength) {
                        barPointerPosition = barPointerHaloRadius + barLength
                        color = Color.WHITE
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

//        if (!isInEditMode) {
//            shader = LinearGradient(
//                    barPointerRadius.toFloat(),
//                    0f,
//                    barThickness.toFloat(),
//                    (barLength + barPointerRadius).toFloat(),
//                    intArrayOf(-0x1,
//                            Color.HSVToColor(mHSVColor),
//                            -0x1000000
//                    ),
//                    null,
//                    Shader.TileMode.CLAMP)
//        } else {
//            shader = LinearGradient(
//                    barPointerRadius.toFloat(),
//                    0f,
//                    barThickness.toFloat(),
//                    (barLength + barPointerRadius).toFloat(),
//                    intArrayOf(
//                            -0x1,
//                            -0x7e0100,
//                            -0x1000000),
//                    null,
//                    Shader.TileMode.CLAMP
//            )
//            Color.colorToHSV(-0x7e0100, mHSVColor)
//        }

//        barPaint.shader = shader
//        posToSVFactor = 1 / (barLength.toFloat() / 2)
//        svToPosFactor = barLength.toFloat() / 2 / 1
//        val hsvColor = FloatArray(3)
//        Color.colorToHSV(color, hsvColor)
//
//        barPointerPosition = if (hsvColor[1] < hsvColor[2])
//            Math.round(svToPosFactor * hsvColor[1] + barPointerRadius)
//        else
//            Math.round(svToPosFactor * (1 - hsvColor[2])
//                            + barPointerRadius.toFloat() + (barLength / 2).toFloat())
//
//        if (isInEditMode)
//            barPointerPosition = barLength / 2 + barPointerRadius

    }

    fun setColor(color: Int) {
        Color.colorToHSV(color, mHSVColor)
        shader = LinearGradient(
                barPointerHaloRadius.toFloat(),
                0f,
                barThickness.toFloat(),
                (barLength + barPointerHaloRadius).toFloat(),
                intArrayOf(Color.BLACK, color, Color.WHITE),
                null,
                Shader.TileMode.CLAMP
        )
        barPaint.shader = shader
        calcColor(barPointerPosition)
        barPointerPaint.color = this.color
        barPointerHaloPaint.color = this.color
        barPointerHaloPaint.alpha = 0x50

        invalidate()
    }

    private fun calcColor(i: Int) {
        color = if (i > (barLength / 2) && (i < barLength))
            Color.HSVToColor(floatArrayOf(mHSVColor[0], 1 - posToSVFactor * (i - barLength / 2),1f))
        else if (i in 1..(barLength - 1))
            Color.HSVToColor(floatArrayOf(mHSVColor[0],1f, posToSVFactor * i ))
        else if (i == (barLength / 2))
            Color.HSVToColor(floatArrayOf(mHSVColor[0], 1f, 1f))
        else if (i <= 0)
            Color.BLACK
        else if (i >= barLength)
            Color.WHITE
        else 0

    }
}