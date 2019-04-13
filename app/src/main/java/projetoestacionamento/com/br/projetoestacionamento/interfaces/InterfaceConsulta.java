package projetoestacionamento.com.br.projetoestacionamento.interfaces;

import projetoestacionamento.com.br.projetoestacionamento.modelo.RetornoConsulta;
import projetoestacionamento.com.br.projetoestacionamento.modelo.RetornoInsert;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface InterfaceConsulta {

    @FormUrlEncoded
    @POST("insere.php")
    Call<RetornoInsert> inserir(@Field("placa") String placa, @Field("posicao") int posicao,
                                @Field("tipo_veiculo") int tipo_veiculo, @Field("codconvenio") int cod_convenio,
                                @Field("cod_tabela") int cod_tabela, @Field("smart") int smart);
    @FormUrlEncoded
    @POST("consulta.php")
    Call<RetornoConsulta> consultar(@Field("placa") String placa);

}
