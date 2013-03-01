import java.util.*;

public class State
{
	// We use bitboards.
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
		long column_bit = 1L << 7*(move-1);

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
	}

		System.out.println("An illegal move was made. Exiting.");
		System.exit(1);
		return new State();
	}

	public boolean isFinal()
	{
		// The following is borrowed from John Tromp. http://homepages.cwi.nl/~tromp/c4/fhour.html
		// It would have taken us some ugly loops to implement this function.
		long board = red | white;

		long y = board & (board>>6);
		if ((y & (y >> 2*6)) != 0) // check diagonal \
			return true;
		y = board & (board>>7);
		if ((y & (y >> 2*7)) != 0) // check horizontal -
			return true;
		y = board & (board>>8); // check diagonal /
		if ((y & (y >> 2*8)) != 0)
			return true;
		y = board & (board>>1); // check vertical |
		return (y & (y >> 2)) != 0;
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
		Scanner input = new Scanner(System.in);
		State s = new State();

		for(int i = 0; i < 49; i++)
		{
			s = s.next_state(input.nextInt(), i % 2 == 0);
			System.out.println("White: " + Long.toBinaryString(s.white));
			System.out.println("Red: " + Long.toBinaryString(s.red));
			System.out.println("Both: " + Long.toBinaryString(s.white | s.red));
		}


/*		// This code tests the functions.
		State s = new State();

		// Test get_legal_moves.
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

		// Test isFinal.
		// Vertical.
		System.out.println("true->" + s.isFinal());

		// None
		s.white = 0;
		System.out.println("false->" + s.isFinal());

		// Horizontal
		s.white = 0x204081;
		System.out.println("true->" + s.isFinal());

		// Diagonal /
		s.white = 0x1010101;
		System.out.println("true->" + s.isFinal());
*/	}

}
