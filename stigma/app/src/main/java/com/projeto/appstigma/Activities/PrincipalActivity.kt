package com.example.stigma

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.projeto.appstigma.Activities.*
import com.projeto.appstigma.Utils.exibiuMensagem
import com.projeto.appstigma.Utils.relatosList
import com.projeto.appstigma.Utils.relatosListReverse
import com.projeto.appstigma.Utils.usuariosList
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.custom_modal_mensagem.view.*
import kotlinx.android.synthetic.main.custom_modal_sair.view.*
import kotlinx.android.synthetic.main.custom_modal_sair.view.txt_mensagem_modal
import kotlinx.android.synthetic.main.custom_modal_surpresa.view.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class PrincipalActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    val context: Context = this
    var emailLogado = ""
    var avatar: String? = ""
    var nome = ""
    var datadecriacao = ""

    var feliz :Float= 0f
    var triste:Float= 0f
    var cansado :Float= 0f
    var total :Float= 0f

    var felizpc :Float = 0.0f
    var tristepc :Float = 0.0f
    var cansadopc :Float = 0.0f



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_principal)


        val user = FirebaseAuth.getInstance().currentUser
        emailLogado = user!!.email.toString()

        for (i in usuariosList) {
            if (i.email == emailLogado) {
                nome = i.nome
                avatar = i.avatar
                datadecriacao = i.datadecriacao
            }
        }

        setUpPieChartData()

        if (user != null) {
            txt_nome_logado.text = nome
            if (avatar == "avatar_boy1") {
                img_avatar.setImageResource(R.drawable.boy1)
            }
            if (avatar == "avatar_boy2") {
                img_avatar.setImageResource(R.drawable.boy2)
            }
            if (avatar == "avatar_boy3") {
                img_avatar.setImageResource(R.drawable.boy3)
            }
            if (avatar == "avatar_boy4") {
                img_avatar.setImageResource(R.drawable.boy4)
            }
            if (avatar == "avatar_boy5") {
                img_avatar.setImageResource(R.drawable.boy5)
            }
            if (avatar == "avatar_boy6") {
                img_avatar.setImageResource(R.drawable.boy6)
            }
            if (avatar == "avatar_boy7") {
                img_avatar.setImageResource(R.drawable.boy7)
            }
            if (avatar == "avatar_boy8") {
                img_avatar.setImageResource(R.drawable.boy8)
            }
            if (avatar == "avatar_boy9") {
                img_avatar.setImageResource(R.drawable.boy9)
            }
            if (avatar == "avatar_girl1") {
                img_avatar.setImageResource(R.drawable.girl1)
            }
            if (avatar == "avatar_girl2") {
                img_avatar.setImageResource(R.drawable.girl2)
            }
            if (avatar == "avatar_girl3") {
                img_avatar.setImageResource(R.drawable.girl3)
            }
            if (avatar == "avatar_girl4") {
                img_avatar.setImageResource(R.drawable.girl4)
            }
            if (avatar == "avatar_girl5") {
                img_avatar.setImageResource(R.drawable.girl5)
            }
            if (avatar == "avatar_girl6") {
                img_avatar.setImageResource(R.drawable.girl6)
            }
        } else {
            finish()
        }

        if (avatar == "") {
            val intent = Intent(this, PrincipalActivity::class.java)
            finish()
            startActivity(intent)
        }

        for (i in usuariosList) {
            if (i.email == emailLogado) {

                var dataNasc = i.dataNasc
                var dataComp = dataNasc.split("/")
                var niverMesDia = dataComp[1] + "/" + dataComp[0]

                val date = getCurrentDateTime()
                val dateInString = date.toString("MM/dd")

                if(niverMesDia == dateInString && exibiuMensagem == false){

                    val dialog: Dialog = Dialog(context)
                    val view = layoutInflater.inflate(R.layout.custom_modal_surpresa, null)
                    dialog.setContentView(view)
                    view.txt_nome_surpresa.text = i.nome

                    dialog.show()

                    exibiuMensagem = true

                }

            }
        }

        val dataHoraAtual = Date()
        val hora = SimpleDateFormat("HH:mm").format(dataHoraAtual)

        if(hora == "00:00"){
            exibiuMensagem = false
        }



        btn_tela_relato.setOnClickListener {
            val intent = Intent(this, RelatoActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_tela_mensagem.setOnClickListener {
            val intent = Intent(this, MensagemActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_tela_questao.setOnClickListener {
            val intent = Intent(this, QuestoesMotivadorasActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_tela_desafio.setOnClickListener {
            val intent = Intent(this, DesafioActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_config.setOnClickListener {
            val intent = Intent(this, ConfiguracoesActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_sair.setOnClickListener {
            val dialog: Dialog = Dialog(context)
            val view = layoutInflater.inflate(R.layout.custom_modal_sair, null)
            dialog.setContentView(view)
            dialog.show()
            view.btn_sair_modal.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            view.btn_cancelar_modal.setOnClickListener {
                dialog.dismiss()
            }
            exibiuMensagem = false
        }

        btn_config.setOnClickListener {
            val intent = Intent(this, ConfiguracoesActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_tela_mauqinadotempo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val date = getCurrentDateTime()
                val dateInString = date.toString("MM/dd")
                var date2 = datadecriacao
                if (dateInString == date2) {
                    var intent = Intent(this, MaquinaDoTempoActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Você só pode abrir sua máquina do tempo no seu aniversário de cadastro",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun setUpPieChartData() {

        val yVals = ArrayList<PieEntry>()

        for (r in relatosList) {
            if (r.usuario == emailLogado) {

                if(r.emocao == "feliz"){
                    feliz = feliz + 1
                }
                if(r.emocao == "muitofeliz"){
                    feliz = feliz + 2
                }
                if(r.emocao == "triste"){
                    triste = triste + 1
                }
                if(r.emocao == "muitotriste"){
                    triste = triste + 2
                }
                if(r.emocao == "cansado"){
                    cansado = cansado + 1
                }

            }
        }

        total = feliz + triste + cansado

        if(total == 0f){
            felizpc = 33f
            tristepc = 33f
            cansadopc = 33f

            pctg_alegre.text = "33%"
            pctg_cansado.text = "33%"
            pctg_triste.text = "33%"

        }else{
            felizpc = (feliz/total)*100

            tristepc = (triste/total)*100

            cansadopc = (cansado/total)*100
            val dec = DecimalFormat("##.0")

            pctg_alegre.text = if(felizpc>0) dec.format(felizpc)+"%" else "0%"
            pctg_cansado.text = if(cansadopc>0) dec.format(cansadopc)+"%" else "0%"
            pctg_triste.text = if(tristepc>0) dec.format(tristepc)+"%" else "0%"
        }



        yVals.add(PieEntry(felizpc + 0f))
        yVals.add(PieEntry(tristepc + 0f))
        yVals.add(PieEntry(cansadopc + 0f))

        //Valores do gráfico


        val dataSet = PieDataSet(yVals, "")
        dataSet.valueTextSize = 0f
        val colors = java.util.ArrayList<Int>()
        colors.add(Color.rgb(189, 120, 121))
        colors.add(Color.rgb(91, 176, 196))
        colors.add(Color.rgb(182, 182, 182))


        dataSet.setColors(colors)
        val data = PieData(dataSet)
        grafico.data = data
        grafico.centerTextRadiusPercent = 0f
        grafico.isDrawHoleEnabled = false
        grafico.legend.isEnabled = false
        grafico.description.isEnabled = false
        grafico.isRotationEnabled = false
    }
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}
