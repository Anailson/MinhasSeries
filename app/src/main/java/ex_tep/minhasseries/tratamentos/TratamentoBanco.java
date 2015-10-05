package ex_tep.minhasseries.tratamentos;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ex_tep.minhasseries.entidades.Episodio;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.entidades.Temporada;
import ex_tep.minhasseries.entidades.Usuario;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

import static ex_tep.minhasseries.Constantes.FAVORITO;
import static ex_tep.minhasseries.Constantes.ID;
import static ex_tep.minhasseries.Constantes.NOTA_ALTERADA;

/**
 * Created by Anailson on 24/08/2015.
 */
public class TratamentoBanco {


    private TratamentoBanco (){}

    public static void criarBanco(Context context) {

        RealmConfiguration configuration = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(configuration);
    }

    public static void logar() {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(Usuario.class).findFirst().setLogado(true);
        realm.commitTransaction();
    }

    public static void salvar(List<Serie> series) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        for (Serie s : series) {
            realm.copyToRealmOrUpdate(s);
        }
        realm.commitTransaction();

    }

    public static List<Integer> buscarIds(Class cls, String tipoParametro, boolean parametro){

        Realm realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(cls).equalTo(tipoParametro, parametro).findAll();
        List<Integer> ids = new ArrayList<>();

        if(Serie.class.equals(cls)){

            for(int i = 0; i < results.size(); i++){
                Serie s = (Serie) results.get(i);
                ids.add(s.getId());
            }

        } else if(Temporada.class.equals(cls)){

            for(int i = 0; i < results.size(); i++){
                Temporada t = (Temporada) results.get(i);
                ids.add(t.getId());
            }

        } else if(Episodio.class.equals(cls)){

            for(int i = 0; i < results.size(); i++){
                Episodio e = (Episodio) results.get(i);
                ids.add(e.getId());
            }
        }
        return ids;
    }

    public static RealmObject buscar(Class cls){
        return buscar(cls, -1);
    }

    public static RealmObject buscar(Class cls, int id) {

        RealmObject realmObject ;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        if(Usuario.class.equals(cls)) {

            if (realm.where(cls).findAll().size() == 0) {

                realmObject = realm.createObject(cls);
                realm.copyToRealmOrUpdate(realmObject);

            } else {
                realmObject = realm.where(cls).findFirst();
            }

        } else{

            if (id == -1){
                realmObject = realm.where(cls).findFirst();
            } else {
                realmObject = realm.where(cls).equalTo(ID, id).findFirst();
            }
        }

        realm.commitTransaction();
        return realmObject;
    }

    public static RealmResults<Serie> buscarSeries() {

        Realm realm = Realm.getDefaultInstance();
        return realm.where(Serie.class).findAll();
    }

    public static RealmResults<Serie> buscarSeries(boolean flag) {

        Realm realm = Realm.getDefaultInstance();
        return realm.where(Serie.class).equalTo(FAVORITO, flag).findAll();
    }

    public static Usuario alterar(Usuario u) {

        Usuario usuario = (Usuario) buscar(Usuario.class, -1);

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        usuario.setId(u.getId());
        usuario.setNome(u.getNome());
        usuario.setLogin(u.getLogin());
        usuario.setSenha(u.getSenha());
        usuario.setLogado(u.isLogado());

        realm.copyToRealmOrUpdate(usuario);
        realm.commitTransaction();
        return usuario;
    }

    public static void maracarSelecao(Serie serie, boolean flag) {

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        serie.setMarcado(flag);
        realm.copyToRealmOrUpdate(serie);
        realm.commitTransaction();
    }

    public static int atualizarFavoritos(boolean flag) {

        int cont = 0;
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        List<Serie> series = realm.where(Serie.class).findAll();

        for (int i = 0; i < series.size(); i++) {
            Serie s = series.get(i);
            if (s.isMarcado()) {

                s.setFavorito(flag);
                s.setMarcado(false);
                realm.copyToRealmOrUpdate(s);
                if (cont < 2) {
                    cont++;
                }
            }
        }

        realm.commitTransaction();
        return cont;
    }

    public static void alterarNota(float nota, int epiId, int tempId, int serieId){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        Episodio episodio = realm.where(Episodio.class).equalTo(ID, epiId).findFirst();
        episodio.setNota(nota);
        episodio.setNotaAlterada(true);
        realm.copyToRealmOrUpdate(episodio);

        Temporada temporada = realm.where(Temporada.class).equalTo(ID, tempId).findFirst();
        temporada.setNota(calcularMedia(temporada.getEpisodios()));
        temporada.setNotaAlterada(true);
        realm.copyToRealmOrUpdate(temporada);

        Serie serie = realm.where(Serie.class).equalTo(ID, serieId).findFirst();
        serie.setNota(calcularMedia(serie.getTemporadas()));
        serie.setNotaAlterada(true);
        realm.copyToRealmOrUpdate(serie);
        
        realm.commitTransaction();
    }

    public static void atualizarAlterado(boolean flag){

        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();

        List<Episodio> episodios = realm.where(Episodio.class).equalTo(NOTA_ALTERADA, !flag).findAll();

        for(int i = 0; i < episodios.size(); i++){

            Episodio e = episodios.get(i);
            e.setNotaAlterada(flag);
            realm.copyToRealmOrUpdate(e);
        }

        List<Temporada> temporadas = realm.where(Temporada.class).equalTo(NOTA_ALTERADA, !flag).findAll();
        for(int i = 0; i < temporadas.size(); i++){
            Temporada t = temporadas.get(i);
            t.setNotaAlterada(flag);
            realm.copyToRealmOrUpdate(t);
        }

        List<Serie> series = realm.where(Serie.class).equalTo(NOTA_ALTERADA, !flag).findAll();
        for(int i = 0; i < series.size(); i++){
            Serie s = series.get(i);
            s.setNotaAlterada(flag);
            realm.copyToRealmOrUpdate(s);
        }
        realm.commitTransaction();
    }


    public static int totalSeries(boolean flag) {
        return buscarSeries(flag).size();
    }

    private static float calcularMedia(RealmList lista){

        float nota = 0;

        if (lista.size() > 0){

            for(int i = 0; i < lista.size(); i++){
                RealmObject obj = lista.get(i);

                if(obj instanceof Episodio){
                    nota += ((Episodio) obj).getNota();
                } else if (obj instanceof Temporada) {
                    nota += ((Temporada) obj).getNota();
                }
            }
            nota /= lista.size();
        }

        return nota;
    }
}
