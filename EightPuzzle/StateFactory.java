import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * State factory class is used to generate different states for 8 puzzle problem
 * 
 */
public class StateFactory 
{
	private final Random rng;//Random Number Generator
	
	/**
	 * Initializes StateFactory class
	 * 
	 * @param seed is the starting seed of the random number generator 
	 * 	that is used for random operations off this class.
	 * 
	 */
	public StateFactory(long seed)
	{
		rng = new Random(seed);
	}
	
	/**
	 * Initializes StateFactory class
	 * Randomly selects a seed for the random number generator
	 * 
	 */
	public StateFactory()
	{
		rng = new Random();
	}
	
	/**
	 * When called, it creates a new solvable random State(board) for 8 puzzle problem.
	 * @return Solvable state.
	 */
	public State getRandomState(){
		
		byte[] board = {1,2,3,4,5,6,7,8,9};
		do
			shuffle(board);//Like a boss
		while(!isSolvable(board));
		
		return new State(board);
	}
	
	/**
	 * Checks whether given board is solvable.
	 * -> This method is inherited from; Solvability of the Tiles Game by Mark Ryan
	 * http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html  
	 * 
	 * @param board Location of the pieces in board
	 * @return True if puzzle is solvable using valid moves
	 */
	private boolean isSolvable(byte[] board)
	{
		byte inversionVariable = 0;
		
		for (int i=0;i<board.length;i++)
			if (board[i]!=State.EMPTY)
				for(int k=i+1;k<board.length;k++)
					if(board[k]!=State.EMPTY && board[k]>board[i])
						++inversionVariable;
		
		return inversionVariable%2==0;
	}
	
	/**
	 * Shuffles an array.
	 * @param array
	 */
	private  void shuffle (byte [] array){
		byte n = (byte) array.length;
		for (int i = 0; i < array.length; i++) {
		    // Get a random index of the array past i.
			byte random = (byte) (i + rng.nextInt(n - i));
		    // Swap the random element with the present element.
		    byte randomElement = array[random];
		    array[random] = array[i];
		    array[i] = randomElement;
		}
	}
	
	/**
	 * Returns Set of random solvable states.
	 *  Since it it returns a set, elements of the set is distinct.
	 * @param size Size of the List that will be returned. 
	 * @return Set that containing distinct puzzle board states
	 */
	public Set<State> getRandomStates(int size)
	{
		//181440 is the max number of valid(solvable) board configurations
		//181440 = 9! / 2;
		if (size > 181440)
		{
			System.out.println("Warning: Size cannot be bigger than max number"
					+ " of distinct states which is 181440. Setting size to max value");
			Thread.dumpStack();
			size = 181440;
		}
		
		Set<State> set = new HashSet<>();
		
		while(set.size()<size)
			set.add(this.getRandomState());
		
		return set;
	}
	
	/**
	 * Returns the solved state
	 * @return State that represent solved puzzle
	 */
	public State getSolvedState()
	{
		return new State();
	}
}
