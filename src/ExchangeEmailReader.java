import microsoft.exchange.webservices.data.*;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.ItemView;

import javax.mail.Folder.*;
import java.net.URI;

import microsoft.exchange.webservices.data.*;

public class ExchangeEmailReader {
    public static void main(String[] args) throws Exception {
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        service.setUrl(new URI("https://outlook.office365.com/EWS/Exchange.asmx"));
        service.setCredentials(new WebCredentials(" ", " "));

        // Définir la boîte aux lettres à partir de laquelle vous souhaitez récupérer les e-mails
        Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);

        // Récupérer les 10 premiers e-mails de la boîte de réception
        FindItemsResults<Item> emails = service.findItems(inbox.getId(), new ItemView(10));

        for (Item email : emails.getItems()) {
            // Charger les propriétés de l'e-mail avant de lire le corps
            email.load();  // Charger toutes les propriétés de l'e-mail depuis le serveur Exchange

            // Lire le sujet de l'e-mail
            System.out.println("Subject: " + email.getSubject());

            // Lire le corps de l'e-mail
            if (email.getBody() != null) {
                System.out.println("Body: " + email.getBody().toString());
            } else {
                System.out.println("Body is empty or cannot be retrieved.");
            }
        }
    }
}
