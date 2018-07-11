import java.util.*;
import java.util.regex.Pattern;

/**
* Joined up writing class.
* @author Joseph McManamon
* @author Jayde Medder
*/
public class Joined{
	
	private static ArrayList<Word> dictionarySingles = new ArrayList<Word>();
	private static ArrayList<Word> dictionaryDoubles = new ArrayList<Word>();
	//private static ArrayList<Word> connections = new ArrayList<Word>();
	private boolean flag1 = false;
	private boolean flag2 = false;

	private static Word start;
	private static Word end;


	public static void main(String[] args){
		/*Checks we have the correct number of arguments. */
		if (args.length != 2){
			System.exit(0);
		}
		start = new Word(args[0]);
		end = new Word(args[1]);
		//System.out.println("Link " + args[0] + " to " + args[1]);		

		/* Scans in dictionary. Potentially will upgrade to hash at some point. */
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextLine()){
			String s = sc.nextLine();
			Word w1 = new Word(s);
			Word w2 = new Word(s);
			dictionarySingles.add(w1);
			dictionaryDoubles.add(w2);						
		}
		singly();
		doubly();
	}


	/**
	* Checks the start and end aren't already linked. Then calls a breadfirstsearch,
	* printing out the results if a connection is found.  
	*/
	private static void singly(){
		if(start.isSinglyLinked(start, end)){
			System.out.print("2 " + start + " " + end);
			System.out.println();
			return;			
		}
		if(searchSingles(start, end)){
			print();
		} else{
			System.out.println("0");
		}
		/* Reset some things so doubly search can take place. */
		start.visited = false;
		end.visited = false;
		start.previousWord = null;
		end.previousWord = null;		
	}

	/**
	* Checks the start and end aren't already linked. Then calls a breadfirstsearch,
	* printing out the results if a connection is found.  
	*/
	private static void doubly(){
		if(start.isDoublyLinked(start, end)){
			System.out.print("2 " + start + " " + end);
			System.out.println();
			return;			
		}		
		
		if(searchDoubles(start, end)){	
			print();
		} else{
			System.out.println("0");
		}

	}	

	/**
	* Bread first search. Only difference between this and the doubly one is they
	* use a different comparison function in the Word class.
	* @param word to start with. 
	* @param end word
	*/
	private static boolean searchSingles(Word word, Word end){
	    //Comparator<Word> comparator = new QueueComparator();
		//PriorityQueue<Word> pQueue = new PriorityQueue<Word>(comparator);
		ArrayDeque<Word> queue = new ArrayDeque<Word>();
		//PriorityQueue queue = new PriorityQueue();
		queue.add(word);


		do {
	    	Word curr = queue.remove();
	    	if(curr.equals(end)){
	    		end.previousWord = curr;
	    		return true;
	    	} else if (curr.isSinglyLinked(curr, end)){	
				//System.out.println("Found singly target");
				end.previousWord = curr;
				return true;
			} else {
	   				for(Word w : dictionarySingles){
						curr.isSinglyLinked(curr, w);
					}
					for(Word connects : curr.next){
						if(!(connects.visited)){
							connects.visited = true;
							connects.previousWord = curr;		
							queue.add(connects);
						}		
					}
				}
			} while(!queue.isEmpty());
			return false;	
	}

	/**
	* Bread first search. Only difference between this and the singly one is they
	* use a different comparison function in the Word class.
	* @param word to start with. 
	* @param end word
	*/
	private static boolean searchDoubles(Word word, Word end){
		ArrayDeque<Word> queue = new ArrayDeque<Word>();
		ArrayDeque<Word> stack = new ArrayDeque<Word>();
		Word curr = null;
		queue.add(word);
		//queue.push(word);

		//Stack<Word> queue = new Stack<Word>();

		do {
			if(curr != null){
				if(queue.peek().next.size() >0){
				//	System.out.println("yo");
				}
			}

	    	curr = queue.remove();
	    	//Word curr = stack.pop();

		    if(curr.equals(end)){
				end.previousWord = curr;
	    		return true;
	    	} else if (curr.isDoublyLinked(curr, end)){		
				//System.out.println("Found doubly target");
				end.previousWord = curr;
				return true;
			} else {	
	   				for(Word w : dictionaryDoubles){
						curr.isDoublyLinked(curr, w);
					}
					for(Word connects : curr.next){
						if(!(connects.visited)){
							connects.visited = true;
							connects.previousWord = curr;		
							queue.add(connects);
						//	if(curr.next.size)
						}
					}
				}


			} while(!queue.isEmpty());

			return false;	
	}	


	public static void print(){
		ArrayList<String> connections = new ArrayList<String>();
		try {
		connections = getConnections(start, end);		
		} catch (Exception e){
			System.out.println("0");
			return;

		}
		int count = connections.size() + 2;
		System.out.print(count + " " + start + " ");
		for(int i = connections.size()-1; i >= 0; i--){
			System.out.print(connections.get(i) + " ");
		}
		System.out.print(end);
		System.out.println();
		
	}	

    public static ArrayList<String> getConnections(Word firstWord, Word end) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		Word currentWord = end;
		int broken = 0;
		
			do {
				list.add(currentWord.previousWord.toString());
				if(currentWord.previousWord == end){
					broken++;
					if(broken == 10){
						throw new Exception("Infinite loop");
					}
				}
		    	currentWord = currentWord.previousWord;

		    	
			} while (currentWord.previousWord != firstWord);
		

			return list;	
		
	}	
}