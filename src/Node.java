import java.util.*

public class Node
{
	public State s;
	public Node parent;

	public Node(State s, Node parent)
	{
		this.s = s;
		this.parent = parent;
	}
}
