package ex_tep.minhasseries.telas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.adaptadores.AdaptadorListaFavorito;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;
import io.realm.RealmResults;

import static ex_tep.minhasseries.Constantes.FAVORITO_NAO;
import static ex_tep.minhasseries.Constantes.FAVORITO_SIM;
import static ex_tep.minhasseries.Constantes.ID;
import static ex_tep.minhasseries.Constantes.SER_ID;

public class TelaFavorito extends Fragment {

    private RealmResults<Serie> series;
    private ListView lvwFavorito;
    private Button btnRemoverFavorito;

    public TelaFavorito() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewLancamento = inflater.inflate(R.layout.frag_favorito, container, false);

        series = TratamentoBanco.buscarSeries(FAVORITO_SIM);
        lvwFavorito = (ListView) viewLancamento.findViewById(R.id.lvw_favorito);
        btnRemoverFavorito = (Button) viewLancamento.findViewById(R.id.btn_remover_serie);

        lvwFavorito.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!view.isLongClickable()) {

                    Intent intent = new Intent(getActivity(), TelaFavoritoDetalhes.class);
                    intent.putExtra(ID, series.get(position).getId());
                    startActivity(intent);
                }
            }
        });

        btnRemoverFavorito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cont = TratamentoBanco.atualizarFavoritos(FAVORITO_NAO);
                String mensagem;

                switch(cont){
                    case 0:
                        mensagem = getString(R.string.msg_excluir_erro);
                        break;
                    case 1:
                        mensagem = getString(R.string.msg_excluir_unico_fav_sucesso);
                        break;
                    case 2:
                        mensagem = getString(R.string.msg_excluir_multi_fav_sucesso);
                        break;
                    default:
                        mensagem = getString(R.string.msg_excluir_erro_inesperado);
                        break;
                }

                Toast.makeText(getActivity(), mensagem, Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        registerForContextMenu(lvwFavorito);
        return viewLancamento;
    }

    @Override
    public void onResume() {
        super.onResume();
        series = TratamentoBanco.buscarSeries(FAVORITO_SIM);
        lvwFavorito.setAdapter(new AdaptadorListaFavorito(this.getActivity(), series));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        ListView listView = (ListView) v;
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        Serie serie = (Serie) listView.getItemAtPosition(info.position);

        menu.setHeaderTitle(serie.getTitulo());
        menu.add(0, listView.getId(), 0, "Detalhes da série");
        menu.add(0, listView.getId(), 1, "Configurar alarme");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int position = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;

        switch (item.getOrder()){
            case 0:
                Intent intent = new Intent(getActivity(), TelaSerieDetalhes.class);
                intent.putExtra(SER_ID, series.get(position).getId());
                startActivity(intent);

                Toast.makeText(this.getActivity(), "Detalhes da serie", Toast.LENGTH_SHORT).show();
            break;
            case 1:

                Toast.makeText(this.getActivity(), "Configurar alarme", Toast.LENGTH_SHORT).show();
            break;
        }

        return super.onContextItemSelected(item);
    }
}