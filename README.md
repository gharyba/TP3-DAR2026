# TP3 DAR 2026-2027

Faculté des Sciences de Sfax - LSI3 - Développement d'applications réparties
Activités 3-1 et 3-2 : serveurs TCP multi-threads

Ce projet reprend les échanges texte du TP1 et la classe `Operation` sérialisable du TP2. J'ai utilisé un `ServerSocket` pour attendre les connexions et un `Thread` avec `ClientProcess` pour chaque client. Les deux activités se lancent séparément.

## Fichiers

```text
src/
  activite31/Server.java, ClientProcess.java, Client.java
  activite32/Server.java, ClientProcess.java, Client.java, Operation.java
```

Il n'y a pas de bibliothèque externe. Un JDK 8 ou plus récent suffit. Les fichiers `.class` sont créés dans `bin`, qui n'est pas déposé sur Git.

## Compiler et lancer (PowerShell)

Depuis le dossier `TP3-DAR2026` :

```powershell
New-Item -ItemType Directory -Path bin -Force | Out-Null
javac -encoding UTF-8 -d bin (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object FullName)
```

Activité 3-1, dans deux terminaux :

```powershell
java -cp bin activite31.Server
java -cp bin activite31.Client localhost 1234
```

Le serveur donne `client n°1`, puis `client n°2`, etc. Le client Java reste connecté jusqu'à ce qu'on appuie sur Entrée. On peut donc ouvrir plusieurs terminaux clients en même temps. Le serveur affiche leur adresse avec `getRemoteSocketAddress()` et leur numéro. Chaque connexion a son propre thread ; un client qui attend ne bloque pas `accept()` pour les autres.

Pour le test Telnet demandé dans le sujet, activer **Client Telnet** dans « Activer ou désactiver des fonctionnalités Windows », puis lancer :

```powershell
telnet localhost 1234
```

Le message d'accueil est une ligne de texte, lisible par Telnet. Le protocole de cette activité est volontairement séparé de celui de la calculatrice.

Activité 3-2, dans deux terminaux :

```powershell
java -cp bin activite32.Server
java -cp bin activite32.Client localhost 1235
```

Le client accepte une opération avec des espaces, par exemple `10 + 5`, puis `exit` pour quitter. Il peut envoyer plusieurs opérations sans se reconnecter. Il envoie un objet `Operation` qui contient les deux entiers et l'opérateur. Le serveur le lit avec `ObjectInputStream`, calcule, puis renvoie le résultat ou un message d'erreur avec `ObjectOutputStream`. Des deux côtés, le flux de sortie est créé et vidé avant le flux d'entrée pour éviter que les deux programmes attendent l'en-tête de l'autre.

Exemple :

```text
10 + 5   -> Resultat : 15
20 / 4   -> Resultat : 5.0
20 / 0   -> Resultat : Erreur : division par zero
2 ? 3    -> Resultat : Erreur : operateur invalide
```

Le compteur `nombreOperations` appartient au serveur, pas aux clients. La méthode `compterOperation()` est `synchronized` : un seul thread à la fois peut modifier et afficher sa valeur. Elle est appelée après un calcul réussi ; une demande invalide ou une division par zéro n'est pas comptée.

## Essais effectués sur cette machine

Le 18 novembre 2026, j'ai compilé les sept classes avec JDK 21.0.12.1. Un essai local automatisé a maintenu trois connexions texte ouvertes en même temps : les trois numéros étaient distincts, puis un quatrième client Java s'est connecté après la fermeture d'un des trois. Pour la calculatrice, j'ai vérifié les quatre opérateurs, un entier négatif, zéro, la division par zéro, un opérateur inconnu et une saisie invalide. Trois clients Java ont ensuite envoyé chacun 20 additions en parallèle ; le compteur du serveur est passé de 1 à 66 sans saut ni doublon (6 calculs initiaux réussis + 60 additions).

Le test avec la commande Telnet réelle n'a pas été effectué : le programme `telnet` n'est pas installé ici. Les tests entre plusieurs machines ne sont pas encore effectués. Aucune capture d'écran n'est jointe ; il faut placer dans `docs/screenshots/` les captures réelles du serveur et des clients lors de la démonstration, avec une courte légende dans ce README avant le dépôt final.

## Essai sur le réseau local

Sur la machine serveur, trouver son adresse IPv4 avec `ipconfig`, puis lancer le serveur. Depuis chaque autre machine du même réseau, vérifier le port avec `Test-NetConnection ADRESSE_IP -Port 1234` (ou `1235`) et lancer `java -cp bin activite31.Client ADRESSE_IP 1234` ou `java -cp bin activite32.Client ADRESSE_IP 1235`. Les mêmes classes compilées, en particulier `Operation`, doivent être présentes côté client et serveur. Si le pare-feu bloque la connexion, autoriser le port TCP concerné pour le réseau local. Certains réseaux isolent les clients entre eux.

## Synthèse et dépôt

Ce TP m'a montré la différence entre la socket d'écoute et la socket utilisée pour parler à un client. Le thread évite qu'un client immobilise tous les autres. La sérialisation permet de transmettre directement l'opération du TP2. Enfin, `synchronized` protège le compteur commun aux threads.

Après les essais Telnet et réseau local et l'ajout des vraies captures, déposer le projet dans un dépôt GitHub puis transmettre son lien dans l'espace de dépôt ENT. Aucun lien de dépôt distant n'est encore disponible.
