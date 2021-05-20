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
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.nio.charset.Charset

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

        var countPassword = 12

        if(editTextNumberCountPassword.text.isNotEmpty()){
            countPassword = editTextNumberCountPassword.text.toString().toInt()
        }

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
        for (i in listOf(128..175)){
            for (j in i){
                stringExtendedASCII += j.toChar()
            }
        }

        buttonGenerate.setOnClickListener{
            if(switch09.isChecked){
                stringTemp += string09
            }
            if(switchAZBig.isChecked){
                stringTemp += stringAZBig
            }
            if(switchAZSmall.isChecked){
                stringTemp += stringAZSmall
            }
            if(switchCustom1.isChecked){
                stringTemp += stringCustom1
            }
            if(switchCustom2.isChecked){
                stringTemp += stringCustom2
            }
            if(switchCustom3.isChecked){
                stringTemp += stringCustom3
            }
            if(switchExtendedASCII.isChecked){
                stringTemp += stringExtendedASCII
            }
            if(editTextTextYourCustomChar.text.isNotEmpty()){
                stringTemp += editTextTextYourCustomChar.text
            }
            if(!switch09.isChecked && !switchAZBig.isChecked && !switchAZSmall.isChecked && !switchCustom1.isChecked && !switchCustom2.isChecked && !switchCustom3.isChecked && !switchExtendedASCII.isChecked && editTextTextYourCustomChar.text.isEmpty()){
                stringTemp += string09
                stringTemp += stringAZBig
                stringTemp += stringAZSmall
            }


            if(editTextNumberCountPassword.text.isNotEmpty()){
                countPassword = editTextNumberCountPassword.text.toString().toInt()
            }


            for(i in 0 until countPassword){
                stringResult += stringTemp[(Math.random()*stringTemp.length).toInt()]
            }
            textViewResult.text = stringResult
            stringTemp = ""

            val byteTempArray = stringResult.toByteArray(Charset.defaultCharset())
            val byteTemp = byteTempArray.sum()/8

            when {
                byteTemp <= 80 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FFE91E63"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 10
                    textViewGaugePassword.text = "$byteTemp , Weak"
                }
                byteTemp in 81..160 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FFFF9800"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 30
                    textViewGaugePassword.text = "$byteTemp , Medium"
                }
                byteTemp in 161..320 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FFFFC107"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 50
                    textViewGaugePassword.text = "$byteTemp , Strong"
                }
                byteTemp in 321..480 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FF4CAF50"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 70
                    textViewGaugePassword.text = "$byteTemp , Super"
                }
                byteTemp in 481..800 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FF00BCD4"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 90
                    textViewGaugePassword.text = "$byteTemp , Super Strong"
                }
                byteTemp in 801..10000 -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FF3F51B5"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 99
                    textViewGaugePassword.text = "$byteTemp , Strongest"
                }
                else -> {
                    progressBarGaugePassword.progressDrawable.setColorFilter(
                        Color.parseColor("#FF9C27B0"), android.graphics.PorterDuff.Mode.SRC_IN)
                    progressBarGaugePassword.progress = 100
                    textViewGaugePassword.text = "$byteTemp , Ahlan Wa Sahlan"
                }
            }
            stringResult = ""
        }

        textViewResult.setOnClickListener{
            clipData = ClipData.newPlainText("text", textViewResult.text)
            clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }

            Toast.makeText(this, "Your Password Copied to Clipboard. Cleared after 16 second", Toast.LENGTH_SHORT).show()

            val timer = object: CountDownTimer(16000,1000){
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    clipData = ClipData.newPlainText("", "")
                    clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }
                }
            }
            timer.start()
        }

        buttonCopy.setOnClickListener{
            clipData = ClipData.newPlainText("text", textViewResult.text)
            clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }

            Toast.makeText(this, "Your Password Copied to Clipboard. Cleared after 16 second", Toast.LENGTH_SHORT).show()

            val timer = object: CountDownTimer(16000,1000){
                override fun onTick(millisUntilFinished: Long) {
                }

                override fun onFinish() {
                    clipData = ClipData.newPlainText("", "")
                    clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }
                }
            }
            timer.start()
        }

        //dark theme
        val linearLayoutGeneral: LinearLayout = findViewById(R.id.linearLayoutGeneral)
        val imageButtonDarkTheme: ImageButton = findViewById(R.id.imageButtonDarkTheme)
        var darkTheme = false
        imageButtonDarkTheme.setOnClickListener{
            darkTheme = if(darkTheme){
                linearLayoutGeneral.setBackgroundColor(Color.WHITE)
                false
            } else {
                linearLayoutGeneral.setBackgroundColor(Color.GRAY)
                true
            }
        }
    }
}