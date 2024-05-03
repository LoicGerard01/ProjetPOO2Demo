import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.Session;

public class Main {
    public static void main(String[] args) {
        // Paramètres SMTP pour Outlook
        String smtpHost = "smtp.office365.com";
        String smtpPort = "587"; // Port SMTP (587 avec TLS)

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

            // Transport.send(message);
            System.out.println("Mail envoyé avec succès.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
