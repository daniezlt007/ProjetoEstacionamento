package projetoestacionamento.com.br.projetoestacionamento;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import projetoestacionamento.com.br.projetoestacionamento.modelo.Monitoramento;
import projetoestacionamento.com.br.projetoestacionamento.modelo.Usuario;
import projetoestacionamento.com.br.projetoestacionamento.util.ConexaoHttp;

public class TesteActivity extends AppCompatActivity {

    private static final String HOST = "http://192.168.0.147/estacionamento";
    String url = HOST + "/listaUsuario.php";
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        btnEnviar = findViewById(R.id.btnEnviar);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {

                    new SolicitaDados().execute(HOST + url);
                }

            }
        });

    }

    private class SolicitaDados extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            try {
                return ConexaoHttp.postDados(HOST, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void getLista(String url){
        Ion.with(TesteActivity.this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        getJson(result);
                    }
                });
    }

    public static void getJson(JsonArray result){
        System.out.println("Array: " + result.toString());
        ArrayList<Usuario> mm = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            JsonObject jsonObject = result.get(i).getAsJsonObject();
            Usuario u = new Usuario();
            //"codigo":16,"placa":"MTH-8977","data":"2018-04-08","hora":"14:31:05.991571-03"
            u.setUsuario(jsonObject.get("usuario").getAsString());

            mm.add(u);
        }

        for (Usuario k:mm) {
            System.out.println("Lista: " + k.getNome());
        }
    }

}
