package ex_tep.minhasseries;

/**
 *
 * Created by Anailson on 19/07/2015.
 */
public final class Constantes {

    // Parâmetros usados para manipular as listas da TelaPrincipal
    public static final int PERFIL = 0;
    public static final int SERIES = 1;
    public static final int FAVORITOS = 2;
    public static final int CONFIGURAOES = 3;
    public static final int SOBRE = 4;

    //URL para acessar o Web Service
    public static final String URL_WEBSERVICE = "http://192.168.0.100:8080/WebServer/webresources/";
    //public static final String URL_WEBSERVICE = "http://10.13.157.161:8080/WebServer/webresources/";
    //public static final String URL_WEBSERVICE = "http://10.0.2.2:8080/WebServer/webresources/";
    public static final String URL_USUARIO = "tabelas.tbusuario/";
    public static final String URL_SERIE = "tabelas.tbserie/";
    public static final String URL_TEMPORADA = "tabelas.tbtemporada/";
    public static final String URL_EPISODIO = "tabelas.tbepisodio/";
    public static final String URL_FAVORITO = "tabelas.tbfavorito/";
    public static final String URL_BUSCAR = "buscar/";
    public static final String URL_SALVAR = "salvar/";
    public static final String URL_EDITAR = "editar/";
    public static final String URL_REMOVER = "remover/";

    // Parâmetros de tratamento do JSON
    public static final String EPISODIO = "episodio";
    public static final String TEMPORADA = "temporada";
    public static final String SERIE = "serie";
    public static final String ID = "id";
    public static final String TITULO = "titulo";
    public static final String NOTA = "nota";
    public static final String RESUMO = "resumo";
    public static final String EMISSORA = "emissora";
    public static final String ANO = "ano";
    public static final String FAVORITO = "favorito";
    public static final String NUMERO = "numero";
    public static final String DIA = "dia";
    public static final String HORARIO = "horario";
    public static final String LOGIN = "login";
    public static final String SENHA = "senha";
    public static final String NOME = "nome";
    public static final String NOTA_ALTERADA = "notaAlterada";
    public static final String FAV_ID = "favId";
    public static final String USU_ID = "usuId";
    public static final String SER_ID = "serId";
    public static final String TEMP_ID = "tempId";

    // Parâmetro usado para verificar se o usuário já realizou algum login
    public static final int SEM_CONEXAO = 0;
    public static final int LOGIN_ANTERIOR = 1;
    public static final int LOGIN_SUCESSO = 2;
    public static final int LOGIN_FALHA = 3;

    // Parâmetro usado para selecionar as series favoritas (ou não favoritas)
    public static final boolean FAVORITO_SIM = true;
    public static final boolean FAVORITO_NAO = false;

    // Parâmetro usado para acessar WebService
    public static final int BAIXAR_SERIES = 0;
    public static final int ATUALIZAR_WEB_SERVICE = 1;
    public static final int ATUALIZAR_SERIES = 2;
}
