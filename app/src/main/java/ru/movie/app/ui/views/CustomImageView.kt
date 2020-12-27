package ru.movie.app.ui.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.Px
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toRectF
import ru.movie.app.R
import ru.movie.app.ui.extensions.dpToPxFloat

class CustomImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.ratingBarStyle
) : AppCompatImageView(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_TOP_CORNER_RADIUS = 0
        private const val DEFAULT_BOTTOM_GRADIENT_HEIGHT = 1f
    }

    @Px
    private var topCornerRadius: Float = context.dpToPxFloat(DEFAULT_TOP_CORNER_RADIUS)
    private var bottomBackgroundHeight: Float = DEFAULT_BOTTOM_GRADIENT_HEIGHT
    private var bottomBackgroundDrawable: Drawable? = null

    private val imagePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val maskPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
    }

    private val viewRect = Rect()
    private var placeHolder: Drawable? = null
    private lateinit var resultBm: Bitmap

    init {
        if (attrs != null) {
            val typeArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView)
            placeHolder = typeArray.getDrawable(R.styleable.CustomImageView_imagePlaceHolder)
            topCornerRadius = typeArray.getDimension(
                R.styleable.CustomImageView_topCornerRadius,
                context.dpToPxFloat(DEFAULT_TOP_CORNER_RADIUS)
            )
            bottomBackgroundHeight = typeArray.getDimension(
                R.styleable.CustomImageView_bottomBackgroundHeight,
                DEFAULT_BOTTOM_GRADIENT_HEIGHT
            )
            bottomBackgroundDrawable =
                typeArray.getDrawable(R.styleable.CustomImageView_bottomBackgroundDrawable)
            typeArray.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w == 0) return
        with(viewRect) {
            top = 0
            bottom = h
            left = 0
            right = w
        }
        prepareShader(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        viewRect.set(0, 0, width, height - topCornerRadius.toInt())
        canvas?.save()
        canvas?.clipRect(0, 0, width, height)
        canvas?.drawRoundRect(viewRect.toRectF(), topCornerRadius, topCornerRadius, imagePaint)
        canvas?.restore()

        viewRect.set(0, height - topCornerRadius.toInt(), width, height)
        canvas?.drawRect(viewRect, imagePaint)
    }

    private fun prepareShader(w: Int, h: Int) {
        prepareBitmap(w, h)
        imagePaint.shader = BitmapShader(resultBm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        if (width != 0 && height != 0)
            prepareShader(width, height)
    }

    private fun prepareBitmap(w: Int, h: Int) {
        viewRect.set(0, 0, w, h)

        val drawable = drawable ?: placeHolder!!
        resultBm = drawable.toBitmap(w, h, Bitmap.Config.ARGB_8888)
            .copy(Bitmap.Config.ARGB_8888, true)

        val canvas = Canvas(resultBm)
        canvas.drawBitmap(resultBm, viewRect, viewRect, imagePaint)

        if (bottomBackgroundDrawable != null) {
            val layerGradient = bottomBackgroundDrawable!!

            val layerBm =
                layerGradient.toBitmap(w, bottomBackgroundHeight.toInt(), Bitmap.Config.ARGB_8888)
            maskPaint.shader = BitmapShader(layerBm, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

            canvas.save()
            canvas.translate(0f, h - bottomBackgroundHeight + 10)
            canvas.drawRect(viewRect, maskPaint)
            canvas.restore()
            layerBm.recycle()
        }

        viewRect.set(0, 0, w, h)
    }
}