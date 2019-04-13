package projetoestacionamento.com.br.projetoestacionamento.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import io.paperdb.Paper;
import projetoestacionamento.com.br.projetoestacionamento.R;
import projetoestacionamento.com.br.projetoestacionamento.enums.TipoMsg;
import projetoestacionamento.com.br.projetoestacionamento.modelo.Configuracao;
import projetoestacionamento.com.br.projetoestacionamento.modelo.RetornoConsulta;
import projetoestacionamento.com.br.projetoestacionamento.modelo.RetornoInsert;
import projetoestacionamento.com.br.projetoestacionamento.util.Common;
import projetoestacionamento.com.br.projetoestacionamento.util.MaskEditUtil;
import projetoestacionamento.com.br.projetoestacionamento.util.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgCarro, imgMoto;
    private TextView textView5;
    private EditText txtPlaca, txtPlaca2;
    private Button btnConsultar, btnConfirmar, btnConfig;
    private Integer codigoTabela = 1;
    private Integer codigoConvenio = -1;
    private Integer tipoVeiculo = -1;
    private Integer posicao;
    private Integer smart = 0;
    private String placa;
    private String placa2;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        imgCarro = findViewById(R.id.imgCarro);
        imgMoto = findViewById(R.id.imgMoto);
        txtPlaca = findViewById(R.id.txtPlaca);
        txtPlaca2 = findViewById(R.id.txtPlaca2);
        btnConfig = findViewById(R.id.btnConfig);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnConsultar = findViewById(R.id.btnConsultar);
        textView5 = findViewById(R.id.textView5);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!Character.isLetter(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        txtPlaca.setFilters(new InputFilter[] { filter });

        txtPlaca.addTextChangedListener(MaskEditUtil.mask(txtPlaca, MaskEditUtil.FORMAT_PLACA.toUpperCase()));

        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
                startActivity(intent);
            }
        });

        imgCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoVeiculo = 1;
                textView5.setText("CARRO");
            }
        });

        imgMoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipoVeiculo = 2;
                textView5.setText("MOTO");
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placa = txtPlaca.getText().toString();
                placa2 = txtPlaca2.getText().toString();
                if (placa.equals("")) {
                    Common.showMsgAlertOK(MainActivity.this, "Informe as letras da placa do veículo.","ALERTA", TipoMsg.ALERTA);
                    txtPlaca.setFocusable(true);
                } else if(placa.length() < 3){
                    Common.showMsgAlertOK(MainActivity.this, "É necessário informar 3 letras.","ALERTA", TipoMsg.ALERTA);
                    txtPlaca.setFocusable(true);
                } else if(placa2.equals("")){
                    Common.showMsgAlertOK(MainActivity.this, "Informe os números da placa do veículo.","ALERTA", TipoMsg.ALERTA);
                    txtPlaca2.setFocusable(true);
                } else if(placa2.length() < 4){
                    Common.showMsgAlertOK(MainActivity.this, "É necessário informar 4 números.","ALERTA", TipoMsg.ALERTA);
                    txtPlaca.setFocusable(true);
                } else if(lerConfiguracao().equals("") || lerConfiguracao() == null){
                        Common.showMsgAlertOK(MainActivity.this, "É necessário informar o IP do Servidor na tela de configuração!","ALERTA", TipoMsg.ALERTA);
                } else {
                    Call<RetornoConsulta> call = new RetrofitConfig("http://" + lerConfiguracao() + "/estacionamento/").pegarServicoComunicacaoServidor().consultar(placa.toUpperCase().replace("-","").trim() + placa2.trim());
                    call.enqueue(new Callback<RetornoConsulta>() {
                        @Override
                        public void onResponse(Call<RetornoConsulta> call, Response<RetornoConsulta> response) {
                            if (response.code() == 200) {
                                if(response != null){
                                    RetornoConsulta jsonObject = response.body();
                                    String retorno = jsonObject.getENCONTRADO();
                                    String msg = "";
                                    Log.d("TAG ANTESCONSULTADO", "MSG: " + retorno);
                                    if (retorno.equals("OK")) {
                                        Log.d("TAG CONSULTADO", "OK: " + response.body().getENCONTRADO());
                                        msg += Common.linha;
                                        msg += "Veículo da placa: " + placa + placa2 + " possui registro em aberto!\n";
                                        msg += Common.linha;
                                        Common.showMsgAlertOK(MainActivity.this, msg,"Consulta", TipoMsg.INFO);
                                        //Toast.makeText(MainActivity.this, "Encontrado", Toast.LENGTH_LONG).show();
                                        limparCampos();
                                    }
                                    else {
                                        msg += Common.linha;
                                        msg += "Veículo da placa: " + placa + placa2  + " não possui registro em aberto!\n";
                                        msg += Common.linha;
                                        Log.d("TAG NAOCONSULTADO", "OK: " + response.body().getENCONTRADO());
                                        Common.showMsgAlertOK(MainActivity.this, msg,"Consulta", TipoMsg.INFO);
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<RetornoConsulta> call, Throwable t) {
                            Common.showMsgAlertOK(MainActivity.this, "Ocorreu um problema: " + t.getMessage(),"Comportamento inesperado", TipoMsg.ERRO);
                        }
                    });
                }

            }
        });

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posicao = 0;
                codigoConvenio = 1;
                placa = txtPlaca.getText().toString();
                placa2 = txtPlaca2.getText().toString();

                if (placa.equals("") || placa.length() < 3) {
                    //Toast.makeText(MainActivity.this, "Informe as letras da placa do veículo.", Toast.LENGTH_SHORT).show();
                    Common.showMsgAlertOK(MainActivity.this, "Informe as letras da placa do veículo.","ALERTA", TipoMsg.ALERTA);
                } else if(placa.length() < 3){
                    Common.showMsgAlertOK(MainActivity.this, "É necessário informar 3 letras.","ALERTA", TipoMsg.ALERTA);
                    txtPlaca.setFocusable(true);
                } else if(placa2.equals("")){
                    Common.showMsgAlertOK(MainActivity.this, "Informe os números da placa do veículo.","ALERTA", TipoMsg.ALERTA);
                    txtPlaca2.setFocusable(true);
                } else if(placa2.length() < 4){
                    Common.showMsgAlertOK(MainActivity.this, "É necessário informar 4 números.","ALERTA", TipoMsg.ALERTA);
                    txtPlaca.setFocusable(true);
                } else if (tipoVeiculo == -1) {
                    Toast.makeText(MainActivity.this, "Selecione um tipo de Veículo.", Toast.LENGTH_SHORT).show();
                } else if(lerConfiguracao().equals("") || lerConfiguracao() == null){
                    Common.showMsgAlertOK(MainActivity.this, "É necessário informar o IP do Servidor na tela de configuração!","ALERTA", TipoMsg.ALERTA);
                } else {
                    final String placaFinal = placa.replace("-","").trim() + placa2.trim();
                    Call<RetornoInsert> call = new RetrofitConfig("http://" + lerConfiguracao() + "/estacionamento/").pegarServicoComunicacaoServidor().inserir(placaFinal, posicao, tipoVeiculo, codigoConvenio, codigoTabela, smart);
                    call.enqueue(new Callback<RetornoInsert>() {
                        @Override
                        public void onResponse(Call<RetornoInsert> call, Response<RetornoInsert> response) {
                            if (response.code() == 200) {
                                if(response != null){
                                    RetornoInsert jsonObject = response.body();
                                    String retorno = jsonObject.getCREATE();
                                    Log.d("TAG ANTESCONSULTADO", "MSG: " + retorno);
                                    if (retorno.equals("OK")) {
                                        Log.d("TAG CONSULTADO", "OK: " + response.body().getCREATE());
                                        String msg = "";
                                        msg += Common.linha;
                                        msg += "Veículo da placa: " + placaFinal + " foi inserido com sucesso!\n";
                                        msg += Common.linha;
                                        Common.showMsgAlertOK(MainActivity.this, msg,"Salvo", TipoMsg.SUCESSO);
                                        limparCampos();
                                    } else {
                                        Log.d("TAG NAOCONSULTADO", "OK: " + response.body().getCREATE());
                                        Common.showMsgAlertOK(MainActivity.this, "Ocorreu um problema ao inserir!","Comportamento inesperado", TipoMsg.ERRO);
                                    }
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<RetornoInsert> call, Throwable t) {
                            Common.showMsgAlertOK(MainActivity.this, "Ocorreu um problema ao inserir: " + t.getMessage(),"Comportamento inesperado", TipoMsg.ERRO);
                            Log.d("TAG ERRO insere", "Erro: " + t.getMessage());
                        }
                    });
                }
            }
        });
    }

    public void limparCampos() {
        codigoConvenio = -1;
        tipoVeiculo = -1;
        posicao = -1;
        txtPlaca.setText("");
        txtPlaca2.setText("");
        textView5.setText("");
        txtPlaca.setFocusable(true);
    }

    public String lerConfiguracao(){
        try {
            Configuracao configuracao = Paper.book().read("configuracao");
            String config = configuracao.getIpServidor();
            return config;
        }catch (Exception ex){
            Common.showMsgAlertOK(MainActivity.this, "É necessário informar o IP do Servidor na tela de configuração!","ALERTA", TipoMsg.ALERTA);
        }
        return "";

    }

}
