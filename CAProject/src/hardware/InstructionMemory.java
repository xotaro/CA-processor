package hardware;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

/**
 * 
  * 
 * 
 */

public class InstructionMemory {
	public static String program_path = "data/Program.txt";
	public static String ISA = "data/ISA.csv";
	public static String[] instructions;
	public static int int_count = 0;
	public static ArrayList toinst = new ArrayList<>();
	public static String[] supportInst = { "sub", "add", "lw", "sw", "beq", "j", "sll", "srl", "blt", "andi", "addi",
			"sltu", "or", "mult" };
	public static int numberofinst = 0;
	public static Hashtable label_index = new Hashtable<>();
	public static ArrayList label_arr = new ArrayList<>();
	public static ArrayList<Object> infos = new ArrayList<Object>(); // give me infos about instruction
	// inst -> 0 , type ->1

	/**
	 * Instruction Memory Size = 1024 x 32 bits i.e 1024 x 4 Bytes = 4096
	 */
	public InstructionMemory() {
		instructions = new String[4096];
	}

	/**
	 * load the program to memory
	 * 
	 * @throws IOException
	 * @throws MipsException
	 */
	public void loadProgram() throws IOException, MipsException {
		ArrayList<String> inst = new ArrayList<String>();
		try {
			File myObj = new File(program_path);
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (data.isEmpty() || data == null) {
					Parser(inst);
					return;
				}
				inst.add(data);
			}
			myReader.close();

		} catch (FileNotFoundException e) {
			System.out.println("ERROR!!!!!!!");
			e.printStackTrace();
		}
		System.out.println(inst);
		toinst = inst;
		parselabel(inst);
		Parser(inst);
		numberofinst = Numberof_INS();
	}

	public static void parselabel(ArrayList<String> inst) throws IOException, MipsException {

		ArrayList<String> info = new ArrayList<String>();
		for (int i = 0; i < inst.size(); i++) {
			String instr = inst.get(i);
			String finals = instr.replace(' ', ',');
			String[] sp = finals.split(",");
			List<String> list = Arrays.asList(sp);
			if (list.contains(":")) {
				label_index.put(sp[0], i);
				label_arr.add(i);
			} else {
				String ch = list.get(0);
				String re = "";
				for (int j = 0; j < ch.length(); j++) {
					if (ch.charAt(j) == ':') {
						label_index.put(re, i);
						label_arr.add(i);
						break;
					} else {
						re = re + ch.charAt(j);
					}
				}
			}

		}

	}

	public static int Numberof_INS() {
		int c = 0;
		for (int i = 0; i < instructions.length; i++) {

			if (instructions[i] == null) {
				break;
			}
			c++;
		}

		return c / 4;

	}

	/**
	 * 0- > Inst , ->1 OP ->FUNCT > 2 , TYPE ->3 , FLAG ->4
	 */
	public static ArrayList<String> getInstInfo(String Instruction) throws IOException {

		ArrayList<String> Result = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(ISA));
		String line = br.readLine();
		while (line != null) {
			String[] info = line.split(",");
			String Inst = info[0];

			if (Inst.equalsIgnoreCase(Instruction)) {
				String OP = info[1];
				String FUNCT = info[2];
				String TYPE = info[3];
				String FLAG = info[4];
				Result.add(OP);
				Result.add(FUNCT);
				Result.add(TYPE);
				Result.add(FLAG);

			}

			line = br.readLine();

		}

		br.close();
		return Result;

	}

	/**
	 * 0- > Inst , ->1 OP ->FUNCT > 2 , TYPE ->3 , FLAG ->4 OP , FUNCT , TYPE , FLAG
	 */
	public static void Parser(ArrayList<String> inst) throws IOException, MipsException {
		System.out.println(label_index);
		ArrayList<String> info = new ArrayList<String>();

		for (int i = 0; i < inst.size(); i++) {
			String binary_inst = "";
			// i is the first instruction
			String instr = inst.get(i);
			String finals = instr.replace(' ', ',');
			String[] sp = finals.split(",");
			boolean Label_exist = false;
			List<String> list = Arrays.asList(sp);
			if (list.contains(":")) {
				Label_exist = true;
			} else {
				String ch = list.get(0);
				String re = "";
				for (int j = 0; j < ch.length(); j++) {
					if (ch.charAt(j) == ':') {
						Label_exist = true;
						break;
					} else {
						re = re + ch.charAt(j);
					}
				}
			}
			if (Label_exist) {
				for (int z = 0; z < supportInst.length; z++) {
					if (list.contains(supportInst[z])) {
						info = getInstInfo(supportInst[z]);
					}
				}

			} else {
				info = getInstInfo(sp[0]);
			}
			if (info.isEmpty()) {
				throw new MipsException("Compilation error");
			}
			// System.out.println("this is info: " + info);
			String op = info.get(0);
			String funct = info.get(1);
			String type = info.get(2);
			String flag = info.get(3);
			String rs1 = "";
			String rs2 = "";
			String rd = "";
			boolean rdf = true;
			boolean rsf = true;
			boolean rtf = true;
			boolean labelflag = true;
			String Imm_Add = "";
			String jlabel = "";
			String label = "";
			String inst_ = "";
			// inst_ = sp[0].toLowerCase(); // not always rightt
			if (Label_exist) {
				for (int j = 1; j < sp.length; j++) {
					if (!sp[j].equalsIgnoreCase("")) {
						if (sp[j].equalsIgnoreCase(":")) {
							continue;
						}
						inst_ = sp[j].toLowerCase();
						break;
					}
				}
			} else {
				inst_ = sp[0].toLowerCase();
			}
			//System.out.println("This is inst_ : " + inst_);
			for (int z = 1; z < sp.length; z++) {

				if (type.equalsIgnoreCase("R")) {

					if (sp[z].equalsIgnoreCase(":")) {
						continue;
					}
					if (!sp[z].equalsIgnoreCase("") && !sp[z].equalsIgnoreCase(":")) {
						if (Label_exist) {
							inst_ = sp[z].toLowerCase();
							Label_exist = false;
							continue;

						}
						if (rdf) {

							rs1 = sp[z].toLowerCase();
							rdf = false;
							continue;
						}
						if (rsf) {
							rs2 = sp[z].toLowerCase();
							rsf = false;
							continue;
						}
						if (rtf) {
							rd = sp[z].toLowerCase();
							rtf = false;
						}

					}
				} else if (type.equalsIgnoreCase("I") && inst_.equalsIgnoreCase("beq")
						|| inst_.equalsIgnoreCase("blt")) {

					if (sp[z].equalsIgnoreCase(":")) {
						continue;
					}
					if (!sp[z].equalsIgnoreCase("") && !sp[z].equalsIgnoreCase(":")) {
						if (Label_exist) {
							inst_ = sp[z].toLowerCase();

							Label_exist = false;
							continue;
						}
						if (rtf) {
							rs1 = sp[z].toLowerCase();

							rtf = false;
							continue;
						}
						if (rsf) {
							rs2 = sp[z].toLowerCase();
							rsf = false;
							continue;

						}
						if (labelflag) {

							String labelz = "";
							labelz = sp[z];
							labelz = "" + label_index.get(labelz);
							// -1 ? think
							int fix = Integer.parseInt(labelz);
							fix = fix - 1;
							fix = fix - i;
							label = Integer.toBinaryString(fix);
							labelflag = false;
						}
					}
				} else if (type.equalsIgnoreCase("I")) {
					if (inst_.equalsIgnoreCase("addi") || inst_.equalsIgnoreCase("andi")) {
						if (sp[z].equalsIgnoreCase(":")) {
							continue;
						}

						if (!sp[z].equalsIgnoreCase("") && !sp[z].equalsIgnoreCase(":")) {
							if (Label_exist) {
								inst_ = sp[z].toLowerCase();

								Label_exist = false;
								continue;
							}
							if (rtf) {
								rs1 = sp[z].toLowerCase();

								rtf = false;
								continue;
							}
							if (rsf) {
								rs2 = sp[z].toLowerCase();
								rsf = false;
								continue;

							}

							int x = Integer.parseInt(sp[z]);
							Imm_Add = Integer.toBinaryString(x);
							Imm_Add = extend("Imm", Imm_Add);
						}
					}

					else if (inst_.equalsIgnoreCase("sw") || inst_.equalsIgnoreCase("lw")) {
						// for sw , lw
						// System.out.println("this is me" + sp[z]);
						if (sp[z].equalsIgnoreCase(":")) {
							continue;
						}

						if (!sp[z].equalsIgnoreCase("") && !sp[z].equalsIgnoreCase(":")) {
							if (Label_exist) {
								inst_ = sp[z].toLowerCase();

								Label_exist = false;
								continue;
							}
							if (rtf) {
								// to be fixed
								rs1 = sp[z].toLowerCase();

								rtf = false;
								continue;
							}
							if (rsf) {

								rs2 = sp[z].toLowerCase();
								rs2 = rs2.replace('(', ',');
								rs2 = rs2.replace(')', ',');
								// System.out.println("this rs2 "+rs2);
								String[] newz = rs2.split(",");

								// imm equal first value ,, register is the second
								Integer bin = Integer.parseInt(newz[0]);
								String Immediate = bin.toBinaryString(bin);
								Imm_Add = extend("Imm", Immediate);
								rs2 = newz[1].toLowerCase();
								rsf = false;

							}
						}

					}

				} else if (type.equalsIgnoreCase("J")) {
					if (sp[z].equalsIgnoreCase(":")) {
						continue;
					}
					if (!sp[z].equalsIgnoreCase("") && !sp[z].equalsIgnoreCase(":")) {
						if (Label_exist) {
							inst_ = sp[z].toLowerCase();
							Label_exist = false;
						}
						String labelz = "";
						labelz = sp[z].toLowerCase();
						labelz = "" + label_index.get(labelz);
						// -1 ? think
						int fix = Integer.parseInt(labelz);
						jlabel = Integer.toBinaryString(fix);

					}

				} else if (type.equalsIgnoreCase("S")) {
					if (sp[z].equalsIgnoreCase(":")) {
						continue;
					}

					if (!sp[z].equalsIgnoreCase("") && !sp[z].equalsIgnoreCase(":")) {
						if (Label_exist) {
							inst_ = sp[z].toLowerCase();
							Label_exist = false;
							continue;
						}
						if (rtf) {
							rs1 = sp[z].toLowerCase();
							rtf = false;
							continue;
						}
						if (rsf) {
							rs2 = sp[z].toLowerCase();
							rsf = false;
							continue;

						}

						int x = Integer.parseInt(sp[z]);
						Imm_Add = Integer.toBinaryString(x);
						Imm_Add = extend("funct", Imm_Add);
					}
				}
				if (!rdf || !rsf || !rtf || !Label_exist) {
					//System.out.println("type:" + type);
					String Binary_rs1 = "";
					String Binary_rs2 = "";
					String Binary_rd = "";
					String Binary_imm = "";
					// System.out.println(type);
					if (type.equalsIgnoreCase("R")) {

						Binary_rs1 = RegisterFile.reg.get(rs1);
						// System.out.println("rs1"+ rs1 + "rs2" + rs2+" "+"rd"+rd);
						Binary_rs2 = RegisterFile.reg.get(rs2);
						Binary_rd = RegisterFile.reg.get(rd);
						// System.out.println("rd"+" " + Binary_rd);
						// System.out.println(op);
						op = extend("OP", op);
						binary_inst = "" + flag + op + Binary_rs1 + Binary_rs2 + Binary_rd;

					} else if (type.equalsIgnoreCase("I")) {

						Binary_rs1 = RegisterFile.reg.get(rs1);
						Binary_rs2 = RegisterFile.reg.get(rs2);
						funct = extend("funct", funct);
						// System.out.println(Imm_Add +" " + Imm_Add.length());
						binary_inst = "" + flag + Imm_Add + Binary_rs1 + Binary_rs2 + funct;

						if (labelflag == false) {

							String labelex = InstructionMemory.extend("Imm", label);
							// System.out.println("beq: "+labelex+ labelex.length());
							binary_inst = "" + flag + labelex + Binary_rs1 + Binary_rs2 + funct;
						}

					} else if (type.equalsIgnoreCase("j")) {
 						String op2 = extend("funct", funct);
						String ext = extend("j", jlabel);
						//System.out.println("this op2 j : " + op2);
						binary_inst = "" + flag + ext + op2;
					} else if (type.equalsIgnoreCase("S")) {
						Binary_rs1 = RegisterFile.reg.get(rs1);
						Binary_rs2 = RegisterFile.reg.get(rs2);
						op = info.get(1);
						op = extend("OP", op);
						// System.out.println("funct: " + funct);
						// funct = extend("funct", funct);
						// System.out.println(Imm_Add +" " + Imm_Add.length());
						binary_inst = "" + flag + op + Binary_rs1 + Binary_rs2 + Imm_Add;
					}

					// System.out.println("binary_inst" + " " + binary_inst + " " + "size" + " " +
					// binary_inst.length());
					ArrayList<Object> s = new ArrayList<Object>();
					s.add(sp[0]);
					s.add(type);
					infos.add(s);
					// System.out.println("instruction: " + " " + binary_inst);
					setInstruction(binary_inst);
				}
			}
		}

	}

	public static String extend(String type, String test) {

		int len = test.length();

		int target = 0;
		if (type.equalsIgnoreCase("op")) {
			target = 16;

		} else if (type.equalsIgnoreCase("Imm")) {
			target = 16;
		} else if (type.equalsIgnoreCase("PC")) {
			target = 32;
		} else if (type.equalsIgnoreCase("j")) {
			target = 26;
		} else if (type.equalsIgnoreCase("28")) {
			target = 28;
		} else if (type.equalsIgnoreCase("funct")) {
			target = 5;
		}

		String z = "";
		for (int i = len; i < target; i++) {
			z = z + '0';
		}
		String result = z + test;
		return result;
	}

	public static void setInstruction(String instruction) {

		if (instruction.length() > 36) {
			System.out.println("Wrong instruction format!!");
			return;
		}
		String first_byte = "";
		for (int i = 0; i < 32; i = i + 8) {
			first_byte = "";
			for (int j = i; j < i + 8; j++) {

				first_byte = first_byte + instruction.charAt(j);
			}

			instructions[int_count / 8] = first_byte;
			int_count += 8;

		}

	}

	public static String readInstruction(int index) throws MipsException {
		// index is giveen by pc ;
		/// decoding....
//		if (index % 4 != 0) {
//			throw new MipsException("************Cant Read the instruction**********");
//		}
		String comp_inst = "";
		for (int i = index; i < index + 4; i++) {
			if (instructions[i] == null) {
				throw new MipsException("************Cant Read the instruction**********");

			}
			comp_inst = comp_inst + instructions[i];
		}
		return comp_inst;
	}

}
