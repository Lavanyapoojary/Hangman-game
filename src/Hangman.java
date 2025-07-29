import java.util.*;
public class Hangman {
    private static final String[] WORDS = {
            "developer", "hangman", "java", "keyboard", "interface", "terminal", "github", "function"
    };
    private static final int MAX_TRIES = 6;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    private String selectedWord;
    private Set<Character> guessedLetters = new HashSet<>();
    private int remainingTries = MAX_TRIES;

    public Hangman() {
        Random rand = new Random();
        selectedWord = WORDS[rand.nextInt(WORDS.length)];
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(ANSI_CYAN + "\n=== Welcome to Hangman! ===" + ANSI_RESET);

        while (remainingTries > 0 && !isWordGuessed()) {
            printWordProgress();
            System.out.print(ANSI_YELLOW + "Guess a letter: " + ANSI_RESET);
            String input = scanner.nextLine().toLowerCase();

            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                System.out.println(ANSI_RED + "Please enter a valid single letter." + ANSI_RESET);
                continue;
            }

            char guess = input.charAt(0);

            if (guessedLetters.contains(guess)) {
                System.out.println(ANSI_RED + "You already guessed that letter!" + ANSI_RESET);
                continue;
            }

            guessedLetters.add(guess);

            if (selectedWord.contains(String.valueOf(guess))) {
                System.out.println(ANSI_GREEN + "Correct!" + ANSI_RESET);
            } else {
                remainingTries--;
                System.out.println(ANSI_RED + "Wrong! Tries left: " + remainingTries + ANSI_RESET);
            }
        }

        if (isWordGuessed()) {
            System.out.println(ANSI_GREEN + "\n🎉 Congratulations! You guessed the word: " + selectedWord + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "\n😢 Game Over! The word was: " + selectedWord + ANSI_RESET);
        }
    }

    private void printWordProgress() {
        StringBuilder sb = new StringBuilder();
        for (char c : selectedWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                sb.append(c).append(' ');
            } else {
                sb.append("_ ");
            }
        }
        System.out.println("\nWord: " + sb.toString().trim());
        System.out.println("Guessed letters: " + guessedLetters);
    }

    private boolean isWordGuessed() {
        for (char c : selectedWord.toCharArray()) {
            if (!guessedLetters.contains(c)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Hangman game = new Hangman();
        game.play();
    }
}
