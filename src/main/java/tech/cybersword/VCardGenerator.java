package tech.cybersword;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ezvcard.VCard;
import ezvcard.parameter.ImageType;
import ezvcard.property.Email;
import ezvcard.property.Photo;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;

public class VCardGenerator {

    static final String[] FIRST_NAMES = { "Alice", "Bob", "Charlie", "Diana", "Eva", "Frank", "Grace" };
    static final String[] LAST_NAMES = { "Müller", "Meier", "Schmidt", "Fischer", "Weber", "Becker", "Hoffmann" };
    static final String[] DOMAINS = { "example.com", "test.org", "mail.net", ""};

    public List<VCard> generateRandomVCard(int size, int picSize) {
        List<VCard> cards = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String firstName = randomFrom(FIRST_NAMES);
            String lastName = randomFrom(LAST_NAMES);
            String fullName = firstName + " " + lastName;

            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + randomFrom(DOMAINS);
            String phone = "+49 " + (100 + randomInt(900)) + " " + (1000000 + randomInt(8999999));

            VCard vcard = new VCard();

            StructuredName n = new StructuredName();
            n.setGiven(firstName);
            n.setFamily(lastName);
            vcard.setStructuredName(n);
            vcard.setFormattedName(fullName);
            vcard.addEmail(new Email(email));
            vcard.addTelephoneNumber(new Telephone(phone));

            byte[] fakeImage = new byte[picSize];
            new Random().nextBytes(fakeImage);
            Photo photo = new Photo(fakeImage, ImageType.JPEG);
            vcard.addPhoto(photo);

            cards.add(vcard);
        }

        return cards;
    }

    static String randomFrom(String[] array) {
        return array[new Random().nextInt(array.length)];
    }

    static int randomInt(int max) {
        return new Random().nextInt(max);
    }
}
