package projetoestacionamento.com.br.projetoestacionamento;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.nio.file.Files;

import projetoestacionamento.com.br.projetoestacionamento.util.Common;
import projetoestacionamento.com.br.projetoestacionamento.util.ConexaoHttp;

public class LoginActivity extends AppCompatActivity {

    private EditText txtLogin, txtSenha;
    private Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtPassword);
        btnLogar = findViewById(R.id.btnLogar);


        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = txtLogin.getText().toString();
                String senha = txtSenha.getText().toString();

                if(usuario.equals("")){
                    Toast.makeText(LoginActivity.this, "Campo Login é obrigatório!", Toast.LENGTH_LONG).show();
                }else if(senha.equals("")){
                    Toast.makeText(LoginActivity.this, "Campo Senha é obrigatório!", Toast.LENGTH_LONG).show();
                }else{
                    Ion.with(LoginActivity.this)
                            .load(Common.URL_LOGIN)
                            .setBodyParameter("usuario", usuario)
                            .setBodyParameter("senha", senha)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    if (result.get("LOGADO").getAsString().equals("OK")) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(LoginActivity.this, "Erro ao efetuar login.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

}
