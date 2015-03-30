package Memory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SegmentDefragmenter {
	private ArrayList<String> Data_seg = new ArrayList<String>();
	private ArrayList<String> Code_seg = new ArrayList<String>();
	private String filePath;
	private String Data_seg_start_address;
	private String Data_seg_end_address;
	private String Code_seg_end_address;
	private String Code_seg_start_address;
	private boolean isCode = false;
	private boolean isData = false;

	public ArrayList<String> getData_seg() {
		return Data_seg;
	}

	public void setData_seg(ArrayList<String> data_seg) {
		Data_seg = data_seg;
	}

	public ArrayList<String> getCode_seg() {
		return Code_seg;
	}

	public void setCode_seg(ArrayList<String> code_seg) {
		Code_seg = code_seg;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getData_seg_start_address() {
		return Data_seg_start_address;
	}

	public void setData_seg_start_address(String data_seg_start_address) {
		Data_seg_start_address = data_seg_start_address;
	}

	public String getData_seg_end_address() {
		return Data_seg_end_address;
	}

	public void setData_seg_end_address(String data_seg_end_address) {
		Data_seg_end_address = data_seg_end_address;
	}

	public String getCode_seg_end_address() {
		return Code_seg_end_address;
	}

	public void setCode_seg_end_address(String code_seg_end_address) {
		Code_seg_end_address = code_seg_end_address;
	}

	public String getCode_seg_start_address() {
		return Code_seg_start_address;
	}

	public void setCode_seg_start_address(String code_seg_start_address) {
		Code_seg_start_address = code_seg_start_address;
	}

	public SegmentDefragmenter(String filePath) {
		this.filePath = filePath;
		programParser(new File(filePath));
	}

	public void programParser(File file) {
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				line = line.trim();
				line = line.replaceAll("^.+:([\\s]+)?", "");
				line = line.replaceAll("[#].+", ""); 
				line = line.replace("(", ","); 
				line = line.replace(")", "");
				if (line.isEmpty())
					continue;
				if (line.equalsIgnoreCase(".code")) {
					isData = false;
					isCode = true;
					continue;
				}
				if (line.equalsIgnoreCase(".data")) {
					isCode = false;
					isData = true;
					continue;
				}
				helper(line);
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void helper(String line){
		if(isCode){
			Code_seg.add(line);
		}else if (isData) {
			Data_seg.add(line);
		}
	}
}
