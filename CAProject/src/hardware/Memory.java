package hardware;

public class Memory {
	public static String[] data = new String[4096];
	public static String MemRead = "0";
	public static String MemWrite = "0";

	public Memory() {
		/*
		 * filling memory here with random values in case of pipe
		 */

		for (int i = 0; i < data.length; i++) {
			data[i] = "00000000000000000000000000011111";
		}

	}

	public static String Read(String address) {

		int decimal = Integer.parseInt(address, 2);

		MemRead = "1";
		if (MemRead.equalsIgnoreCase("1")) {
			System.out.print(data[decimal] + " ");
		}
		System.out.println("MemRead" + " " + MemRead);
		MemRead = "0";
		return data[decimal];

	}

	public static void write(String address, String info) {
		MemWrite = "1";
		int decimal = Integer.parseInt(address, 2);
		if (MemWrite.equalsIgnoreCase("1")) {
			data[decimal] = info;
		}
		System.out.println("MemWrite" + " " + MemWrite);
		MemWrite = "0";

	}

	public static void main(String[] args) {

//		Memory x = new Memory();
//	 	x.write(1, 50);
//		x.Read(1);

	}
}
