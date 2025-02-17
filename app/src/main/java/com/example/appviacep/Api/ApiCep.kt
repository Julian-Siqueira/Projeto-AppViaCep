package com.example.appviacep.Api

import com.example.appviacep.Model.Cep
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCep {

    //Definimos a URL e a forma de buscar o CEP
    @GET("ws/{cep}/json/")
    fun getCep(
        @Path("cep") cep: String // Passa o cep que será consultado
    ): Call<Cep> // A resposta será um objeto do tipo Cep

}