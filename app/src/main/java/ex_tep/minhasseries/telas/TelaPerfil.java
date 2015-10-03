package ex_tep.minhasseries.telas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.entidades.Usuario;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;

public class TelaPerfil extends Fragment {

    private Button btnSair;

    public TelaPerfil() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_perfil, container, false);

        btnSair = (Button) view.findViewById(R.id.btn_sair);
        btnSair.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TratamentoBanco.alterar(new Usuario(-1, "", "", "", false));
                startActivity(new Intent(getActivity(), TelaLogin.class));
                getActivity().finish();
            }
        });

        return view;
    }
}
