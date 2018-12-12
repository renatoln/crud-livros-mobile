package br.edu.ifba.renatonovais.crudlivrosmobile.rest;

import java.util.List;

import br.edu.ifba.renatonovais.crudlivrosmobile.model.Books;
import br.edu.ifba.renatonovais.crudlivrosmobile.model.Livro;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface BookService {

    @Headers({
            "Accept: application/json",
            "Content-type: application/json"
    })
    @GET("api_rest/livros/")
    Call<List<Books>> getBooks();

    @POST("api_rest/cadastrarLivro/")
    Call<Books> insereBook(@Body Books books);

    @POST("api_rest/atualizarLivro/")
    Call<Books> atualizaBook(@Body Books books);

    @POST("api_rest/excluirLivro/")
    Call<Void> excluirBook(@Body Books books);


}

