import java.util.*;
import java.lang.*;

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
		startTime = System.nanoTime();

		//Call AbSearch for every child of the state with 
		//increasing depth size and return the best move (column number for best drop)

		//The bestMove the player can find for the state
		int bestMove = 0;

		//The best result returned from search
		int bestResult = Integer.MIN_VALUE;

	    List<Integer> legalMoves = s.get_legal_moves();

	    //Iterative deepening loop that increments the maximum depth by 1 in each loop
	    for(int depth=1; depth < Integer.MAX_VALUE; depth++)
	    {
	    	//Check the timer by calculating elapsed nanoTime(), converting it to seconds and
			//comparing with a little bit less time than playclock.. sek = 1*e⁹ nanosek
	    	if((System.nanoTime() - startTime)*Math.pow(10, 8) >= playclock-1)
	    		break;

		    for(int move : legalMoves)
		    {
		    	//We check here as well if the time is almost over
	    		if((System.nanoTime() - startTime)*Math.pow(10, 8) >= playclock-1)
	    			break;

				int result = AbSearch(s.next_state(move,true), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
				
		    	if(result > bestResult)
					bestMove = move;
			}
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

		int whiteCount = 0;
		long white = s.white;

		for(int i = 0; i < 48; i++) //grid 49 is unused
		{
			long mask = 1L << i;
			// We calculate only for set bits. 
			if((mask & white) == 0)
				continue;

			System.out.println(i);

			// We use unsigned shift to check all adjacent squares.
			// Because of the buffer, then we do not need to check for edge cases.
			// The diligent reader can check for himself that this work.
			if((mask & (  white << 1 | white << 6 | white << 7 | white << 8
					   | white >>> 1 | white >>> 6 | white >>> 7 | white >>> 8)) != 0 )
				whiteCount++;
		}


		return whiteCount - redCount;
	}

	public int AbSearch(State s, int depth, int a, int b, boolean isWhite)
	{       
		//Check if we sould go further down the tree
		//We also check here as if the time is almost over
	    if(depth == 0 || s.isFinal() || (System.nanoTime() - startTime)*Math.pow(10, 8) >= playclock-1)
	        return evaluate(s, isWhite);

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
