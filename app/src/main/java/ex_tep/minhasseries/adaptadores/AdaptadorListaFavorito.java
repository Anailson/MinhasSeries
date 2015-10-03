package ex_tep.minhasseries.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;

/**
 * Created by Anailson on 26/06/2015.
 */
public class AdaptadorListaFavorito extends ArrayAdapter<Serie>  {

    private Context context;
    private TextView lblTituloFavorito, lblNotaFavorito;
    private CheckBox chbFavorito;
    private List<Serie> series;

    public AdaptadorListaFavorito(Context context, List<Serie> series) {
        super(context, R.layout.lista_favorito, series);
        this.context = context;
        this.series = series;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lista_lancamento, parent, false);

        lblTituloFavorito = (TextView) view.findViewById(R.id.lbl_titulo_lancamento);
        lblNotaFavorito = (TextView) view.findViewById(R.id.lbl_nota_lancamento);
        chbFavorito = (CheckBox) view.findViewById(R.id.chb_lancamento);

        lblTituloFavorito.setText(series.get(position).getTitulo());
        lblNotaFavorito.setText("Nota: " + String.format("%.1f", series.get(position).getNota()));
        chbFavorito.setChecked(series.get(position).isMarcado());

        chbFavorito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                TratamentoBanco.maracarSelecao(series.get(position), isChecked);
            }
        });

        return view;
    }
}
