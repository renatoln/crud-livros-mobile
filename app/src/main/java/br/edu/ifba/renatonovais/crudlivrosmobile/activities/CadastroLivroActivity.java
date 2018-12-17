package br.edu.ifba.renatonovais.crudlivrosmobile.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import br.edu.ifba.renatonovais.crudlivrosmobile.R;
import br.edu.ifba.renatonovais.crudlivrosmobile.model.Livro;
import br.edu.ifba.renatonovais.crudlivrosmobile.rest.LivroService;
import br.edu.ifba.renatonovais.crudlivrosmobile.rest.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroLivroActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);
        Intent intent = getIntent();


        final EditText edtTitulo = (EditText) findViewById(R.id.edtTitulo);
        final EditText edtAutor = (EditText) findViewById(R.id.edtAutor);
        final EditText edtAno = (EditText) findViewById(R.id.edtAno);
        final EditText edtIsbn = (EditText) findViewById(R.id.edtIsbn);
        final EditText edtEditora = (EditText) findViewById(R.id.edtEditora);

        final String codigo = (String) intent.getSerializableExtra("codigo");

        if (Integer.parseInt(codigo) != -1){ //atualizar
            edtTitulo.setText((String) intent.getSerializableExtra("titulo"));
            edtAutor.setText((String) intent.getSerializableExtra("autor"));
            edtAno.setText((String) intent.getSerializableExtra("ano"));
            edtIsbn.setText((String) intent.getSerializableExtra("ISBN"));
            edtEditora.setText((String) intent.getSerializableExtra("editora"));
        }

        Button btnAtualizar = (Button) findViewById(R.id.botao_enviar);
        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(CadastroLivroActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();

                Livro livro = new Livro(codigo,
                        edtIsbn.getText().toString(),
                        edtTitulo.getText().toString(),
                        edtAutor.getText().toString(),
                        edtAno.getText().toString(),
                        edtEditora.getText().toString()
                );

                insere_atualiza_livro(livro);
            }
        });

        Button btnCancelar = (Button) findViewById(R.id.botao_cancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroLivroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button btnExcluir = (Button) findViewById(R.id.botao_excluir);
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Livro livro = new Livro(codigo,
                        edtIsbn.getText().toString(),
                        edtTitulo.getText().toString(),
                        edtAutor.getText().toString(),
                        edtAno.getText().toString(),
                        edtEditora.getText().toString()
                );
                exibirConfirmacao(livro);

            }
        });

    }

    private void exibirConfirmacao(final Livro livro) {
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Excluindo...");
        msgBox.setIcon(android.R.drawable.ic_menu_delete);
        msgBox.setMessage("Tem certeza que deseja excluir este livro?");

        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delete_livro(livro);
                Intent intent = new Intent(CadastroLivroActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Exclusão cancelada", Toast.LENGTH_SHORT).show();
                //finish();
            }
        });
        msgBox.show();
    }

    private void insere_atualiza_livro(Livro livro) {

        LivroService livroService = ServiceGenerator.createService(LivroService.class);
        Call<Livro> call;
        final String msn;
        if (Integer.parseInt(livro.getCodigo()) == -1) {
            livro.setCodigo(Integer.toString(MainActivity.getNovoCodigo()));
            call = livroService.insereLivro(livro);
            msn = "inserido";
        }else{
            call = livroService.atualizaLivro(livro);
            msn = "atualizado";
        }

        call.enqueue(new Callback<Livro>() {

            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Livro "+msn+" com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CadastroLivroActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Não foi possível realizar a operação", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (t instanceof IOException) {
                    Toast.makeText(getBaseContext(),
                            "Problema ao conectar, verifique sua internet",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void delete_livro(Livro livro) {

        dialog = new ProgressDialog(CadastroLivroActivity.this);
        dialog.setMessage("Deletando...");
        dialog.setCancelable(false);
        dialog.show();

        LivroService livroService = ServiceGenerator.createService(LivroService.class);
        Call<Livro> call = livroService.excluirLivro(livro);

        call.enqueue(new Callback<Livro>() {

            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "Livro excluido com sucesso", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CadastroLivroActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Não foi possível realizar a operação", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                if (dialog.isShowing())
                    dialog.dismiss();
                if (t instanceof IOException) {
                    Toast.makeText(getBaseContext(),
                            "Problema ao conectar, verifique sua internet",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
