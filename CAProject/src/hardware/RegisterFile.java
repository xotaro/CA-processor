package hardware;

import java.util.Hashtable;

import hardware.MipsException;

/**
 * 
 * Kindly Check Read, ReadReg and Write for reading and writing from Reg File
 */
public class RegisterFile {
	public static Register[] registers;
	public static boolean writeflag;
	public static Hashtable<String, String> reg = new Hashtable<String, String>();

	public static String[] _REGNAMES_ = { "$0", "$r1", "$r2", "$r3", "$r4", "$r5", "$r6", "$r7", "$r8", "$r9", "$r10",
			"$r11", "$r12", "$r13", "$r14", "$r15", "$r16", "$r17", "$r18", "$r19", "$r20", "$r21", "$r22", "$r23",
			"$r24", "$r25", "$r26", "$r27", "$r28", "$r29", "$r30", "$r31" };

	public RegisterFile() throws MipsException {
		registers = new Register[32];
		fillReg();
		for (int z = 0; z < 32; z++) {
			registers[z] = new Register();
			registers[z].Regname = _REGNAMES_[z];

		}
		registers[0].set_Reg_Value("00000000000000000000000000000000"); // constant value zero

	}

	/**
	 * getting reg number
	 * 
	 * @param inst
	 * @return
	 */
	public static String read_flag(String inst) {
		return "" + inst.charAt(0);
	}

	public static String read_16_op_imm(String inst) {
		String result = "";
		for (int i = 1; i < inst.length(); i++) {
			if (i == 17) {
				break;
			}
			result += inst.charAt(i);
		}
		return result;
	}

	public static int read_rs1(String inst) {
		String result = "";
		for (int i = 6; i < inst.length(); i++) {
			if (i == 11) {
				break;
			}
			result += inst.charAt(i);
		}
		Integer decimal_value = Integer.parseInt(result, 2);
		return decimal_value;
	}

	public static int read_rt1(String inst) {
		String result = "";
		for (int i = 11; i < inst.length(); i++) {
			if (i == 16) {
				break;
			}
			result += inst.charAt(i);
		}
		Integer decimal_value = Integer.parseInt(result, 2);
		return decimal_value;
	}

	public static int read_rd1(String inst) {
		String result = "";
		for (int i = 16; i < inst.length(); i++) {
			if (i == 21) {
				break;
			}
			result += inst.charAt(i);
		}
		Integer decimal_value = Integer.parseInt(result, 2);
		return decimal_value;
	}

	public static int read_imm1(String inst)

	{
		String result = "";
		for (int i = 16; i < inst.length(); i++) {

			result += inst.charAt(i);
		}
		Integer decimal_value = Integer.parseInt(result, 2);
		return decimal_value;
	}

	public static int read_addr1(String inst)

	{
		String result = "";
		for (int i = 6; i < inst.length(); i++) {

			result += inst.charAt(i);
		}
		Integer decimal_value = Integer.parseInt(result, 2);
		return decimal_value;
	}

	public static String read_op(String inst) {
		String op = "";
		for (int i = 0; i < inst.length(); i++) {
			if (i == 6) {
				break;
			}
			op = op + inst.charAt(i);

		}
		return op;

	}

	public static String read_addr_j(String inst) {
		String op = "";
		for (int i = 1; i < inst.length(); i++) {
			if (i == 27) {
				break;
			}
			op = op + inst.charAt(i);

		}
		return op;

	}

	public static String read_imm(String inst)

	{
		/*
		 * s
		 */
		String result = "";
		for (int i = 16; i < inst.length(); i++) {

			result += inst.charAt(i);
		}
		return result;
	}

	public static String read_funct(String inst) {
		String op = "";
		for (int i = 27; i < inst.length(); i++) {
			op = op + inst.charAt(i);

		}
		return op;

	}

	public static String readrs1(String inst) {
		String result = "";
		for (int i = 17; i < inst.length(); i++) {
			if (i == 22) {
				break;
			}
			result += inst.charAt(i);
		}

		return result;
	}

	public static String readrs2(String inst) {
		String result = "";
		for (int i = 22; i < inst.length(); i++) {
			if (i == 27) {
				break;
			}
			result += inst.charAt(i);
		}
		return result;
	}

	public static String readrd(String inst) {
		String result = "";
		for (int i = 27; i < inst.length(); i++) {
			result += inst.charAt(i);
		}
		return result;
	}

	private void fillReg() {
		reg.put("$0", "00000"); // 0
		reg.put("$r1", "00001"); // 1
		reg.put("$r2", "00010"); // 2
		reg.put("$r3", "00011"); // 3
		reg.put("$r4", "00100"); // 4
		reg.put("$r5", "00101"); // 5
		reg.put("$r6", "00110"); // 6
		reg.put("$r7", "00111"); // 7
		reg.put("$r8", "01000"); // 8
		reg.put("$r9", "01001"); // 9
		reg.put("$r10", "01010"); // 10
		reg.put("$r11", "01011"); // 11
		reg.put("$r12", "01100"); // 12
		reg.put("$r13", "01101"); // 13
		reg.put("$r14", "01110"); // 14
		reg.put("$r15", "01111"); // 15
		reg.put("$r16", "10000"); // 16
		reg.put("$r17", "10001"); // 17
		reg.put("$r18", "10010"); // 18
		reg.put("$r19", "10011"); // 19
		reg.put("$r20", "10100"); // 20
		reg.put("$r21", "10101"); // 21
		reg.put("$r22", "10110"); // 22
		reg.put("$r23", "10111");// 23
		reg.put("$r24", "11000");// 24
		reg.put("$r25", "11001");// 25
		reg.put("$r26", "11010");// 26
		reg.put("$r27", "11011");// 27
		reg.put("$r28", "11100");// 28
		reg.put("$r29", "11101");// 29
		reg.put("$r30", "11110");// 30
		reg.put("$r31", "11111");// 31

	}

	public static String Regname(int index) {
		return _REGNAMES_[index];
	}

	public void read(int first_index, int second_index) {
		System.out.println(registers[first_index].Regvalue);
		System.out.println(registers[second_index].Regvalue);

	}

	/**
	 * read the register value
	 * 
	 * @param index
	 * @return String
	 */
	public static String readReg(String indexx) {
		int index = Integer.parseInt(indexx, 2);
		writeflag = false;
		if (!writeflag) {
			return registers[index].Regvalue;
		} else {
			return "";
		}

	}

	public static void write(String indexx, String value) throws MipsException {
		int index = Integer.parseInt(indexx, 2);

		if (index == 0) {
			throw new MipsException("you cant change Zero register");

		}

		writeflag = true;
		if (writeflag) {
			registers[index].set_Reg_Value(value);
		}
	}

}
