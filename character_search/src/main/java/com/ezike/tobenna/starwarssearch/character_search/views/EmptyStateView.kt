package com.ezike.tobenna.starwarssearch.character_search.views

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.ezike.tobenna.starwarssearch.character_search.R
import com.ezike.tobenna.starwarssearch.character_search.databinding.SimpleEmptyStateViewLayoutBinding
import com.ezike.tobenna.starwarssearch.core.ext.safeOffer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.debounce

typealias ActionButtonClickListener = () -> Unit

class EmptyStateView : LinearLayout {

    private var binding: SimpleEmptyStateViewLayoutBinding

    var buttonClickListener: ActionButtonClickListener? = null

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = SimpleEmptyStateViewLayoutBinding.inflate(inflater, this, true)

        val attributes: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.EmptyStateView, 0, 0)

        val emptyStateTitle: String? =
            attributes.getString(R.styleable.EmptyStateView_emptyStateTitleText)
        val emptyStateImageSrc: Drawable? =
            attributes.getDrawable(R.styleable.EmptyStateView_emptyStateImageSrc)
        val emptyStateCaption: String? =
            attributes.getString(R.styleable.EmptyStateView_emptyStateCaptionText)
        val emptyStateButtonText: String? =
            attributes.getString(R.styleable.EmptyStateView_emptyStateButtonText)

        val emptyStateButtonVisible: Boolean =
            attributes.getBoolean(R.styleable.EmptyStateView_isButtonVisible, false)
        val emptyStateTitleVisible: Boolean =
            attributes.getBoolean(R.styleable.EmptyStateView_isTitleVisible, true)

        attributes.recycle()

        setTitle(emptyStateTitle)

        binding.title.isVisible = emptyStateTitleVisible

        setImage(emptyStateImageSrc)

        setCaption(emptyStateCaption)

        if (emptyStateButtonText != null) {
            binding.retryBtn.text = emptyStateButtonText
        }

        binding.retryBtn.isVisible = emptyStateButtonVisible

        binding.retryBtn.setOnClickListener {
            buttonClickListener?.invoke()
        }

        val actionBtnBounceAnim: ObjectAnimator =
            ObjectAnimator.ofFloat(binding.image, "translationY", 0f, 25f, 0f)
        actionBtnBounceAnim.interpolator = AccelerateDecelerateInterpolator()
        actionBtnBounceAnim.duration = 3000
        actionBtnBounceAnim.repeatMode = ValueAnimator.RESTART
        actionBtnBounceAnim.repeatCount = ValueAnimator.INFINITE
        actionBtnBounceAnim.start()
    }

    fun setImage(emptyStateImageSrc: Drawable?) {
        if (emptyStateImageSrc != null) {
            binding.image.setImageDrawable(emptyStateImageSrc)
        } else {
            binding.image.visibility = View.INVISIBLE
        }
    }

    var isButtonVisible: Boolean = false
        set(value) {
            field = value
            binding.retryBtn.isVisible = value
        }

    var isTitleVisible: Boolean = false
        set(value) {
            field = value
            binding.title.isVisible = value
        }

    fun setCaption(emptyStateCaption: String?) {
        if (emptyStateCaption != null) {
            binding.caption.text = emptyStateCaption
        }
    }

    fun resetCaption() {
        binding.caption.text = ""
    }

    fun setTitle(emptyStateTitle: String?) {
        if (emptyStateTitle != null) {
            binding.title.text = emptyStateTitle
        }
    }

    val clicks: Flow<Unit>
        get() = callbackFlow {
            val listener: () -> Unit = {
                safeOffer(Unit)
                Unit
            }
            buttonClickListener = listener
            awaitClose { buttonClickListener = null }
        }.conflate().debounce(200)
}
