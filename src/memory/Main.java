package memory;

public class Main {

	public static void main(String[] args) {
		SegmentDefragmenter pr = new SegmentDefragmenter("test.asm");
		pr.getCode_seg();
		AddressAllocator aa = new AddressAllocator();
	}

}
