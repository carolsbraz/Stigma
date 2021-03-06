package com.projeto.appstigma.Activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.stigma.MainActivity
import com.example.stigma.PrincipalActivity
import com.example.stigma.R
import com.google.firebase.auth.FirebaseAuth
import com.projeto.appstigma.Utils.exibiuMensagem
import com.projeto.appstigma.Utils.usuarios
import com.projeto.appstigma.Utils.usuariosList
import kotlinx.android.synthetic.main.activity_configuracoes.*
import kotlinx.android.synthetic.main.custom_modal_apagar_conta.view.*

class ConfiguracoesActivity : AppCompatActivity() {

    var emailLogado = ""
    var idLogado = ""
    var senhaLogado = ""
    var avataruser = ""

    val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_configuracoes)

        val user = FirebaseAuth.getInstance().currentUser
        emailLogado = user!!.email.toString()

        for (i in usuariosList) {
            if (i.email == emailLogado) {
                avataruser = i.avatar.toString()
                txt_editar_nome.setText(i.nome)
                txt_editar_dataNasc.setText(i.dataNasc)
                txt_editar_email.setText(i.email)
                txt_editar_senha.setText(i.senha)
                idLogado = i.id
                senhaLogado = i.senha
            }
        }

        val extras = intent.extras

        if (extras != null && avataruser != extras.getString("avatar").toString()) {
            avataruser = extras.getString("avatar").toString()
        }
        if (avataruser == "avatar_boy1") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy1)
        }
        if (avataruser == "avatar_boy2") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy2)
        }
        if (avataruser == "avatar_boy3") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy3)
        }
        if (avataruser == "avatar_boy4") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy4)
        }
        if (avataruser == "avatar_boy5") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy5)
        }
        if (avataruser == "avatar_boy6") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy6)
        }
        if (avataruser == "avatar_boy7") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy7)
        }
        if (avataruser == "avatar_boy8") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy8)
        }
        if (avataruser == "avatar_boy9") {
            btn_avatar_conf.setBackgroundResource(R.drawable.boy9)
        }
        if (avataruser == "avatar_girl1") {
            btn_avatar_conf.setBackgroundResource(R.drawable.girl1)
        }
        if (avataruser == "avatar_girl2") {
            btn_avatar_conf.setBackgroundResource(R.drawable.girl2)
        }
        if (avataruser == "avatar_girl3") {
            btn_avatar_conf.setBackgroundResource(R.drawable.girl3)
        }
        if (avataruser == "avatar_girl4") {
            btn_avatar_conf.setBackgroundResource(R.drawable.girl4)
        }
        if (avataruser == "avatar_girl5") {
            btn_avatar_conf.setBackgroundResource(R.drawable.girl5)
        }
        if (avataruser == "avatar_girl6") {
            btn_avatar_conf.setBackgroundResource(R.drawable.girl6)
        }

        btn_voltar_6.setOnClickListener {
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_avatar_conf.setOnClickListener {
            val intent = Intent(this, AvatarActivityMudar::class.java)
            startActivity(intent)
        }

        btn_alterar.setOnClickListener {

            if (txt_editar_senha.text.toString() != senhaLogado) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.updatePassword(txt_editar_senha.text.toString())
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            usuarios.child(idLogado).child("avatar")
                                .setValue(avataruser)
                            usuarios.child(idLogado).child("nome")
                                .setValue(txt_editar_nome.text.toString())
                            usuarios.child(idLogado).child("email")
                                .setValue(txt_editar_email.text.toString())
                            usuarios.child(idLogado).child("dataNasc")
                                .setValue(txt_editar_dataNasc.text.toString())
                            usuarios.child(idLogado).child("senha")
                                .setValue(txt_editar_senha.text.toString())
                            Toast.makeText(
                                baseContext, "Alterações feitas com sucesso.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this, PrincipalActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
            } else {
                usuarios.child(idLogado).child("avatar").setValue(avataruser)
                usuarios.child(idLogado).child("nome").setValue(txt_editar_nome.text.toString())
                usuarios.child(idLogado).child("email").setValue(txt_editar_email.text.toString())
                usuarios.child(idLogado).child("dataNasc")
                    .setValue(txt_editar_dataNasc.text.toString())
                usuarios.child(idLogado).child("senha").setValue(txt_editar_senha.text.toString())
                Toast.makeText(
                    baseContext, "Alterações feitas com sucesso.",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this, PrincipalActivity::class.java)
                startActivity(intent)
                finish()
            }
        }



        btn_apagar_conf.setOnClickListener {

            val dialog: Dialog = Dialog(context)
            val view = layoutInflater.inflate(R.layout.custom_modal_apagar_conta, null)
            dialog.setContentView(view)
            dialog.show()
            view.btn_apagar_conta_modal.setOnClickListener {
                user.delete().addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (i in usuariosList) {
                            if (i.email == emailLogado) {
                                usuarios.child(i.id).removeValue()
                            }
                        }
                        Toast.makeText(
                            baseContext, "Conta apagada com sucesso.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            baseContext, "Erro ao apagar conta.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                exibiuMensagem = false
            }
            view.btn_cancelar_apagar_conta_modal.setOnClickListener {
                dialog.dismiss()
            }


        }
    }
}
