package projetoestacionamento.com.br.projetoestacionamento.util;

import io.paperdb.Paper;
import projetoestacionamento.com.br.projetoestacionamento.interfaces.InterfaceConsulta;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private final Retrofit retrofit;

    public RetrofitConfig(String url) {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public String lerConfiguracao(){
        String configuracao = Paper.book().read("configuracao");
        return configuracao;
    }

    public InterfaceConsulta pegarServicoComunicacaoServidor() {
        return this.retrofit.create(InterfaceConsulta.class);
    }

}
