package AllStages;

import hardware.*;

/**
 * Instruction Fetch: Fetching instructions from the Instruction Memory
 * 
 * 
 */
public class IF {
	public static String Instruction = "";
	public static String pcc = "";
	public static boolean last = false;

	/**
	 * This method takes in the instruction address from the program counter as an
	 * input, fetches and outputs the instruction. Inputs: PC address. (32-bits)
	 * Outputs: Instruction. (32-bits), PC incremented by 4. (32-bits)
	 * 
	 * @param inst_addr
	 * @throws MipsException
	 */
	public static void InstFetch(int inst_addr) throws MipsException {
		if ((InstructionMemory.numberofinst - 1) == ProgramCounter.PCvalue / 4) {
			last = true;
			PipeReg.IF_End = true;
		}
		System.out.println("------------Instruction Fetch Stage ---------");
		System.out.println("Instruction: " + " " + InstructionMemory.toinst.get(ProgramCounter.PCvalue / 4));
		PipeReg.IF_Work = true;
		Integer pc_value = ProgramCounter.PCvalue;
		String inst = InstructionMemory.readInstruction(pc_value);
		ProgramCounter.PCvalue = pc_value + 4;
		String pc = Integer.toBinaryString(ProgramCounter.PCvalue);
		pc = InstructionMemory.extend("PC", pc);
		Instruction = inst;
		pcc = pc;
		/*
		 * for pipe
		 */
		System.out.println("Instruction -> " + inst);
		if (InstructionMemory.numberofinst == ProgramCounter.PCvalue / 4) {
			System.out.println("Next pc ->" + " this is Last instruction");
		} else {
			System.out.println("Next PC ->     " + pc);
		}
		if (!PipeReg.isID) {
			PipeReg.ID_Instruction = inst;
			PipeReg.ID_pc = pcc;
			PipeReg.IF_Work = false;

		}
		PipeReg.ID_Instruction = inst;
		PipeReg.ID_pc = pcc;
		if (PipeReg.control == 0) {
			if (PipeReg.isWB && !PipeReg.WB_End) {
				WB.WriteBack(PipeReg.WB_ALUResult, PipeReg.WB_Readdata, PipeReg.WB_MemToReg, "", PipeReg.WB_pc);
			}
			if (PipeReg.isMem && !PipeReg.Mem_End) {
				MemAcc.MemAccess(PipeReg.Mem_ALUResult, PipeReg.Mem_data2, "", "", "", PipeReg.Mem_MemWrite,
						PipeReg.Mem_MemRead, "", PipeReg.Mem_pc);
			}

			if (PipeReg.isExec && !PipeReg.Exec_End) {
				EXEC.Execute(PipeReg.Ex_ALUOP, PipeReg.Ex_ALUSrc, PipeReg.Ex_data1, PipeReg.Ex_data2, PipeReg.Ex_signex,
						PipeReg.Ex_pc);
			}

			if (PipeReg.isID && !PipeReg.ID_End) {
				ID.InstDecode(PipeReg.ID_Instruction, PipeReg.ID_pc);
			}

		}
		PipeReg.IF_Work = false;
		PipeReg.isID = true;

	}

	/**
	 * ProgCount: This method points to the next instruction to be executed. PC is
	 * incremented by 4 after each instruction is executed. A branch instruction
	 * alters the flow of control by modifying the PC.
	 */
	public void ProgCount() {

	}
}
