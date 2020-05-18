package hardware;

public class Cache {
	static Block[] block = new Block[512];
	static int hits;
	static int miss;

	public Cache() {
		for (int i = 0; i < block.length; i++) {
			block[i] = new Block();
		}
	}

	public static String read(String[] m, String add) {
		int address = Integer.parseInt(add, 2);

		int index = address % 8;

		int validbit = block[index].Valid_bit;
		int checktag = block[index].Tag;
		int tag = address / 8;
		if (validbit == 1 && checktag == tag) {
			hits++;
			String f = block[index].Data;

			return f;

		} else {
			String data = m[address];
			block[index].Data = data;
			block[index].Tag = tag;
			block[index].Valid_bit = 1;
			// Size[index].Index = index;
			miss++;

			return data;

		}

	}

	public static void main(String[] args) {
//		Memory mem = new Memory();
//		mem.write(0, 10);
//		mem.write(1, 20);
//		mem.write(2, 30);
//		mem.write(3, 40);
//		mem.write(4, 50);
//		mem.write(5, 60);
//		mem.write(6, 70);
//		mem.write(7, 80);
//		mem.write(8, 90);
//		mem.write(9, 100);
//		mem.write(10, 110);
//		Cache cache = new Cache();
//		cache.read(mem, 0);
//		// System.out.println("after 1");
//		cache.read(mem, 0);
//		cache.read(mem, 8);
//		cache.read(mem, 8);
//		cache.read(mem, 0);
//
//		System.out.println(cache.hits);
//		System.out.println(cache.miss);

	}
}
