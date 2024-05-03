import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

public class Smtp_POP {
    public static void main(String[] args) {
        // Paramètres SMTP pour Outlook
        String smtpHost = "smtp.office365.com";
        String smtpPort = "587"; // Port SMTP (587 avec TLS)

        // Paramètres POP3 pour Outlook
        String pop3Host = "outlook.office365.com";
        String pop3Port = "995"; // Port POP3 (995 avec SSL)

        // Identifiants de compte Outlook
        String username = "";
        String password = "";
        // Destinataire de l'email
        String to = "";

        // Propriétés pour configurer la session SMTP
        Properties smtpProps = new Properties();
        smtpProps.put("mail.smtp.host", smtpHost);
        smtpProps.put("mail.smtp.port", smtpPort);
        smtpProps.put("mail.smtp.auth", "true");
        smtpProps.put("mail.smtp.starttls.enable", "true");

        // Propriétés pour configurer la session POP3
        Properties pop3Props = new Properties();
        pop3Props.put("mail.pop3.host", pop3Host);
        pop3Props.put("mail.pop3.port", pop3Port);
        pop3Props.put("mail.pop3.ssl.enable", "true");

        try {
            // Créer une session SMTP avec authentification
            Session smtpSession = Session.getInstance(smtpProps, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            // Envoyer un email
            MimeMessage message = new MimeMessage(smtpSession);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Ceci est un test");
            message.setText("Bonjour,\n\nCe mail est envoyé depuis l'API JavaMail.");

            Transport.send(message);
            System.out.println("Mail envoyé avec succès.");

            // Créer une session POP3 pour récupérer les emails
            Store pop3Store = Session.getInstance(pop3Props).getStore("pop3s");
            pop3Store.connect(pop3Host, username, password);

            Folder inbox = pop3Store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Récupérer les 10 derniers messages
            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages(Math.max(1, messageCount - 9), messageCount);
            System.out.println("---- Emails récents ----");
            for (Message msg : messages) {
                System.out.println("De: " + InternetAddress.toString(msg.getFrom()));
                System.out.println("Sujet: " + msg.getSubject());
                System.out.println("Date de réception: " + msg.getReceivedDate());
                System.out.println("Contenu : "+msg.getContent());
                System.out.println("-----------------------");
            }

            inbox.close(false);
            pop3Store.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
