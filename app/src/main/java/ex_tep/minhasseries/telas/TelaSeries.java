package ex_tep.minhasseries.telas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.adaptadores.AdaptadorListaSeries;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;
import io.realm.RealmResults;

import static ex_tep.minhasseries.Constantes.ANO;
import static ex_tep.minhasseries.Constantes.FAVORITO_NAO;
import static ex_tep.minhasseries.Constantes.FAVORITO_SIM;
import static ex_tep.minhasseries.Constantes.NOTA;
import static ex_tep.minhasseries.Constantes.ORD_LANCAMENTO;
import static ex_tep.minhasseries.Constantes.ORD_NOTA;
import static ex_tep.minhasseries.Constantes.ORD_TITULO;
import static ex_tep.minhasseries.Constantes.SER_ID;
import static ex_tep.minhasseries.Constantes.TITULO;


public class TelaSeries extends Fragment {

    private RealmResults<Serie> series;
    private ImageView imgIconSerie; //TODO: Como inserir o ícone da série?
    private ListView lvwSeries;
    private Button btnAdicionarSeries;
    private Spinner spnSeries;

    public TelaSeries() { }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_series, container, false);

        spnSeries = (Spinner) view.findViewById(R.id.spnSeries);
        imgIconSerie = (ImageView) view.findViewById(R.id.imgIconSeries);
        lvwSeries = (ListView) view.findViewById(R.id.lvwSeries);
        btnAdicionarSeries = (Button) view.findViewById(R.id.btnAdiconarSeries);

        spnSeries.setAdapter(adaptadorSpinner());

        spnSeries.setOnItemSelectedListener(new SpnSeries());
        lvwSeries.setOnItemClickListener(new LvwSeries());
        btnAdicionarSeries.setOnClickListener(new BtnOnClickListener());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        String ordenacao = null;
        switch(TratamentoBanco.buscarOrdenacao(FAVORITO_NAO)){
            case ORD_TITULO:
                ordenacao = TITULO;
            break;
            case ORD_NOTA:
                ordenacao = NOTA;
            break;
            case ORD_LANCAMENTO:
                ordenacao = ANO;
            break;
        }

        series = TratamentoBanco.buscarSeries(FAVORITO_NAO, ordenacao);
        lvwSeries.setAdapter(new AdaptadorListaSeries(this.getActivity(), series));
    }

    private ArrayAdapter adaptadorSpinner(){
        return ArrayAdapter.createFromResource(this.getActivity(), R.array.spn_ordenacao, android.R.layout.simple_spinner_item);
    }

    private class SpnSeries implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            TratamentoBanco.salvarOrdenacao(FAVORITO_NAO, position);
            onResume();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }

    private class LvwSeries implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), TelaSerieDetalhes.class);
            intent.putExtra(SER_ID, series.get(position).getId());
            startActivity(intent);
        }
    }

    private class BtnOnClickListener implements View.OnClickListener {
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
    }

}