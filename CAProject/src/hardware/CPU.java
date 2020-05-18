package hardware;

import java.io.IOException;
import java.util.ArrayList;

import AllStages.EXEC;
import AllStages.ID;
import AllStages.IF;
import AllStages.MemAcc;
import AllStages.WB;
import hardware.*;

/**
 * Run Single_Cycle() method
 */
public class CPU {
	ProgramCounter pc = new ProgramCounter();
	RegisterFile reg = new RegisterFile();
	InstructionMemory mem = new InstructionMemory(); // 1024 byte
	ALU alu = new ALU();
	public static int clock_cycle = 0;
	IF InstructionFetch = new IF();
	ID InstructionDecode = new ID();
	EXEC execution = new EXEC();
	MemAcc MemoryAccess = new MemAcc();
	WB writeBack = new WB();
	static Cache Cache = new Cache();
	public static Memory Datamem = new Memory();
	PipeReg RID = new PipeReg();
	PipeReg RExec = new PipeReg();
	PipeReg RMem = new PipeReg();
	PipeReg RWB = new PipeReg();
	public static boolean Multi = false;

	public CPU() throws IOException, MipsException {
		System.out.println("The loaded Program from File...");
		mem.loadProgram();
	}

	public void Single_Cycle() throws IOException, MipsException {
		System.out.println("");
		int n_inst = mem.toinst.size(); // get the number of inst // to run the pc
		System.out.println("this is number of inst: " + n_inst);
		for (int i = ProgramCounter.PCvalue / 4; i < n_inst; i = ProgramCounter.PCvalue / 4) {
			System.out.println("this is i" + i);
			System.out.println("this is PC" + ProgramCounter.PCvalue);
			System.out.println("----------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			System.out.println(mem.toinst.get((i)) + " " + "Instruction Fetch:");
			InstructionFetch.InstFetch(pc.PCvalue);
			System.out.println("-------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			System.out.println(mem.toinst.get(i) + " " + "Instruction Decode:");
			InstructionDecode.curr_inst = i;
			InstructionDecode.InstDecode(InstructionFetch.Instruction, InstructionFetch.pcc);
			System.out.println("-------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			System.out.println(mem.toinst.get(i) + " " + "Execution:");
			execution.Execute(ControlUnit.ALUOP, ControlUnit.ALUSrc, InstructionDecode.data1, InstructionDecode.data2,
					InstructionDecode.signex, InstructionFetch.pcc);
			System.out.println("-------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			System.out.println(mem.toinst.get(i) + " " + "Memory Access:");
			MemoryAccess.MemAccess(ALU.ALUResult, InstructionDecode.data2, "", "", "", "", "", "",
					InstructionFetch.pcc);
			System.out.println("-------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			System.out.println(mem.toinst.get(i) + " " + "Write Back:");
			writeBack.WriteBack(ALU.ALUResult, MemoryAccess.ReadData, "", "", InstructionFetch.pcc);
			System.out.println("-------------------------------");

		}
	}

	public void Multi_cycle() throws IOException, MipsException {
		Multi = true;
		System.out.println("");
		int n_inst = mem.toinst.size(); // get the number of inst // to run the pc
		System.out.println("this is number of inst: " + n_inst);
		while (true) {
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			PipeReg.control = 0;
			InstructionFetch.InstFetch(pc.PCvalue);
			System.out.println("-------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			PipeReg.control = 1;
			InstructionDecode.InstDecode(PipeReg.ID_Instruction, PipeReg.ID_pc);
			System.out.println("-------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			PipeReg.control = 2;
			execution.Execute(PipeReg.Ex_ALUOP, PipeReg.Ex_ALUSrc, PipeReg.Ex_data1, PipeReg.Ex_data2,
					PipeReg.Ex_signex, PipeReg.Ex_pc);
			System.out.println("-------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			PipeReg.control = 3;
			MemoryAccess.MemAccess(PipeReg.Mem_ALUResult, PipeReg.Mem_data2, "", "", "", PipeReg.Mem_MemWrite,
					PipeReg.Mem_MemRead, "", PipeReg.Mem_pc);
			System.out.println("-------------------------------");
			clock_cycle++;
			System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
			PipeReg.control = 4;
			writeBack.WriteBack(PipeReg.WB_ALUResult, PipeReg.WB_Readdata, PipeReg.WB_MemToReg, "", PipeReg.WB_pc);

			System.out.println("-------------------------------");

			int target = n_inst - 1;
			System.out.println("this is $$$$ !!!~~~  target: " + target + " ********************************");
			while (target != 0) {
				clock_cycle++;
				System.out.println("(" + "After Clock Cycle: " + clock_cycle + ")");
				PipeReg.control = 4;
				writeBack.WriteBack(PipeReg.WB_ALUResult, PipeReg.WB_Readdata, PipeReg.WB_MemToReg, "", PipeReg.WB_pc);
				System.out.println("-------------------------------");
				target--;
			}
			break;

		}

	}

	public static void Reg_Value() {
		for (int i = 0; i < RegisterFile.registers.length; i++) {
			System.out.println(RegisterFile.registers[i].Regname + "  " + RegisterFile.registers[i].Regvalue);
		}
	}

	public static void fill_reg() {
		for (int i = 1; i < RegisterFile.registers.length; i++) {
			RegisterFile.registers[i].Regvalue = "00000000000000000000000000000111"; // reg value -> 15 , each reg

		}
	}

	public static void show_mem() {
		int len = Datamem.data.length;
		for (int i = 0; i < 12; i++) {
			System.out.println(i + "-> " + Datamem.data[i]);
		}
	}

	public static void main(String[] args) throws IOException, MipsException

	{
		CPU cpu = new CPU();
//		fill_reg();            // to put values in registers
//	    cpu.Single_Cycle();      // to run single cycle
//		cpu.Multi_cycle();       // to run multi cycle
//		Reg_Value();           // to see reg_value
//		show_mem();            // to see Datamemory value

	}
}
