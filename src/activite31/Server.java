package activite31;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int prochainNumero = 0;

    private static synchronized int nouveauNumero() {
        return ++prochainNumero;
    }

    public static void main(String[] args) {
        int port = 1234;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Port invalide.");
                return;
            }
        }

        try (ServerSocket serveur = new ServerSocket(port)) {
            System.out.println("Serveur en attente sur le port " + port);
            while (true) {
                Socket socket = serveur.accept();
                int numero = nouveauNumero();
                System.out.println("Client n°" + numero + " : " + socket.getRemoteSocketAddress());
                new Thread(new ClientProcess(socket, numero)).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur : " + e.getMessage());
        }
    }
}
