package projetoestacionamento.com.br.projetoestacionamento.modelo;

import java.util.Date;

public class Monitoramento {

    private Integer codigo;
    private String placa;
    private String data;
    private String hora;
    private int posicao;
    private int tipo_veiculo;
    private int cod_convenio;
    private int cod_table;
    private Double desconto;
    private int cod_usuario;
    private int virada_dia;

    public Monitoramento() {

    }

    public Monitoramento(Integer codigo, String placa, String data, String hora, int posicao,
                         int tipo_veiculo, int cod_convenio, int cod_table, Double desconto,
                         int cod_usuario, int virada_dia) {
        this.codigo = codigo;
        this.placa = placa;
        this.data = data;
        this.hora = hora;
        this.posicao = posicao;
        this.tipo_veiculo = tipo_veiculo;
        this.cod_convenio = cod_convenio;
        this.cod_table = cod_table;
        this.desconto = desconto;
        this.cod_usuario = cod_usuario;
        this.virada_dia = virada_dia;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public int getTipo_veiculo() {
        return tipo_veiculo;
    }

    public void setTipo_veiculo(int tipo_veiculo) {
        this.tipo_veiculo = tipo_veiculo;
    }

    public int getCod_convenio() {
        return cod_convenio;
    }

    public void setCod_convenio(int cod_convenio) {
        this.cod_convenio = cod_convenio;
    }

    public int getCod_table() {
        return cod_table;
    }

    public void setCod_table(int cod_table) {
        this.cod_table = cod_table;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public int getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(int cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public int getVirada_dia() {
        return virada_dia;
    }

    public void setVirada_dia(int virada_dia) {
        this.virada_dia = virada_dia;
    }

}
