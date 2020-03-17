package mobi.covid.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class ImageHelper {

    fun getBitmapDescriptorFromVector(id: Int, context: Context): BitmapDescriptor {
        val vectorDrawable: Drawable = context.getDrawable(id)!!
        val h = (24 * context.resources.displayMetrics.density).toInt();
        val w = (24 * context.resources.displayMetrics.density).toInt();
        vectorDrawable.setBounds(0, 0, w, h)
        val bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bm)
    }
}