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
import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat


class GeneratorActivity : AppCompatActivity() {
    private var clipData: ClipData? = null
    private var clipboardManager: ClipboardManager? = null




    @SuppressLint("UseSwitchCompatOrMaterialCode", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        //Load Preferences for Theme
        //fetch data from shared preferences
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        // set your custom theme here before setting layout
        super.setTheme(sharedPreferences.getInt("theme",R.style.Theme_PembangkitSandi))


        setContentView(R.layout.activity_generator)

        //Clipboard
        clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?

        val switch09: Switch = findViewById(R.id.switch09)
        val switchAZSmall: Switch = findViewById(R.id.switchAZSmall)
        val switchAZBig: Switch = findViewById(R.id.switchAZBig)
        val switchCustom1: Switch = findViewById(R.id.switchCustom1)
        val switchCustom2: Switch = findViewById(R.id.switchCustom2)
        val switchCustom3: Switch = findViewById(R.id.switchCustom3)
        val switchExtendedASCII: Switch = findViewById(R.id.switchExtendedASCII)

        val editTextTextFavoriteLetters: EditText= findViewById(R.id.editTextTextFavoriteLetters)
        val editTextTextDislikedLetters: EditText= findViewById(R.id.editTextTextDislikedLetters)
        val editTextNumberCountPassword: EditText = findViewById(R.id.editTextNumberCountPassword)

        val buttonGenerate: Button = findViewById(R.id.buttonGenerate)
        val buttonCopy: Button = findViewById(R.id.buttonCopy)

        val textViewResult: TextView = findViewById(R.id.textViewResult)
        val textViewGaugePassword: TextView = findViewById(R.id.textViewGaugePassword)

        val progressBarGaugePassword: ProgressBar = findViewById(R.id.progressBarGaugePassword)


        /////////////////////////////////////////////////////////////
        //Load Configuration
        switch09.isChecked = sharedPreferences.getBoolean("switch09",true)
        switchAZSmall.isChecked = sharedPreferences.getBoolean("switchAZSmall",true)
        switchAZBig.isChecked = sharedPreferences.getBoolean("switchAZBig",true)
        switchCustom1.isChecked = sharedPreferences.getBoolean("switchCustom1",false)
        switchCustom2.isChecked = sharedPreferences.getBoolean("switchCustom2",false)
        switchCustom3.isChecked = sharedPreferences.getBoolean("switchCustom3",false)
        switchExtendedASCII.isChecked =
            sharedPreferences.getBoolean("switchExtendedASCII",false)

        editTextTextFavoriteLetters.text.clear()
        editTextTextFavoriteLetters.text.insert(0,
            sharedPreferences.getString("editTextTextFavoriteLetters",""))

        editTextTextDislikedLetters.text.clear()
        editTextTextDislikedLetters.text.insert(0,
            sharedPreferences.getString("editTextTextDislikedLetters",""))

        editTextNumberCountPassword.text.clear()
        editTextNumberCountPassword.text.insert(0,
            sharedPreferences.getString("editTextNumberCountPassword",""))



        /////////////////////////////////////////////////////////////


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

            textViewResult.text = getString(R.string.string_progress)

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
            if(editTextTextFavoriteLetters.text.isNotEmpty()){
                stringTemp += editTextTextFavoriteLetters.text
            }


            if(!switch09.isActivated && !switchAZBig.isActivated && !switchAZSmall.isActivated &&
                !switchCustom1.isActivated && !switchCustom2.isActivated &&
                !switchCustom3.isActivated && !switchExtendedASCII.isActivated &&
                editTextTextFavoriteLetters.text.isEmpty()){
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

            textViewResult.text = getString(R.string.string_progress)

            //Exclude letter
            if(editTextTextDislikedLetters.text.isNotEmpty()){
                var stringTemp1 = ""
                for (i in stringTemp){
                    if (i !in editTextTextDislikedLetters.text){
                        stringTemp1 += i
                    }
                }
                stringTemp = stringTemp1
            }

            for(i in 0 until countPassword){
                stringResult += stringTemp[(Math.random()*stringTemp.length).toInt()]
            }

            //Result Here
            textViewResult.text = stringResult

            stringTemp = ""

            for(i in 2..13){
                if(scorePass == i){
                    scorePass += i
                }
            }

            var c = 0
            for (i in stringResult.indices) {
                c += stringResult[i].code
            }
            val byteTemp = scorePass*c/(13*8)


            when {
                byteTemp <= 100 -> {

                    progressBarGaugePassword.progressDrawable.colorFilter =
                        BlendModeColorFilterCompat.
                        createBlendModeColorFilterCompat(
                            Color.parseColor("#FFE91E63"),
                            BlendModeCompat.SRC_ATOP)
                    progressBarGaugePassword.progress = 10
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_weak)}"
                }
                byteTemp in 101..300 -> {
                    progressBarGaugePassword.progressDrawable.colorFilter =
                        BlendModeColorFilterCompat.
                        createBlendModeColorFilterCompat(
                            Color.parseColor("#FFFF9800"),
                            BlendModeCompat.SRC_ATOP)

                    progressBarGaugePassword.progress = 30
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_medium)}"
                }
                byteTemp in 301..500 -> {

                    progressBarGaugePassword.progressDrawable.colorFilter =
                        BlendModeColorFilterCompat.
                        createBlendModeColorFilterCompat(
                            Color.parseColor("#FFFFC107"),
                            BlendModeCompat.SRC_ATOP)
                    progressBarGaugePassword.progress = 50
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_strong)}"
                }
                byteTemp in 501..800 -> {

                    progressBarGaugePassword.progressDrawable.colorFilter =
                        BlendModeColorFilterCompat.
                        createBlendModeColorFilterCompat(
                            Color.parseColor("#FF4CAF50"),
                            BlendModeCompat.SRC_ATOP)
                    progressBarGaugePassword.progress = 70
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_super)}"
                }
                byteTemp in 801..1300 -> {

                    progressBarGaugePassword.progressDrawable.colorFilter =
                        BlendModeColorFilterCompat.
                        createBlendModeColorFilterCompat(
                            Color.parseColor("#FF00BCD4"),
                            BlendModeCompat.SRC_ATOP)
                    progressBarGaugePassword.progress = 90
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_super_strong)}"
                }
                byteTemp in 1301..24000 -> {

                    progressBarGaugePassword.progressDrawable.colorFilter =
                        BlendModeColorFilterCompat.
                        createBlendModeColorFilterCompat(
                            Color.parseColor("#FF3F51B5"),
                            BlendModeCompat.SRC_ATOP)
                    progressBarGaugePassword.progress = 99
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_ultra_strong)}"
                }
                else -> {

                    progressBarGaugePassword.progressDrawable.colorFilter =
                        BlendModeColorFilterCompat.
                        createBlendModeColorFilterCompat(
                            Color.parseColor("#FF9C27B0"),
                            BlendModeCompat.SRC_ATOP)
                    progressBarGaugePassword.progress = 100
                    textViewGaugePassword.text = "$byteTemp , ${getString(R.string.gauge_smile)}"
                }
            }
            stringResult = ""


            ////////////////////////////////Save Configuration
            //storing data in shared preferences
            val sharedPreferencesEditor: SharedPreferences.Editor =
                this.getSharedPreferences("mySharedPreferences", MODE_PRIVATE).edit()

            switch09.isChecked.let {
                sharedPreferencesEditor.putBoolean("switch09", it) }
            switchAZBig.isChecked.let {
                sharedPreferencesEditor.putBoolean("switchAZBig", it) }
            switchAZSmall.isChecked.let {
                sharedPreferencesEditor.putBoolean("switchAZSmall", it) }
            switchCustom1.isChecked.let {
                sharedPreferencesEditor.putBoolean("switchCustom1", it) }
            switchCustom2.isChecked.let {
                sharedPreferencesEditor.putBoolean("switchCustom2", it) }
            switchCustom3.isChecked.let {
                sharedPreferencesEditor.putBoolean("switchCustom3", it) }
            switchExtendedASCII.isChecked.let {
                sharedPreferencesEditor.putBoolean("switchExtendedASCII", it) }
            editTextNumberCountPassword.text.let {
                sharedPreferencesEditor.putString("editTextNumberCountPassword", it.toString()) }
            editTextTextDislikedLetters.text.let {
                sharedPreferencesEditor.putString("editTextTextDislikedLetters", it.toString()) }
            editTextTextFavoriteLetters.text.let {
                sharedPreferencesEditor.putString("editTextTextFavoriteLetters", it.toString()) }

            sharedPreferencesEditor.apply()

            println(switch09.isChecked)
            ////////////////////////////////////////////////////

        }

        textViewResult.setOnLongClickListener{
            clipData = ClipData.newPlainText("text", textViewResult.text)
            clipData?.let { it1 -> clipboardManager?.setPrimaryClip(it1) }

            Toast.makeText(this,
                getString(R.string.string_notif_copy), Toast.LENGTH_SHORT).show()

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

            Toast.makeText(this,
                getString(R.string.string_notif_copy), Toast.LENGTH_SHORT).show()

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




    }

    //OPTION MENU (TOP RIGHT)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        //Load Preferences for Theme
        //fetch data from shared preferences
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        menu.findItem(R.id.dark_theme).isChecked =
            sharedPreferences.getBoolean("themeConfig",false)

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
            R.id.help -> {
                val intentAboutActivity =
                    Intent(this, HelpActivity::class.java)
                startActivity(intentAboutActivity)
                true
            }
            R.id.dark_theme -> {
                when (item.isChecked){
                    false -> {//switch to dark theme

                        item.isChecked = true

                        //storing data in shared preferences
                        val sharedPreferencesEditor: SharedPreferences.Editor =
                            this.getSharedPreferences(
                                "mySharedPreferences", MODE_PRIVATE).edit()
                        sharedPreferencesEditor.putInt("theme",
                            R.style.Theme_PembangkitSandiDark)
                        sharedPreferencesEditor.putBoolean("themeConfig",true)
                        sharedPreferencesEditor.apply()

                        Toast.makeText(this,
                            "Dark Theme, Please Restart the App", Toast.LENGTH_SHORT).show()
                    }
                    true -> {//switch to light theme

                        item.isChecked = false

                        //storing data in shared preferences
                        val sharedPreferencesEditor: SharedPreferences.Editor =
                            this.getSharedPreferences(
                                "mySharedPreferences", MODE_PRIVATE).edit()
                        sharedPreferencesEditor.putInt("theme",
                            R.style.Theme_PembangkitSandi)
                        sharedPreferencesEditor.putBoolean("themeConfig",false)
                        sharedPreferencesEditor.apply()

                        Toast.makeText(this,
                            "Light Theme, Please Restart the App", Toast.LENGTH_SHORT).show()
                    }
                }

                true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}