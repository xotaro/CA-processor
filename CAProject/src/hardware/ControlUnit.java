package hardware;

/**
 * RegDst,Branch,MemRead,MemtoReg,ALUOP,MemWrite,ALUSrc,RegWrite // 8
 * 
 *
 */

public class ControlUnit {
	public static String Branch = "";
	public static String MemRead = "";
	public static String MemtoReg = "";
	public static String MemWrite = "";
	public static String ALUOP = "";
	public static String ALUSrc = "";
	public static String RegWrite = "";
	public static String Jump = "0";
	public static String ALUCONT_OP = ""; // control to be passed to ALU;

	public static void MainControl(String flag, String opcode) {
		// Op or funct
		if (flag.equalsIgnoreCase("0")) {
			RegWrite = "1";
			Jump = "0";
			Branch = "0";
			ALUSrc = "0";
			MemtoReg = "0";
			MemWrite = "0";
			MemRead = "0";
			if (opcode.equalsIgnoreCase("0000000000000000")) {
				// ADD
				ALUOP = "000";
			} else if (opcode.equalsIgnoreCase("0000000000000001")) {
				ALUOP = "001";
			} else if (opcode.equalsIgnoreCase("0000000000000010")) {
				ALUOP = "010";
			} else if (opcode.equalsIgnoreCase("0000000000000011")) {
				ALUOP = "011";
			} else if (opcode.equalsIgnoreCase("0000000000000100")) {
				ALUOP = "100";
			}

		} else if (flag.equalsIgnoreCase("1")) {
			// funct
			if (opcode.equalsIgnoreCase("00000")) {
				// lw
				ALUOP = "000";
				RegWrite = "1";
				Jump = "0";
				Branch = "0";
				ALUSrc = "1";
				MemtoReg = "1";
				MemWrite = "0";
				MemRead = "1";
			} else if (opcode.equalsIgnoreCase("00001")) {
				// sw
				ALUOP = "000";
				RegWrite = "0";
				Jump = "0";
				Branch = "0";
				ALUSrc = "1";
				MemtoReg = "X";
				MemWrite = "1";  // changed
				MemRead = "0";
			} else if (opcode.equalsIgnoreCase("00010")) {
				ALUOP = "000";
				RegWrite = "1";
				Jump = "0";
				Branch = "0";
				ALUSrc = "1";
				MemtoReg = "0";
				MemWrite = "0";
				MemRead = "0";
			} else if (opcode.equalsIgnoreCase("00011")) {
				ALUOP = "101";
				RegWrite = "1";
				Jump = "0";
				Branch = "0";
				ALUSrc = "1";
				MemtoReg = "0";
				MemWrite = "0";
				MemRead = "0";
			} else if (opcode.equalsIgnoreCase("00100")) {
				ALUOP = "001";
				RegWrite = "0";
				Jump = "0";
				Branch = "1";
				ALUSrc = "0";
				MemtoReg = "X";
				MemWrite = "0";
				MemRead = "0";
			} else if (opcode.equalsIgnoreCase("00101")) {
				ALUOP = "100";
				RegWrite = "0";
				Jump = "0";
				Branch = "1";
				ALUSrc = "0";
				MemtoReg = "X";
				MemWrite = "0";
				MemRead = "0";
			} else if (opcode.equalsIgnoreCase("00110")) {
				ALUOP = "XXX";
				RegWrite = "0";
				Jump = "1";
				Branch = "0";
				ALUSrc = "X";
				MemtoReg = "X";
				MemWrite = "X";
				MemRead = "X";
			} else if (opcode.equalsIgnoreCase("0000000000000111")) {
				ALUOP = "110";
				RegWrite = "1";
				Jump = "0";
				Branch = "0";
				ALUSrc = "0";
				MemtoReg = "0";
				MemWrite = "0";
				MemRead = "0";
			} else if (opcode.equalsIgnoreCase("0000000000001000")) {
				ALUOP = "111";
				RegWrite = "1";
				Jump = "0";
				Branch = "0";
				ALUSrc = "0";
				MemtoReg = "0";
				MemWrite = "0";
				MemRead = "0";
			}
		}

	}

}
