package ex_tep.minhasseries.telas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.entidades.Usuario;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;
import ex_tep.minhasseries.tratamentos.TratamentoLogin;

import static ex_tep.minhasseries.Constantes.LOGIN_ANTERIOR;
import static ex_tep.minhasseries.Constantes.LOGIN_FALHA;
import static ex_tep.minhasseries.Constantes.LOGIN_SUCESSO;
import static ex_tep.minhasseries.Constantes.SEM_CONEXAO;

public class TelaLogin extends ActionBarActivity implements View.OnClickListener {

    private EditText edtLogin, edtSenha;
    private Button btnLogin, btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TratamentoBanco.criarBanco(this);
        Usuario usuario = (Usuario) TratamentoBanco.buscar(Usuario.class);


        if (!usuario.isLogado()) {

            setContentView(R.layout.activity_login);
            getSupportActionBar().hide();

            edtLogin = (EditText) findViewById(R.id.edtLogin);
            edtSenha = (EditText) findViewById(R.id.edtSenha);
            btnLogin = (Button) findViewById(R.id.btn_login);
            btnCadastrar = (Button) findViewById(R.id.btn_novo_cadastro);

            //TODO: Remover isso...
            edtLogin.setText("Anailson");
            edtSenha.setText("12345");

            btnLogin.setOnClickListener(this);
            btnCadastrar.setOnClickListener(this);

        } else {
            startActivity(new Intent(this, TelaPrincipal.class));
            finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_login:

                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                try {
                    int resultadoLogin = new LoginAsync(this).execute(login, senha).get();
                    switch (resultadoLogin) {

                        case LOGIN_SUCESSO:
                            startActivity(new Intent(this, TelaPrincipal.class));
                            finish();
                        break;

                        case LOGIN_FALHA:
                            Toast.makeText(this, getString(R.string.msg_login_falha), Toast.LENGTH_LONG).show();
                        break;

                        case SEM_CONEXAO:
                            Toast.makeText(this, getString(R.string.msg_sem_conexao), Toast.LENGTH_LONG).show();
                        break;

                        case LOGIN_ANTERIOR:
                            startActivity(new Intent(this, TelaPrincipal.class));
                            finish();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            break;

            case R.id.btn_novo_cadastro:
                startActivity(new Intent(this, TelaCadastrar.class));
                finish();
            break;
        }
    }

    private class LoginAsync extends AsyncTask <String, Void, Integer>{

        private ProgressDialog progressDialog;
        private Context context;

        public LoginAsync (Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(context.getString(R.string.msg_baixar_serie_sucesso));
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {

            String login = params[0];
            String senha = params[1];
            return TratamentoLogin.logar(context, login, senha);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}