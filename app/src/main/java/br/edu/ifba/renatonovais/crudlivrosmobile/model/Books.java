package br.edu.ifba.renatonovais.crudlivrosmobile.model;


public class Books {

    private static int numberBooks = 0;
    private String codigo;
    private String ISBN;
    private String titulo;
    private String autor;
    private String ano;
    private String editora;

    public Books(String codigo) {
        this.codigo = codigo;
    }

    public Books() {
    }

    public Books(String codigo, String ISBN, String titulo, String autor, String ano, String editora) {
        this.codigo = codigo;
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.editora = editora;
        this.numberBooks += 1;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

}

