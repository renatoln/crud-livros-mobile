package br.edu.ifba.renatonovais.crudlivrosmobile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.edu.ifba.renatonovais.crudlivrosmobile.model.Livro;

public class MainActivity extends AppCompatActivity {

    // the placeholder to set content for each list item
    String[] from = {"titulo", "autor"};

    // the elements ids that will be set for each list item
    int[] to = {R.id.lista_livro_personalizada_nome, R.id.lista_livro_personalizada_descricao};

    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setData();

    }

    public void setData(){
        ListView listViewDeLivros = (ListView) findViewById(R.id.lista);
        List<Livro> livros = todosOsLivros();
        for (int i=0;i<livros.size();i++)
        {
            Livro livro = livros.get(i);
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("titulo",livro.getTitulo());
            hashMap.put("autor",livro.getAutor());
            hashMap.put("ano",livro.getAno()+"");
            arrayList.add(hashMap);//add the hashmap into arrayList
        }

        SimpleAdapter simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.lista_livro_personalizada,from,to);//Create object and set the parameters for simpleAdapter
        listViewDeLivros.setAdapter(simpleAdapter);//sets the adapter for listView

        //perform listView item click event
        listViewDeLivros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),arrayList.get(i).get("titulo"),Toast.LENGTH_LONG).show();//show the selected image in toast according to position
            }
        });
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
