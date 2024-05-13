import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EmailReader {
    public static void main(String[] args) {

        String configFile = "config.properties";
        Properties props = new Properties();
        try (FileInputStream inputStream = new FileInputStream(configFile)) {
            props.load(inputStream);
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier de configuration : " + e.getMessage());
            return;
        }

        String host = "outlook.office365.com";
        String userName = props.getProperty("username");
        String password = props.getProperty("password");

        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imap");
        properties.setProperty("mail.imap.host", host);
        properties.setProperty("mail.imap.port", "993");
        properties.setProperty("mail.imap.ssl.enable", "true");

        try {
            Session session = Session.getInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password);
                }
            });

            Store store = session.getStore("imap");
            store.connect(host, userName, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            System.out.println("Nombre de messages : " + messages.length);

            for (Message message : messages) {
                System.out.println("Sujet : " + message.getSubject());
                System.out.println("De : " + message.getFrom()[0]);
                System.out.println("Contenu : " + message.getContent().toString());
            }

            inbox.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
