package com.novachevskyi.superdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, newFragmentInstance())
                .commitAllowingStateLoss()
        }
    }

    private fun newFragmentInstance(): FirstFragment {
        val args = Bundle()
        val fragment = FirstFragment()
        fragment.arguments = args
        return fragment
    }
}
