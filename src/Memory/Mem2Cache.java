package Memory;

import FileHandler.FileIO;

public class Mem2Cache {
	private int noPrefechedInstructions;
	private int noLoadedPrograms;
	private int noCurrentProgram;
	private AddressAllocator addressAllocator;
	
	public int getNoPrefechedInstructions() {
		return noPrefechedInstructions;
	}
	public void setNoPrefechedInstructions(int noPrefechedInstructions) {
		this.noPrefechedInstructions = noPrefechedInstructions;
	}
	public AddressAllocator getAddressAllocator() {
		return addressAllocator;
	}
	public void setAddressAllocator(AddressAllocator addressAllocator) {
		this.addressAllocator = addressAllocator;
	}
	public int getNoLoadedPrograms() {
		return noLoadedPrograms;
	}
	public void setNoLoadedPrograms(int noLoadedPrograms) {
		this.noLoadedPrograms = noLoadedPrograms;
	}
	
	public Mem2Cache() {
		this.addressAllocator = new AddressAllocator();
		this.noLoadedPrograms = addressAllocator.getPrograms().size();
		this.noCurrentProgram = 0;
	}
	
	
//	public void nextProgram() {
//		String Code="";
//		String Data="";
//		for (int j = 0; j < addressAllocator.getPrograms().get(noCurrentProgram).getCode_seg().size(); j++) {
//			Code += addressAllocator.getPrograms().get(noCurrentProgram).getCode_seg().get(j)+System.lineSeparator();
//		}
//		FileIO.TextTOFile(Code, "instructionCache.txt");
//		for (int j = 0; j < addressAllocator.getPrograms().get(noCurrentProgram).getData_seg().size(); j++) {
//			Data += addressAllocator.getPrograms().get(noCurrentProgram).getData_seg().get(j)+System.lineSeparator();
//		}
//		FileIO.TextTOFile(Data, "dataCache.txt");
//		noCurrentProgram++;
//	}
	public void nextProgram() {
		String Code="";
		String Data="";
		String Address = addressAllocator.getPrograms().get(noCurrentProgram).getCode_seg_start_address();
		for (int j = 0; j < addressAllocator.getPrograms().get(noCurrentProgram).getCode_seg().size(); j++) {
			String tmp = Integer.toHexString(Integer.parseInt(Address,16)+Integer.parseInt(Integer.toHexString(4*j),16));
			Code += tmp+"->"+addressAllocator.getMemory().get(tmp)+System.lineSeparator();
		}
		FileIO.TextTOFile(Code, "instructionCache.txt");
		Address = addressAllocator.getPrograms().get(noCurrentProgram).getData_seg_start_address();
		for (int j = 0; j < addressAllocator.getPrograms().get(noCurrentProgram).getData_seg().size(); j++) {
			String tmp = Integer.toHexString(Integer.parseInt(Address,16)+Integer.parseInt(Integer.toHexString(4*j),16));
			Data +=tmp+"->"+addressAllocator.getMemory().get(tmp)+System.lineSeparator();
		}
		FileIO.TextTOFile(Data, "dataCache.txt");
		noCurrentProgram++;
	}
	
	
	public static int Hex2Decimal(String hex) {
		int deci = (Integer.parseInt(hex,16)- Integer.parseInt("0x00400000",16))/4;
		return deci;
	}
	
	
//	String[] parts = Code.split("->");
//	for (int i = 0; i < parts.length; i++) {
//		System.out.println(parts[i]);
//	}
	
}
