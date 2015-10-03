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

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.entidades.Usuario;
import ex_tep.minhasseries.tratamentos.TratamentoJSON;
import ex_tep.minhasseries.tratamentos.TratamentoLogin;

import static ex_tep.minhasseries.Constantes.LOGIN_ANTERIOR;
import static ex_tep.minhasseries.Constantes.LOGIN_FALHA;
import static ex_tep.minhasseries.Constantes.LOGIN_SUCESSO;
import static ex_tep.minhasseries.Constantes.SEM_CONEXAO;


public class TelaCadastrar extends ActionBarActivity implements View.OnClickListener {

    private EditText edtNome, edtLogin, edtSenha;
    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        getSupportActionBar().hide();

        edtNome = (EditText) findViewById(R.id.edt_cadastrar_nome);
        edtLogin = (EditText) findViewById(R.id.edt_cadastrar_login);
        edtSenha  = (EditText) findViewById(R.id.edt_cadastrar_senha);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);

        btnCadastrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        //TODO: Se registron nulo?
        String nome = edtNome.getText().toString();
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();

        new CadastrarAsync(this).execute(new Usuario(-1, nome, login, senha, false));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, TelaLogin.class));
        finish();
    }

    private class CadastrarAsync extends AsyncTask<Usuario, Void, Integer>{

        private ProgressDialog progressDialog;
        private Context context;

        private CadastrarAsync(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("CADASTRANDO");
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Usuario... params) {

            Usuario u = params[0];
            TratamentoJSON.cadastrar(u);
            return TratamentoLogin.logar(context, u.getLogin(), u.getSenha());
        }

        @Override
        protected void onPostExecute(Integer resultadoLogin) {
            super.onPostExecute(resultadoLogin);

            String mensagem;

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            switch (resultadoLogin) {

                case LOGIN_SUCESSO:
                    mensagem = context.getString(R.string.msg_login_sucesso);
                    startActivity(new Intent(context, TelaPrincipal.class));
                    Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
                    finish();
                break;

                case LOGIN_FALHA:
                    mensagem = context.getString(R.string.msg_login_falha);
                    Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
                break;

                case SEM_CONEXAO:
                    mensagem = context.getString(R.string.msg_sem_conexao);
                    Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
                break;

                case LOGIN_ANTERIOR:
                    startActivity(new Intent(context, TelaPrincipal.class));
                    finish();
                break;
            }
        }
    }
}
