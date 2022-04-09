package com.android.example.ratemycourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class MainActivity : AppCompatActivity() {

    // TODO: PHASE 2.4 - late-Initialize HousingService variable
//    lateinit var service: HousingService
//
//    companion object {
//        val BASE_URL = "https://project-3-rentwell-server.totoroyyb.repl.co/"
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: PHASE 2.4 - Create instance of a retrofit service (HousingService) and connect
        //  it as a member variable of the MainActivity to reference is in Fragments
        //  (in other words, initialize the HousingService variable above that was defined
        //  as being late-initialized)
//        service = Retrofit
//            .Builder()
//            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build().create()

        setContentView(R.layout.activity_main)
    }
}
