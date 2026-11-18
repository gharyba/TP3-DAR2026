package activite32;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientProcess implements Runnable {
    private final Socket socket;

    public ClientProcess(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (Socket client = socket;
             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {
            // Les deux côtés écrivent et vident l'en-tête avant de créer le flux d'entrée.
            out.flush();
            try (ObjectInputStream in = new ObjectInputStream(client.getInputStream())) {
                while (true) {
                    Object demande;
                    try {
                        demande = in.readObject();
                    } catch (EOFException e) {
                        break;
                    }
                    if ("exit".equals(demande)) break;
                    String reponse;
                    if (!(demande instanceof Operation)) {
                        reponse = "Erreur : demande invalide";
                    } else {
                        Operation operation = (Operation) demande;
                        int x = operation.getX();
                        int y = operation.getY();
                        switch (operation.getOperateur()) {
                            case '+': reponse = String.valueOf(x + y); break;
                            case '-': reponse = String.valueOf(x - y); break;
                            case '*': reponse = String.valueOf(x * y); break;
                            case '/':
                                reponse = y == 0 ? "Erreur : division par zero" : String.valueOf((double) x / y);
                                break;
                            default: reponse = "Erreur : operateur invalide";
                        }
                        if (!reponse.startsWith("Erreur")) Server.compterOperation();
                        System.out.println(x + " " + operation.getOperateur() + " " + y + " = " + reponse);
                    }
                    out.writeObject(reponse);
                    out.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Connexion client terminee : " + e.getMessage());
        }
        System.out.println("Client deconnecte.");
    }
}
