package tech.cybersword;

import java.io.FileWriter;
import java.io.IOException;

import net.fortuna.ical4j.vcard.VCard;

public class App {
    public static void main(String[] args) {
        int counter = 1000;
        VCardGenerator generator = new VCardGenerator();
        try (FileWriter writer = new FileWriter("contacts.vcf")) {
            for (int i = 0; i < counter; i++) {
                VCard vcard = generator.generateRandomVCard();
                writer.write(vcard.toString());
                writer.write("\n");
            }
            System.out.println(counter + " vCards wurden erfolgreich in 'contacts.vcf' gespeichert.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
