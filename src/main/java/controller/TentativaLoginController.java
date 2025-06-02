package controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TentativaLoginController {
    private static final int LIMITE_TENTATIVAS = 3;
    private static final int TEMPO_BLOQUEIO_MINUTOS = 1;

    private static class InfoTentativa {
        int tentativas = 0;
        LocalDateTime ultimoErro;
        LocalDateTime bloqueadoAte;
    }

    private static final Map<String, InfoTentativa> tentativasMap = new HashMap<>();

    public boolean estaBloqueado(String login) {
        InfoTentativa info = tentativasMap.get(login);
        if (info == null || info.bloqueadoAte == null) return false;
        if (LocalDateTime.now().isAfter(info.bloqueadoAte)) {
            tentativasMap.remove(login); // desbloqueia automaticamente
            return false;
        }
        return true;
    }

    public void registrarFalha(String login) {
        InfoTentativa info = tentativasMap.getOrDefault(login, new InfoTentativa());
        info.tentativas++;
        info.ultimoErro = LocalDateTime.now();

        if (info.tentativas >= LIMITE_TENTATIVAS) {
            info.bloqueadoAte = LocalDateTime.now().plusMinutes(TEMPO_BLOQUEIO_MINUTOS);
        }

        tentativasMap.put(login, info);
    }

    public void limparTentativas(String login) {
        tentativasMap.remove(login);
    }
}