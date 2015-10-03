package ex_tep.minhasseries.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.entidades.Episodio;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.entidades.Temporada;

/**
 * Created by Anailson on 27/06/2015.
 */
public class AdaptadorListaFavoritoDetalhes extends BaseExpandableListAdapter {

    //TODO: E essas constantes...
    private static final String NOTA_TEMP = "Nota da temporada: ";
    private static final String NOTA_EPI  = "Nota do episodio: ";
    private static final String ANO_LANCAMENTO = "Ano de lancamento: ";

    private Context context;
    private Serie serie;

    public AdaptadorListaFavoritoDetalhes(Context context, Serie serie) {
        this.context = context;
        this.serie = serie;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return serie.getTemporadas().get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return serie.getTemporadas().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return serie.getTemporadas().get(groupPosition).getId();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_favorito_temporada, null);
        }

        if(isExpanded){
            onGroupExpanded(groupPosition);
        }

        Temporada temporada = (Temporada) getGroup(groupPosition);

        TextView lblTituloTemporada = (TextView) view.findViewById(R.id.lblTituloTemporada);
        TextView lblAnoLancamento = (TextView) view.findViewById(R.id.lbl_ano);
        TextView lblNotaTemporada = (TextView) view.findViewById(R.id.lblNotaTemporada);

        lblTituloTemporada.setText(temporada.getNumero() + "° temporada");
        lblAnoLancamento.setText(ANO_LANCAMENTO + temporada.getAno());
        lblNotaTemporada.setText(NOTA_TEMP + String.format("%.1f", temporada.getNota()));

        return view;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return serie.getTemporadas().get(groupPosition).getEpisodios().get(childPosition);
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return serie.getTemporadas().get(groupPosition).getEpisodios().size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return serie.getTemporadas().get(groupPosition).getEpisodios().get(childPosition).getId();
    }

    @Override
    public View getChildView(final int groupPosition,  final int childPosition, final boolean isLastChild, View view, final ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lista_favorito_episodio, null);
        }

        Episodio episodio = (Episodio) getChild(groupPosition, childPosition);

        TextView lblTituloEpisodio = (TextView) view.findViewById(R.id.lblTituloEpisodio);
        final TextView lblNotaEpisodio = (TextView) view.findViewById(R.id.lblNotaEpisodio);
        RatingBar ratNotaEpisodio = (RatingBar) view.findViewById(R.id.ratNotaEpisodio);

        lblTituloEpisodio.setText(episodio.getTitulo());
        lblNotaEpisodio.setText(NOTA_EPI + episodio.getNota());
        ratNotaEpisodio.setRating(episodio.getNota() / 2);

        ratNotaEpisodio.setEnabled(false);

        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}
