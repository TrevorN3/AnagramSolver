//Trevor Nichols
//AnagramSolver
//Section: AM; William Ceriale

//Given a dictionary creates an instance of AnagramSolver that can find all the
//combinations of words in the dictionary that are anagrams for a given
//statement. 
import java.util.*;

public class AnagramSolver {
    private List<String> currentDictionary;
    private Map<String, LetterInventory> dictionaryCounts;
    
    //Creates a new instance of anagrams for a given dictionary which will be
    //used to determine which words for a given text statement are anagrams.
    public AnagramSolver(List<String> dictionary){
        this.currentDictionary = dictionary;
        this.dictionaryCounts = new HashMap<String, LetterInventory>();
        for(String word : dictionary){           
            this.dictionaryCounts.put(word, new LetterInventory(word));
        }
    }
    
    //Given a text statement and a max number of anagrams; finds all 
    //possible anagrams for the statement and prints them out in the order
    //that they appear in the original dictionary. If zero is passed as the
    //max it will print out an unlimited number of words.
    //throws IllegalArgumentException if max is less than zero.    
   
    //Example: Phrase: wesley clark, max: 2.
    //[creaky, swell]
    public void print(String text, int max){
        if(max < 0){
            throw new IllegalArgumentException
                ("Max needs to be greater than or equal to zero");
        }
        LetterInventory currentPhraseCounts = new LetterInventory(text);
        
        //prunes the original dictionary down to relevant words before
        //starting the recursive process.
        List<String> relevantDictionary = this.prune(currentPhraseCounts, 
                                                    this.currentDictionary);
        this.print(currentPhraseCounts, relevantDictionary,
                                                    new Stack<String>(), max);
    }       
    
    //Explores all possible combinations of anagrams given a LetterInventory,
    //a list containing words from a dictionary, a stack of words, and an int
    //for the max number of words.
    private void print(LetterInventory originalTextCounts,     
                             List<String> relevantDictionary, 
                                     Stack<String> chosenWords, int max){
        if(originalTextCounts.isEmpty()){   
            System.out.println(chosenWords);   
        }else if (chosenWords.size() < max || max == 0){  
            for(String word : relevantDictionary){
                chosenWords.push(word); 
       
                //Creates an updated LetterInventory for the next 
                //recursive call based on chosen word .
                LetterInventory tempCounts = originalTextCounts.subtract
                                   (this.dictionaryCounts.get(word));
       
                //prunes the dictionary down to words relative to the new 
                //LetterInventory for the next recursive call
                List<String> tempDict = this.prune
                                   (tempCounts, relevantDictionary);               
                this.print(tempCounts, tempDict, chosenWords, max);
                chosenWords.pop();    
            }
        }     
    }    

    //Reduces the dictionary's words based on the current phrase. Given a 
    //LetterInventory and a list of dictionary words.
    private List<String> prune(LetterInventory currentPhraseCounts,
                                                List<String> dictionary){     
        List<String> relevantDictionary = new ArrayList<String>();
        LetterInventory tempCheck = null;  
        for(String word : dictionary){
            tempCheck = currentPhraseCounts.subtract
                                    (this.dictionaryCounts.get(word));
            if(tempCheck != null){
                relevantDictionary.add(word);
            }
        }              
        return relevantDictionary;
    }
}
