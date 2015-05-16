package memory;

public class Main {

    public static void main(String[] args) {
        Mem2Cache m2c = new Mem2Cache();
        //@Ali:Just Give It A Address In Real Mode to Load The Whole Program To Cache
        //Hint: It Loads Binary Not Assembly!Take Care OF it!
//        m2c.loadProgramFromAddress2Cache("");
        m2c.loadProgramFromAddress2Cache("400058");
        //After That you'll See a program with loaded instruction and Data into your DataCache.txt and InstructionCache.txt

    }
}
