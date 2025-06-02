package core;

public class EmailConfig {
    private String remetente;
    private String autenticacaoRemetente;

    // Construtor vazio exigido pelo Firebase
    public EmailConfig() {
        // Apenas para construir a classe construtora
    }

    // Getters
    public String getRemetente() {
        return remetente;
    }

    public String getAutenticacaoRemetente() {
        return autenticacaoRemetente;
    }

    // Setters
    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public void setAutenticacaoRemetente(String autenticacaoRemetente) {
        this.autenticacaoRemetente = autenticacaoRemetente;
    }
}