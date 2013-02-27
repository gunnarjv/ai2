import java.util.*

public class State
{
	long white = 0;
	long red = 0;

	public List<Int> get_legal_moves(String role)
	{
		return new List<Int>;
	}

	public State next_state(int move)
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
