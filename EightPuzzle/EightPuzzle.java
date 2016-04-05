
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Driver class that tests beam search on the Eight Puzzle problem
 *
 */
public class EightPuzzle 
{
	public static void main(String[] args) 
	{
		final int TEST_SIZE = 1000; //# of tests
		final int W = 2; 			//Beam size
		
		System.out.println(TEST_SIZE + " distinct board configuration will be tested"
				+ " using beam sort (w = " + W + "): ");
		
		
		//Create a new state factory with given
		StateFactory factory = new StateFactory();
		
		//Create startStates and goal state using factory
		Set<State> set = factory.getRandomStates(TEST_SIZE);
		State goalState = factory.getSolvedState();
		
		int solvedCount=0; // # of solved puzzles
		Path solutionPath;
		boolean solutionPrinted = false;
		for(State s : set)
		{
			solutionPath = beamSearch(s, goalState, W);
			if(solutionPath != null)//If solved
				solvedCount++;
			
			if (!solutionPrinted && solutionPath!=null)
			{
				System.out.println("Example Solution : ");
				System.out.println(solutionPath);
				solutionPrinted = true;
			}
		}
		
		System.out.println("Successfully solved " + solvedCount  + " of " + TEST_SIZE + " puzzles");
	}
	
	/**
	 * Performs beam search with beam width w to find a path from start state and goal state
	 * It might not be able to find any path when the width of the beam is small or heuristic
	 *   is not guiding the goal state or there is no path from start state to goal state.
	 * If solution cannot be found it returns null, otherwise it returns the path that starts from startState 
	 * and lead to the goal state.
	 * 
	 * @param startState Starting state for the search.
	 * @param goalState Goal state of the search
	 * @param w Width of the beam
	 * @return Path if solution found, otherwise null.
	 */
	public static Path beamSearch(State startState, State goalState, int w){
		//Hash set is used to check whether any state is already visited
		//Eliminates cycles
		Set<State> visitedStates = new HashSet<>();

		//Agenda holds paths that is visited.
		//It is used to determine next states that will be searched.
		Queue<Path> agenda = new LinkedList<>();
		
		agenda.offer(new Path(startState));//Enqueue start state (standard search starting)
		
		//Search loop. Runs until a solution is found or no solution is found.
		while(!agenda.isEmpty())
		{
			Path curPath = agenda.poll();
			State curState = curPath.getLastState();

			//Gets the possible moves from current state
			//Returned neighbour states are sorted according to their heuristic scores.
			List<State> neighbours = curState.getNeighbours();
			
			int remainingBests = w;//Stores how many beam is added in this iteration
			
			for (State s : neighbours)
			{
				//If all of the beams is added exit the for loop
				if(remainingBests==0)
					break;
				
				if(!visitedStates.contains(s))//If not already visited
					if(s.equals(goalState)) 
						return new Path(curPath,s); // Solution found
					else
					{
						agenda.offer(new Path(curPath,s));
						remainingBests--; //Implementation 1
					}
		//			remainingBests--; //Implementation 2
				
			}//for all neighbours
			
			// Add last visited state into visitedStates
			visitedStates.add(curState);
			
		}//while agenda is not empty
		
		return null;//Solution is not found
	}
	
}
