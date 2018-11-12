package br.edu.ifba.renatonovais.crudlivrosmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.renatonovais.crudlivrosmobile.model.Livro;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listaDeLivros = (ListView) findViewById(R.id.lista);

        List<Livro> cursos = todosOsLivros();

        ArrayAdapter<Livro> adapter = new ArrayAdapter<Livro>(this,
                android.R.layout.simple_list_item_1, cursos);

        listaDeLivros.setAdapter(adapter);

    }

    private List<Livro> todosOsLivros() {
        List<Livro> livros = new ArrayList<>();

        Livro livro1 = new Livro(1,
                "ISBN23455", "Crud Básico full stack", "Renato Novais",2018, "IFBA tech");
        Livro livro2 = new Livro(1,
                "ISBN23455", "Django", "Renato Lima",2017, "GSort");
        Livro livro3 = new Livro(1,
                "ISBN23455", "Android", "Letícia Gomes",2014, "Favo");

        livros.add(livro1);
        livros.add(livro2);
        livros.add(livro3);

        return livros;
    }
}
