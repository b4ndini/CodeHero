package com.lfelipe.codehero.repository

import com.lfelipe.codehero.api.ApiService
import com.lfelipe.codehero.api.ResponseApi
import java.lang.Exception

class MainRepository {

    suspend fun getCharacters(offset: Int, name: String?): ResponseApi {
        return try{
            val response = ApiService.marvelApi.characters(off = offset, name = name)

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

    fun getPaging(pages: Int): MutableList<Int> {
            val list = mutableListOf<Int>()
            for(i in 1..pages){
                list.add(i)
            }
            return list

    }

    suspend fun getHeroByName(name: String): ResponseApi {
        return try{
            val response = ApiService.marvelApi.charactersByName(name = name)

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