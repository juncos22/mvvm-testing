package dev.demo.mvvmtesting.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.demo.mvvmtesting.models.Drink
import dev.demo.mvvmtesting.models.DrinkResponse
import dev.demo.mvvmtesting.repository.CocktailRepository
import dev.demo.mvvmtesting.repository.CocktailService
import dev.demo.mvvmtesting.states.DataState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailViewModel : ViewModel() {
    private var _cocktailMutableLiveData = MutableLiveData<DataState<List<Drink>>>()
    val drinkLiveData: LiveData<DataState<List<Drink>>>
        get() = _cocktailMutableLiveData

    private lateinit var cocktailRepository: CocktailRepository

    init {
        searchCocktail("")
    }

    fun searchCocktail(drinkName: String) {
        cocktailRepository = CocktailService.client.create(CocktailRepository::class.java)

        val call = cocktailRepository.listCocktailsByName(drinkName)

        _cocktailMutableLiveData.postValue(DataState.Loading(null))
        call.enqueue(object : Callback<DrinkResponse>{
            override fun onResponse(
                call: Call<DrinkResponse>,
                response: Response<DrinkResponse>
            ) {
                Log.i("RESPONSE", response.body().toString())
                if (response.isSuccessful) {
                    _cocktailMutableLiveData.postValue(DataState.Success(response.body()!!.drinks))
                }else {
                    _cocktailMutableLiveData.postValue(DataState.Error(null, response.message()))
                }
            }

            override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {
                _cocktailMutableLiveData.postValue(DataState.Error(null, t.message))
            }

        })
    }
}