import java.util.*;
import java.util.regex.Pattern;

/**
* Word class. Probably should move the extra functions into the main class at some point.
* @author Joseph McManamon
* @author Jayde Medder
*/
public class Word{
	private String word;
    Word previousWord = null;
    boolean visited;
	ArrayList<Word> next = new ArrayList<Word>();

	public Word(String word){
		this.word = word;
	}
	public String toString(){
		return word;
	}
	
	/**
	 * Asseses if a word from the dictionary is singly linked. If so, add it to the
	 * objects list of connections.
	 * @param current word.
	 * @param nextWord in the dictionary.
	 * @return true if there is a link
	 */
	public boolean isSinglyLinked(Word word, Word nextWord){
		String curr = word.toString();
		String next = nextWord.toString();
		int halfCurr = 0;
		int halfNext = 0;

		if(curr.length() % 2 == 0)
			halfCurr = curr.length()/2;
		else halfCurr = (curr.length()/2) + 1;

		if(next.length() % 2 == 0)
			halfNext = next.length()/2;
		else halfNext = (next.length()/2) + 1;
		///System.err.println(curr + " : " + next);
		//System.err.println(halfCurr + " : " + halfNext);

		for(int i = 0; i < curr.length(); i++){
			if(next.length() >= curr.length()-i){
				String substringCurr = curr.substring(i);
				String substringNext = next.substring(0, curr.length()-i);
				if(substringCurr.equals(substringNext)){
					if(substringCurr.length() >= halfCurr || (substringCurr.length() >= halfNext)){
						word.next.add(nextWord);
						return true; 
					}
				}
			}
		}				

		return false;
	}
	/**
	 * Asseses if a word from the dictionary is doubly linked. If so, add it to the
	 * objects list of connections.
	 * @param current word.
	 * @param nextWord in the dictionary.
	 * @return true if there is a link
	 */
	public boolean isDoublyLinked(Word word, Word nextWord){
		//System.out.println(this.word);
		String curr = word.toString();
		String next = nextWord.toString();
		int halfCurr = 0;
		int halfNext = 0;

		if(curr.length() % 2 == 0)
			halfCurr = curr.length()/2;
		else halfCurr = (curr.length()/2) + 1;

		if(next.length() % 2 == 0)
			halfNext = next.length()/2;
		else halfNext = (next.length()/2) + 1;

		//System.out.println(halfCurr);
		//System.out.println(halfNext);
		for(int i = 0; i < curr.length(); i++){
			if(next.length() >= curr.length()-i){
				String substringCurr = curr.substring(i);
				String subStringNext = next.substring(0, curr.length()-i);
				if(substringCurr.equals(subStringNext)){
					if(substringCurr.length() >= halfCurr && subStringNext.length() >= halfNext){
						word.next.add(nextWord);
						return true; 
					}
				}
			}
		}
		return false;
	}

	/**
	 * Comparison method. 90% confident it goes unused in all cases.
	 */
	public boolean equals(Word end){
		if(this.word.toString().equals(end.toString())){
			return true;
		}
		return false;
	}


}