package memory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class AddressAllocator {
	private HashMap<String, String> Memory = new HashMap<String, String>();
	private HashMap<Integer, SegmentDefragmenter> Programs = new HashMap<Integer, SegmentDefragmenter>();
	private int physicalAddress = 0;

	public HashMap<String, String> getMemory() {
		return Memory;
	}

	public void setMemory(HashMap<String, String> memory) {
		Memory = memory;
	}

	public AddressAllocator() {
		Scanner scanner;
		int physicalAddress = 0;
		int pNum = 0;
		try {
			scanner = new Scanner(new File("MainMemory.dat"));
			while (scanner.hasNextLine()) {
				String file = scanner.nextLine();
				if (file.isEmpty())
					continue;
				else {
					SegmentDefragmenter sd = new SegmentDefragmenter(file);
					Programs.put(pNum, sd);
					sd.setCode_seg_start_address(parse8DigitHex(physicalAddress));
					for (int i = 0; i < sd.getCode_seg().size(); i++) {
						Memory.put(parse8DigitHex(physicalAddress), sd.getCode_seg().get(i));
						physicalAddress++;
					}
					sd.setCode_seg_end_address(parse8DigitHex(physicalAddress - 1));
					sd.setData_seg_start_address(parse8DigitHex(physicalAddress));
					for (int i = 0; i < sd.getData_seg().size(); i++) {
						Memory.put(parse8DigitHex(physicalAddress), sd
								.getData_seg().get(i));
						physicalAddress++;
					}
					sd.setData_seg_end_address(parse8DigitHex(physicalAddress-1));
					pNum++;
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < Memory.size(); i++) {
			System.out.println(parse8DigitHex(i) + " : "+ Memory.get(parse8DigitHex(i)));
		}
		for (int i = 0; i < Programs.size(); i++) {
			System.out.println("Code Start: "+Programs.get(i).getCode_seg_start_address());
			System.out.println("Code End: "+Programs.get(i).getCode_seg_end_address());
			System.out.println("Data Start: "+Programs.get(i).getData_seg_start_address());
			System.out.println("Data End: "+Programs.get(i).getData_seg_end_address());
		}
	}

	public static String parse8DigitHex(int dec) {
		String hex = Integer.toHexString(0x00400000 + 4 * dec);
		return hex;
	}


	public HashMap<Integer, SegmentDefragmenter> getPrograms() {
		return Programs;
	}

	public void setPrograms(HashMap<Integer, SegmentDefragmenter> programs) {
		Programs = programs;
	}
}
