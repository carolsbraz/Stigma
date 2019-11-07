package com.projeto.appstigma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.stigma.PrincipalActivity
import com.example.stigma.R
import kotlinx.android.synthetic.main.activity_passo3.*

class Passo3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_passo3)

        btn_pular_tutorial.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        })


    }
}
