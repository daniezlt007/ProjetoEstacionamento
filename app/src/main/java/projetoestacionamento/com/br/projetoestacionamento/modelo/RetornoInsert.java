package projetoestacionamento.com.br.projetoestacionamento.modelo;

public class RetornoInsert {

    private String CREATE;

    public RetornoInsert() {

    }

    public RetornoInsert(String CREATE) {
        this.CREATE = CREATE;
    }

    public String getCREATE() {
        return CREATE;
    }

    public void setCREATE(String CREATE) {
        this.CREATE = CREATE;
    }
}
