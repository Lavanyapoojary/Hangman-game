import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class WordDB {
    // will contain key -> category, value -> WORDS
    private HashMap<String, String[]> wordList;
    private ArrayList<String> categories;

    public WordDB() {
        wordList = new HashMap<>();
        categories = new ArrayList<>();

        try {
            // Safely get resource URL
            URL resourceURL = getClass().getClassLoader().getResource(CommonConstants.DATA_PATH);
            if (resourceURL == null) {
                throw new IOException("Could not find resource: " + CommonConstants.DATA_PATH);
            }

            // Get path and handle spaces
            String filePath = resourceURL.getPath().replaceAll("%20", " ");

            // Read the file
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 2) continue; // Skip lines without words

                String category = parts[0].trim();
                String[] values = Arrays.copyOfRange(parts, 1, parts.length);

                categories.add(category);
                wordList.put(category, values);
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error loading word data: " + e.getMessage());
        }
    }

    public String[] loadChallenge() {
        if (categories.isEmpty()) {
            return new String[]{"NO_CATEGORY", "NO_WORD"};
        }

        Random rand = new Random();
        String category = categories.get(rand.nextInt(categories.size()));
        String[] categoryWords = wordList.get(category);

        if (categoryWords == null || categoryWords.length == 0) {
            return new String[]{category.toUpperCase(), "NO_WORD"};
        }

        String word = categoryWords[rand.nextInt(categoryWords.length)];
        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }
}
