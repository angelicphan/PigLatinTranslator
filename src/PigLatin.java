/**
 * Created by Angelic Phan on 7/17/2020.
 * This is a Pig Latin translator. It will translate input text into/from Pig Latin.
 * It will follow the following conventions based off of the rules found on Wikipedia:
 * https://en.wikipedia.org/wiki/Pig_Latin.
 * The general rules are as follows:
 * 1. Words beginning with a consonant or a consonant cluster (all letters before the initial
 *    vowel) are placed at the end of the word preceded by a "-' and ending with an "ay" at the end.
 * 2. Words beginning with a vowel are left alone and a "-ay" is added to the end.
 * The different ways to run the program:
 * 1. Translate from file: the program is run through command line passing in the following arguments:
 *    - source: This is the source file that should be translated. It should always be a .txt file
 *    - dest: This is the new file that the translated contents should be translated to. It will be
 *      saved as a .txt file.
 *    - action: "encode" into piglatin or "decode" piglatin.
 * 2. Manually entered: the program is run through command line with one argument:
 *    - action: "encode" into piglatin or "decode" piglatin.
 *    Then the user will be prompted to enter the desired text block they want to be translated.
 * 3. No arguments passed in: this will only output to the user how to run the program.
 *
 * List of exit codes:
 * - 1 : Successful exit
 * - 2 : command line argument errors
 * - 3 : file I/O errors
 **/
