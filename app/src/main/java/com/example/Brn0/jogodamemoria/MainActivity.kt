package com.example.Brn0.jogodamemoria

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    var cartaVirada: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5) //controle da posicao das cartas
    var cartaTeste: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5) //controle das cartas que ja foram acertadas
    /* cada posicao e um botao, os valores indicarao qual a carta do respectivo botao */
    var pares: Array<Int> = arrayOf(-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1)

    /* variaveis de controles das jogadas */
    var lastIndex = -1
    var count = 0

    /* id's dos botoes para verificacoes */
    val botoes: Array<String> = arrayOf(R.id.bt00.toString(), R.id.bt01.toString(), R.id.bt02.toString(),
            R.id.bt03.toString(), R.id.bt04.toString(), R.id.bt05.toString(), R.id.bt06.toString(),
            R.id.bt07.toString(), R.id.bt08.toString(), R.id.bt09.toString(), R.id.bt10.toString(),
            R.id.bt11.toString())

    fun virarCarta(view: View){
        val botao = view as Button
        count++

        if(pares[botoes.indexOf(botao.id.toString())] >= 0){
            val card = pares[botoes.indexOf(botao.id.toString())]
            if (verifica(card)){ // verifica se a carta ja foi acertada
                if (card == 0){
                    teste(0, lastIndex, botoes.indexOf(botao.id.toString()), botao)
                }
                if (card == 1){
                    teste(1, lastIndex, botoes.indexOf(botao.id.toString()), botao)
                }
                if (card == 2){
                    teste(2, lastIndex, botoes.indexOf(botao.id.toString()), botao)
                }
                if (card == 3){
                    teste(3, lastIndex, botoes.indexOf(botao.id.toString()), botao)
                }
                if (card == 4){
                    teste(4, lastIndex, botoes.indexOf(botao.id.toString()), botao)
                }
                if (card == 5){
                    teste(5, lastIndex, botoes.indexOf(botao.id.toString()), botao)
                }
                lastIndex = botoes.indexOf(botao.id.toString())
            }else{
                count = 0
            }
        }else{
            val par = cartaVirada.random()
            selecionaCartaVirada(par, lastIndex, botao)
            lastIndex = botoes.indexOf(botao.id.toString())
        }

        if (cartaTeste.size == 0){ //quando todas as cartas sao acertadas, muda a tela
            val handler = Handler()
            handler.postDelayed({
                val intent = Intent(this, fimDeJogo::class.java)
                startActivity(intent)
            }, 3000)
        }
    }

    fun verifica(card:Int): Boolean {
        if (cartaTeste.contains(card)){
            return true
        }

        return false
    }

    /* caso sejam viradas duas cartas, estas sao comparadas, se iguais, sao consideradas como acerto */
    fun teste(card: Int, lastIndex: Int, newIndex:Int, botao: Button){
        selecionaImagem(card, botao)

        if (count == 2){
            if (pares[lastIndex] != pares[newIndex]){
                count = 0
                val handler = Handler()
                handler.postDelayed({
                    val img = botao.context.resources.getDrawable(R.drawable.verso)
                    botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
                    retornaVerso(lastIndex)
                }, 2450)
            }else{
                count = 0
                cartaTeste.remove(card)
            }
        }
    }

    /* designa uma carta para o botao clicado */
    fun selecionaCartaVirada(card:Int, lastIndex:Int, botao:Button){
        selecionaImagem(card, botao)

        var flag = 0

        if (pares.contains(card)){
            flag++
        }
        pares[botoes.indexOf(botao.id.toString())] = card
        teste(card, lastIndex, botoes.indexOf(botao.id.toString()), botao)
        if (flag > 0){
            cartaVirada.remove(card)
        }
    }

    /* revela a carta*/
    fun selecionaImagem(card:Int, botao:Button){
        var img: Drawable
        if (card == 0){
            img = botao.context.resources.getDrawable(R.drawable.pree)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (card == 1){
            img = botao.context.resources.getDrawable(R.drawable.amaa)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (card == 2){
            img = botao.context.resources.getDrawable(R.drawable.cnzz)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (card == 3){
            img = botao.context.resources.getDrawable(R.drawable.azuu)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (card == 4){
            img = botao.context.resources.getDrawable(R.drawable.vrdd)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (card == 5){
            img = botao.context.resources.getDrawable(R.drawable.verr)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
    }

    /* em caso de erro, retorna a primeira carta da jogada para o verso */
    fun retornaVerso(pos:Int) {
        if (pos == 0){
            val botao: Button = findViewById(R.id.bt00)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 1){
            val botao: Button = findViewById(R.id.bt01)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 2){
            val botao: Button = findViewById(R.id.bt02)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 3){
            val botao: Button = findViewById(R.id.bt03)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 4){
            val botao: Button = findViewById(R.id.bt04)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 5){
            val botao: Button = findViewById(R.id.bt05)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 6){
            val botao: Button = findViewById(R.id.bt06)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 7){
            val botao: Button = findViewById(R.id.bt07)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 8){
            val botao: Button = findViewById(R.id.bt08)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 9){
            val botao: Button = findViewById(R.id.bt09)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 10){
            val botao: Button = findViewById(R.id.bt10)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
        if (pos == 11){
            val botao: Button = findViewById(R.id.bt11)
            val img: Drawable = botao.context.resources.getDrawable(R.drawable.verso)
            botao.setCompoundDrawablesWithIntrinsicBounds(null, img, null, null)
        }
    }
}