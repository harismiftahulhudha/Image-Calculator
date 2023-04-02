package co.harismiftahulhudha.imagecalculator.core.extension

import android.os.Handler
import android.os.Looper
import android.view.View

const val minimumInterval: Long = 1000


private var handler: Handler = Handler(Looper.getMainLooper())
private var runnable = Runnable {  }
/**
 * Used for debouncing click listener in it's component scope only
 *
 * */
fun View.setOnSelfDebouncedClickListener(intervalMillis: Long = minimumInterval, func: (View) -> Unit) {
    var isClicked = false
    this.setOnClickListener {
        handler.removeCallbacks(runnable)
        runnable = Runnable {
            isClicked = false
        }
        handler.postDelayed(runnable, minimumInterval)
        if (!isClicked) {
            isClicked = true
            func(it)
        }
    }
}