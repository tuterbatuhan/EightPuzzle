
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class EightPuzzle {

	public static void main(String[] args) {
		
		long time = System.currentTimeMillis();
		List<State> list = new ArrayList<>();
		while(list.size()<30)
		{
			State s2 = StateFactory.getRandomState();
			if(!list.contains(s2))
				list.add(s2);
		}
		System.out.println("Generate Time: " + (System.currentTimeMillis() - time));
		
		time = System.currentTimeMillis();
		int found=0;
		for(State s : list){
			if(beamSearch(s, new State(), 2)!=null)
				found++;
		}
		System.out.println(found);
		System.out.println("Solve Time: " +(System.currentTimeMillis() - time));
		
	}
	public static Path beamSearch(State startState, State endState, int w){
		Set<State> agenda = new HashSet<>();
		
		Queue<Path> Q = new LinkedList<>();
		Q.offer(new Path(null,startState));
		
		while(!Q.isEmpty()){
			Path curPath = Q.poll();
			State curState = curPath.getHead();
		//	System.out.println(curState);
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			List<State> neighbours = curState.getNeighbours();
			int remainingBests = w;
			for (State s : neighbours)
			{
				if(remainingBests==0)
					break;
				if(!agenda.contains(s))
					if(s.equals(endState))
						return new Path(curPath,s);
					else{
						Q.offer(new Path(curPath,s));
						
					}
				remainingBests --;					
			}
			agenda.add(curState);
		}
		return null;
		
	}
	
}
