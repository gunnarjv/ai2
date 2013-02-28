import java.util.*;

public class State
{
	// LSB is bottom left on board. 
	// Then comes the bottom left column.
	// Followed by one unused bit.
	// Followed by other columns.
	long white = 0;
	long red = 0;

	public State()
	{}

	public State(long white, long red)
	{
		this.white = white;
		this.red = red;
	}

	public List<Integer> get_legal_moves()
	{
		/* We search all bits that represent the top-most lots in the grid.
		   For each column, if that bit is not set, then we can drop a disc there.
		 */
		long board = white | red;
		List<Integer> moves = new ArrayList<Integer>();
		int shift = 7;

		if((board & 1 << 5) == 0) moves.add(1);
		if((board & 1 << shift+5) == 0) moves.add(2);
		if((board & 1 << shift*2+5) == 0) moves.add(3);
		if((board & 1 << shift*3+5) == 0) moves.add(4);
		if((board & 1 << shift*4+5) == 0) moves.add(5);
		if((board & 1 << shift*5+5) == 0) moves.add(6);
		if((board & 1 << shift*6+5) == 0) moves.add(7);

		return moves;
	}

	public State next_state(int move, boolean isWhite)
	{
		/* column_bit is a mask that is initially the bottom-most lot in the
		   chosen column. If that bit is not set, that bit is set in the current player's mask.
		   If it is not set, we search the lots above.
		*/
		long board = white | red;
		long column_bit = 1 << 7*(move-1);

		for(int i = 0; i < 6; i++)
		{
			if((column_bit & board) == 0)
			{
/*				if(isWhite)
					return new State(column_bit | white, red);
				else
					return new State(white, column_bit | red);
*/			}
			else
				column_bit *= 2;
		}

		System.out.println("An illegal move was made. Exiting.");
		System.exit(1);
		return new State();
	}

	public boolean equals(Object other)
	{
		if(other == this) return true;
		if(other == null) return false;
		if(other.getClass() != this.getClass()) return false;
		State that = (State) other;
		if(this.white == that.white && this.red == that.red) return true;
		return false;
	}

	public static void main(String args[])
	{
		State s = new State();

		// First test if empty board has every state as legal.
		List<Integer> legal = s.get_legal_moves();
		for(Integer i : legal)
			System.out.print(i + " ");
		System.out.println("");

		// Then if it is correctly legal after three moves from white.
		s = s.next_state(1, true);
		s = s.next_state(1, true);
		s = s.next_state(1, true);

		legal = s.get_legal_moves();
		for(Integer i : legal)
			System.out.print(i + " ");
		System.out.println("");

		// Then if it is incorrectly legal after three more moves from white.
		s = s.next_state(1, true);
		s = s.next_state(1, true);
		s = s.next_state(1, true);

		legal = s.get_legal_moves();
		for(Integer i : legal)
			System.out.print(i + " ");
		System.out.println("");

		// Test the same thing for red
		s = s.next_state(2, false);
		s = s.next_state(2, false);
		s = s.next_state(2, false);

		legal = s.get_legal_moves();
		for(Integer i : legal)
			System.out.print(i + " ");
		System.out.println("");

		// Then if it is incorrectly legal after three more moves from white.
		s = s.next_state(2, false);
		s = s.next_state(2, false);
		s = s.next_state(2, false);

		legal = s.get_legal_moves();
		for(Integer i : legal)
			System.out.print(i + " ");
		System.out.println("");
		

	}

}
