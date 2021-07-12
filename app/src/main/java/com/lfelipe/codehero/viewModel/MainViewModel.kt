package com.lfelipe.codehero.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lfelipe.codehero.api.ResponseApi
import com.lfelipe.codehero.model.Characters
import com.lfelipe.codehero.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var repository: MainRepository = MainRepository()
    var listLiveData: MutableLiveData<MutableList<Int>> = MutableLiveData()
    val characterLiveData: MutableLiveData<Characters> = MutableLiveData()
    val errorMsgLiveData: MutableLiveData<String> = MutableLiveData()

    fun getCharacters(off: Int){
        viewModelScope.launch {
            when(val response = repository.getCharacters(off)){
                is ResponseApi.Success -> {
                    characterLiveData.postValue(response.data as? Characters)
                }
                is ResponseApi.Error -> {
                    errorMsgLiveData.postValue(response.msg)
                }
            }
        }
    }

    fun getPaging(pages: Int){

        viewModelScope.launch {
                listLiveData.postValue(repository.getPaging(pages))

            }
        }

    fun searchHeroByName(name: CharSequence) {

        viewModelScope.launch {
            when(val response = repository.getHeroByName(name.toString())){
                is ResponseApi.Success -> {
                    characterLiveData.postValue(response.data as? Characters)
                }
                is ResponseApi.Error -> {
                    errorMsgLiveData.postValue(response.msg)
                }
            }
        }

    }


}
