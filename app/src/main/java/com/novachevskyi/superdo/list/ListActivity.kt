package com.novachevskyi.superdo.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.novachevskyi.superdo.R

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, newFragmentInstance())
                .commitAllowingStateLoss()
        }
    }

    private fun newFragmentInstance(): ListFragment {
        val args = Bundle()
        val fragment = ListFragment()
        fragment.arguments = args
        return fragment
    }
}
