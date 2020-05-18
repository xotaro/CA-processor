package hardware;

import java.util.Scanner;

import AllStages.IF;

/**
 * @NOTE i'm using void ALUEvaluator (String OP ,String
 *       Operand1,StringOperand2);
 * @note INPUT IS BINARY !!
 *
 */
public class ALU {
	public static int zeroFlag;
	public static String ALUResult = "";

	public static String extend(String test) {
		int len = test.length();
		int target = 32;
		String z = "";
		for (int i = len; i < target; i++) {
			z = z + '0';
		}
		String result = z + test;
		return result;
	}

	public static void ALUEvaluator(String Op, String Operand1, String Operand2) throws MipsException {
		if (Operand1.length() > 32 || Operand2.length() > 32) {
			throw new MipsException("YOU CANT EXCEED 32 BIT !!!");
		}
		
		Integer op1 = Integer.parseInt(Operand1, 2);  
		
		Integer op2 = Integer.parseInt(Operand2, 2);
		System.out.println("1st Operand:" + Operand1 + "  - Decimal =>" + op1);
		System.out.println("2nd Operand:" + Operand2 + "  - Decimal =>" + op2);

		if (Op.equals("000")) {
			System.out.println("Operation Name :" + "ADD");
			Integer result = add(op1, op2);
			String fresult = extend(Integer.toBinaryString(result));
			System.out.println("Integer - Output: " + result);
			ALUResult = fresult;

			System.out.println("Output: " + fresult);
			if (result == 0) {
				zeroFlag = 1;

				System.out.println("Z-Flag Value: " + zeroFlag);
			} else {
				zeroFlag = 0;

				System.out.println("Z-Flag Value: " + zeroFlag);

			}

		} else if (Op.equals("001")) {
			System.out.println("Operation Name :" + "SUB");

			Integer result = sub(op1, op2);
			String fresult = extend(Integer.toBinaryString(result));
			System.out.println("Integer - Output: " + result);
			ALUResult = fresult;

			System.out.println("Output: " + fresult);
			if (result == 0) {
				zeroFlag = 1;

				System.out.println("Z-Flag Value: " + zeroFlag);

			} else {
				zeroFlag = 0;
				System.out.println("Z-Flag Value: " + zeroFlag);

			}
			if (ControlUnit.Branch.equalsIgnoreCase("1")) {

				if (zeroFlag == 1) {
					String imm = RegisterFile.read_16_op_imm(IF.Instruction);
					System.out.println("imm: " + imm);
					imm = InstructionMemory.extend("Imm", imm); // imm value of Branch
					Integer oo = Integer.parseInt(imm, 2);
					oo = oo * 4;
					oo = oo + (ProgramCounter.PCvalue);
					String newpc = Integer.toBinaryString(oo);
					newpc = InstructionMemory.extend("pc", newpc);
					ProgramCounter.PCvalue = oo;
					System.out.println("this is programcounter: " + ProgramCounter.PCvalue / 4);
					System.out.println("Branch!! - > this is new pc " + newpc);

				}
			}

		} else if (Op.equals("010")) {
			System.out.println("Operation Name :" + "MULT");

			Integer result = mult(op1, op2);
			String fresult = extend(Integer.toBinaryString(result));
			System.out.println("Integer - Output: " + result);
			ALUResult = fresult;

			System.out.println("Output: " + fresult);
			if (result == 0) {
				zeroFlag = 1;
				System.out.println("Z-Flag Value: " + zeroFlag);

			} else {
				zeroFlag = 0;
				System.out.println("Z-Flag Value: " + zeroFlag);

			}

		} else if (Op.equals("011")) {
			System.out.println("Operation Name :" + "OR");

			Integer result = or(op1, op2);
			String fresult = extend(Integer.toBinaryString(result));
			System.out.println("Integer - Output: " + result);
			ALUResult = fresult;

			System.out.println("Output: " + fresult);
			if (result == 0) {
				zeroFlag = 1;

				System.out.println("Z-Flag Value: " + zeroFlag);

			} else {
				zeroFlag = 0;

				System.out.println("Z-Flag Value: " + zeroFlag);

			}

		} else if (Op.equals("100")) {
			/// BLT
			System.out.println("Operation Name :" + "SLT");
			// op1 is rs1 , op2 is rs2
			Integer result = slt(op1, op2);
			String fresult = extend(Integer.toBinaryString(result));
			System.out.println("Integer - Output: " + result);
			System.out.println("Output: " + fresult);
			ALUResult = fresult;

			if (result == 0) {
				zeroFlag = 1;

				System.out.println("Z-Flag Value: " + zeroFlag);
			} else {
				zeroFlag = 0;

				System.out.println("Z-Flag Value: " + zeroFlag);

			}

			// System.out.println("op1: " + op1 + " " + "op2: " + op2);
			// System.out.println("result: " + result);

			if (result == 1) {
				String imm = RegisterFile.read_16_op_imm(IF.Instruction);
				System.out.println("imm: " + imm);
				imm = InstructionMemory.extend("Imm", imm); // imm value of Branch
				Integer oo = Integer.parseInt(imm, 2);
				oo = oo * 4;
				oo = oo + (ProgramCounter.PCvalue);
				String newpc = Integer.toBinaryString(oo);
				newpc = InstructionMemory.extend("pc", newpc);
				ProgramCounter.PCvalue = oo;
				System.out.println("this is programcounter: " + ProgramCounter.PCvalue / 4);
				System.out.println("Branch!! - > this is new pc " + newpc);

			}

		} else if (Op.equals("101")) {
			System.out.println("Operation Name :" + "AND");

			Integer result = and(op1, op2);
			String fresult = extend(Integer.toBinaryString(result));
			System.out.println("Integer - Output: " + result);
			ALUResult = fresult;

			System.out.println("Output: " + fresult);
			if (result == 0) {
				zeroFlag = 1;

				System.out.println("Z-Flag Value: " + zeroFlag);

			} else {
				zeroFlag = 0;

				System.out.println("Z-Flag Value: " + zeroFlag);

			}

		} else if (Op.equals("110")) {
			System.out.println("Operation Name :" + "SRL");

			Integer result = srl(op1, op2);
			String fresult = extend(Integer.toBinaryString(result));
			System.out.println("Integer - Output: " + result);
			ALUResult = fresult;

			System.out.println("Output: " + fresult);
			if (result == 0) {
				zeroFlag = 1;

				System.out.println("Z-Flag Value: " + zeroFlag);

			} else {
				zeroFlag = 0;

				System.out.println("Z-Flag Value: " + zeroFlag);

			}

		} else if (Op.equals("111")) {
			System.out.println("Operation Name :" + "SLL");
        //     System.out.println("op1: "+ op1 + " "  + "op2: " + op2 );
			Integer result = sll(op1, op2);
			String fresult = extend(Integer.toBinaryString(result));
			System.out.println("Integer - Output: " + result);
			ALUResult = fresult;

			System.out.println("Output: " + fresult);
			if (result == 0) {
				zeroFlag = 1;

				System.out.println("Z-Flag Value: " + zeroFlag);

			} else {
				zeroFlag = 0;

				System.out.println("Z-Flag Value: " + zeroFlag);

			}

		}

	}

