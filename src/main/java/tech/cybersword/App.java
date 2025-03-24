package tech.cybersword;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import ezvcard.Ezvcard;
import ezvcard.VCard;

public class App {
    public static void main(String[] args) {
        VCardGenerator generator = new VCardGenerator();
        saveDataIntoFile(generator.generateRandomVCard(1000, 10000), "random-contacts.vcf");
    }

    public static void saveDataIntoFile(List<VCard> cards, String filename) {

        try {
            File outputFile = new File(filename);
            try (OutputStream out = new FileOutputStream(outputFile)) {
                Ezvcard.write(cards).version(ezvcard.VCardVersion.V4_0).go(out);
            }

            System.out.println("✅ " + cards.size() + " vCards saved to: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("❌ Error saving vCards: " + e.getMessage());
        }
    }
}
