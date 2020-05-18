package AllStages;

import hardware.ALU;
import hardware.CPU;
import hardware.InstructionMemory;
import hardware.MipsException;
import hardware.PipeReg;
import hardware.ProgramCounter;
import hardware.RegisterFile;

/**
 * Execute: Performing the required operation in the Arithmetic Logic Unit
 * (ALU). Control 2
 */
public class EXEC {
	static boolean time2 = false;

	public static void Execute(String ALUOp, String ALUSrc, String ReadData1, String ReadData2, String SignExtend,
			String PC) throws MipsException {
		PipeReg.Exec_Work = true;

		System.out.println("-----Execution Stage ------- ");
		int pr_pc;
		if (CPU.Multi) {
			pr_pc = Integer.parseUnsignedInt(PipeReg.Ex_pc, 2);
			pr_pc = (pr_pc / 4) - 1;
			System.out.println("Instruction: " + " " + InstructionMemory.toinst.get((pr_pc)));
		}
		if (ALUSrc.equalsIgnoreCase("0")) {
			ALU.ALUEvaluator(ALUOp, ReadData1, ReadData2);

		} else if (ALUSrc.equalsIgnoreCase("1")) { // here is the immediate mafrod yt3mlha extend mn ele ableha [30-15]
													// w fl rasma el sign extend tt7at
			ALU.ALUEvaluator(ALUOp, ReadData1, SignExtend);

		} else if (ALUOp.equalsIgnoreCase("XXX")) {
			// kda anta jump
			String inst_j;
			if (CPU.Multi) {
				inst_j = PipeReg.Ex_Instruction;
			} else {
				inst_j = IF.Instruction;
			}
			int x4 = Integer.parseInt(RegisterFile.read_addr_j(inst_j), 2);
			// System.out.println("x4: "+x4);
			x4 = x4 * 4;
			String x28 = Integer.toBinaryString(x4);
			x28 = InstructionMemory.extend("28", x28);
			String tojump = conc_pc_j(x28);
			System.out.println("im new PC to jump: " + tojump + " " + tojump.length());
			int f_pc = Integer.parseInt(tojump, 2);
			IF.pcc = tojump;
			System.out.println(f_pc);
			ProgramCounter.PCvalue = f_pc;
			return;
			// System.out.println("jump" + RegisterFile.read_addr_j(IF.Instruction));
			// ALU.ALUEvaluator("0110", ReadData1, ReadData2);
		}

		if (InstructionMemory.numberofinst == ProgramCounter.PCvalue / 4) {
			System.out.println("Next pc ->" + " this is Last instruction");
		} else {
			System.out.println("Next PC ->     " + PC);
		}
		if (!PipeReg.isMem) {
			PipeReg.Mem_MemRead = PipeReg.Ex_MemRead;
			PipeReg.Mem_MemWrite = PipeReg.Ex_MemWrite;
			PipeReg.Mem_MemToReg = PipeReg.Ex_MemToReg;
			PipeReg.Mem_ALUResult = ALU.ALUResult;
			PipeReg.Mem_data2 = PipeReg.Ex_data2; // bta3 el instruction decode ele ableha el exec 3yzaha
			PipeReg.Exec_Work = false;
			PipeReg.Mem_pc = PipeReg.Ex_pc;
		}
		PipeReg.Mem_ALUResult = ALU.ALUResult;
		PipeReg.Mem_pc = PipeReg.Ex_pc;
		PipeReg.Mem_MemRead = PipeReg.Ex_MemRead;
		PipeReg.Mem_MemWrite = PipeReg.Ex_MemWrite;
		PipeReg.Mem_MemToReg = PipeReg.Ex_MemToReg;
		PipeReg.Mem_data2 = PipeReg.Ex_data2; // bta3 el instruction decode ele ableha el exec 3yzaha
		// System.out.println("Exec data2: " + PipeReg.Ex_data2);

		// System.out.println("IAM EXEC " + PipeReg.Mem_pc);
		if (PipeReg.control == 2) {

			if (PipeReg.isWB && !PipeReg.WB_End) {
				WB.WriteBack(PipeReg.WB_ALUResult, PipeReg.WB_Readdata, PipeReg.WB_MemToReg, "", PipeReg.WB_pc);
				System.out.println(" ");
			}
			if (PipeReg.isMem && !PipeReg.Mem_End) {
				MemAcc.MemAccess(PipeReg.Mem_ALUResult, PipeReg.Mem_data2, "", "", "", PipeReg.Mem_MemWrite,
						PipeReg.Mem_MemRead, "", PipeReg.Mem_pc);
				System.out.println(" ");

			}
			if (PipeReg.isID && !PipeReg.ID_End) {
				ID.InstDecode(PipeReg.ID_Instruction, PipeReg.ID_pc);
				System.out.println(" ");

			}
			if (!PipeReg.IF_End) {
				IF.InstFetch(ProgramCounter.PCvalue);
				System.out.println(" ");

			}

		}

		if (PipeReg.IF_End && PipeReg.ID_End) {
			PipeReg.Exec_End = true;
		}
		if (InstructionMemory.numberofinst == 2 && !time2) {
			PipeReg.Exec_End = false;
			time2 = true;
		}
		PipeReg.Exec_Work = false;
		PipeReg.isMem = true;

	}

	public static String conc_pc_j(String x28) {
		String pc_4 = "";
		String result = "";
		for (int i = 0; i < 4; i++) {
			pc_4 = pc_4 + IF.pcc.charAt(i);
		}
		result = pc_4 + "" + x28;
		return result;
	}

}
