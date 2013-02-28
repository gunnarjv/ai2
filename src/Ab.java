import java.util.*
import java.lang.*

public class Ab
{
	private int playclock;

	public Ab(int playclock)
	{
		this.playclock = playclock;
	}

	public int search(State s)
	{
		//Create new node and call AbSearch for every child of the node with increasing depth size
		//and return the best move (column number for best drop)

		Node root = new Node()

	    //Initial call
		int result =AbSearch(origin, depth, -infinity, +infinity, true);
		return 1;
	}

	public int evaluation(Node node, boolean isWhite)
	{
		return 1;
	}

	public AbSearch(Node node, int depth, int a, int b, boolean isWhite)
	{       
		//Check if we sould go further down the tree
	    if(depth == 0 || isFinal(node))
	        return evaluation(node, isWhite);

	    //Get legal moves for the state in the node
	    List<int> legalMoves = node.s.get_legal_moves();

	    //If player is the first player (WHITE-MAX)
	    if(isWhite)
	    {
	    	//First get all the children of node from legal moves
	    	List<Node> nodes = new ArrayList<int>();
	    	for(move : legalMoves)
	    	{
	    		nodes.add(new Node( node.s.next_state(move, 1), node));
	    	}

	    	//Then for each child node calculate alpha value by callin AbSearch recursively
	    	//and make alpha the largest value of the outcome
	    	//Then to do the pruning check if a >= b and if so we can break out of the for loop
	        for(child : nodes)
	        {
	            a = Math.max(a, AbSearch(child, depth-1, a, b, !isWhite));
	            if(b =< a)
	                break;
	        }

	        return a;
	    }
	    //If player is the second player (RED-MIN)
	    else
	    {
	    	//First get all the children of node from legal moves
	    	List<Node> nodes = new ArrayList<int>();
	    	for(move : legalMoves)
	    	{
	    		nodes.add(new Node( node.s.next_state(move, 0), node));
	    	}


	        for(child : nodes)
	        {
	            a = Math.min(b, AbSearch(child, depth-1, a, b, !isWhite));
	            if(b =< a)
	                break;
	        }
	    
	    	return b; 
		}
	}

	public boolean isFinal(node)
	{
		//Check if this node is a final node for either player
		return true;
	}
}
