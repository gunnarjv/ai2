import java.util.*;
import java.lang.*;

public class Ab
{
	private double playclock;
	private long startTime = 0;
	private boolean isWhite;

	public Ab(int playclock, boolean isWhite)
	{
		this.isWhite = isWhite;
		this.playclock = (double)playclock;
	}

	public int search(State s)
	{
		startTime = System.nanoTime();

		//Call AbSearch for every child of the state with 
		//increasing depth size and return the best move (column number for best drop)

		int bestMove = 0;

		int bestResult = Integer.MIN_VALUE;

	    List<Integer> legalMoves = s.get_legal_moves();

	    //Iterative deepening loop that increments the maximum depth by 1 in each loop
	    for(int depth=1; depth < Integer.MAX_VALUE; depth++)
	    {
	    	//Check the timer by calculating elapsed nanoTime(), converting it to seconds and
			//comparing with a little bit less time than playclock.. sek = 1*e⁹ nanosek
	    	if((System.nanoTime() - startTime)/Math.pow(10, 9) >= playclock-1)
	    		break;

		    for(int move : legalMoves)
		    {
		    	//Check the timer by calculating elapsed nanoTime(), converting it to seconds and
				//comparing with a little bit less time than playclock.. sek = 1*e⁹ nanosek
		    	if((System.nanoTime() - startTime)/Math.pow(10, 9) >= playclock-1)
		    		break;

				int result = AbSearch(s.next_state(move, isWhite), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
				
		    	if(result > bestResult)
		    	{
		    		bestResult = result;
					bestMove = move;
		    	}
			}
		}
		
		return bestMove;
	}

	public int AbSearch(State s, int depth, int a, int b, boolean isMax)
	{       
		//Check if we should go further down the tree
	    if(depth == 0 || s.isFinal())
	        return evaluate(s, isMax);

	    List<Integer> legalMoves = s.get_legal_moves();

	    //If player is the first player (MAX)
	    if(isMax)
	    {
	    	//For each child state calculate alpha value by calling AbSearch recursively
	    	//and make alpha the largest value of the outcome
	    	//Then to do the pruning check if a >= b and if so we can break out of the for loop
	        for(int move : legalMoves)
	        {
	            a = Math.max(a, AbSearch(s.next_state(move, isWhite), depth-1, a, b, !isMax));
	            if(b <= a)
	                break;
	        }

	        return a;
	    }
	    else
	    {
	    	//For each child state calculate beta value by calling AbSearch recursively
	    	//and make beta the smallest value of the outcome
	    	//Then to do the pruning check if a >= b and if so we can break out of the for loop
	        
	        for(int move : legalMoves)
	        {
	            b = Math.min(b, AbSearch(s.next_state(move, !isWhite), depth-1, a, b, !isMax));
	            if(b <= a)
	                break;
	        }
	    
	    	return b; 
		}
	}

	public int evaluate(State s, boolean isMax)
	{
		long red = s.red;
		int redCount = 0;

		for(int i = 0; i < 48; i++) //grid 49 is unused
		{
			long mask = 1L << i;
			// We calculate only for set bits. 
			if((mask & red) == 0)
				continue;


			// We use unsigned shift to check all adjacent squares.
			// Because of the buffer, then we do not need to check for edge cases.
			// The diligent reader can check for himself that this work.
			if((mask & (  red << 1 | red << 6 | red << 7 | red << 8
					   | red >>> 1 | red >>> 6 | red >>> 7 | red >>> 8)) != 0 )
				redCount++;
		}

		int whiteCount = 0;
		long white = s.white;

		for(int i = 0; i < 48; i++) //grid 49 is unused
		{
			long mask = 1L << i;
			// We calculate only for set bits. 
			if((mask & white) == 0)
				continue;

			// We use unsigned shift to check all adjacent squares.
			// Because of the buffer, then we do not need to check for edge cases.
			// The diligent reader can check for himself that this work.
			if((mask & (  white << 1 | white << 6 | white << 7 | white << 8
					   | white >>> 1 | white >>> 6 | white >>> 7 | white >>> 8)) != 0 )
				whiteCount++;
		}

		int evaluation = 0;
		
		//If the game is won check for winner and return correct value
		if(s.isFinal() && !s.isDraw())
		{
			if(isMax)
				return Integer.MAX_VALUE;
			else
				return Integer.MIN_VALUE;
		}
		
		evaluation = whiteCount - redCount;

		if(isWhite)
			return evaluation;
		else
			return -evaluation;
	}


/*	public static void main(String args[])
	{
		State s = new State(0, 0x1F);
		Ab ab = new Ab(0, true);

		System.out.println(ab.evaluate(s) + " (5)");

		s.red = 0x1B;
		System.out.println(ab.evaluate(s) + " (4)");

		s.red = 0x9B;
		System.out.println(ab.evaluate(s) + " (5)");

		s.red = 0x1009B;
		System.out.println(ab.evaluate(s) + " (5)");
		
		s.red = 0xb00000000000L;
		System.out.println(ab.evaluate(s) + " (2)");

		s.white = 0xb00000000000L;
		s.red = 0;
		System.out.println(ab.evaluate(s) + " (2)");
<<<<<<< HEAD



	} */
}
