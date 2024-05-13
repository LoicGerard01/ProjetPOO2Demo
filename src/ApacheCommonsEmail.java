import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApacheCommonsEmail {
    public static void main(String[] args) {

        String configFile = "config.properties";
        Properties props = new Properties();
        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier de configuration : " + e.getMessage());
            return;
        }

        String host = "smtp.office365.com";
        int port = 587;
        String userName = props.getProperty("username");
        String password = props.getProperty("password");

        try {
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(port);
            email.setAuthenticator(new DefaultAuthenticator(userName, password));
            email.setStartTLSEnabled(true); // Utilisation de TLS
            email.setFrom("loicgerardtest@outlook.com");
            email.setSubject("Test d'envoi d'e-mail avec Apache Commons Email");
            email.setMsg("Ceci est un e-mail de test envoyé avec Apache Commons Email");
            email.addTo("loicgerardtest@outlook.com");

            email.send();
            System.out.println("E-mail envoyé avec succès !");
        } catch (EmailException e) {
            System.out.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
}
