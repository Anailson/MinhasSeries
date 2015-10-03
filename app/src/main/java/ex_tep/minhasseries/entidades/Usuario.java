package ex_tep.minhasseries.entidades;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Anailson on 23/07/2015.
 */
public class Usuario extends RealmObject {

    @PrimaryKey
    private int id;
    private String nome;
    private String login;
    private String senha;
    private boolean logado;

    public Usuario (){
    }

    public Usuario(int id, String nome, String login, String senha, boolean logado) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.logado = logado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
}
