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
		long one = 1;
		long board = white | red;

		List<Integer> moves = new ArrayList<Integer>();
		int shift = 7;

		if((board & one << 5) == 0) moves.add(1);
		if((board & one << shift+5) == 0) moves.add(2);
		if((board & one << shift*2+5) == 0) moves.add(3);
		if((board & one << shift*3+5) == 0) moves.add(4);
		if((board & one << shift*4+5) == 0) moves.add(5);
		if((board & one << shift*5+5) == 0) moves.add(6);
		if((board & one << shift*6+5) == 0) moves.add(7);

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
				if(isWhite)
					return new State(column_bit | white, red);
				else
					return new State(white, column_bit | red);
			}
			else
				column_bit *= 2;
				//System.out.println("red is " + red);
				//System.out.println("white is " + white);
				//System.out.println("The column bit is " + column_bit);
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
		for(Integer i : s.get_legal_moves())
			System.out.print(i + " ");
		System.out.println("\n-> 1 2 3 4 5 6 7 \n");

		s.red = 0x1FBF;
		for(Integer i : s.get_legal_moves())
			System.out.print(i + " ");
		System.out.println("\n-> 3 4 5 6 7\n");

		s.white = (long)0x1fbf7efdfbfL;
		for(Integer i : s.get_legal_moves())
			System.out.print(i + " ");
		System.out.println("\n->7 \n");

		s.white = 0x1f;
		s.red = 0;
		for(Integer i : s.get_legal_moves())
			System.out.print(i + " ");
		System.out.println("\n-> 1 2 3 4 5 6 7 \n");


	}

}