	public static int srl(int i1, int i2) {
		int result = i1 >> i2;
		return result;
	}

	public static int sll(int i1, int i2) {
		int result = i1 << i2;
		return result;

	}

	public static int and(int i1, int i2) {
		int result = i1 & i2;
		return result;
	}

	public static int or(int i1, int i2) {
		int result = i1 | i2;
		return result;

	}

	public static int add(int i1, int i2) {
		int result = i1 + i2;

		return result;
	}

	public static int sub(int i1, int i2) {
		int result = i1 - i2;

		return result;
	}

	public static int mult(int i1, int i2) {
		int result = i1 * i2;

		return result;
	}

	public static int slt(int i1, int i2) {
		int result;
		if (i1 < i2) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}

	public static int nor(int i1, int i2) {
		int result = ~(i1 | i2);
		return result;
	}

	/*
	 * --------------Testing---------------------------- LAB TASK4
	 */

	public static void main(String[] args) throws MipsException {
//		System.out.println("NOTE@: **~INPUT IS BINARY~** ");
//		System.out.println("Input: ");
//		Scanner sc = new Scanner(System.in);
//		System.out.print("1st Operand: ");
//		String x = sc.nextLine();
//		System.out.print("2nd Operand: ");
//		String y = sc.nextLine();
//		System.out.print("Operation: ");
//		String z = sc.nextLine();
//		System.out.println("----------");
//		System.out.println("Output: ");
//		ALUEvaluator(z, x, y);

		int x = 10;
		String p = Integer.toBinaryString(10);
		System.out.println(x << 2);

	}
}
