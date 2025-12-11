# CircoloSportivoFX

CircoloSportivoFX è un progetto Java basato su **JavaFX** che gestisce le funzioni amministrative e operative di un circolo sportivo: attività, corsi, competizioni, membri e dati amministrativi.

# Importante
Per testare l'applicazione la password degli utenti già inseriti è `password`

## 🧱 Struttura del Progetto

Il progetto segue una tipica organizzazione **Maven + JavaFX**.


## 🎯 Funzionalità Principali

### **Gestione Attività (Activity)**

Permette di creare, modificare e rimuovere attività dal circolo.


### **Gestione Membri (Member)**

Permette di creare, modificare e rimuovere membri e admin dal circolo.

### **Autenticazione e Sicurezza**

Il file `SHA256Hashing.java` implementa l’hashing delle password.

### **Interfaccia Grafica (views + styles)**

La cartella **views** contiene i file FXML.
La cartella **styles** contiene fogli di stile CSS.
La cartella **icons** raccoglie le icone usate nell’applicazione.

## 🚀 Avvio dell’Applicazione

Il file principale è:

```
Launcher.java
```

che avvia il runtime JavaFX e carica le interfacce definite in FXML.

## Simulazione
Durante l'esecuzione dell'applicazione ad ogni funzione di creazione rimozione o modifica verra stampato su un file di output il tipo di operazione effettuata con i relativi dettagli.
Ad ogni esecuzione dell'applicazione il file di output viene sovrascritto.

## 📁 File JSON

Nella cartella **json** sono salvati i dati : attività e membri.


## 📌 Note
* `target/` contiene i risultati della build Maven.
* Volendo il progetto funziona anche creando il jar eseguibile, i file json vengono copiati nella cartella dove e presente il file .jar in questo modo possono essere letti e scritti.



---
