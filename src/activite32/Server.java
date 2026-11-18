package activite32;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int nombreOperations = 0;

    // Cette section critique est partagée par tous les threads clients.
    public static synchronized int compterOperation() {
        nombreOperations++;
        System.out.println("Nombre total d'operations : " + nombreOperations);
        return nombreOperations;
    }

    public static void main(String[] args) {
        int port = 1235;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Port invalide.");
                return;
            }
        }

        try (ServerSocket serveur = new ServerSocket(port)) {
            System.out.println("Calculatrice en attente sur le port " + port);
            while (true) {
                Socket socket = serveur.accept();
                System.out.println("Client connecte : " + socket.getRemoteSocketAddress());
                new Thread(new ClientProcess(socket)).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur : " + e.getMessage());
        }
    }
}
