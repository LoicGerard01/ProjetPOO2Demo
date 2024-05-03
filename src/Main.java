import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        // Paramètres SMTP pour Outlook
        String smtpHost = "smtp.office365.com";
        String smtpPort = "587"; // Port SMTP (587 avec TLS)

        // Paramètres IMAP pour Outlook
        String imapHost = "outlook.office365.com";
        String imapPort = "993"; // Port IMAP (993 avec SSL)

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

        // Propriétés pour configurer la session IMAP
        Properties imapProps = new Properties();
        imapProps.put("mail.imap.host", imapHost);
        imapProps.put("mail.imap.port", imapPort);
        imapProps.put("mail.imap.ssl.enable", "true");

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

            // Créer une session IMAP pour récupérer les emails
            Store imapStore = Session.getInstance(imapProps).getStore("imaps");
            imapStore.connect(imapHost, username, password);

            Folder inbox = imapStore.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            // Récupérer les 10 derniers messages
            int messageCount = inbox.getMessageCount();
            Message[] messages = inbox.getMessages(Math.max(1, messageCount - 9), messageCount);
            System.out.println("---- Emails récents ----");
            for (Message msg : messages) {
                System.out.println("De: " + InternetAddress.toString(msg.getFrom()));
                System.out.println("Sujet: " + msg.getSubject());
                System.out.println("Date de réception: " + msg.getReceivedDate());
                System.out.println("-----------------------");
            }

            inbox.close(false);
            imapStore.close();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
