package ex_tep.minhasseries.entidades;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Anailson on 25/06/2015.
 */
public class Temporada extends RealmObject {

    @PrimaryKey
	private int id;
    private int numero;
    private float nota;
	private String resumo;
    private int ano;
    private int serId;
    private RealmList<Episodio> episodios;
    private boolean notaAlterada;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getSerId() {
        return serId;
    }

    public void setSerId(int serId) {
        this.serId = serId;
    }

    public RealmList<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(RealmList<Episodio> episodios) {
        this.episodios = episodios;
    }

    public boolean isNotaAlterada() {
        return notaAlterada;
    }

    public void setNotaAlterada(boolean notaAlterada) {
        this.notaAlterada = notaAlterada;
    }
}