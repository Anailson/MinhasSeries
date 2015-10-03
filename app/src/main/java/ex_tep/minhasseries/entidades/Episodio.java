package ex_tep.minhasseries.entidades;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Anailson on 25/06/2015.
 */

@RealmClass
public class Episodio extends RealmObject {

    @PrimaryKey
	private int id;
    private int numero;
    private String titulo;
    private float nota;
    private String resumo;
    private String dia;
    private String horario;
    private int tempId;
	private boolean assistido;
    private boolean notaAlterada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public int getTempId() {
        return tempId;
    }

    public void setTempId(int tempId) {
        this.tempId = tempId;
    }

    public boolean isAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    public boolean isNotaAlterada() {
        return notaAlterada;
    }

    public void setNotaAlterada(boolean notaAlterada) {
        this.notaAlterada = notaAlterada;
    }
}
