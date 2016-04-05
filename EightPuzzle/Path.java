
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Describes a path of states that is found by valid moves.
 * 
 *
 */
public class Path {
	//Path is an immutable stack data structure
	
	private Path tail; //Tail stores rest of the path
	private State head; //Last state in path
	
	/**
	 * Constructs a new Path by adding a state to the end of the given path
	 * @param p Path that will be used 
	 * @param s State that will be added at the end of the path
	 */
	public Path(Path p,State s){
		tail=p;
		head=s;
	}
	
	/**
	 * Creates a new path with initial state
	 * @param s Initial state
	 */
	public Path(State s)
	{
		this(null,s);
	}
	
	/**
	 * Returns last state added in the path
	 */
	public State getLastState(){
		return head;
	}
	
	/**
	 * Returns the list of States in the Path.
	 * First item of the list is the first added State.
	 * Last item is the last state added.
	 * @return
	 */
	public List<State> toList()
	{
		//Since items are ordered reverse in stack, A reversal is needed.
		List<State> list = new LinkedList<>();
		Stack<State> stack = new Stack<>();
		
		Path cur = this;
		while(cur!=null)
		{
			stack.push(cur.head);
			cur=cur.tail;
		}
		
		while(!stack.isEmpty())
			list.add(stack.pop());
		
		return list;
	}
	
	/**
	 * Returns a string that describes path. 
	 */
	public String toString(){
		Stack<State> stack = new Stack<>();
		Path cur = this;
		while(cur!=null)
		{
			stack.push(cur.head);
			cur=cur.tail;
		}
		
		System.out.println("Inital Configuration of the puzzle:");
		
		StringBuilder builder = new StringBuilder();
		
		int moveCounter = 0;
		while(!stack.isEmpty())
		{
			if (moveCounter != 0)
				builder.append("\nMove " + moveCounter + " : ");
			
			appendMove(builder, stack.pop());
			moveCounter++;
		}
		
		return builder.toString();
	}
	
	/**
	 * Utility function for toString method of the Path
	 * @param builder StringBuilder that move will be recorded
	 * @param state Current State that will be recorded
	 */
	private static void appendMove(StringBuilder builder,State state)
	{
		State.MoveType lastMove = state.getLastMove();
		String move = "";
		if (lastMove != null)
			move = lastMove.name();
		
		builder.append(move + "\n");
		builder.append(state.toString());
	}
}
