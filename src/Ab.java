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
		//Call AbSearch for every child of the state with 
		//increasing depth size and return the best move (column number for best drop)

		//The bestMove the player can find for the state
		int bestMove;

		//The best result returned from search
		int bestResult = Integer.MIN_VALUE;

		//Get legal moves for the state
	    List<int> legalMoves = s.get_legal_moves();

	    for(move : legalMoves)
	    {
			int result = AbSearch(s.next_state(move,true), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
			
			//If the result for this child is greater than previous best result
	    	//make bestMove the new result
	    	if(result > bestResult)
				bestMove = move;
		}

		return bestMove;
	}

	public int evaluation(State s, boolean isWhite)
	{
		long red = s.red;
		long iterator = 1;

		for(int i = 0; i < 49; i++)
		{
			;
		}

		.
		return 1;
	}

	public int AbSearch(State s, int depth, int a, int b, boolean isWhite)
	{       
		//Check if we sould go further down the tree TODO: PUT THE TIMER IN THE IF FUNCTION
	    if(depth == 0 || s.isFinal())
	        return evaluation(s, isWhite);

	    //Get legal moves for the state
	    List<int> legalMoves = s.get_legal_moves();

	    //If player is the first player (WHITE-MAX)
	    if(isWhite)
	    {
	    	//For each child state calculate alpha value by callin AbSearch recursively
	    	//and make alpha the largest value of the outcome
	    	//Then to do the pruning check if a >= b and if so we can break out of the for loop
	        for(child : s.next_state(move, true))
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
	    	//For each child state calculate alpha value by callin AbSearch recursively
	    	//and make alpha the largest value of the outcome
	    	//Then to do the pruning check if a >= b and if so we can break out of the for loop
	        
	        for(child : s.next_state(move, false))
	        {
	            a = Math.min(b, AbSearch(child, depth-1, a, b, !isWhite));
	            if(b =< a)
	                break;
	        }
	    
	    	return b; 
		}
	}
}
