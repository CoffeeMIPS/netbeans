package Assembler;

public class Instruction {
	private String Instruction;
	private String Address;
	public String getInstruction() {
		return Instruction;
	}
	public void setInstruction(String instruction) {
		Instruction = instruction;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public Instruction(String instruction, String address) {
		Instruction = instruction;
		Address = address;
	}
	
}
