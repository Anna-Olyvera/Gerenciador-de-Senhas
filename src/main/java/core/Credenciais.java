package core;

public class Credenciais {
    private String nomeCredencial;
    private String senhaCredencial;

    public Credenciais(String nomeCredencial, String senhaCredencial){
        this.nomeCredencial = nomeCredencial;
        this.senhaCredencial = senhaCredencial;
    }

    // GETTERS
    public String getNomeCredencial() {
        return nomeCredencial;
    }

    public String getSenhaCredencial() {
        return senhaCredencial;
    }

    // SETTERS
    public void setNomeCredencial(String nomeCredencial) {
        this.nomeCredencial = nomeCredencial;
    }

    public void setSenhaCredencial(String senhaCredencial) {
        this.senhaCredencial = senhaCredencial;
    }

    @Override
    public String toString() {
        return "Credenciais{" +
                "nomeCredencial='" + nomeCredencial + '\'' +
                ", senhaCredencial='" + senhaCredencial + '\'' +
                '}';
    }
}
