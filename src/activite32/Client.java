package activite32;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String hote = args.length > 0 ? args[0] : "localhost";
        int port = 1235;
        try {
            if (args.length > 1) port = Integer.parseInt(args[1]);
            InetAddress adresse = InetAddress.getByName(hote);
            try (Socket socket = new Socket(); Scanner clavier = new Scanner(System.in)) {
                socket.connect(new InetSocketAddress(adresse, port));
                try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                    out.flush();
                    try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                        System.out.println("Connecte. Saisir par exemple 10 + 5, ou exit.");
                        while (clavier.hasNextLine()) {
                            String ligne = clavier.nextLine().trim();
                            if (ligne.equalsIgnoreCase("exit")) {
                                out.writeObject("exit");
                                out.flush();
                                break;
                            }
                            String[] morceaux = ligne.split("\\s+");
                            if (morceaux.length != 3 || morceaux[1].length() != 1) {
                                System.out.println("Format attendu : nombre operateur nombre");
                                continue;
                            }
                            try {
                                int x = Integer.parseInt(morceaux[0]);
                                int y = Integer.parseInt(morceaux[2]);
                                out.writeObject(new Operation(x, morceaux[1].charAt(0), y));
                                out.flush();
                                System.out.println("Resultat : " + in.readObject());
                            } catch (NumberFormatException e) {
                                System.out.println("Nombre invalide.");
                            }
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Port invalide.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
