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

	public State(int white, int red)
	{
		this.white = white;
		this.red = red;
	}

	public List<Integer> get_legal_moves()
	{
		long board = white & red;
		List<Integer> moves = new ArrayList<Integer>();
		int shift = 7;

		if((board & 1 << 5) != 0) moves.add(1);
		if((board & 1 << shift+5) != 0) moves.add(2);
		if((board & 1 << shift*2+5) != 0) moves.add(3);
		if((board & 1 << shift*3+5) != 0) moves.add(4);
		if((board & 1 << shift*4+5) != 0) moves.add(5);
		if((board & 1 << shift*5+5) != 0) moves.add(6);
		if((board & 1 << shift*6+5) != 0) moves.add(7);

		return moves;
	}

	// Returns a state as if 'move' was made.
	public State next_state(int move, String role)
	{
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


	int main()
	{
		State s = new State();

		System.out.println(s.get_legal_moves());

		return 0; 
	}

}
