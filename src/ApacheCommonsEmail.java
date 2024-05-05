import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class ApacheCommonsEmail {
    public static void main(String[] args) {

        String host = "smtp.office365.com";
        int port = 587;
        String userName = "";
        String password = "";

        try {
            Email email = new SimpleEmail();
            email.setHostName(host);
            email.setSmtpPort(port);
            email.setAuthenticator(new DefaultAuthenticator(userName, password));
            email.setStartTLSEnabled(true); // Utilisation de TLS
            email.setFrom("");
            email.setSubject("Test d'envoi d'e-mail avec Apache Commons Email");
            email.setMsg("Ceci est un e-mail de test envoyé avec Apache Commons Email");
            email.addTo("");

            email.send();
            System.out.println("E-mail envoyé avec succès !");
        } catch (EmailException e) {
            System.out.println("Erreur lors de l'envoi de l'e-mail : " + e.getMessage());
        }
    }
}
