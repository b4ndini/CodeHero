package com.lfelipe.codehero.repository

import com.lfelipe.codehero.api.ApiService
import com.lfelipe.codehero.api.ResponseApi
import java.lang.Exception

class MainRepository {

    suspend fun getCharacters(offset: Int): ResponseApi {
        return try{
            val response = ApiService.marvelApi.characters(offset)

            if(response.isSuccessful){
                ResponseApi.Success(response.body())
            }
            else{
                ResponseApi.Error("Erro")
            }
        }catch (exception: Exception){
            ResponseApi.Error("ERRO CARREGAR")
        }
    }
}