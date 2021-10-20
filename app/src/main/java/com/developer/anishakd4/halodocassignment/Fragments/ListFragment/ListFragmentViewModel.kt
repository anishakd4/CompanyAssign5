package com.developer.anishakd4.halodocassignment.Fragments.ListFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.developer.anishakd4.halodocassignment.Database.NewsDao
import com.developer.anishakd4.halodocassignment.Model.HitsModel
import com.developer.anishakd4.halodocassignment.Model.NewsModel
import com.developer.anishakd4.halodocassignment.Networking.GetDataService
import kotlinx.coroutines.*

class ListFragmentViewModel(val database: NewsDao) : ViewModel(){

    val getData = GetDataService.getGetDataService()

    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        //onError("Exception: ${throwable.localizedMessage}")
    }

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var news = MutableLiveData<NewsModel>()
    val loadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    var news2: LiveData<List<HitsModel>>

    init {
        news2 = database.getAllNews()
    }

    fun fetchNews(){
        refreshSlots()
    }

    fun refreshSlots(){
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            Log.i("anisham", "anisham start")
            val response = getData.getNews()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    news.value = response.body()
                    Log.i("anisham", "anisham success")
                    loadError.value = null
                    loading.value = false
                }else{
                    Log.i("anisham", "anisham else")
                    onError("Error ${response.message()}")
                }
            }
        }
    }

    fun haveData(){
        loadError.value = null
        loading.value = false
    }

    fun insertIntoDb(){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val value = news.value
                if(value != null){
                    for (i in value.hits){
                        database.insert(i)
                    }
                }
            }
        }
    }

    fun makeNextApiCall(){

    }

    private fun onError(message: String) {
        loadError.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        viewModelJob.cancel()
    }
}