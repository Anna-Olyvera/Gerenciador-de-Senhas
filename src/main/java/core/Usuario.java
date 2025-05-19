package core;

public class Usuario {
    private String login;
    private String email;
    private String telefone;
    private String chaveMestra;

    public Usuario(String login, String email, String telefone, String chaveMestra) {
        this.login = login;
        this.email = email;
        this.telefone = telefone;
        this.chaveMestra = chaveMestra;
    }

    // Metódos Getters
    public String getLogin() {
        return login;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getChaveMestra() {
        return chaveMestra;
    }

    // Metódos Setters
    public void setLogin(String login){
        this.login = login;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setChaveMestra(String chaveMestra){
        this.chaveMestra = chaveMestra;
    }
}