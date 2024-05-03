
import java.io.IOException;
import java.util.Properties;
import javax.mail.*;


public class Exchange {
    public static void main(String[] args) throws MessagingException, IOException {
        Properties props = new Properties();
        props.setProperty("mail.imap.ssl.enable", "true"); // Utilisez SSL pour se connecter
        props.setProperty("mail.imap.auth.mechanisms", "NTLM"); // Mécanisme d'authentification NTLM
        props.setProperty("mail.imap.auth.ntlm.domain", "outlook.office365.com"); // Remplacez DOMAIN par votre domaine
        props.setProperty("mail.imap.auth.ntlm.host", "smtp.office365.com"); // Remplacez EXCHANGE_HOST par l'hôte Exchange

        Session session = Session.getInstance(props);


        Store store = session.getStore("imap");
        store.connect("EXCHANGE_HOST", "USERNAME", "PASSWORD"); // Remplacez USERNAME et PASSWORD par vos identifiants

        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_ONLY);

        Message[] messages = inbox.getMessages();
        for (Message message : messages) {
            System.out.println("Subject: " + message.getSubject());
            System.out.println("From: " + message.getFrom()[0]);
            // Lire le contenu du message
            System.out.println("Content: " + message.getContent());
        }
        inbox.close(false);
        store.close();
    }
}
