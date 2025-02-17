package com.example.appviacep.Model

data class Cep(

    val logradouro: String? = null,
    val bairro: String? = null,
    val regiao: String? = null,
    val localidade: String? = null,
    val uf: String? = null,
    val ddd: String? = null,
    val erro: Boolean? = null
)
