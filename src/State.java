import java.util.*

public class State
{
	long white = 0;
	long red = 0;

	public State(int white, int red)
	{
		this.white = white;
		this.red = red;
	}

	public List<Int> get_legal_moves(String role)
	{
		return new List<Int>;
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

}
