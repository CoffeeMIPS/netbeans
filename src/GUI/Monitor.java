/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.util.HashMap;

/**
 *
 * @author cloud
 */
public class Monitor {
    HashMap<String, String> Memory;
    final int monitorHeight=25;
    final int monitorWidth=80;
    final int startAddress=753664; //0x000b8000
    final int lenght = monitorHeight*monitorWidth*2; // it's * 2 because one byte used for color
    final int endAddress=startAddress+lenght;
    final String[] LOGO = new String[monitorHeight*monitorWidth];
    
    public Monitor(HashMap<String, String> mem){
        Memory = mem;
        for (int i = startAddress; i < endAddress; i++) {
            mem.put(memory.AddressAllocator.parse8DigitHex(i), "0");
        }
    }
    
    @Override
    public String toString(){
        String output="";
        Memory.put(memory.AddressAllocator.parse8DigitHex(753664),"A");
        Memory.put(memory.AddressAllocator.parse8DigitHex(753666),"L");
        Memory.put(memory.AddressAllocator.parse8DigitHex(753668),"I");
        for (int i = startAddress; i < endAddress; i=i+2) {
            String Ch = Memory.get(memory.AddressAllocator.parse8DigitHex(i));
            if(output.length()%81 != 0 || output.length()==0){
                
                output = output.concat(Ch);
            }
            else{
                output = output.concat("\n".concat(Ch));
            }
        }
        return output;
    }
    
}
