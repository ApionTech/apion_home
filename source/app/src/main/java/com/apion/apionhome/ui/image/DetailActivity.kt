package com.apion.apionhome.ui.image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.apion.apionhome.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        findNavController(R.id.navHostActivityDetail)
            .setGraph(R.navigation.detail_graph, intent.extras)
    }
}