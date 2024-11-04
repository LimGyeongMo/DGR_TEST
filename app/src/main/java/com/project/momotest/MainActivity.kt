package com.project.momotest

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.project.dgr.ConvertJsonUtil
import com.project.dgr.dgrDrawable
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        getDrawable(dgrDrawable.bg_circle_2)
        ConvertJsonUtil.convertJson("", "", object : ConvertJsonUtil.ConvertJsonListener {
            override fun success(jsonArray: JSONArray) {
                // TODO: 성공 처리
            }

            override fun error(error: String) {
                // TODO: 실패 처리
            }
        })
    }
}