package com.app.shopin.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class Preference constructor(mctx: Context) {


    companion object {

        private lateinit var editor: SharedPreferences.Editor
        private lateinit var prefers: SharedPreferences
        private var mInstance: Preference? = null
        private val gson = GsonBuilder().create()

        fun getInstance(mctx: Context): Preference? {
            if (mInstance == null) {
                mInstance = Preference(mctx)
                prefers = mctx.getSharedPreferences(Constant.MyPreferences, MODE_PRIVATE)
                editor = prefers.edit()
            }
            return mInstance
        }


    }

    fun setboolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun writeIntValue(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }



    fun setString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }




    fun getInt(key: String): Int {

        return prefers.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean {
        return prefers.getBoolean(key, false)
    }

    fun getString(key: String): String {
        return prefers.getString(key, "").toString()
    }

    fun <T> putObject(key: String, y: T) {
        //Convert object to JSON String.
        val inString = gson.toJson(y)
        editor.putString(key, inString)
        editor.commit()
    }

    fun <T> getObject(key: String, c: Class<T>): T? {
        //We read JSON String which was saved.
        val value = prefers.getString(key, null)
        if (value != null) {
            //JSON String was found which means object can be read.
            //We convert this JSON String to model object. Parameter "c" (of
            // type Class<T>" is used to cast.
            return gson.fromJson(value, c)
        } else {

            //throw IllegalArgumentException("No object with key: $key was saved")
            return null
        }
    }


//special case for URI
    fun saveArrayList(key: String, list: ArrayList<Bitmap>) {
        val editor = prefers.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply() // This line is IMPORTANT !!!

    }

    fun getArrayList(key: String): ArrayList<Bitmap> {
        val gson = Gson()
        val json = prefers.getString(
            key, null)
        val type = object : TypeToken<ArrayList<Bitmap>>() {}.type
        return gson.fromJson(json, type)
    }


    fun saveStringArrayList(key: String, list: ArrayList<String>) {
        val editor = prefers.edit()
        val gson = Gson()
        val json = gson.toJson(list)
        editor.putString(key, json)
        editor.apply() // This line is IMPORTANT !!!

    }

    fun getStringArrayList(key: String): ArrayList<String> {
        val gson = Gson()
        val json = prefers.getString(
            key, null)
        val type = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(json, type)
    }
    fun clearData(key: String) {
        editor.remove(key)
        editor.apply()
    }

    fun clearAll() {
        editor.clear()
        editor.commit()
    }
    fun checkDataKey(key: String): Boolean {
        return prefers.contains(key)

    }
    fun setInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }


}