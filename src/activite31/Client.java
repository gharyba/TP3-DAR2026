package activite31;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hote = args.length > 0 ? args[0] : "localhost";
        int port = 1234;
        try {
            if (args.length > 1) port = Integer.parseInt(args[1]);
            InetAddress adresse = InetAddress.getByName(hote);
            try (Socket socket = new Socket();
                 Scanner clavier = new Scanner(System.in)) {
                socket.connect(new InetSocketAddress(adresse, port));
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(in.readLine());
                System.out.println("Appuyez sur Entree pour quitter.");
                if (clavier.hasNextLine()) clavier.nextLine();
            }
        } catch (NumberFormatException e) {
            System.err.println("Port invalide.");
        } catch (IOException e) {
            System.err.println("Erreur reseau : " + e.getMessage());
        }
    }
}
