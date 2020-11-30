package gr.fellow.fellow_traveller.data.base


import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.target.ImageViewTarget


class GifDrawableImageViewTarget : ImageViewTarget<Drawable?> {
    private var mLoopCount = GifDrawable.LOOP_FOREVER
    private lateinit var function: (Boolean) -> Unit

    constructor(view: ImageView?, loopCount: Int, listener: (Boolean) -> Unit) : super(view) {
        mLoopCount = loopCount
        function = listener
    }

    constructor(view: ImageView?, loopCount: Int, waitForLayout: Boolean) : super(view, waitForLayout) {
        mLoopCount = loopCount
    }

    override fun setResource(resource: Drawable?) {
        if (resource is GifDrawable) {
            resource.setLoopCount(mLoopCount)
            resource.registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable) {
                    super.onAnimationEnd(drawable)
                    function.invoke(true)
                }
            })
        }

        view.setImageDrawable(resource)
    }
}