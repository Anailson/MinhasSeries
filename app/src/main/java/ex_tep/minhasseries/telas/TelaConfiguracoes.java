package ex_tep.minhasseries.telas;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ex_tep.minhasseries.R;



public class TelaConfiguracoes extends Fragment {

    private Button btnConfiguracoes;

    public TelaConfiguracoes() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewConfiguracoes = inflater.inflate(R.layout.frag_configuracoes, container, false);
        btnConfiguracoes = (Button) viewConfiguracoes.findViewById(R.id.btn_configuracoes);

        return viewConfiguracoes;
    }
}
