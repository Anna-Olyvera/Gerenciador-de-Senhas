import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;



public class Senha {
    public static String gerarSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String gerarHash(String senha, String salt) throws Exception {
        KeySpec spec = new PBEKeySpec(senha.toCharArray(), Base64.getDecoder().decode(salt), 100_000, 256);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public static void salvarUsuario(String usuario, String salt, String hash) throws IOException {
        FileWriter fw = new FileWriter("usuarios.txt", true);
        fw.write(usuario + ":" + salt + ":" + hash + "\n");
        fw.close();
    }

    public static String[] buscarUsuario(String usuario) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("usuarios.txt"));
        String linha;
        while ((linha = br.readLine()) != null) {
            String[] partes = linha.split(":");
            if (partes[0].equals(usuario)) {
                br.close();
                return partes;
            }
        }
        br.close();
        return null;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.print("Deseja (1) Cadastrar ou (2) Fazer login? ");
        int escolha = Integer.parseInt(sc.nextLine());

        if (escolha == 1) {
            System.out.print("Digite o nome de usuário: ");
            String usuario = sc.nextLine();

            System.out.print("Digite a senha: ");
            String senha = sc.nextLine();

            String salt = gerarSalt();
            String hash = gerarHash(senha, salt);

            salvarUsuario(usuario, salt, hash);
            System.out.println("Usuário cadastrado com sucesso!");
        }

        if (escolha == 2) {
            System.out.print("Digite seu nome de usuário: ");
            String usuario = sc.nextLine();

            System.out.print("Digite sua senha: ");
            String senha = sc.nextLine();

            String[] dados = buscarUsuario(usuario);

            if (dados == null) {
                System.out.println("Usuário não encontrado.");
            } else {
                String salt = dados[1];
                String hashSalvo = dados[2];
                String novoHash = gerarHash(senha, salt);

                if (novoHash.equals(hashSalvo)) {
                    System.out.println("Login bem-sucedido!");
                } else {
                    System.out.println("Senha incorreta!");
                }
            }
        }

        sc.close();
    }
}
