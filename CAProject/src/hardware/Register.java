package hardware;

public class Register {
	int Regsize;
	String Regvalue;
	String Regname;

	public Register() {
		this.Regsize = 32;
	}

	public String getRegValue() {
		return this.Regvalue;
	}

	public String getRegname() {
		return this.Regname;
	}

	public int getRegSize() {
		return this.Regsize;
	}

	public void set_Reg_Value(String RegValue) throws MipsException {
		if (RegValue.length() > 32) {
			throw new MipsException("ERROR REG MAX VALUE IS 32 BIT");
		}
		this.Regvalue = RegValue;
	}

	public void setZero() {
		Regvalue = "00000000000000000000000000000000";
	}

}
