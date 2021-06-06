package com.example.myrxjava

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myrxjava.api.GetData
import com.example.myrxjava.util.Constants.Companion.BASE_URL
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    private val updateButton: Button get() = findViewById(R.id.updateCurrancyBtn)
    private val titleName: TextView get() = findViewById(R.id.titleProgrammName)
    private val updateInfoDate: TextView get() = findViewById(R.id.updateExchangeDate)


    private var myAdapter: CustomAdapter? = null

    private val list: ListView get() = findViewById(R.id.currency_list)
    private var myCompositeDisposable: CompositeDisposable? = null
    private var myCurrancyArrayList: ArrayList<CurrancyItems>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myCompositeDisposable = CompositeDisposable()

        val hourNow = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
        val minNow = Calendar.getInstance().get(Calendar.MINUTE).toString()
        val secNow = Calendar.getInstance().get(Calendar.SECOND)

        updateInfoDate.text = "$hourNow:$minNow:$secNow"

        loadData()
    }


    private fun loadData() {
//Define the Retrofit request//
        val requestInterface = Retrofit.Builder()
//Set the API’s base URL//
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetData::class.java)
//Add all RxJava disposables to a CompositeDisposable//
        myCompositeDisposable?.add(
            requestInterface.getData()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }


    private fun handleResponse(currancyList: List<CurrancyItems>) {

        myCurrancyArrayList = ArrayList(currancyList)
        myAdapter = CustomAdapter(
            this,
            R.layout.list_item_layout,
            myCurrancyArrayList!!,
            layoutInflater
        )

//Set the adapter//

        list.adapter = myAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
     //Clear
        myCompositeDisposable?.clear()
    }
}