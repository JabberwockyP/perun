package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val siema = findViewById<TextView>(R.id.textView)
        val miasto = findViewById<TextView>(R.id.textView2)
        val czas = findViewById<TextView>(R.id.czas)
        val yo = findViewById<TextView>(R.id.textView5)
        val pogoda = findViewById<TextView>(R.id.pogoda)
        val obrazek = findViewById<ImageView>(R.id.obrazek)
        val but = findViewById<Button>(R.id.button)
        var miejsce = "https://geocoding-api.open-meteo.com/v1/search?name=Berlin&count=1&language=en&format=json"
//        var lat = "49.8225"
//        var long = "19.0469"
        val edit = findViewById<EditText>(R.id.editText)
//        var url = "https://api.open-meteo.com/v1/forecast?latitude=49.8225&longitude=19.0469&current=temperature_2m,is_day,rain,weather_code&hourly=temperature_2m,cloud_cover"
        var queue = Volley.newRequestQueue(this)






//        Volley.newRequestQueue(this).add(re)







//        Volley.newRequestQueue(this).add(request)

        but.setOnClickListener {
            miejsce = "https://geocoding-api.open-meteo.com/v1/search?name=${edit.text}&count=1&language=en&format=json"

            var re = JsonObjectRequest(Request.Method.GET, miejsce, null,
                { response ->
                    // Handle successful response
                    val apiResponseArray = response.getJSONArray("results")[0].toString()
                    val apiResponse = JSONObject(apiResponseArray)




                    var lat = apiResponse.get("latitude").toString()
                    var long = apiResponse.get("longitude").toString()
                    var url = "https://api.open-meteo.com/v1/forecast?latitude=${lat}&longitude=${long}&current=temperature_2m,is_day,rain,weather_code&hourly=temperature_2m,cloud_cover"
                    yo.text = lat.toString()

                    var request = JsonObjectRequest(Request.Method.GET, url, null,
                        { response ->
                            // Handle successful response
                            val apiResponse = response.getJSONObject("current")
                            siema.text = apiResponse.get("temperature_2m").toString() + "°C"
                            czas.text = apiResponse.get("time").toString()
                            if(apiResponse.get("weather_code").toString().toInt() < 4){
                                pogoda.text = "Sloneczko"
                                obrazek.setImageResource(R.drawable.kacper);
                            }else if(apiResponse.get("weather_code").toString().toInt() > 60 && apiResponse.get("weather_code").toString().toInt() < 66){
                                pogoda.text = "deszczyk"
                                obrazek.setImageResource(R.drawable.klapouchy);
                            }else{
                                pogoda.text = "niby zolw i niby filip"
                                obrazek.setImageResource(R.drawable.lublanska);
                            }

//                yo.text = url
                        },
                        { error ->
                            // Handle error response
                            val errorMessage = error.message
                        }
                    )
                    queue.add(request)




                },
                { error ->
                    // Handle error response
                    val errorMessage = error.message
                }
            )
            miasto.text = edit.text

            queue.add(re)

//            yo.text = "bambusie"

        }





    }


}