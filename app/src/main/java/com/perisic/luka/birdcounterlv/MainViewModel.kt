package com.perisic.luka.birdcounterlv

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainViewModel : ViewModel() {

    val birds = MutableLiveData<List<Bird>>()
    val selectedBird = MutableLiveData<Bird>(null)

    fun setData(stringData: String) {
        birds.value = toBirdList(stringData)
    }

    fun setSelected(stringData: String) {
        selectedBird.value = toBird(stringData)
    }

    fun countBird(index: Int) {
        birds.value = birds.value?.also {
            it[index].count++
            selectedBird.value = it[index]
        }
    }

    fun resetCounter() {
        birds.value = birds.value?.also {
            it.forEach { bird -> bird.count = 0 }
        }
        selectedBird.value = null
    }

    fun createData() {
        birds.value = arrayListOf(
            Bird(color = R.color.colorFirst, name = "Doves"),
            Bird(color = R.color.colorSecond, name = "Ducks"),
            Bird(color = R.color.colorThird, name = "Hummingbirds"),
            Bird(color = R.color.colorFourth, name = "Sparrows")
        )
    }

    fun getStringData(): String? = Gson().toJson(birds.value)

    fun getSelectedStringData(): String? = Gson().toJson(selectedBird.value)

    private fun toBirdList(data: String?): List<Bird>? {
        val listType = object : TypeToken<List<Bird>>() {}.type
        return Gson().fromJson(data, listType)
    }

    private fun toBird(data: String?): Bird? {
        val type = object : TypeToken<Bird>() {}.type
        return Gson().fromJson(data, type)
    }

}