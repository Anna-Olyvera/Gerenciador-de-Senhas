package core;

public class EmailConfig {
    private String Remetente;
    private String AutenticacaoRemetente;

    // Construtor vazio exigido pelo Firebase
    public EmailConfig() {
        // Email config
    }

    // Getters
    public String getRemetente() {
        return Remetente;
    }

    public String getAutenticacaoRemetente() {
        return AutenticacaoRemetente;
    }

    // Setters
    public void setRemetente(String remetente) {
        this.Remetente = remetente;
    }

    public void setAutenticacaoRemetente(String autenticacaoRemetente) {
        this.AutenticacaoRemetente = autenticacaoRemetente;
    }
}
