
public class StateFactory {
	public static State getRandomState(){
		byte[] board = {1,2,3,4,5,6,7,8,9};
		do
			shuffle(board);
		while(!isSolvable(board));
		return new State(board);
	}
	/* This method is inherited from; Solvability of the Tiles Game by Mark Ryan
	 * http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html  
	*/
	private static boolean isSolvable(byte[] board){	
		byte inversionVariable = 0;
		
		for (int i=0;i<board.length;i++)
			for(int k=i+1;k<board.length;k++)
				if(board[k]!=State.EMPTY && board[i]!=State.EMPTY && board[k]>board[i])
					++inversionVariable;
		return inversionVariable%2==0;
	}
	private static void shuffle (byte [] array){
		byte n = (byte) array.length;
		for (int i = 0; i < array.length; i++) {
		    // Get a random index of the array past i.
			 byte random = (byte) (i + (Math.random() * (n - i)));
		    // Swap the random element with the present element.
		    byte randomElement = array[random];
		    array[random] = array[i];
		    array[i] = randomElement;
		}
	}
	
}
