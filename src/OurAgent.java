import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OurAgent implements Agent
{
	private boolean isWhite;
	private int playclock;
	private boolean myTurn;
	private State s = new State();
	private Ab ab;
	
	/*
		init(String role, int playclock) is called once before you have to select the first action. Use it to initialize the agent. role is either "WHITE" or "RED" and playclock is the number of seconds after which nextAction must return.
	*/
    public void init(String role, int playclock) {
		if(role.equals("WHITE"))
			this.isWhite = true;
		else
			this.isWhite = false;

		this.playclock = playclock;

		ab = new Ab(playclock, isWhite);

		myTurn = !role.equals("WHITE");
    }

	// lastDrop is 0 for the first call of nextAction (no action has been executed),
	// otherwise it is a number n with 0<n<8 indicating the column that the last piece was dropped in by the player whose turn it was
    public String nextAction(int lastDrop) { 
		if(lastDrop != 0)
		{
			if(myTurn)
				s = s.next_state(lastDrop, !isWhite);
			else
				s = s.next_state(lastDrop, isWhite);
		}

		myTurn = !myTurn;

		if (myTurn) {
			return "(DROP " + ab.search(s) + ")";
		} else {
			return "NOOP";
		}
	}

}
