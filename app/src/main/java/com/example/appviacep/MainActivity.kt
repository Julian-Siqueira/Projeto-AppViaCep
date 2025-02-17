package com.example.appviacep

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.appviacep.Api.ApiCep
import com.example.appviacep.Model.Cep
import com.example.appviacep.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val urlBase = "https://viacep.com.br/"

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configurando Retrofit

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(urlBase)
            .build()

        //Associando nossa Configuração com a API

        val apiCep = retrofit.create(ApiCep::class.java)

        binding.btnBuscarCep.setOnClickListener {

            val cepDigitado = binding.etCep.text.toString()

            if (cepDigitado.isEmpty()) {
                Toast.makeText(this, "Digite o Cep!", Toast.LENGTH_SHORT).show()
            } else {

                apiCep.getCep(cepDigitado).enqueue(object : Callback<Cep> {

                    override fun onResponse(call: Call<Cep>, response: Response<Cep>) {

                        if (response.isSuccessful) {
                            val cepResponse = response.body()

                            if (cepResponse?.erro == true) {
                                Toast.makeText(
                                    applicationContext,
                                    "Cep Não Encontrado!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val logradouro = cepResponse?.logradouro.toString()
                                val bairro = cepResponse?.bairro.toString()
                                val regiao = cepResponse?.regiao.toString()
                                val cidade = cepResponse?.localidade.toString()
                                val estado = cepResponse?.uf.toString()
                                val ddd = cepResponse?.ddd.toString()
                                setDados(logradouro, bairro, regiao, cidade, estado, ddd)
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Cep Inválido!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Cep>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            "Erro Inesperado!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            }
        }
    }

    private fun setDados(
        logradouro: String,
        bairro: String,
        regiao: String,
        cidade: String,
        estado: String,
        ddd: String
    ) {
        binding.etLogradouro.setText(logradouro)
        binding.etBairro.setText(bairro)
        binding.etRegiao.setText(regiao)
        binding.etCidade.setText(cidade)
        binding.etEstado.setText(estado)
        binding.etDdd.setText(ddd)
    }
}