/*
 * MIT License
 *
 * Copyright (c) 2021 A M Satrio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.ams64.pembangkitsandi

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var clipData: ClipData? = null
    private var clipboardManager: ClipboardManager? = null

    @SuppressLint("UseSwitchCompatOrMaterialCode", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Clipboard
        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        val switch09: Switch = findViewById(R.id.switch09)
        val switchAZSmall: Switch = findViewById(R.id.switchAZSmall)
        val switchAZBig: Switch = findViewById(R.id.switchAZBig)
        val switchCustom1: Switch = findViewById(R.id.switchCustom1)
        val switchCustom2: Switch = findViewById(R.id.switchCustom2)
        val switchCustom3: Switch = findViewById(R.id.switchCustom3)
        val switchExtendedASCII: Switch = findViewById(R.id.switchExtendedASCII)

        val editTextTextYourCustomChar: EditText= findViewById(R.id.editTextTextYourCustomChar)
        val editTextNumberCountPassword: EditText = findViewById(R.id.editTextNumberCountPassword)

        val buttonGenerate: Button = findViewById(R.id.buttonGenerate)
        val buttonCopy: Button = findViewById(R.id.buttonCopy)

        val textViewResult: TextView = findViewById(R.id.textViewResult)
        val textViewGaugePassword: TextView = findViewById(R.id.textViewGaugePassword)

        val progressBarGaugePassword: ProgressBar = findViewById(R.id.progressBarGaugePassword)



        var stringTemp = ""
        var stringResult = ""


        //Generate ASCII to Char, then to String
        var string09 = ""
        for (i in 48..57){
            string09 += i.toChar()
        }
        var stringAZSmall = ""
        for (i in 97..122){
            stringAZSmall += i.toChar()
        }
        var stringAZBig = ""
        for (i in 65..90){
            stringAZBig += i.toChar()
        }

        var stringCustom1 = ""
        for (i in 33..47){
            stringCustom1 += i.toChar()
        }
        var stringCustom2 = ""
        for (i in 58..64){
            stringCustom2 += i.toChar()
        }
        var stringCustom3 = ""
        for (i in listOf(91..96,123..126)){
            for (j in i){
                stringCustom3 += j.toChar()
            }
        }

        var stringExtendedASCII = ""
        for (i in listOf(161..254)){
            for (j in i){
                stringExtendedASCII += j.toChar()
            }
        }


        var countPassword: Int
        buttonGenerate.setOnClickListener{
            textViewResult.text = getString(R.string.string_progress)
            var scorePass = 0

            countPassword = if(editTextNumberCountPassword.text.isNotEmpty()){
                editTextNumberCountPassword.text.toString().toInt()
            } else {
                12
            }

            if(switch09.isChecked){
                stringTemp += string09
                scorePass++
            }
            if(switchAZBig.isChecked){
                stringTemp += stringAZBig
                scorePass++
            }
            if(switchAZSmall.isChecked){
                stringTemp += stringAZSmall
                scorePass++
            }
            if(switchCustom1.isChecked){
                stringTemp += stringCustom1
                scorePass += 2
            }
            if(switchCustom2.isChecked){
                stringTemp += stringCustom2
                scorePass += 2
            }
            if(switchCustom3.isChecked){
                stringTemp += stringCustom3
                scorePass += 2
            }
            if(switchExtendedASCII.isChecked){
                stringTemp += stringExtendedASCII
                scorePass += 2
            }
            if(editTextTextYourCustomChar.text.isNotEmpty()){
                stringTemp += editTextTextYourCustomChar.text
            }
            if(!switch09.isChecked && !switchAZBig.isChecked && !switchAZSmall.isChecked && !switchCustom1.isChecked && !switchCustom2.isChecked && !switchCustom3.isChecked && !switchExtendedASCII.isChecked && editTextTextYourCustomChar.text.isEmpty()){
                stringTemp += string09
                stringTemp += stringAZBig
                stringTemp += stringAZSmall
                scorePass += 3
            } else {
                scorePass += 2
            }


            if(editTextNumberCountPassword.text.isNotEmpty()){
                countPassword = editTextNumberCountPassword.text.toString().toInt()
            }

            for(i in 0 until countPassword){
                stringResult += stringTemp[(Math.random()*stringTemp.length).toInt()]
            }
            textViewResult.text = stringResult
            stringTemp = ""

            for(i in 2..13){
                if(scorePass == i){
                    scorePass += i
                }
            }

            var c = 0
            for (i in stringResult.indices) {
                c += stringResult[i].toInt()
            }
            val byteTemp = scorePass*c/(13*8)


            when {
                byteTemp <= 100 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FFE91E63"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 10
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_weak)}"
                }
                byteTemp in 101..300 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FFFF9800"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 30
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_medium)}"
                }
                byteTemp in 301..500 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FFFFC107"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 50
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_strong)}"
                }
                byteTemp in 501..800 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FF4CAF50"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 70
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_super)}"
                }
                byteTemp in 801..1300 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FF00BCD4"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 90
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_super_strong)}"
                }
                byteTemp in 1301..24000 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FF3F51B5"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 99
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_ultra_strong)}"
                }
                else -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FF9C27B0"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 100
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_smile)}"
                }
            }
            stringResult = ""
        }

        textViewResult.setOnLongClickListener{
            clipData = ClipData.newPlainText("text", textViewResult.text)
            clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }

            Toast.makeText(this, getString(R.string.string_notif_copy), Toast.LENGTH_SHORT).show()

            val timer = object: CountDownTimer(16000,1000){
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    clipData = ClipData.newPlainText("text", "")
                    clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }
                }
            }
            timer.start()
            true
        }

        buttonCopy.setOnClickListener{
            clipData = ClipData.newPlainText("text", textViewResult.text)
            clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }

            Toast.makeText(this, getString(R.string.string_notif_copy), Toast.LENGTH_SHORT).show()

            val timer = object: CountDownTimer(16000,1000){
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    clipData = ClipData.newPlainText("text", "")
                    clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }
                }
            }
            timer.start()
        }



//        //dark theme
//        val linearLayoutGeneral: LinearLayout = findViewById(R.id.linearLayoutGeneral)
//        val imageButtonDarkTheme: ImageButton = findViewById(R.id.imageButtonDarkTheme)
//        var darkTheme = false
//        imageButtonDarkTheme.setOnClickListener{
//            darkTheme = if(darkTheme){
//                linearLayoutGeneral.setBackgroundColor(Color.WHITE)
//                false
//            } else {
//                linearLayoutGeneral.setBackgroundColor(Color.GRAY)
//                true
//            }
//        }
    }

    //OPTION MENU (TOP RIGHT)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }


    //OPTION MENU (TOP RIGHT)
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }


    //OPTION MENU (TOP RIGHT)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        return when (item.itemId) {
            R.id.about -> {
                val intentAboutActivity =
                    Intent(this, AboutActivity::class.java)
                startActivity(intentAboutActivity)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}