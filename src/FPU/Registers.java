package FPU;

import java.util.HashMap;

/**
 * Created by mohammad on 6/27/2016.
 */
public class Registers {
    private HashMap<String, String> floatRegisters = new HashMap<String, String>();

    public Registers(){

        initRegisterCodes();
    }

    public String getRegister(String reg){
        return floatRegisters.get(reg);
    }

    private void initRegisterCodes() {
        floatRegisters.put("$f0", "00000");
        floatRegisters.put("$f1", "00001");

        floatRegisters.put("$f2", "00010");
        floatRegisters.put("$f3", "00011");

        floatRegisters.put("$f4", "00100");
        floatRegisters.put("$f5", "00101");
        floatRegisters.put("$f6", "00110");
        floatRegisters.put("$f7", "00111");

        floatRegisters.put("$f8", "01000");
        floatRegisters.put("$f9", "01001");
        floatRegisters.put("$f10", "01010");
        floatRegisters.put("$f11", "01011");
        floatRegisters.put("$f12", "01100");
        floatRegisters.put("$f13", "01101");
        floatRegisters.put("$f14", "01110");
        floatRegisters.put("$f15", "01111");

        floatRegisters.put("$f16", "10000");
        floatRegisters.put("$f17", "10001");
        floatRegisters.put("$f18", "10010");
        floatRegisters.put("$f19", "10011");
        floatRegisters.put("$f20", "10100");
        floatRegisters.put("$f21", "10101");
        floatRegisters.put("$f22", "10110");
        floatRegisters.put("$f23", "10111");

        floatRegisters.put("$f24", "11000");
        floatRegisters.put("$f25", "11001");

        floatRegisters.put("$f26", "11010");
        floatRegisters.put("$f27", "11011");

        floatRegisters.put("$f28", "11100");
        floatRegisters.put("$f29", "11101");
        floatRegisters.put("$f30", "11110");
        floatRegisters.put("$f31", "11111");
    }
}