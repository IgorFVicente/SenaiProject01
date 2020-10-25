package br.senai.sc.projeto01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.senai.sc.projeto01.database.ProdutoDAO;
import br.senai.sc.projeto01.modelo.Produto;

public class CadastroProdutoActivity extends AppCompatActivity {

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        setTitle("Cadastro de Produto");
        carregarProduto();
    }

    private void carregarProduto() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("produtoEdicao") != null) {
            Produto produto = (Produto) intent.getExtras().get("produtoEdicao");
            EditText editTextNome = findViewById(R.id.editText_nome);
            EditText editTextValor = findViewById(R.id.editText_valor);
            editTextNome.setText(produto.getNome());
            editTextValor.setText(String.valueOf(produto.getValor()));
            id = produto.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickSalvar(View v) {
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextValor = findViewById(R.id.editText_valor);

        String nome = editTextNome.getText().toString();
        String valorString = editTextValor.getText().toString();

        if ((nome.equals("")) || (valorString.equals(""))) {
            Toast.makeText(CadastroProdutoActivity.this, "Por favor, preencha todos os campos",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Float valor = Float.parseFloat(valorString);

        Produto produto = new Produto(id, nome, valor);
        ProdutoDAO produtoDao = new ProdutoDAO(getBaseContext());
        boolean salvou = produtoDao.salvar(produto);
        if (salvou) {
            finish();
        } else {
            Toast.makeText(CadastroProdutoActivity.this, "Erro ao salvar", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickExcluir(View v) {
        ProdutoDAO produtoDao = new ProdutoDAO(getBaseContext());
        boolean excluiu = produtoDao.excluir(id);
        if (excluiu) {
            finish();
        } else {
            Toast.makeText(CadastroProdutoActivity.this, "Erro ao excluir", Toast.LENGTH_LONG).show();
        }
    }
}