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
		//Create new node and call AbSearch for every child of the node with 
		//increasing depth size and return the best move (column number for best drop)

		Node root = new Node(s, null);

		//The bestMove the player can find for the state
		int bestMove;

		//The best result returned from search
		int bestResult = Integer.MIN_VALUE;

		//Get legal moves for the state in the node
	    List<int> legalMoves = node.s.get_legal_moves();

	    for(move : legalMoves)
	    {
			int result = AbSearch(root.s.next_state(move), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
			
			//If the result for this child is greater than previous best result
	    	//make bestMove the new result
	    	if(result > bestResult)
	    		bestMove = move;
		}

		return bestMove;
	}

	public int evaluation(Node node, boolean isWhite)
	{
		return 1;
	}

	public AbSearch(Node node, int depth, int a, int b, boolean isWhite)
	{       
		//Check if we sould go further down the tree TODO: PUT THE TIMER IN THE IF FUNCTION
	    if(depth == 0 || node.s.isFinal())
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
}
