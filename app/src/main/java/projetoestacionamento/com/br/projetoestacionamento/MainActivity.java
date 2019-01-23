package projetoestacionamento.com.br.projetoestacionamento;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

import projetoestacionamento.com.br.projetoestacionamento.modelo.Monitoramento;
import projetoestacionamento.com.br.projetoestacionamento.util.Common;
import projetoestacionamento.com.br.projetoestacionamento.util.MaskEditUtil;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgCarro, imgMoto;
    private EditText txtPlaca;
    private Button btnConsultar, btnConfirmar;
    private RadioGroup rbGroup;
    private RadioButton rbEstacionamento, rbConvenio;
    private Integer codigoTabela = -1;
    private Integer codigoConvenio = -1;
    private Integer tipoVeiculo = -1;
    private Integer posicao;
    private Integer smart = 1;
    private String placa;
    private static final String HOST = "http://192.168.0.22/estacionamento";
    private static final String FORMATED_PLACA = "###-####";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCarro = findViewById(R.id.imgCarro);
        imgMoto = findViewById(R.id.imgMoto);
        txtPlaca = findViewById(R.id.txtPlaca);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConsultar = findViewById(R.id.btnConsultar);
        rbGroup = findViewById(R.id.rbGroup);
        rbConvenio = findViewById(R.id.rbConvenio);
        rbEstacionamento = findViewById(R.id.rbEstacionamento);

        txtPlaca.addTextChangedListener(MaskEditUtil.mask(txtPlaca, MaskEditUtil.FORMAT_PLACA));

        imgCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoVeiculo = 1;
            }
        });

        imgMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoVeiculo = 2;
            }
        });

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idRadio = group.getCheckedRadioButtonId();
                if (idRadio == rbEstacionamento.getId()) {
                    codigoTabela = 1;
                    Toast.makeText(MainActivity.this, "Estacionamento" + 1, Toast.LENGTH_SHORT).show();
                }
                if (idRadio == rbConvenio.getId()) {
                    codigoTabela = 2;
                    Toast.makeText(MainActivity.this, "Convênio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placa = txtPlaca.getText().toString();
                Ion.with(MainActivity.this)
                        .load(Common.URL_CONSULTA)
                        .setBodyParameter("placa", placa)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                if (result.get("ENCONTRADO").getAsString().equals("OK")) {
                                    Toast.makeText(MainActivity.this, "Existe registro em aberto", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Não existe registro em aberto", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String url = HOST + "/lista.php";
                //getLista(url);
                posicao = 0;
                placa = txtPlaca.getText().toString();
                codigoConvenio = 1;
                String url = HOST + "/insere.php";

                if (placa.equals("")) {
                    Toast.makeText(MainActivity.this, "Informe a placa do veículo.", Toast.LENGTH_SHORT).show();
                } else if (tipoVeiculo == -1) {
                    Toast.makeText(MainActivity.this, "Selecione um tipo de Veículo.", Toast.LENGTH_SHORT).show();
                } else if (codigoTabela == -1) {
                    Toast.makeText(MainActivity.this, "Selecione Estacionamento ou Convênio.", Toast.LENGTH_SHORT).show();
                } else {
                    Ion.with(MainActivity.this)
                            .load(url)
                            .setBodyParameter("placa", placa)
                            .setBodyParameter("tipo_veiculo", String.valueOf(tipoVeiculo))
                            .setBodyParameter("posicao", String.valueOf(codigoConvenio))
                            .setBodyParameter("cod_tabela", String.valueOf(codigoTabela))
                            .setBodyParameter("cod_convenio", String.valueOf(1))
                            .setBodyParameter("cod_usuario", String.valueOf(1))
                            .setBodyParameter("smart", String.valueOf(1))
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("CREATE").getAsString().equals("OK")) {
                                        Toast.makeText(MainActivity.this, "Cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                                        limparCampos();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Ocorreu um problema ao cadastrar.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    public void getLista(String url){
        Ion.with(MainActivity.this)
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
        ArrayList<Monitoramento> mm = new ArrayList<>();
        for (int i = 0; i < result.size(); i++) {
            JsonObject jsonObject = result.get(i).getAsJsonObject();
            Monitoramento m = new Monitoramento();
            //"codigo":16,"placa":"MTH-8977","data":"2018-04-08","hora":"14:31:05.991571-03"
            m.setCodigo(jsonObject.get("codigo").getAsInt());
            m.setPlaca(jsonObject.get("placa").getAsString());
            m.setData(jsonObject.get("data").getAsString());
            m.setHora(jsonObject.get("hora").getAsString());

            mm.add(m);
        }

        for (Monitoramento k:mm) {
            System.out.println("Lista: " + k.getPlaca());
        }
    }

    public void limparCampos() {
        codigoTabela = -1;
        codigoConvenio = -1;
        tipoVeiculo = -1;
        posicao = -1;
        txtPlaca.setText("");
        txtPlaca.setFocusable(true);
    }
}
