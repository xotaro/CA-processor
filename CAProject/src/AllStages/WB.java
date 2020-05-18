package AllStages;

import hardware.CPU;
import hardware.ControlUnit;
import hardware.InstructionMemory;
import hardware.MipsException;
import hardware.PipeReg;
import hardware.ProgramCounter;
import hardware.RegisterFile;

/**
 * WriteBack: Write instruction results into the register file (In case of
 * R-type Instructions). Control 4
 *
 */
public class WB {
	static boolean time4 = false;

	public static void WriteBack(String ALUResult, String ReadData, String MemToReg, String RegDst, String pc)
			throws MipsException {
		PipeReg.WB_Work = true;
		String MemToReg_ = "";
		System.out.println("-----WriteBack Stage------");
		int pr_pc = 0;
		if (CPU.Multi) {
			pr_pc = Integer.parseUnsignedInt(PipeReg.WB_pc, 2);
			System.out.println("Instruction: " + " " + InstructionMemory.toinst.get((pr_pc / 4) - 1));
			MemToReg_ = MemToReg;
			pr_pc = (pr_pc - 4);
		} else {
			MemToReg_ = ControlUnit.MemtoReg;
		}
		System.out.println("pr_pc: " + pr_pc);
		if (MemToReg_.equalsIgnoreCase("0")) {
			String rs1 = "";
			if (CPU.Multi) {
				String curr_instr = InstructionMemory.readInstruction(pr_pc);
				rs1 = RegisterFile.readrs1(curr_instr); // was IF instruction
			}
			if (!CPU.Multi) {
				rs1 = RegisterFile.readrs1(IF.Instruction);
			}
			// ReadData
			RegisterFile.write(rs1, ALUResult);
			System.out.println("this rs1: " + rs1);
			System.out.println("ALU,WriteData: " + ALUResult);

		} else if (MemToReg_.equalsIgnoreCase("1")) {
			String rs1 = "";
			if (CPU.Multi) {
				String curr_instr = InstructionMemory.readInstruction(pr_pc);
				rs1 = RegisterFile.readrs1(curr_instr); // was IF instruction
			}
			if (!CPU.Multi) {
				rs1 = RegisterFile.readrs1(IF.Instruction);
			}
			RegisterFile.write(rs1, ReadData);
			System.out.println("Readdata -> WriteData: " + ReadData);

		} else {
			System.out.println("NO WRITE DATA");

		}
		if (PipeReg.control == 4) {

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
			if (PipeReg.isID && !PipeReg.ID_End) {
				ID.InstDecode(PipeReg.ID_Instruction, PipeReg.ID_pc);
				System.out.println(" ");

			}
			if (!PipeReg.IF_End) {
				IF.InstFetch(ProgramCounter.PCvalue);
				System.out.println(" ");
			}

		}
		if (PipeReg.IF_End && PipeReg.Mem_End) {
			PipeReg.WB_End = true;
		}
		if (InstructionMemory.numberofinst == 2 && !time4) {
			PipeReg.WB_End = false;
			time4 = true;
		}
		PipeReg.WB_Work = false;

	}
}
