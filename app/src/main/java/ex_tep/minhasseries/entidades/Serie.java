package ex_tep.minhasseries.entidades;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Anailson on 25/06/2015.
 */
public class Serie extends RealmObject {

    @PrimaryKey
    private int id;
    private String titulo;
    private float nota;
    private String resumo;
    private String emissora;
    private int ano;
    private RealmList<Temporada> temporadas;
    private boolean favorito;
    private boolean marcado;
    private boolean notaAlterada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getEmissora() {
        return emissora;
    }

    public void setEmissora(String emissora) {
        this.emissora = emissora;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public RealmList<Temporada> getTemporadas() {
        return temporadas;
    }

    public void setTemporadas(RealmList<Temporada> temporadas) {
        this.temporadas = temporadas;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public boolean isMarcado() {
        return marcado;
    }

    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public boolean isNotaAlterada() {
        return notaAlterada;
    }

    public void setNotaAlterada(boolean notaAlterada) {
        this.notaAlterada = notaAlterada;
    }
}
