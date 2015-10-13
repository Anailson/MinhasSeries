package ex_tep.minhasseries.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RatingBar;
import android.widget.TextView;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.entidades.Episodio;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;

import static ex_tep.minhasseries.Constantes.EPISODIO;
import static ex_tep.minhasseries.Constantes.SERIE;
import static ex_tep.minhasseries.Constantes.TEMPORADA;
import static ex_tep.minhasseries.Constantes.TITULO;
import static ex_tep.minhasseries.Constantes.ID;
import static ex_tep.minhasseries.Constantes.NUMERO;

public class TelaEpisodioDetalhes extends ActionBarActivity {

    private TextView lblTituloSerie, lblTituloTemporada, lblTituloEpisodio, lblResumo, lblNotaEpisodio;
    private RatingBar ratNotaEpisodio;
    private int epiId, tempId, serieId;
    private float nota;
    private boolean alterado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodio_detalhes);

        Intent intent = getIntent();
        epiId = intent.getIntExtra(EPISODIO + ID, -1);
        tempId = intent.getIntExtra(TEMPORADA + ID, -1);
        serieId = intent.getIntExtra(SERIE + ID, -1);
        int tempNumero = intent.getIntExtra(TEMPORADA + NUMERO, -1);
        String serieTitulo = intent.getStringExtra(TITULO);

        Episodio episodio = (Episodio) TratamentoBanco.buscar(Episodio.class, epiId);

        lblTituloSerie = (TextView) findViewById(R.id.lbl_titulo_serie);
        lblTituloTemporada = (TextView) findViewById(R.id.lblTituloTemporada);
        lblTituloEpisodio = (TextView) findViewById(R.id.lblTituloEpisodio);
        lblResumo = (TextView) findViewById(R.id.lbl_resumo);
        lblNotaEpisodio = (TextView) findViewById(R.id.lblNotaEpisodio);
        ratNotaEpisodio = (RatingBar) findViewById(R.id.ratNotaEpisodio);

        lblTituloSerie.setText(serieTitulo);
        lblTituloTemporada.setText("T" + tempNumero);
        lblTituloEpisodio.setText(episodio.getTitulo());
        lblResumo.setText(episodio.getResumo());
        lblNotaEpisodio.setText(episodio.getNota() + "");
        ratNotaEpisodio.setRating(episodio.getNota() / 2);

        ratNotaEpisodio.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                alterado = true;
                nota = rating * 2;
                lblNotaEpisodio.setText(nota + "");
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (alterado){
            TratamentoBanco.alterarNota(nota, epiId, tempId, serieId);
        }
        super.onBackPressed();
    }
}
