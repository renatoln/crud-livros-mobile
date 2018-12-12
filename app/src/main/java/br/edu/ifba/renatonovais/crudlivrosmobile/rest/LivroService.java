package br.edu.ifba.renatonovais.crudlivrosmobile.rest;

import java.util.List;

import br.edu.ifba.renatonovais.crudlivrosmobile.model.Livro;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface LivroService {

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @GET("api_rest/livros/")
    Call<List<Livro>> getLivros();

    @POST("api_rest/cadastrarLivro/")
    Call<Livro> insereLivro(@Body Livro livro);

    @POST("api_rest/atualizarLivro/")
    Call<Livro> atualizaLivro(@Body Livro livro);

    @POST("api_rest/excluirLivro/")
    Call<Void> excluirLivro(@Body Livro livro);


}

