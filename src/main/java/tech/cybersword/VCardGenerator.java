package tech.cybersword;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.property.Tel;
import net.fortuna.ical4j.vcard.Entity;
import net.fortuna.ical4j.vcard.VCard;
import net.fortuna.ical4j.vcard.property.BDay;
import net.fortuna.ical4j.vcard.property.Fn;
import net.fortuna.ical4j.vcard.property.Note;
import net.fortuna.ical4j.vcard.property.Org;
import net.fortuna.ical4j.vcard.property.Title;

public class VCardGenerator {
    private final String[] FIRST_NAMES = { "Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace",
            "Hannah" };
    private final String[] LAST_NAMES = { "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller" };
    private final String[] DOMAINS = { "example.com", "mail.com", "test.org", "demo.net" };
    private final String[] COUNTRIES = { "Germany", "USA", "France", "UK", "Canada", "Italy" };
    private final String[] SOCIAL_MEDIA = { "twitter", "linkedin", "facebook" };

    public VCard generateRandomVCard() {
        Random RANDOM = new Random();

        String firstName = getRandom(FIRST_NAMES);
        String lastName = getRandom(LAST_NAMES);
        String fullName = firstName + " " + lastName;
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + getRandom(DOMAINS);
        String phone = "+49 " + (1000 + RANDOM.nextInt(9000)) + " " + (100000 + RANDOM.nextInt(900000));
        String street = (RANDOM.nextInt(99) + 1) + " " + lastName + " Street";
        String city = "Berlin";
        String country = getRandom(COUNTRIES);
        String birthday = String.format("%04d-%02d-%02d", 1950 + RANDOM.nextInt(50), 1 + RANDOM.nextInt(12),
                1 + RANDOM.nextInt(28));
        String social = "https://" + getRandom(SOCIAL_MEDIA) + ".com/" + firstName.toLowerCase()
                + lastName.toLowerCase();

        VCard vcard = new VCard();

        List<Property> properties = new ArrayList<>();
        properties.add(new Fn(fullName));
        properties.add(new Tel(phone));
        properties.add(new BDay(birthday));
        properties.add(new Org("Example Corp."));
        properties.add(new Title("Software Engineer"));
        properties.add(new Note("Dies ist eine automatisch generierte vCard für " + fullName + "."));

        Entity e = new Entity();
        e.getPropertyList().addAll(properties);

        vcard.add(e);

        return vcard;
    }

    private static String getRandom(String[] array) {
        Random RANDOM = new Random();
        return array[RANDOM.nextInt(array.length)];
    }
}
