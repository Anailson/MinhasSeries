package ex_tep.minhasseries.telas;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ex_tep.minhasseries.R;
import ex_tep.minhasseries.adaptadores.AdaptadorItemMenu;
import ex_tep.minhasseries.entidades.Episodio;
import ex_tep.minhasseries.entidades.ItemMenu;
import ex_tep.minhasseries.entidades.Serie;
import ex_tep.minhasseries.entidades.Temporada;
import ex_tep.minhasseries.entidades.Usuario;
import ex_tep.minhasseries.tratamentos.TratamentoBanco;
import ex_tep.minhasseries.tratamentos.TratamentoJSON;

import static ex_tep.minhasseries.Constantes.ATUALIZAR_SERIES;
import static ex_tep.minhasseries.Constantes.ATUALIZAR_WEB_SERVICE;
import static ex_tep.minhasseries.Constantes.BAIXAR_SERIES;
import static ex_tep.minhasseries.Constantes.CONFIGURAOES;
import static ex_tep.minhasseries.Constantes.FAVORITO;
import static ex_tep.minhasseries.Constantes.FAVORITOS;
import static ex_tep.minhasseries.Constantes.FAVORITO_NAO;
import static ex_tep.minhasseries.Constantes.FAVORITO_SIM;
import static ex_tep.minhasseries.Constantes.NOTA_ALTERADA;
import static ex_tep.minhasseries.Constantes.PERFIL;
import static ex_tep.minhasseries.Constantes.SERIES;
import static ex_tep.minhasseries.Constantes.SOBRE;

public class TelaPrincipal extends ActionBarActivity {

    //Lista de prioridades a fazer
    //TODO: Pesquisar como manipular e configurar os alarmes
    //TODO: Checar todos os TODOs espalhados pelo projeto.

    // Para o fim do projeto
    //TODO: Inserir um ícone para a série. O ícone vai exibir os detalhes da série
    //TODO: Inserir uma imagem/botão para exibir os detalhes da temporada / episódio
    //TODO: Apagar os logs do código
    //TODO: Melhorar o design do aplicativo (padronizar as cores, os layouts, as fontes etc)
    //TODO: Se possível, documentar.

    //Versão 2.0
    //TODO: Adicionar slide de imagens para os posters da série na tela com detalhes da série.
    //TODO: Adicionar alguma área para exibir críticas internas (usuários) e externas (face, sites etc)
    //TODO: Exibir notas / link do IMDB
    //TODO: Uma mini rede social. =)

    private DrawerLayout drawerLayout;
    private ListView lvwMenuLateral;
    private ActionBarDrawerToggle actionBarDrawer;
    private AdaptadorItemMenu adaptadorItemMenu;

    private String tituloPrincipal, tituloMenu;
    private int menuAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        TratamentoBanco.criarBanco(this);

        if (savedInstanceState == null) {

            menuAtual = 2;
            tituloMenu = getString(R.string.lbl_serie);
            tituloPrincipal = getString(R.string.title_principal);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            lvwMenuLateral = (ListView) findViewById(R.id.lvwMenuLateral);
            actionBarDrawer = new DrawerListener(this, drawerLayout, R.string.lbl_null, R.string.title_principal);

            lvwMenuLateral.setOnItemClickListener(new SlideMenuClickListener());
            drawerLayout.setDrawerListener(actionBarDrawer);

            WebServerAsync webAsync = new WebServerAsync(this);
            webAsync.setOperacao(BAIXAR_SERIES);
            webAsync.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        lvwMenuLateral.setAdapter(adaptadorItemMenu = adaptadorItemMenu());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        exibirTela(menuAtual);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawer.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.menu_atualizar:
                atualizarSeries();
            break;
            case R.id.menu_pesquisar:
                Toast.makeText(this,  "pesquisar clickado", Toast.LENGTH_SHORT).show();
            break;
            case R.id.menu_feedback:
                Toast.makeText(this, this.getString(R.string.menu_feedback) + " clickado", Toast.LENGTH_SHORT).show();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawer.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawer.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {

        atualizarWebService();
        super.onBackPressed();
    }

    private AdaptadorItemMenu adaptadorItemMenu(){

        String [] txtMenuLateral = getResources().getStringArray(R.array.txt_menu_lateral);
        TypedArray iconeMenuLateral = getResources().obtainTypedArray(R.array.img_menu_lateral);

        String quantSeries = TratamentoBanco.totalSeries(FAVORITO_NAO) + "";
        String quantFavoritos = TratamentoBanco.totalSeries(FAVORITO_SIM) + "";

        ArrayList<ItemMenu> itensMenus = new ArrayList<>();
        itensMenus.add(new ItemMenu(txtMenuLateral[PERFIL], iconeMenuLateral.getResourceId(PERFIL, -1)));
        itensMenus.add(new ItemMenu(txtMenuLateral[SERIES], iconeMenuLateral.getResourceId(SERIES, -1), true, quantSeries));
        itensMenus.add(new ItemMenu(txtMenuLateral[FAVORITOS], iconeMenuLateral.getResourceId(FAVORITOS, -1), true, quantFavoritos));
        itensMenus.add(new ItemMenu(txtMenuLateral[CONFIGURAOES], iconeMenuLateral.getResourceId(CONFIGURAOES, -1)));
        itensMenus.add(new ItemMenu(txtMenuLateral[SOBRE], iconeMenuLateral.getResourceId(SOBRE, -1)));
        iconeMenuLateral.recycle();

        return new AdaptadorItemMenu(this, itensMenus);
    }

    private void exibirTela(int position) {

        Fragment fragment = null;
        menuAtual = position;

        switch (position) {
            case PERFIL:
                tituloMenu = getString(R.string.lbl_perfil);
                fragment = new TelaPerfil();
                break;
            case SERIES:
                tituloMenu = getString(R.string.lbl_serie);
                fragment = new TelaSeries();
                break;
            case FAVORITOS:
                tituloMenu = getString(R.string.lbl_favorito);
                fragment = new TelaFavorito();
            break;
            case CONFIGURAOES:
                tituloMenu = getString(R.string.lbl_configuracoes);
                fragment = new TelaConfiguracoes();
                break;
            case SOBRE:
                tituloMenu = getString(R.string.lbl_sobre);
                fragment = new TelaSobre();
            break;
        }

        if (fragment == null) {
            fragment = new TelaSeries();
        }

        getSupportActionBar().setTitle(tituloMenu);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

        lvwMenuLateral.setItemChecked(position, true);
        lvwMenuLateral.setSelection(position);
        drawerLayout.closeDrawer(lvwMenuLateral);
    }

    private void atualizarWebService(){

        List idSeries = TratamentoBanco.buscarIds(Serie.class, NOTA_ALTERADA, true);
        List idTemporadas = TratamentoBanco.buscarIds(Temporada.class, NOTA_ALTERADA, true);
        List idEpisodios = TratamentoBanco.buscarIds(Episodio.class, NOTA_ALTERADA, true);
        List idFavoritos = TratamentoBanco.buscarIds(Serie.class, FAVORITO, FAVORITO_SIM);

        WebServerAsync serverAsync = new WebServerAsync(this);

        serverAsync.setOperacao(ATUALIZAR_WEB_SERVICE);
        serverAsync.execute(idSeries, idTemporadas, idEpisodios, idFavoritos);
        onResumeFragments();
    }

    private void atualizarSeries(){
        List idSerAlterada = TratamentoBanco.buscarIds(Serie.class, NOTA_ALTERADA, true);
        List idTempAlterada= TratamentoBanco.buscarIds(Temporada.class, NOTA_ALTERADA, true);
        List idEpiAlterada= TratamentoBanco.buscarIds(Episodio.class, NOTA_ALTERADA, true);
        List idFavoritos = TratamentoBanco.buscarIds(Serie.class, FAVORITO, FAVORITO_SIM);
        List idSerTotal = TratamentoBanco.buscarIds(Serie.class);

        WebServerAsync serverAsync = new WebServerAsync(this);
        serverAsync.setOperacao(ATUALIZAR_SERIES);
        serverAsync.execute(idSerAlterada, idTempAlterada, idEpiAlterada, idFavoritos, idSerTotal);
    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            exibirTela(menuAtual = position);
        }
    }

