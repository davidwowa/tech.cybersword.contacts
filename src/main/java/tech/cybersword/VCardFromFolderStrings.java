package tech.cybersword;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.parameter.ImageType;
import ezvcard.property.Email;
import ezvcard.property.Photo;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;

public class VCardFromFolderStrings {
    public static void main(String[] args) throws Exception {
        String folderPath = "/Users/david/git/tech.cybersword.payloads";
        int anzahlKontakte = 10000;

        Set<String> uniqueStrings = new HashSet<>();

        Files.walk(Paths.get(folderPath))
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        List<String> lines = Files.readAllLines(path);
                        uniqueStrings.addAll(lines);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        System.out.println("Gesammelte Strings: " + uniqueStrings.size());

        List<String> pool = new ArrayList<>(uniqueStrings);
        Random random = new Random();

        List<VCard> vcards = new ArrayList<>();

        for (int i = 0; i < anzahlKontakte; i++) {
            String fn = randomFrom(pool, random);
            String ln = randomFrom(pool, random);
            String fullName = fn + " " + ln;
            String email = fn.toLowerCase() + "." + ln.toLowerCase() + "@example.org";
            String phone = "+49 " + (100 + random.nextInt(900)) + " " + (1000000 + random.nextInt(9000000));

            VCard vcard = new VCard();
            StructuredName name = new StructuredName();
            name.setGiven(fn);
            name.setFamily(ln);
            vcard.setStructuredName(name);
            vcard.setFormattedName(fullName);
            vcard.addEmail(new Email(email));
            vcard.addTelephoneNumber(new Telephone(phone));

            byte[] fakeImage = new byte[256];
            random.nextBytes(fakeImage);
            Photo photo = new Photo(fakeImage, ImageType.JPEG);
            vcard.addPhoto(photo);

            vcards.add(vcard);
        }

        File outFile = new File("contacts-with-payloads_2.vcf");
        try (OutputStream out = new FileOutputStream(outFile)) {
            Ezvcard.write(vcards).version(ezvcard.VCardVersion.V4_0).go(out);
        }

        System.out.println("✅ Datei erstellt: " + outFile.getAbsolutePath());
    }

    private static String randomFrom(List<String> list, Random random) {
        // return list.get(random.nextInt(list.size())).trim().replaceAll("[^a-zA-Z0-9]", "");
        return list.get(random.nextInt(list.size()));
    }
}