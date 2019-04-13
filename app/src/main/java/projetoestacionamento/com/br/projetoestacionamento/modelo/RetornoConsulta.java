package projetoestacionamento.com.br.projetoestacionamento.modelo;

public class RetornoConsulta {

    private String ENCONTRADO;

    public RetornoConsulta() {
    }

    public RetornoConsulta(String ENCONTRADO) {
        this.ENCONTRADO = ENCONTRADO;
    }

    public String getENCONTRADO() {
        return ENCONTRADO;
    }

    public void setENCONTRADO(String ENCONTRADO) {
        this.ENCONTRADO = ENCONTRADO;
    }
}
