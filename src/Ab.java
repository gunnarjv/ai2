import java.util.*
import java.lang.*

public class Ab
{
	private double playclock;
	private long startTime = 0;

	public Ab(int playclock)
	{
		this.playclock = (double)playclock;
	}

	public int search(State s)
	{
		//Take the startTime
		startTime = System.nanoTime();

		//Call AbSearch for every child of the state with 
		//increasing depth size and return the best move (column number for best drop)

		//The bestMove the player can find for the state
		int bestMove;

		//The best result returned from search
		int bestResult = Integer.MIN_VALUE;

		//Get legal moves for the state
	    List<int> legalMoves = s.get_legal_moves();

	    //Iterative deepening loop that increments the maximum depth by 1 in each loop
	    for(int depth=1; depth < Integer.MAX_VALUE; i++)
	    {
	    	//Check the timer by calculating elapsed nanotime, converting it to seconds and
			//comparing with a little bit less time than playclock.. sek = 1*e⁹ nanosek
	    	if((System.nanoTime - startTime)*Math.Pow(10, 8) >= playclock-1)
	    		break;

		    for(move : legalMoves)
		    {
		    	//We check here as well if the time is almost over
	    		if((System.nanoTime - startTime)*Math.Pow(10, 8) >= playclock-1)
	    			break;

				int result = AbSearch(s.next_state(move,true), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
				
				//If the result for this child is greater than previous best result
		    	//make bestMove the new result
		    	if(result > bestResult)
					bestMove = move;
			}
		}
		return bestMove;
	}

	public int evaluation(State s, boolean isWhite)
	{
		return 1;
	}

	public int AbSearch(State s, int depth, int a, int b, boolean isWhite)
	{       
		//Check if we sould go further down the tree
		//We also check here as if the time is almost over
	    if(depth == 0 || s.isFinal() || (System.nanoTime - startTime)*Math.Pow(10, 8) >= playclock-1)
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