    private class DrawerListener extends ActionBarDrawerToggle {

        public DrawerListener(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }

        public void onDrawerOpened(View drawerView) {

            getSupportActionBar().setTitle(tituloPrincipal);
            invalidateOptionsMenu();
        }

        public void onDrawerClosed(View view) {
            getSupportActionBar().setTitle(tituloMenu);
            invalidateOptionsMenu();
        }
    }

    private class WebServerAsync extends AsyncTask<List<Integer>, Void, Void> {

        private int operacao;
        private ProgressDialog pDialog;
        private Context context;
        private List<Integer> idsSerTotal, idsFavorito, idsEpisodio, idsTemporada, idsSerie;


        private WebServerAsync(Context context) {
            this.context = context;
        }

        public void setOperacao(int operacao){
            this.operacao = operacao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            String mensagem = "";
            if(operacao == BAIXAR_SERIES ) {
                mensagem = TelaPrincipal.this.getString(R.string.msg_baixar_serie_sucesso);
            } else if (operacao == ATUALIZAR_SERIES){
                mensagem = TelaPrincipal.this.getString(R.string.msg_atualizar_serie_sucesso);
            }

            pDialog = new ProgressDialog(context);
            pDialog.setMessage(mensagem);
            pDialog.setCancelable(false);

            if(operacao != ATUALIZAR_WEB_SERVICE){
                pDialog.show();
            }
        }

        @Override
        protected final Void doInBackground(List<Integer>... listas) {

            switch (operacao){

                case BAIXAR_SERIES:

                    boolean logado = ((Usuario) TratamentoBanco.buscar(Usuario.class)).isLogado();
                    if (!logado){
                        TratamentoJSON.baixarDadosWebService();
                        TratamentoBanco.logar();
                    }
                break;

                case ATUALIZAR_WEB_SERVICE:
                    idsSerie = listas[0];
                    idsTemporada = listas[1];
                    idsEpisodio = listas[2];
                    idsFavorito = listas[3];

                    TratamentoJSON.atualizarWebService(idsSerie, idsTemporada, idsEpisodio, idsFavorito);
                break;

                case ATUALIZAR_SERIES:
                    idsSerie = listas[0];
                    idsTemporada = listas[1];
                    idsEpisodio = listas[2];
                    idsFavorito = listas[3];
                    idsSerTotal = listas[4];

                    TratamentoJSON.atualizarWebService(idsSerie, idsTemporada, idsEpisodio, idsFavorito);
                    TratamentoJSON.atualizarSeries(idsSerTotal);
                break;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);

            if(operacao == BAIXAR_SERIES){

                adaptadorItemMenu = adaptadorItemMenu();
                lvwMenuLateral.setAdapter(adaptadorItemMenu);
                exibirTela(menuAtual);

            } else if (operacao == ATUALIZAR_SERIES){

                Toast.makeText(TelaPrincipal.this, "Series atualizadas", Toast.LENGTH_SHORT).show();
            }

            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
        }
    }
}