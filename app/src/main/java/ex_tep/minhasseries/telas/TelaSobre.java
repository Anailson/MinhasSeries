package ex_tep.minhasseries.telas;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ex_tep.minhasseries.R;


public class TelaSobre extends Fragment {

    private Button btnSobre;

    public TelaSobre() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View viewSobre =  inflater.inflate(R.layout.frag_sobre, container, false);

        btnSobre = (Button) viewSobre.findViewById(R.id.btn_sobre);


        return viewSobre;
    }
}
