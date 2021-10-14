package com.novachevskyi.superdo

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.transition.Transition
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val color = intent.extras?.getInt("color")

        window.sharedElementEnterTransition.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                transition?.removeListener(this)
            }

            override fun onTransitionResume(transition: Transition?) {
            }

            override fun onTransitionPause(transition: Transition?) {
            }

            override fun onTransitionCancel(transition: Transition?) {
            }

            override fun onTransitionStart(transition: Transition?) {
                color?.let {
                    val bgShape = findViewById<ImageView>(R.id.detail).background as GradientDrawable
                    bgShape.setColor(color)
                }
            }
        })
    }
}
