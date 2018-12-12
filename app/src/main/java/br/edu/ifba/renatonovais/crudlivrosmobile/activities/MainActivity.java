package br.edu.ifba.renatonovais.crudlivrosmobile.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import br.edu.ifba.renatonovais.crudlivrosmobile.R;
import br.edu.ifba.renatonovais.crudlivrosmobile.model.Livro;
import br.edu.ifba.renatonovais.crudlivrosmobile.rest.LivroService;
import br.edu.ifba.renatonovais.crudlivrosmobile.rest.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // the placeholder to set content for each list item
    String[] from = {"titulo", "autor"};

    // the elements ids that will be set for each list item
    int[] to = {R.id.lista_livro_personalizada_nome, R.id.lista_livro_personalizada_descricao};

    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void setData(List<Livro> livros){
        ListView listViewDeLivros = (ListView) findViewById(R.id.lista);
        //List<Livro> livros = todosOsLivros();
        for (int i=0;i<livros.size();i++)
        {
            Livro livro = livros.get(i);
            HashMap<String,String> hashMap=new HashMap<>();//create a hashmap to store the data in key value pair
            hashMap.put("codigo",livro.getCodigo());
            hashMap.put("titulo",livro.getTitulo());
            hashMap.put("autor",livro.getAutor());
            hashMap.put("ISBN",livro.getISBN());
            hashMap.put("editora",livro.getEditora());
            hashMap.put("ano",livro.getAno());

            arrayList.add(hashMap);//add the hashmap into arrayList
        }

        simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.lista_livro_personalizada,from,to);//Create object and set the parameters for simpleAdapter
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

        Livro livro1 = new Livro("1",
                "ISBN23455", "Crud Básico full stack", "Renato Novais","2018", "IFBA tech");
        Livro livro2 = new Livro("2",
                "ISBN23455", "Django", "Renato Lima","2017", "GSort");
        Livro livro3 = new Livro("3",
                "ISBN23455", "Android", "Letícia Gomes","2014", "Favo");

        livros.add(livro1);
        livros.add(livro2);
        livros.add(livro3);

        return livros;
    }


    @Override
    protected void onStart() {
        super.onStart();

        final ListView lista = (ListView) findViewById(R.id.lista);
        //registerForContextMenu(lista);
        LivroService livroService = ServiceGenerator.createService(LivroService.class);

        /*dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Carregando...");
        dialog.setCancelable(false);
        dialog.show();*/

        final Call<List<Livro>> call = livroService.getLivros();

        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(@NonNull Call<List<Livro>> call, @NonNull Response<List<Livro>> response) {

                /*if (dialog.isShowing())
                    dialog.dismiss();*/
                if (Objects.requireNonNull(response).isSuccessful()) {
                    final List<Livro> listBooks = response.body();

                    if (listBooks != null) {

                        setData(listBooks);
                        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                HashMap<String,String> livroMap = (HashMap<String,String>)simpleAdapter.getItem(position);
                                //Toast.makeText(getApplicationContext(),livroMap.get("titulo"),Toast.LENGTH_LONG).show();//show the selected image in toast according to position

                                Intent intent = new Intent(MainActivity.this, CadastroLivroActivity.class);
                                intent.putExtra("titulo", livroMap.get("titulo"));
                                intent.putExtra("autor", livroMap.get("autor"));
                                intent.putExtra("ano", livroMap.get("ano"));
                                intent.putExtra("ISBN", livroMap.get("ISBN"));
                                intent.putExtra("editora", livroMap.get("editora"));
                                intent.putExtra("codigo", livroMap.get("codigo"));
                                startActivity(intent);

                            }
                        });

                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Livro>> call, @NonNull Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getBaseContext(),
                            "Problema ao conectar no servidor",
                            Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}

