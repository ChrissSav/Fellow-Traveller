package gr.fellow.fellow_traveller.ui.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.plugin.animation.MapAnimationOptions
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.createPolylineAnnotationManager
import gr.fellow.fellow_traveller.R
import gr.fellow.fellow_traveller.domain.trip.Destination
import gr.fellow.fellow_traveller.ui.views.TripRad

fun MapView.addMarker(destination: Destination, isDestination: Boolean = false, type: TripRad = TripRad.OFFER) {

    val resourceId = isDestination then { R.drawable.ic_pin } ?: R.drawable.ic_location
    val markerColor = (type == TripRad.OFFER) then { R.color.green_new } ?: R.color.orange_new

    bitmapFromDrawableRes(this.context, resourceId, markerColor)?.let {
        val pointAnnotationManager = annotations.createPointAnnotationManager()
        val pointAnnotationOptions: PointAnnotationOptions = PointAnnotationOptions()
            .withPoint(Point.fromLngLat(destination.longitude!!.toDouble(), destination.latitude!!.toDouble()))
            .withIconImage(it)

        pointAnnotationManager.create(pointAnnotationOptions)
    }

}

fun MapView.flyToPoint(destination: Destination) {
    getMapboxMap().flyTo(
        cameraOptions {
            center(Point.fromLngLat(destination.longitude!!.toDouble(), destination.latitude!!.toDouble())) // Sets the new camera position on click point
            zoom(12.0) // Sets the zoom
        },
        MapAnimationOptions.mapAnimationOptions {
            duration(4000)
        })
}

fun bitmapFromDrawableRes(context: Context, @DrawableRes resourceId: Int, @ColorInt markerColor: Int) =
    convertDrawableToBitmap(AppCompatResources.getDrawable(context, resourceId), context, markerColor)

@SuppressLint("ResourceAsColor")
fun convertDrawableToBitmap(sourceDrawable: Drawable?, context: Context, markerColor: Int): Bitmap? {
    if (sourceDrawable == null) {
        return null
    }


    val constantState = sourceDrawable.constantState ?: return null
    val drawable = constantState.newDrawable().mutate()
    drawable.colorFilter = PorterDuffColorFilter(ContextCompat.getColor(context, markerColor), PorterDuff.Mode.SRC_IN)

    val bitmap: Bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap

}