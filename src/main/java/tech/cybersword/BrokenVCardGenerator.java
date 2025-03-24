package tech.cybersword;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class BrokenVCardGenerator {

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

        List<String> pool = new ArrayList<>(uniqueStrings);
        Random random = new Random();

        File outFile = new File("broken-contacts.vcf");
        try (PrintWriter writer = new PrintWriter(new FileWriter(outFile))) {
            for (int i = 0; i < anzahlKontakte; i++) {
                String fn = randomFrom(pool, random);
                String ln = randomFrom(pool, random);
                String email = fn + "." + ln + "@invalid";
                String phone = "+49-FAIL-" + random.nextInt(999999);

                // Zufällige Strukturverletzung
                int type = random.nextInt(5);
                switch (type) {
                    case 0 -> writer.println("FULLNAMEE:" + fn + " " + ln); // falscher Feldname
                    case 1 -> writer.println("BEGIN:VCARD\nN:;" + fn + "\nEND:VCARD"); // fehlender Nachname
                    case 2 -> writer.println("BEGIN:VCARD\nEMAIL;type=🔥:" + email + "\nFN:\nEND:VCARD"); // Emoji-Typ +
                                                                                                          // leeres FN
                    case 3 -> writer.println("BEGIN:VCARD\nHACKED:" + randomHex(20) + "\nTEL:" + phone + "\nEND:VCARD"); // unerlaubtes
                                                                                                                         // Feld
                    case 4 -> writer.println(
                            "BEGIN:VCARD\nFN:" + fn + " " + ln + "\nPHOTO:BASE64," + randomHex(100) + "\nEND:VCARD"); // ungültiges
                                                                                                                      // PHOTO
                }

                writer.println(); // Leerzeile zwischen den vCards
            }
        }

        System.out.println("😈 Datei mit absichtlich beschädigten vCards gespeichert unter:");
        System.out.println("📂 " + outFile.getAbsolutePath());
    }

    private static String randomFrom(List<String> list, Random r) {
        return list.get(r.nextInt(list.size())).replaceAll("[^a-zA-Z0-9]", "").trim();
    }

    private static String randomHex(int len) {
        String chars = "0123456789ABCDEF";
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
