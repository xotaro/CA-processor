package AllStages;

import hardware.CPU;
import hardware.Cache;
import hardware.ControlUnit;
import hardware.InstructionMemory;
import hardware.Memory;
import hardware.MipsException;
import hardware.PipeReg;
import hardware.ProgramCounter;

/**
 * Memory Access: Write/Read data into the Data Memory.
 * 
 * Control 3
 */
public class MemAcc {
	public static String ReadData = "";
	static boolean time3 = false;

	public static void MemAccess(String ALUResult, String ReadData2, String SignExtend, String ZeroFlag,
			String BranchAddressResult, String MemWrite, String MemRead, String Branch, String pc)
			throws MipsException {
		PipeReg.Mem_Work = true;
		String MemWrite_;
		String MemRead_;
		System.out.println("--------Mem Access Stage--------");
		if (CPU.Multi) {
			int pr_pc = Integer.parseUnsignedInt(PipeReg.Mem_pc, 2);
			pr_pc = (pr_pc / 4) - 1;
			System.out.println("Instruction: " + " " + InstructionMemory.toinst.get((pr_pc)));
			MemWrite_ = MemWrite;
			MemRead_ = MemRead;
		} else {
			MemWrite_ = ControlUnit.MemWrite;
			MemRead_ = ControlUnit.MemRead;
		}

		if (MemWrite_.equalsIgnoreCase("1")) { // changed from controlunit. to direct mem
			// System.out.println("ReadData2: " + ReadData2);
			Memory.write(ALUResult, ReadData2);
		} else if (MemRead_.equalsIgnoreCase("1")) {
			
			String read_value = Cache.read(Memory.data,ALUResult);
			ReadData = read_value;

		} else {

		}

		if (MemWrite.equalsIgnoreCase("0") && MemRead.equalsIgnoreCase("0") || ControlUnit.Jump.equalsIgnoreCase("1")) {
			System.out.println("This instruction doesn't need memory");
			System.out.println("MemRead:" + ControlUnit.MemRead);
			System.out.println("MemWrite: " + ControlUnit.MemWrite);
		} else {
			System.out.println("ReadData: " + ReadData);
			System.out.println("ALUResult: " + ALUResult);
			System.out.println("memory word read: " + PipeReg.Mem_MemRead);
			System.out.println("RegWrite: " + ControlUnit.RegWrite);
			System.out.println("MemToReg:" + PipeReg.Mem_MemToReg);
			System.out.println("MemWrite" + PipeReg.Mem_MemWrite);
		}
		if (!PipeReg.isWB) {

			PipeReg.WB_ALUResult = PipeReg.Mem_ALUResult;
			PipeReg.WB_MemToReg = PipeReg.Mem_MemToReg;
			PipeReg.WB_Readdata = ReadData;
			PipeReg.WB_pc = PipeReg.Mem_pc;
			PipeReg.Mem_Work = false;

		}
		PipeReg.WB_ALUResult = PipeReg.Mem_ALUResult;
		PipeReg.WB_MemToReg = PipeReg.Mem_MemToReg;
		PipeReg.WB_pc = PipeReg.Mem_pc;
		PipeReg.WB_Readdata = ReadData;

		if (PipeReg.control == 3) {
			if (PipeReg.isWB && !PipeReg.WB_End) {
				WB.WriteBack(PipeReg.WB_ALUResult, PipeReg.WB_Readdata, PipeReg.WB_MemToReg, "", PipeReg.WB_pc);
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

		if (PipeReg.IF_End && PipeReg.Exec_End) {
			PipeReg.Mem_End = true;
		}
		if (InstructionMemory.numberofinst == 2 && !time3) {
			PipeReg.Mem_End = false;
			time3 = true;
		}

		PipeReg.Mem_Work = false;
		PipeReg.isWB = true;

	}

}
