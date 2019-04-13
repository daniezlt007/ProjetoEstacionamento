package projetoestacionamento.com.br.projetoestacionamento.modelo;

public class Configuracao {

    private String ipServidor;

    public Configuracao() {
    }

    public Configuracao(String ipServidor) {
        this.ipServidor = ipServidor;
    }

    public String getIpServidor() {
        return ipServidor;
    }

    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }
}
