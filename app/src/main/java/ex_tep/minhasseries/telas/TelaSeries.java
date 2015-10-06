package ex_tep.minhasseries.telas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.adaptadores.AdaptadorListaSeries;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;
import io.realm.RealmResults;

import static ex_tep.minhasseries.Constantes.FAVORITO_NAO;
import static ex_tep.minhasseries.Constantes.FAVORITO_SIM;
import static ex_tep.minhasseries.Constantes.SER_ID;


public class TelaSeries extends Fragment {

    private RealmResults<Serie> series;
    private ImageView imgIconSerie; //TODO: Como inserir o ícone da série?
    private ListView lvwSeries;
    private Button btnAdicionarSeries;

    public TelaSeries() { }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_series, container, false);

        series = TratamentoBanco.buscarSeries(FAVORITO_NAO);
        imgIconSerie = (ImageView) view.findViewById(R.id.img_icon_serie);
        lvwSeries = (ListView) view.findViewById(R.id.lvw_series);
        btnAdicionarSeries = (Button) view.findViewById(R.id.btn_adiconar_series);

        lvwSeries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), TelaSerieDetalhes.class);
                intent.putExtra(SER_ID, series.get(position).getId());
                startActivity(intent);
            }
        });

        btnAdicionarSeries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int cont = TratamentoBanco.atualizarFavoritos(FAVORITO_SIM);
                String mensagem;

                switch(cont){
                    case 0:
                        mensagem = getString(R.string.msg_inserir_fav_erro);
                    break;
                    case 1:
                        mensagem = getString(R.string.msg_inserir_unico_fav_sucesso);
                    break;
                    case 2:
                        mensagem = getString(R.string.msg_inserir_multi_fav_sucesso);
                    break;
                    default:
                        mensagem = getString(R.string.msg_excluir_erro_inesperado);
                    break;
                }

                Toast.makeText(getActivity(), mensagem, Toast.LENGTH_SHORT).show();
                onResume();
            }
        });

        return view;
    }

    @Override
    public void onResume() {

        super.onResume();
        series = TratamentoBanco.buscarSeries(FAVORITO_NAO);
        lvwSeries.setAdapter(new AdaptadorListaSeries(this.getActivity(), series));
    }
}