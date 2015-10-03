package ex_tep.minhasseries.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.adaptadores.AdaptadorListaFavoritoDetalhes;
import ex_tep.minhasseries.entidades.Episodio;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.entidades.Temporada;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;

import static ex_tep.minhasseries.Constantes.EPISODIO;
import static ex_tep.minhasseries.Constantes.ID;
import static ex_tep.minhasseries.Constantes.NUMERO;
import static ex_tep.minhasseries.Constantes.SERIE;
import static ex_tep.minhasseries.Constantes.TEMPORADA;
import static ex_tep.minhasseries.Constantes.TITULO;

/**
 * Created by Anailson on 30/06/2015.
 */
public class TelaFavoritoDetalhes extends ActionBarActivity {

    private TextView lblTituloSerie;
    private ExpandableListView expListView;
    private Serie serie;
    private int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporadas);

        Intent intent = getIntent();
        id = intent.getIntExtra(ID, -1);

        serie = (Serie) TratamentoBanco.buscar(Serie.class, id);
        lblTituloSerie = (TextView)findViewById(R.id.lblTituloFavoritoSerie);
        expListView = (ExpandableListView) findViewById(R.id.expListView);

        lblTituloSerie.setText(serie.getTitulo());
        expListView.setAdapter(new AdaptadorListaFavoritoDetalhes(this, serie));
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Temporada t = serie.getTemporadas().get(groupPosition);
                Episodio e = t.getEpisodios().get(childPosition);

                Intent intent = new Intent(TelaFavoritoDetalhes.this, TelaEpisodioDetalhes.class);
                intent.putExtra(EPISODIO + ID, e.getId());
                intent.putExtra(TEMPORADA + ID, t.getId());
                intent.putExtra(SERIE + ID, serie.getId());
                intent.putExtra(NUMERO, t.getNumero());
                intent.putExtra(TITULO, serie.getTitulo());
                startActivity(intent);
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        serie = (Serie) TratamentoBanco.buscar(Serie.class, id);
        expListView.setAdapter(new AdaptadorListaFavoritoDetalhes(this, serie));
    }
}