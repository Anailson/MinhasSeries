package ex_tep.minhasseries.telas;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;

import static ex_tep.minhasseries.Constantes.SER_ID;


public class TelaSerieDetalhes extends ActionBarActivity {

    private TextView lblTitulo, lblNota, lblResumo, lblEmissora, lblAno, lblQuantTemporadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie_detalhes);

        int id = getIntent().getIntExtra(SER_ID, -1);
        Serie serie = (Serie) TratamentoBanco.buscar(Serie.class, id);

        lblTitulo = (TextView) findViewById(R.id.lbl_titulo_serie);
        lblNota = (TextView) findViewById(R.id.lbl_nota);
        lblResumo = (TextView) findViewById(R.id.lbl_resumo);
        lblAno = (TextView) findViewById(R.id.lbl_ano);
        lblEmissora = (TextView) findViewById(R.id.lbl_emissora);
        lblQuantTemporadas = (TextView) findViewById(R.id.lbl_quant_temporadas);

        lblTitulo.setText(serie.getTitulo());
        lblNota.setText(String.format("%.1f", serie.getNota()));
        lblResumo.setText(serie.getResumo());
        lblAno.setText(serie.getAno() + "");
        lblEmissora.setText(serie.getEmissora());
        lblQuantTemporadas.setText(serie.getTemporadas().size() + "");
    }
}