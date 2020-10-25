package br.senai.sc.projeto01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.senai.sc.projeto01.database.ProdutoDAO;
import br.senai.sc.projeto01.modelo.Produto;

public class MainActivity extends AppCompatActivity {

    private ListView listViewProdutos;
    private ArrayAdapter<Produto> adapterProdutos;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Produtos");
        listViewProdutos = findViewById(R.id.listView_produtos);
        registerForContextMenu(listViewProdutos);
        definirOnClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ProdutoDAO produtoDao = new ProdutoDAO(getBaseContext());
        adapterProdutos = new ArrayAdapter<Produto>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                produtoDao.listar());
        listViewProdutos.setAdapter(adapterProdutos);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Editar");
        menu.add(0, v.getId(), 0, "Excluir");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        Produto produtoClicado = adapterProdutos.getItem(position);
        if (item.getTitle() == "Editar") {
            Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
            intent.putExtra("produtoEdicao", produtoClicado);
            startActivity(intent);
        } else if (item.getTitle() == "Excluir") {
            adapterProdutos.remove(produtoClicado);
        }
        return true;
    }

    private void definirOnClickListenerListView() {
        listViewProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoClicado = adapterProdutos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
                intent.putExtra("produtoEdicao", produtoClicado);
                startActivity(intent);
            }
        });
    }

    public void onClickNovoProduto(View v) {
        Intent intent = new Intent(MainActivity.this, CadastroProdutoActivity.class);
        startActivity(intent);
    }

}