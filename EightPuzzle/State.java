import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a configuration of the 8 puzzle
 * 
 *
 */
public class State {
	public static final byte EMPTY = 9; // Id of the empty puzzle piece
	byte emptyIndice = 0; //stores place of the empty puzzle piece
	private byte [] board = new byte [9];//Stores id's of the pieces in board
	
	private int score = -1; //Heuristic value that shows how does it close to the solution
	private int hash = -1; //Hash value of the state
	
	public enum MoveType {UP,DOWN,LEFT,RIGHT}; // Possible moves of the empty puzzle piece
	
	private MoveType lastMove;	//Shows the last move that generated this state.
								//lastMove is not considered when checking whether 2 state is
								//equal.
	
	/**
	 * Initializes a state that is solved.
	 */
	public State()
	{
		for (byte i=0; i<9;i++){
			board[i] = (byte) (i+1);
		}
		emptyIndice = 8;
		this.lastMove = null;
	}
	
	/**
	 * Creates a new state that is clone of given state
	 * @param state State to be copied
	 */
	public State(State state) {
		for (byte i=0; i<9;i++){
			board[i] = state.board[i];
		}
		emptyIndice = state.emptyIndice;
		this.lastMove = state.lastMove;
	}
	
	/**
	 * Initializes a state whose board configuration is given as board
	 * @param board Configuration of board
	 */
	public State(byte[] board) {
		this.score = 0;
		for (byte i=0; i<9;i++)
		{
			this.board[i] = board[i];
			if (board[i]==EMPTY)
				emptyIndice = i;
		}
		this.lastMove = null;
	}

	/**
	 * Utility method that generates a new state created from a move.
	 * NOTE: This method does not check whether move is valid.
	 * @param type Type of the move
	 * @return New State generated from current state
	 */
	private State move(MoveType type){
		State s = new State(this);
		switch(type){
		case UP:
			s.emptyIndice-=3;
			break;
		case DOWN:
			s.emptyIndice+=3;
			break;
		case LEFT:
			s.emptyIndice-=1;
			break;
		case RIGHT:
			s.emptyIndice+=1;
			break;
		}
		
		s.lastMove = type;
		s.board[this.emptyIndice]=this.board[s.emptyIndice];
		s.board[s.emptyIndice]=this.board[this.emptyIndice];
		return s;
	}
	
	/**
	 * Utility method for sorting a list of states with respect to their heuristic scores.
	 * @param list List of State to be sorted.
	 */
	private static void sort(List<State> list){
		Collections.sort(list,new Comparator<State>() {

			@Override
			public int compare(State s1, State s2) {
				return s1.getScore() - s2.getScore();
			}
		});
	}
	
	/**
	 * Returns the States that can be generated from this state using valid moves.
	 * 
	 * @return List of States whose elements are ordered with respect to heuristic score
	 */
	public List<State> getNeighbours(){
		List <State> list = new LinkedList<State>();
		
		switch(emptyIndice){
		case 0:
			list.add(this.move(MoveType.RIGHT));
			list.add(this.move(MoveType.DOWN));
			break;
		case 1:
			list.add(this.move(MoveType.LEFT));
			list.add(this.move(MoveType.RIGHT));
			list.add(this.move(MoveType.DOWN));
			break;
		case 2:
			list.add(this.move(MoveType.LEFT));
			list.add(this.move(MoveType.DOWN));
			break;
		case 3:
			list.add(this.move(MoveType.UP));
			list.add(this.move(MoveType.RIGHT));
			list.add(this.move(MoveType.DOWN));
			break;
		case 4:
			list.add(this.move(MoveType.UP));
			list.add(this.move(MoveType.LEFT));
			list.add(this.move(MoveType.RIGHT));
			list.add(this.move(MoveType.DOWN));
			break;
		case 5:
			list.add(this.move(MoveType.UP));
			list.add(this.move(MoveType.LEFT));
			list.add(this.move(MoveType.DOWN));
			break;
		case 6:
			list.add(this.move(MoveType.UP));
			list.add(this.move(MoveType.RIGHT));
			break;
		case 7:
			list.add(this.move(MoveType.UP));
			list.add(this.move(MoveType.RIGHT));
			list.add(this.move(MoveType.LEFT));
			break;
		case 8:
			list.add(this.move(MoveType.UP));
			list.add(this.move(MoveType.LEFT));
			break;
		}
		sort(list);//Sort state wrt their heuristic score
		return list;
	}
	
	/**
	 * Calculates and returns the heuristic score of the state
	 * @return An integer value that is obtained from heuristic.
	 */
	private int getScore()
	{
		
		if (score == -1) // If method called first time
		{
			//Calculate score
			score = 0;
			
			for (int i = 0 ; i < board.length ; i++)
				if (board[i] != i + 1)
					score += 1;
			
		}
		
		return score;
	}

	/**
	 * Compares the state with an object.
	 * @return True if they are equivalent
	 */
	@Override
	public boolean equals(Object o){
		if(o instanceof State)
			return equals((State)o);
		else 
			return false;
	}
	
	/**
	 * Compares 2 states.
	 * It compares hash values of the states since they are unique
	 * @param s2 other state
	 * @return True if states are same
	 */
	public boolean equals(State s2)
	{
		return this.hashCode() == s2.hashCode();
	}
	
	/**
	 * Calculates a unique hash for state.
	 * Hash code is unique for every configuration
	 * 
	 * @return Hash value of the state
	 */
	@Override
	public int hashCode()
	{
		if (hash == -1) // If method called first time
		{
			//Calculate hash
			hash = 0;
			for (int i = 0 ; i < board.length ; i++)
				hash = hash*10 + board[i];
		}
		return hash;
	}
	
	/**
	 * Returns the last move that is done to the previous state to get this state
	 * @return Last move. If the state does not have an ancestor State it will return null
	 */
	public MoveType getLastMove()
	{
		return lastMove;
	}

	
	/**
	 * Returns a string that describes state
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("\n---------\n");
		
		for(int i=0;i<this.board.length;i++){
			builder.append('|');
			builder.append(this.board[i] == EMPTY ? " ":this.board[i] );
			builder.append('|');
			if(i%3==2)
				builder.append("\n---------\n");
		}
		return builder.toString();
	}

}
