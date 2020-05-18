package hardware;

public class ProgramCounter extends Register {
public static	int PCvalue;

	public ProgramCounter() {
		super();
		this.Regname = "$PC";
	}

	public void inc() {
		PCvalue += 4;
	}

	public int getPCValue() {
		return PCvalue;
	}

	public void reset() {
		PCvalue = 0;
	}

}
