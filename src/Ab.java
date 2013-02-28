import java.util.*;
import java.lang.*;

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
		int bestMove = 0;

		//The best result returned from search
		int bestResult = Integer.MIN_VALUE;

		//Get legal moves for the state
	    List<Integer> legalMoves = s.get_legal_moves();

	    for(int move : legalMoves)
	    {
			int result = AbSearch(s.next_state(move,true), 666, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
			
			//If the result for this child is greater than previous best result
	    	//make bestMove the new result
	    	if(result > bestResult)
				bestMove = move;
		}

		return bestMove;
	}

	public int evaluate(State s, boolean isWhite)
	{
		long red = s.red;
		int redCount = 0;

		for(int i = 0; i < 48; i++) //grid 49 is unused
		{
			long mask = 1L << i;
			// We calculate only for set bits. 
			if((mask & red) == 0)
				continue;

			System.out.println(i);

			// We use unsigned shift to check all adjacent squares.
			// Because of the buffer, then we do not need to check for edge cases.
			// The diligent reader can check for himself that this work.
			if((mask & (  red << 1 | red << 6 | red << 7 | red << 8
					   | red >>> 1 | red >>> 6 | red >>> 7 | red >>> 8)) != 0 )
				redCount++;
		}

		return redCount;
	}

	public int AbSearch(State s, int depth, int a, int b, boolean isWhite)
	{       
		//Check if we sould go further down the tree TODO: PUT THE TIMER IN THE IF FUNCTION
	    if(depth == 0 || s.isFinal())
	        return evaluate(s, isWhite);

	    //Get legal moves for the state
	    List<Integer> legalMoves = s.get_legal_moves();

	    //If player is the first player (WHITE-MAX)
	    if(isWhite)
	    {
	    	//For each child state calculate alpha value by callin AbSearch recursively
	    	//and make alpha the largest value of the outcome
	    	//Then to do the pruning check if a >= b and if so we can break out of the for loop
	        for(int move : legalMoves)
	        {
	            a = Math.max(a, AbSearch(s.next_state(move,true), depth-1, a, b, !isWhite));
	            if(b <= a)
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
	        
	        for(int move : legalMoves)
	        {
	            a = Math.min(b, AbSearch(s.next_state(move,false), depth-1, a, b, !isWhite));
	            if(b <= a)
	                break;
	        }
	    
	    	return b; 
		}
	}

	public static void main(String args[])
	{
		State s = new State(0, 0x1F);
		Ab ab = new Ab(0);

		System.out.println(ab.evaluate(s, true) + " (5)");

		s.red = 0x1B;
		System.out.println(ab.evaluate(s, true) + " (4)");

		s.red = 0x9B;
		System.out.println(ab.evaluate(s, true) + " (5)");

		s.red = 0x1009B;
		System.out.println(ab.evaluate(s, true) + " (5)");
		
		s.red = 0xb00000000000L;
		System.out.println(ab.evaluate(s, true) + " (2)");


	}
}
