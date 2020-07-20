public class Word {
    protected String startSym;     // The symbols and numbers at the beginning of the word
    protected String word;         // The core of the word
    protected String endSym;       // The symbols and numbers at the end of the word
    protected String wholeWord;    // The whole word including starting/ending symbols and the core word

    // *****************

    /**
     * This is the constructor that initializes all class members to null
     */
    public Word() {
        this.startSym = null;
        this.word = null;
        this.endSym = null;
        wholeWord = null;
    }


    // *****************

    /**
     * @return This function returns the core word of the word
     */
    public String getWord() {return this.word;}

    // *****************

    /**
     * @return This function returns the whole word in its entirety
     */
    public String getWholeWord() {return this.wholeWord;}

    // *****************

    /**
     * This function will change the core word to the one passed into the function, and update the value
     * of the whole word
     * @param word the new value of the core word
     */
    public void setWord(String word) {
        this.word = word;
        setWholeWord();
    }

    // *****************

    /**
     * This function will build the whole word in its entirety
     */
    public void setWholeWord() {this.wholeWord = this.startSym + this.word + this.endSym;}

    // *****************

    /**
     * This function will take in a given word, and separate it into three main parts of the word: the leading
     * symbols and numbers, the core of the word that contains letters, and the ending symbols and numbers.
     * After separating the word into the three parts, the class members will be defined as them accordingly.
     * @param word the word to be separted
     */
    public void separateWord(String word)
    {
        Pair ending;                             // Contains ending symbols and their starting index
        Pair starting;                           // Contains starting symbols and index where word starts

        ending = getEndingSymbols(word);         // Retrieve ending symbols if any
        this.endSym = ending.getKey();           // Save as a class member param
        starting = getLeadingSymbols(word);      // Retrieve ending symbols if any
        this.startSym = starting.getKey();       // Save as a class member param
        this.word = shaveWord(word, starting.getValue(), ending.getValue());     // Temporarily remove beginning/ending symbols
        this.wholeWord = this.startSym + this.word + this.endSym;                // Build the whole word of the class member
    }

    // *****************

    /**
     * This function will retrieve the ending symbols and numbers of the word if any
     * @param word the word to find if there are symbols and numbers at the end
     * @return returns the suffix of symbols and numbers
     */
    public Pair getEndingSymbols(String word) {
        Pair endingAndIndex = new Pair();           // The Pair object that contains the suffix and its starting index
        String ending = "";                         // The suffix of the word
        char[] characters = word.toCharArray();     // The array of characters in the word
        int lastIndex = word.length() - 1;          // The last index of the word

        for(int i = lastIndex; i >= 0; --i) {       // For each character in the word starting at the end
            char character = characters[i];         // Grab it
            // Compare to see if its a symbol or number
            if (!(character >= 33 && character <= 64) && !(character >= 91 && character <= 96) && !(character >= 123 && character <= 126)) {
                endingAndIndex.set(ending, i+1);    // If it is not, save the current built suffix and its starting index in the Pair object
                return endingAndIndex;              // And return that saved pair object
            }
            ending = character + ending;            // Else, add the character to the built suffix
        }
        endingAndIndex.set(ending, 0);              // Save the pair object found with the built suffix and its starting index
        return endingAndIndex;                      // Return the saved pair object
    }

    // *****************

    /**
     * This function will retrieve the leading symbols and numbers of the word if any
     * @param word the word to find if there are symbols and numbers at the start
     * @return returns the prefix of symbols and numbers
     */
    public Pair getLeadingSymbols(String word) {
        Pair startingAndIndex = new Pair();              // The Pair object containing the prefix and the starting index of the core word
        String starting = "";                            // The prefix of the word
        int index = 0;                                   // Starting index of the core of the word

        for(char character : word.toCharArray()) {       // For each character in the word
            // Check if it's a symbol or number
            if (!(character >= 33 && character <= 64) && !(character >= 91 && character <= 96) && !(character >= 123 && character <= 126)) {
                startingAndIndex.set(starting, index);   // If not, save the currently built prefix and the core starting index in the Pair object
                return startingAndIndex;                 // And return the Pair object
            }
            starting = starting + character;             // Else, add each character to the building prefix
            ++index;                                     // Continue to next character
        }
        startingAndIndex.set(starting, index);           // Save the built prefix and core starting index into the Pair object
        return startingAndIndex;                         // And return it
    }

    // *****************

    /**
     * This function will determine the core of the given word, that contains letters, ignoring the prefix and suffix
     * of symbols and numbers
     * @param word the word to take the core word from
     * @param startIndex the starting index of the core word
     * @param endIndex the starting index of the suffix of symbols and numbers
     * @return the core of the word
     */
    public String shaveWord(String word, int startIndex, int endIndex)
    {
        String newWord = "";                           // The core of the word to determine

        for(int i = startIndex; i < endIndex; ++i) {   // For the length of the core of the word
            newWord = newWord + word.charAt(i);        // Copy each character to build the core
        }
        return newWord;                                // Return the core of the word
    }

    // *****************
}
