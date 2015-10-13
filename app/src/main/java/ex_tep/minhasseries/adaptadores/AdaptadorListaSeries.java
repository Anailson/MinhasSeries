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
 * Created by Anailson on 25/06/2015.
 */
public class AdaptadorListaSeries extends ArrayAdapter<Serie> {

    private Context context;
    private TextView lblTituloSeries, lblNotaSeries;
    private CheckBox chbSeries;
    private final List<Serie> series;

    public AdaptadorListaSeries(Context context, List<Serie> series) {
        super(context, R.layout.lista_series, series);
        this.context = context;
        this.series = series;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lista_series, parent, false);

        lblTituloSeries = (TextView) view.findViewById(R.id.lbl_titulo_serie);
        lblNotaSeries = (TextView) view.findViewById(R.id.lbl_nota_serie);
        chbSeries = (CheckBox) view.findViewById(R.id.chb_serie);

        lblTituloSeries.setText(series.get(position).getTitulo());
        lblNotaSeries.setText("Nota: " + series.get(position).getNota());
        chbSeries.setChecked(series.get(position).isMarcado());

        chbSeries.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                TratamentoBanco.maracarSelecao(series.get(position), isChecked);
            }
        });

        return view;
    }
}