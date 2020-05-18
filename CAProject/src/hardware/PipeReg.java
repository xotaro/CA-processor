package hardware;

public class PipeReg {
	public static boolean IF_Work = false;
	public static boolean IF_End = false;

	public static int control = 10;
	/*
	 * bos lazm yb2a feh controller 5 stages 5 controller 0 1 2 3 4
	 */
	public static boolean isID = false;
	public static String ID_Instruction = "";
	public static String ID_pc = "";
	public static boolean ID_End = false;
	public static boolean ID_Work = false;

// *********** ID *************//
	public static boolean isExec = false;
	public static String Ex_ALUOP = "";
	public static String Ex_ALUSrc = "";
	public static String Ex_data1 = "";
	public static String Ex_data2 = "";
	public static String Ex_signex = "";
	public static String Ex_pc = "";
	public static String Ex_MemRead = "";
	public static String Ex_MemWrite = "";
	public static String Ex_MemToReg = "";
	public static String Ex_Instruction = "";


	public static boolean Exec_End = false;
	public static boolean Exec_Work = false;

//*********** Exec *************//
	public static boolean isMem = false;
	public static String Mem_ALUResult = "";
	public static String Mem_data2 = "";
	public static String Mem_pc = "";
	public static String Mem_MemWrite = "";
	public static String Mem_MemRead = "";
	public static boolean Mem_End = false;
	public static boolean Mem_Work = false;
	public static String Mem_MemToReg = "";

//*********** MemAcc *************//
	public static boolean isWB = false;
	public static String WB_ALUResult = "";
	public static String WB_Readdata = "";
	public static String WB_pc = "";
	public static String WB_MemToReg = "";
	public static boolean WB_End = false;
	public static boolean WB_Work = false;

//*********** WB *************//

}
