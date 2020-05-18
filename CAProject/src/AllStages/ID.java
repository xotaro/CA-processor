package AllStages;

import java.util.ArrayList;

import hardware.CPU;
import hardware.ControlUnit;
import hardware.InstructionMemory;
import hardware.MipsException;
import hardware.PipeReg;
import hardware.ProgramCounter;
import hardware.RegisterFile;

/**
 * Instruction Decode: Decoding the fetched instruction in the Register File.
 *
 * control 1
 */
public class ID {
	public static String functt = ""; // a5er 5 bits
	public static int curr_inst = 0; // curr inst
	public static String data1 = "";
	public static String data2 = "";
	public static String signex = "";
	static boolean time1 = false;

	public static void InstDecode(String inst, String pc) throws MipsException {
		PipeReg.ID_Work = true;

		System.out.println("------Instruction Decode Stage -----");
		int pr_pc;
		if (CPU.Multi) {
			pr_pc = Integer.parseUnsignedInt(PipeReg.ID_pc, 2);
			// System.out.println("this i : " +i);
			pr_pc = (pr_pc / 4) - 1;
			System.out.println("Instruction: " + " " + InstructionMemory.toinst.get(pr_pc));

		} else {
			// check this
			pr_pc = (ProgramCounter.PCvalue - 4) / 4;

		}

		@SuppressWarnings("unchecked")
		// int y = (pr_pc - 1) / 4;
		// System.out.println(": Curr inst" + " " + y);

		ArrayList<Object> info = (ArrayList<Object>) InstructionMemory.infos.get(pr_pc); // Type
		// System.out.println("ID info: " + info + ": Curr inst" + " " + y);

		String type = (String) info.get(1);
		// String inst_name = (String) info.get(0);
		String op_imm = RegisterFile.read_16_op_imm(inst);
		// String op_code = RegisterFile.read_op(inst);
		String funct = RegisterFile.read_funct(inst);
		String flag = RegisterFile.read_flag(inst);
		// String add_26 = RegisterFile.read_addr_j(inst);
		String rs1 = RegisterFile.readrs1(inst);
		String rs2 = RegisterFile.readrs2(inst); // ReadData1
		String rdd = RegisterFile.readrd(inst); // ReadData2
		String rs1_ = RegisterFile.readReg(rs1);
		String rs2_ = RegisterFile.readReg(rs2);
		String rd = RegisterFile.readReg(rdd); // ReadData
		System.out.println("this rdd" + " " + rdd);
		// System.out.println(inst);
		// System.out.println("this rdd: " + rdd);
		String sign_extend = "";
		String signx = "";
		// System.out.println("Flag" + " " + flag);
		// System.out.println("op_imm: " + op_imm + " " + op_imm.length());
		if (type.equalsIgnoreCase("R") || type.equalsIgnoreCase("S")) {
			ContUnit(flag, op_imm);
		} else {
			// System.out.println("func: " + " " + funct + " " + funct.length());
			ContUnit(flag, funct);
		}
		System.out.println("this is the instruction: " + inst);
		if (!type.equalsIgnoreCase("R")) {
			sign_extend = RegisterFile.read_16_op_imm(inst);
			signx = SignExtend("", sign_extend);
		}
		functt = funct;
		System.out.println("Read data1: " + rs2_);
		System.out.println("Read data2: " + rd);

		if (InstructionMemory.numberofinst == ProgramCounter.PCvalue / 4) {
			System.out.println("Next pc ->" + " this is Last instruction");
		} else {
			System.out.println("Next PC ->     " + pc);
		}
		if (type.equalsIgnoreCase("i") || type.equalsIgnoreCase("j")) {
			System.out.println("sign_extend:" + signx);
		} else {
			System.out.println("sign_extend" + "00000000000000000000000000000000");
		}

		data1 = rs2_;
		data2 = rd;

		// System.out.println("data1: " + data1);
		// System.out.println("data2: " + data2);
		System.out.println("op: " + op_imm + " " + op_imm.length());
		System.out.println("funct:  " + funct + " " + funct.length());

		if (ControlUnit.Branch.equalsIgnoreCase("1")) {
			System.out.println("etneredd?");
			data1 = rs1_;
			data2 = rs2_;
		}
		if (op_imm.equalsIgnoreCase("0000000000000111") || op_imm.equalsIgnoreCase("0000000000001000")) {
			System.out.println("hehehe im here");
			data1 = rs2_;
			funct = SignExtend("", funct);
			data2 = funct;
		}
		if (funct.equalsIgnoreCase("00001")) {
			// y3ny deh sw
			data2 = rs1_;
		}
		signex = signx;
		System.out.println("Branch:" + ControlUnit.Branch + " " + "MemRead:" + ControlUnit.MemRead + " " + "MemtoReg:"
				+ ControlUnit.MemtoReg + " " + "ALUOP:" + ControlUnit.ALUOP + " " + "MemWrite:" + ControlUnit.MemWrite
				+ " " + "ALUSrc:" + ControlUnit.ALUSrc + " " + "RegWrite:" + ControlUnit.RegWrite);

		if (!PipeReg.isExec) {
			PipeReg.Ex_data1 = data1;
			PipeReg.Ex_data2 = data2;
			PipeReg.Ex_signex = signex;
			PipeReg.Ex_ALUOP = ControlUnit.ALUOP;
			PipeReg.Ex_ALUSrc = ControlUnit.ALUSrc;
			// PipeReg.Mem_MemRead = ControlUnit.MemRead;
			// PipeReg.Mem_MemWrite = ControlUnit.MemWrite;
			PipeReg.Ex_MemRead = ControlUnit.MemRead;
			PipeReg.Ex_Instruction = PipeReg.ID_Instruction;

			PipeReg.Ex_MemWrite = ControlUnit.MemWrite;
			PipeReg.Ex_MemToReg = ControlUnit.MemtoReg;
			PipeReg.Ex_pc = PipeReg.ID_pc;
			PipeReg.ID_Work = false;

		}
		PipeReg.Ex_data1 = data1;
		PipeReg.Ex_data2 = data2;
		PipeReg.Ex_signex = signex;
		PipeReg.Ex_ALUOP = ControlUnit.ALUOP;
		PipeReg.Ex_ALUSrc = ControlUnit.ALUSrc;
		PipeReg.Ex_MemRead = ControlUnit.MemRead;
		PipeReg.Ex_Instruction = PipeReg.ID_Instruction;

		PipeReg.Ex_MemWrite = ControlUnit.MemWrite;
		PipeReg.Ex_MemToReg = ControlUnit.MemtoReg;
		PipeReg.Ex_pc = PipeReg.ID_pc;

		System.out.println("Exec data2: " + PipeReg.Ex_data2);

		if (PipeReg.control == 1) {
			if (PipeReg.isWB && !PipeReg.isWB) {
				WB.WriteBack(PipeReg.WB_ALUResult, PipeReg.WB_Readdata, PipeReg.WB_MemToReg, "", PipeReg.WB_pc);
				System.out.println(" ");

			}
			if (PipeReg.isMem && !PipeReg.Mem_End) {
				MemAcc.MemAccess(PipeReg.Mem_ALUResult, PipeReg.Mem_data2, "", "", "", PipeReg.Mem_MemWrite,
						PipeReg.Mem_MemRead, "", PipeReg.Mem_pc);
				System.out.println(" ");

			}
			if (PipeReg.isExec && !PipeReg.Exec_End) {
				EXEC.Execute(PipeReg.Ex_ALUOP, PipeReg.Ex_ALUSrc, PipeReg.Ex_data1, PipeReg.Ex_data2, PipeReg.Ex_signex,
						PipeReg.Ex_pc);
				System.out.println(" ");

			}
			if (!PipeReg.IF_End) {
				IF.InstFetch(ProgramCounter.PCvalue);
				System.out.println(" ");

			}

		}

		if (PipeReg.IF_End) {
			PipeReg.ID_End = true;
		}
		if (InstructionMemory.numberofinst == 2 && !time1) {
			PipeReg.ID_End = false;
			time1 = true;
		}
		PipeReg.ID_Work = false;
		PipeReg.isExec = true;

	}

	public static String SignExtend(String type, String test) {
		int len = test.length();
		int target = 32;
		String z = "";
		for (int i = len; i < target; i++) {
			z = z + '0';
		}
		String result = z + test;
		return result;
	}

	public static void ContUnit(String flag, String opcode) {
		ControlUnit.MainControl(flag, opcode);
	}
}
