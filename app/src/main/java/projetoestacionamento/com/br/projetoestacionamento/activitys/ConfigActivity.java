package projetoestacionamento.com.br.projetoestacionamento.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.paperdb.Paper;
import projetoestacionamento.com.br.projetoestacionamento.R;
import projetoestacionamento.com.br.projetoestacionamento.enums.TipoMsg;
import projetoestacionamento.com.br.projetoestacionamento.modelo.Configuracao;
import projetoestacionamento.com.br.projetoestacionamento.util.Common;
import projetoestacionamento.com.br.projetoestacionamento.util.MaskEditUtil;

public class ConfigActivity extends AppCompatActivity {

    EditText txtIpServidor;
    Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Paper.init(this);

        txtIpServidor = findViewById(R.id.editText);
        txtIpServidor.addTextChangedListener(MaskEditUtil.mask(txtIpServidor, MaskEditUtil.FORMAT_IP.toUpperCase()));

        btnSalvar = findViewById(R.id.button);

        Configuracao configuracao = Paper.book().read("configuracao");
        if(configuracao != null){
            txtIpServidor.setText(configuracao.getIpServidor());
        }


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtIpServidor.getText().toString().equals("")){
                    Common.showMsgAlertOK(ConfigActivity.this, "Informe o IP do Servidor.","ALERTA", TipoMsg.ALERTA);
                    txtIpServidor.setFocusable(true);
                }
                String ipServidor = txtIpServidor.getText().toString();
                Configuracao config = new Configuracao();
                config.setIpServidor(ipServidor);
                Paper.book().write("configuracao", config);
                String msg = "";
                msg += Common.linha;
                msg += "O Ip do Servidor " + config.getIpServidor() + " foi salvo com sucesso!\n";
                msg += Common.linha;
                Common.showMsgAlertOK(ConfigActivity.this, msg,"Salvo", TipoMsg.SUCESSO);
            }
        });
    }
}
