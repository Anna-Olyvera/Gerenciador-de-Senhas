package core;  // ou outro pacote

public class EmailConfig {

    private String Remetente;
    private String AutenticacaoRemetente;

    public EmailConfig() {}  // construtor vazio obrigatório para Firebase

    public String getRemetente() {
        return Remetente;
    }

    public void setRemetente(String remetente) {
        this.Remetente = remetente;
    }

    public String getAutenticacaoRemetente() {
        return AutenticacaoRemetente;
    }

    public void setAutenticacaoRemetente(String autenticacaoRemetente) {
        this.AutenticacaoRemetente = autenticacaoRemetente;
    }
}
