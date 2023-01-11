package com.androidapp.firstapp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.timerTask
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var score = 0
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable { }
    var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageArray.add(imageView)
        imageArray.add(imageView2)
        imageArray.add(imageView3)
        imageArray.add(imageView4)
        imageArray.add(imageView5)
        imageArray.add(imageView6)
        imageArray.add(imageView7)
        imageArray.add(imageView8)
        imageArray.add(imageView9)

        hideImages()


        //CountTimer
        object : CountDownTimer(15500,1000){
            override fun onTick(p0: Long) {
                textTime.text = "Time : " + p0/1000
            }

            override fun onFinish() {
                textTime.text = "Time : 0"

                handler.removeCallbacks(runnable)

                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Restart the game?")
                alert.setPositiveButton("Yes",DialogInterface.OnClickListener{
                    dialogInterface, i ->
                    val intent = intent
                    finish()
                    startActivity(intent)
                })
                alert.setNegativeButton("No",DialogInterface.OnClickListener{
                    dialogInterface, i ->
                    Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
                })
                alert.show()
            }

        }.start()
    }

    fun increaseScore(view: View){
        score = score + 1
        textScore.text = "Score : $score"
    }
    fun hideImages(){

        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }
                val random = java.util.Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE
                handler.postDelayed(runnable,500)
            }
        }
        handler.post(runnable)



    }

}