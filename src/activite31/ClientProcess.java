package activite31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess implements Runnable {
    private final Socket socket;
    private final int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    public void run() {
        try (Socket client = socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
            // Une ligne de texte peut être lue par le client Java et par Telnet.
            out.println("Bonjour, vous etes le client n°" + numero);
            while (in.readLine() != null) {
                // La connexion reste ouverte jusqu'au départ du client.
            }
        } catch (IOException e) {
            System.err.println("Client n°" + numero + " : " + e.getMessage());
        }
        System.out.println("Client n°" + numero + " deconnecte.");
    }
}
