package controller;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TentativaLoginController {
    private static final int LIMITE_TENTATIVAS = 3;
    private static final int TEMPO_BLOQUEIO_MINUTOS = 30;

    private static class InfoTentativa {
        int tentativas = 0;
        LocalDateTime ultimoErro = null;
        LocalDateTime bloqueadoAte = null;
    }

    // Usar ConcurrentHashMap para evitar problemas em acesso concorrente
    private static final Map<String, InfoTentativa> tentativasMap = new ConcurrentHashMap<>();

    public boolean estaBloqueado(String login) {
        InfoTentativa info = tentativasMap.get(login);
        if (info == null || info.bloqueadoAte == null) {
            return false;
        }

        // Se bloqueadoAte já passou, limpa para desbloquear automaticamente
        if (LocalDateTime.now().isAfter(info.bloqueadoAte)) {
            tentativasMap.remove(login);
            return false;
        }

        return true;
    }

    public void registrarFalha(String login) {
        InfoTentativa info = tentativasMap.computeIfAbsent(login, k -> new InfoTentativa());

        info.tentativas++;
        info.ultimoErro = LocalDateTime.now();

        if (info.tentativas >= LIMITE_TENTATIVAS) {
            info.bloqueadoAte = info.ultimoErro.plusMinutes(TEMPO_BLOQUEIO_MINUTOS);
        }

        // Não precisa re-inserir no map, computeIfAbsent já cuidou disso
    }

    public void limparTentativas(String login) {
        tentativasMap.remove(login);
    }

    public LocalDateTime getUltimoErro(String login) {
        InfoTentativa info = tentativasMap.get(login);
        return (info != null) ? info.ultimoErro : null;
    }
}
