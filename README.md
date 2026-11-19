# TP3 - Serveur TCP avec plusieurs clients

## Objectif

Utiliser des threads pour traiter plusieurs clients TCP. La deuxième activité reprend la calculatrice avec plusieurs clients.

## Exercices réalisés

- Activité 3-1 : le serveur accepte plusieurs clients et donne un numéro à chaque connexion.
- Activité 3-2 : plusieurs clients peuvent demander des calculs au même serveur.

## Lancement

Dans PowerShell, depuis `TP3-DAR2026` :

```powershell
New-Item -ItemType Directory -Force bin | Out-Null
javac -encoding UTF-8 -d bin (Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object FullName)
```

Ouvrir deux terminaux dans ce dossier. Lancer le serveur, puis un ou plusieurs clients :

```powershell
java -cp bin activite31.Server
java -cp bin activite31.Client localhost 1234
```

Pour l'activité 3-2, lancer `activite32.Server`, puis `activite32.Client localhost 1235`.

## Captures

![Serveur TCP avec les clients connectés](docs/screenshots/activite31-serveur.png)

![Connexion de trois clients](docs/screenshots/activite31-multiclients.png)

![Test de connexion Telnet](docs/screenshots/activite31-telnet.png)

![Tests de la calculatrice](docs/screenshots/activite32-calculatrice.png)

![Compteur global synchronisé](docs/screenshots/activite32-compteur.png)
