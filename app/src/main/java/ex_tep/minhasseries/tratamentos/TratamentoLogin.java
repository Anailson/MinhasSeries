package ex_tep.minhasseries.tratamentos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import ex_tep.minhasseries.entidades.Usuario;

import static ex_tep.minhasseries.Constantes.LOGIN_ANTERIOR;
import static ex_tep.minhasseries.Constantes.LOGIN_FALHA;
import static ex_tep.minhasseries.Constantes.LOGIN_SUCESSO;
import static ex_tep.minhasseries.Constantes.SEM_CONEXAO;

/**
 * Created by Anailson on 04/08/2015.
 */
public class TratamentoLogin {

    private TratamentoLogin() {}

    public static int logar(Context context, String login, String senha) {

        if (possuiConexao(context)) {

            boolean logado = ((Usuario)TratamentoBanco.buscar(Usuario.class)).isLogado();

            if (!logado) {

                boolean flag = TratamentoJSON.efetuarLogin(login, senha);

                if (flag) {
                    return LOGIN_SUCESSO;
                }
                return LOGIN_FALHA;
            }
            return LOGIN_ANTERIOR;
        }
        return SEM_CONEXAO;
    }

    private static boolean possuiConexao(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