import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class PigLatin {

    // Main Function will translate an input text to/from Pig Latin.
    public static void main(String[] args){

        // Declarations
        Character vowelList[] = {'a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'};  // Keeps track of all possible vowels
        Set<Character> vowels = new HashSet<>(Arrays.asList(vowelList));

        // Check if missing command line args
        if (args.length == 0) {         // The user doesn't know how to run the program
            Rules();                    // Print out the rules and exit the program
            System.exit(1);
        } else if (args.length == 3) {  // The user wants to translate from file: source, dest, action
            translateFromFile(args, vowels);
        } else if (args.length == 1) {  // The user wants to manually translate text: action
            mannualEntry(args, vowels);
        } else {                        // The user entered unsupported amount of arguments
            System.err.println("Invalid number of arguments passed in.");
            System.exit(2);
        }

        // Successful exit
        System.out.println("\nYour input has been successfully translated!");
        System.exit(1);

    }

    // ********************************************** Helper Functions ***********************************************

    /**
     * This function will print out the rules of the program.
     */
    private static void Rules()
    {
        String rules = "This is a Pig Latin translator. It will translate input text into/from Pig Latin.\n\n" +
                "Encode Pig Latin:\n" +
                "1. Words beginning with a consonant or a consonant cluster (all letters before the initial vowel) " +
                "are placed at the end of the word with a \"-\" preceding and an \"ay\" at the very end.\n" +
                "2. Words beginning with a vowel are left alone and a \"-ay\" is added to the end.\n\n" +
                "How to run the program:\n" +
                "1. Translate from file: the program is run through command line passing in the following arguments:\n" +
                "\t- source: This is the source file that should be translated. It should always be a .txt file\n" +
                "\t- dest: This is the new file that the translated contents should be translated to. It will be" +
                "saved as a .txt file.\n" +
                "\t- action: \"encode\" into Pig Latin or \"decode\" Pig Latin.\n" +
                "\t- EX: java PigLatin source.txt dest.txt encode\n" +
                "2. Manually entered: the program is run through command line with one argument:\n" +
                "\t- action: \"encode\" into Pig Latin or \"decode\" Pig Latin.\n" +
                "\t- EX: java PigLatin encode\n" +
                "Then the user will be prompted to enter the desired text block they want to be translated.\n" +
                "3. No arguments passed in: this will only output to the user how to run the program.";
        System.out.println(rules);
    }

    // *****************

    /**
     * This function will check if the user entered a valid action argument. If not, exit with error message
     * @param action the action the user would like to do (valid: "encode" or "decode")
     */
    private static void checkValidActionArgs(String action) {
        // If invalid action argument, exit with error message
        if(!action.equals("encode") && !action.equals("decode")) {
            System.err.println("Invalid action. You can \"encode\" or \"decode\" text.");
            System.exit(2);
        }
    }

    // *****************

    /**
     * Makes sure filename is a .txt file, if not, turn it into one
     * @param filename file to check/convert
     * @return filename as .txt
     */
    private static String convertFilename(String filename)
    {
        if(!filename.endsWith(".txt"))      // Make sure its a .txt file
            filename = filename + ".txt";   // If not, turn it into one
        return filename;
    }

    // *****************

    /**
     * This function will read input text from a source file and then translate into the desired file in the
     * desired language.
     * @param args The program arguments
     * @param vowels List of vowel characters
     */
    private static void translateFromFile(String[] args, Set<Character> vowels)
    {
        checkValidActionArgs(args[2]);       // Check if valid action args
        String text = readFromFile(args[0]); // Read from input text file if so
        if(args[2].equals("encode"))         // User wants to encode file
            text = encode(text, vowels);
        else                                 // Else user wants to decode file
            text = decode(text, vowels);
        writeToFile(args[1], text);          // Write translated text to file
    }

    // *****************

    /**
     * This function will read text to translate from file
     * @param filename filename of file to read from
     * @return Will return the text read from file
     */
    private static String readFromFile(String filename)
    {
        String text = "";

        filename = convertFilename(filename); // Make sure file is .txt
        try {                                 // Try to read from file
            BufferedReader in = new BufferedReader((new FileReader(filename))); // Create new BufferedReader Object
            String line = in.readLine();                                        // Start reading file line by line
            while(line != null) {                                               // Until we reach end of file
                text = text + line + "\n";
                line = in.readLine();
            }
            in.close();                                                         // End action
        } catch(FileNotFoundException fileNotFoundException) {                  // Handle exception errors accordingly
            System.err.println("" + filename + " was not found. Translation failed.");
            System.exit(3);
        } catch(IOException ioException) {
            System.err.println("Unable to read from " + filename + ". Translation failed.");
            System.exit(3);
        }
        return text;    // Return input text read from file
    }

    // *****************

    /**
     * This function will write the translated text to file
     * @param filename filename of file to write to
     * @param text text to write to file
     */
    private static void writeToFile(String filename, String text)
    {
        filename = convertFilename(filename); // Make sure file is .txt
        try {                                 // Try to write to file
            FileWriter fileWriter = new FileWriter(filename); // Create FileWriter Object
            fileWriter.write(text);                           // Write translated text to file
            fileWriter.close();                               // Complete action
        } catch(IOException ioException) {                    // Catch any IO exceptions and exit program if any
            System.err.println("Unable to write to " + filename + ".");
            System.exit(3);
        }
    }

    // *****************

    /**
     * This function will read in input text from the user manually and then encode/decode Pig Latin.
     * @param args The program arguments
     * @param vowels List of vowel characters
     */
    private static void mannualEntry(String[] args, Set<Character> vowels)
    {
        String text = "";

        checkValidActionArgs(args[0]);        // If valid action keyword
        text = getText(args[0]);              // Then proceed to get input text from console
        if(args[0].equals("encode"))          // User wants to encode text
            text = encode(text, vowels);
        else                                  // Else user wants to decode text
            text = decode(text, vowels);
        System.out.println("\nTranslation:"); // Output translation
        System.out.println(text);
    }

    // *****************

    /**
     * This function will read in input text from the user manually and encode/decode the text into Pig Latin.
     * @param action that says to to encode or decode text
     * @return Will return the text that was read in
     */
    private static String getText(String action)
    {
        Scanner input = new Scanner(System.in);        // Utility to read input from command line

        System.out.println("Text to " + action + " (Hit enter to finish):"); // Output prompt
        return input.nextLine();                                             // Return input text
    }

    // *****************

    /**
     * This function will encode input text into Pig Latin
     * @param text
     * @param vowels List of vowel characters
     * @return the encoded text
     */
    private static String encode(String text, Set<Character> vowels)
    {
        String translation = "";                             // Translated text
        String[] sentences = text.split("\n");        // Divide text into sentences
        String[] words;                                      // Keeps track of words in a sentence

        for(String sentence : sentences) {                   // For each sentence in the text
            words = sentence.split(" ");              // Separate sentence into words
            for (String word : words) {                      // For all words in the sentence
                if(!isSequenceOfSymbols(word)) {             // Don't edit if sequence of symbols and numbers only
                    word = encodeWord(word, vowels);         // Encode the word
                }
                translation = translation + word + " ";      // Add encoded word to translated text
            }
            translation = translation + "\n";                // Add newline for next sentence
        }
        return translation;                                  // Return translated text
    }

    // *****************

    /**
     * This function will encode a word into Pig Latin
     * @param word the word to encode
     * @param vowels list of vowels
     * @return the encoded word as a String
     */
    private static String encodeWord(String word, Set<Character> vowels)
    {
        String prefix = "";                    // Beginning consonants of a word
        Word currentWord = new Word();         // Keeps track of the current word as a object of its parts

        currentWord.separateWord(word);                                     // Separate the word into three parts: beginning symbols, core word, and ending symbols
        if (!word.isEmpty() && vowels.contains(word.charAt(0)))             // If first character is a vowel
            currentWord.setWord(currentWord.getWord() + "-ay");             // Then only append "-ay" to the end
        else {                                                              // Otherwise
            prefix = buildNormalPrefix(currentWord.getWord(), vowels);
            // Remove the prefix from the beginning of the word
            currentWord.setWord(currentWord.getWord().replaceFirst(prefix, ""));
            //Alternative to using .replaceFirst() above: currentWord.setWord(word.shaveWord(word, prefix.length(), word.length()));
            // Add prefix to the end of the word preceded by "-", and then add "ay" to the end
            currentWord.setWord(currentWord.getWord() + "-" + prefix + "ay");
        }
        return currentWord.getWholeWord();     // Return the encoded word
    }

    // *****************

    /**
     * This function will take a normal word and determine the prefix that will be moved to the end of the encoded word
     * @param word the normal word excluding leading and ending symbols
     * @param vowels list of vowel characters
     * @return the prefix of the word that should be moved
     */
    private static String buildNormalPrefix(String word, Set<Character> vowels)
    {
        int wordIndex = 0;            // Index of a word
        String prefix = "";           // Beginning consonants of a word

        // While the first characters are not vowels
        while (wordIndex < word.length() && (!vowels.contains(word.charAt(wordIndex)))) {
            prefix = prefix + word.charAt(wordIndex);                   // Keep track of them
            ++wordIndex;                                                // Update word iterator
        }
        // If "y" is the last of many characters in the prefix, remove it from prefix to preserve its original location
        prefix = xyPrefix(prefix);

        return prefix;               // Return prefix
    }
    // *****************

    /**
     * This function will decode input text from Pig Latin
     * @param text
     * @param vowels List of vowel characters
     * @return the decoded text
     */
    private static String decode(String text, Set<Character> vowels)
    {
        String translation = "";                             // Translated text
        String[] sentences = text.split("\n");        // Divide text into sentences
        String[] words;                                      // Keeps track of words in a sentence

        for(String sentence : sentences) {                   // For each sentence in the text
            words = sentence.split(" ");              // Separate sentence into words
            for (String word : words) {                      // For all words in the sentence
                if(!isSequenceOfSymbols(word)) {             // Don't edit if sequence of symbols and numbers only
                    word = decodeWord(word, vowels);         // Decode the word
                }
                translation = translation + word + " ";      // Add decoded word to translated text
            }
            translation = translation + "\n";                // Add newline for the next sentence
        }
        return translation;                                  // Return translated text
    }

    // *****************

    /**
     * This function will decode the Pig Latin word
     * @param word the word in Pig Latin
     * @param vowels list of vowels
     * @return the decoded word as a String
     */
    private static String decodeWord(String word, Set<Character> vowels)
    {
        String prefix = "";                    // Beginning consonants of a word
        Word currentWord = new Word();         // Keeps track of the current word as a object of its parts

        currentWord.separateWord(word);                                              // Separate the word into three parts: beginning symbols, core word, and ending symbols
        if (currentWord.getWord().endsWith("-ay"))                                   // Word end in "-ay"
            currentWord.setWord(currentWord.getWord().replaceFirst("-ay", ""));    // Get rid of "-ay" ending
        else if (currentWord.getWord().endsWith("ay"))                                                // Word ends in "ay"
        {
            prefix = buildPigLatinPrefix(currentWord.getWord(), vowels);                              // Get the prefix of the normal word from Pig Latin word's suffix
            String suffix = "-" + prefix + "ay";                                                      // Suffix to remove is "-" + prefix + "ay"
            currentWord.setWord(currentWord.getWord().replaceFirst(suffix, ""));          // Remove suffix
            currentWord.setWord(prefix + currentWord.getWord());                                      // Put prefix back to beginning and add punctuation back to end
        }

        return currentWord.getWholeWord();        // Return decoded word
    }

    // *****************

    /**
     * This function will take the Pig Latin word and determine the prefix of the normal word based on its suffix.
     * @param word the word in Pig Latin
     * @param vowels list of vowels
     * @return the prefix
     */
    private static String buildPigLatinPrefix(String word, Set<Character> vowels)
    {
        int wordIndex = 0;                     // Index of a word
        String prefix = "";                    // Beginning consonants of a word
        int lastIndex = word.length() - 1;                        // The last index of the word
        if(!word.contains("-"))                                   // If there is no "-" in the word, it is not a Pig-Latin word
            return prefix;
        wordIndex = word.lastIndexOf("-", lastIndex) + 1;    // Find location of "-" for the added prefix and move over to the right one index
        if(word.equals("may"))
            System.out.println(wordIndex);
        // While the first characters are consonants
        while ((wordIndex < word.length()) && (!vowels.contains(word.charAt(wordIndex)))) {
            prefix = prefix + word.charAt(wordIndex);            // Keep track of them
            ++wordIndex;                                         // Update iterator
        }
        return prefix;                                           // Return prefix
    }

    // *****************

    /**
     * This function will check if the word is actually a sequence of symbols and numbers, which should be ignored
     * in the translations.
     * @param word to check
     * @return true if it's a sequence of symbols and numbers, false otherwise
     */
    private static boolean isSequenceOfSymbols(String word)
    {
        //For every character in the word
        for(char character : word.toCharArray()) {
            // If a character is not a symbol or number, return false
            if (!(character >= 33 && character <= 64) && !(character >= 91 && character <= 96) && !(character >= 123 && character <= 126))
                return false;
        }
        return true;    // Otherwise, return true, it is a sequence of symbols and numbers
    }

    // *****************

    /**
     * This will evaluate to see if the prefix of size greater than 1 char has a y as the last character.
     * If so, it will not take that y. This is to preserve pig latin rules. i.e. my is y-may not -myay.
     * @param prefix the prefix whe need to check and modify if needed
     * @return the correct prefix value
     */
    private static String xyPrefix(String prefix)
    {
        int len = prefix.length();            // Length of prefix
        String newPrefix = "";                // New prefix without ending y/Y

        if(len > 1 && (prefix.endsWith("y") || prefix.endsWith("Y"))) {   // If prefix ends with y/Y but is not just the y/Y as a whole
            for(int i = 0; i < (len - 1); ++i)                            // Copy everything except for that ending y/Y char
                newPrefix = newPrefix + prefix.charAt(i);                 // This is to preserve a pig latin case
        }
        else
            return prefix;                                                // Else, return original prefix
        return newPrefix;                                                 // Return the corrected prefix string
    }

    // *****************
}
