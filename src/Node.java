import java.util.*

public class Node
{
	public State s;
	public Node parent;
	public int value;

	public Node(State s, Node parent, int value)
	{
		this.s = s;
		this.parent = parent;
		this.value = value;
	}
}
