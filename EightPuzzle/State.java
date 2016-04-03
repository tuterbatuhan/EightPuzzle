import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class State {
	byte emptyIndice = 0;
	private byte [] board = new byte [9];
	private byte score = -1;
	
	static final byte EMPTY = 9;
	private enum MoveType {UP,DOWN,LEFT,RIGHT};
	
	
	private int hash = -1;
	
	@Override
	public int hashCode()
	{
		if (hash == -1)
		{
			hash = 0;
			for (int i = 0 ; i < board.length ; i++)
				hash = hash*10 + board[i];
		}
		return hash;
	}
	public State()
	{
		for (byte i=0; i<9;i++){
			board[i] = (byte) (i+1);
		}
		emptyIndice = 8;
		
	}
	public State(State state) {
		for (byte i=0; i<9;i++){
			board[i] = state.board[i];
		}
		emptyIndice = state.emptyIndice;
		
	}
	public State(byte[] board) {
		this.score = 0;
		for (byte i=0; i<9;i++){
			this.board[i] = board[i];
			if (board[i]==EMPTY)
				emptyIndice = i;
		}
		
	}

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
		s.board[this.emptyIndice]=this.board[s.emptyIndice];
		s.board[s.emptyIndice]=this.board[this.emptyIndice];
		
		return s;
	}
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
		sort(list);
		return list;
	}
	//With heuristic provided
	private int getScore()
	{
		if (score == -1)
		{
			score = 0;
			
//			for (int i = 0 ; i < board.length ; i++)
//				if (board[i] != i + 1)
//					score += 20 - i;
			int [] cost = {10 , 9 , 8 ,9 , 8 ,7 ,8 , 7 , 6};
			for (int i = 0 ; i < board.length ; i++)
				if (board[i] != i + 1)//sayı yerinde değilse
					score += cost[i];
			
//			for (int i = 0 ; i < board.length ; i++)
//			if (board[i] != i + 1)//sayı yerinde değilse
//				score++;
		}
		return score;
	}
	private static void sort(List<State> list){
		Collections.sort(list,new Comparator<State>() {

			@Override
			public int compare(State s1, State s2) {
				return s1.getScore() - s2.getScore();
			}
		});
	}
	@Override
	public boolean equals(Object o){
		if(o instanceof State)
			return equals((State)o);
		else 
			return false;
	}
	public boolean equals(State s2){
		if(this.emptyIndice != s2.emptyIndice)
			return false;
		for (byte i=0; i<9;i++){
			if (board[i] != s2.board[i])
				return false;
		}
		return true;
	}
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
