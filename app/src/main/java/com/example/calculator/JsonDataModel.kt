package com.example.calculator

import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import com.example.calculator.ui.main.Fragment1
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class JsonDataModel private constructor() {
    var dataModel: JSONObject? = null
    var countryList: JSONArray? = null

    private companion object {
        private var ref: JsonDataModel? = null
    }

    class GetData(private val pref: SharedPreferences) : AsyncTask<JsonDataModel, Void, JsonDataModel>() {

        fun getInstance(): JsonDataModel {
            if (ref == null) {
                ref = JsonDataModel()
                this.execute(ref)
                //ref.dataModel = JSONObject()
                //ref.countryList = ref.dataModel.getJSONObject("rates").names()
                Log.d("JsonDataModel", "dataModel is initialised")
            } else
                Log.d("JsonDataModel", "dataModel is already initialised")

            return ref!!
        }

        override fun doInBackground(vararg params: JsonDataModel?): JsonDataModel? {
            var data: String?
            try {
                val dataBuilder: StringBuilder = StringBuilder()
                val conn: HttpURLConnection =
                    URL("https://api.exchangeratesapi.io/latest").openConnection() as HttpURLConnection
                val inputStream = BufferedReader(InputStreamReader(conn.inputStream))

                val list: List<String> = inputStream.readLines()
                for (input: String in list) {
                    dataBuilder.append(input).append("\n")
                }
                data = dataBuilder.toString()

                JSONObject(data).getJSONObject("rates").names()

                val editor = pref.edit()
                editor.putString("data", data)
                editor.apply()
            } catch (e: Exception) {
                e.printStackTrace()
                data = pref.getString(
                    "data",
                    "{\"rates\":{\"CAD\":1.5528,\"HKD\":9.1608,\"ISK\":161.9,\"PHP\":56.954,\"DKK\":7.4468,\"HUF\":355.71,\"CZK\":26.461,\"AUD\":1.63,\"RON\":4.8698,\"SEK\":10.2537,\"IDR\":16741.86,\"INR\":88.186,\"BRL\":6.4508,\"RUB\":91.6113,\"HRK\":7.572,\"JPY\":123.88,\"THB\":35.646,\"CHF\":1.0805,\"SGD\":1.5934,\"PLN\":4.4888,\"BGN\":1.9558,\"TRY\":9.1303,\"CNY\":7.8071,\"NOK\":10.8123,\"NZD\":1.7304,\"ZAR\":18.4068,\"USD\":1.1815,\"MXN\":24.2239,\"ILS\":3.979,\"GBP\":0.89683,\"KRW\":1311.84,\"MYR\":4.8707},\"base\":\"EUR\",\"date\":\"2023-05-13\"}"
                )
            }

            //val data: String = httpToString()
            Log.d(
                Fragment1.TAG,
                "json data created successfully, params = ${params[0]} and data:\n $data"
            )

            params[0]!!.dataModel = JSONObject(data!!)
            params[0]!!.countryList = params[0]!!.dataModel!!.getJSONObject("rates").names()!!

            return params[0]
        }

        override fun onPostExecute(result: JsonDataModel?) {
            super.onPostExecute(result)
            Fragment1.status.text = "Last updated on ${result!!.dataModel!!.getString("date")}"
        }
    }
}