package com.example.stigma

class Relato{
    var relato : String = ""
    var emocao : String = ""
    var usuario : String = ""
    var data : String = ""

    constructor(){
    }

    constructor(relato : String, emocao : String, usuario : String, data : String){
        this.relato = relato
        this.emocao = emocao
        this.usuario = usuario
        this.data = data
    }
}