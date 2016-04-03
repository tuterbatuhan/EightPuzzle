
import java.util.Stack;

public class Path {
	private Path tail;
	private State head;
	public Path(Path p,State s){
		tail=p;
		head=s;
	}
	
	public State getHead(){
		return head;
	}
	public String toString(){
		Stack<State> stack = new Stack<>();
		Path cur = this;
		while(cur!=null)
		{
			stack.push(cur.head);
			cur=cur.tail;
		}
		
		String s = "";
		
		while(!stack.isEmpty())
			s+=stack.pop()+"\n";
		
		return s;
	}

}
