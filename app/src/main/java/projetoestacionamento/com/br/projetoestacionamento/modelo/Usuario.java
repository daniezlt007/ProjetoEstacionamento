package projetoestacionamento.com.br.projetoestacionamento.modelo;

public class Usuario {

    private Integer codigo;
    private String usuario;
    private String senha;
    private String nome;
    private Integer cod_empresa;

    public Usuario() {

    }

    public Usuario(Integer codigo, String usuario, String senha, String nome, Integer cod_empresa) {
        this.codigo = codigo;
        this.usuario = usuario;
        this.senha = senha;
        this.nome = nome;
        this.cod_empresa = cod_empresa;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCod_empresa() {
        return cod_empresa;
    }

    public void setCod_empresa(Integer cod_empresa) {
        this.cod_empresa = cod_empresa;
    }
}
