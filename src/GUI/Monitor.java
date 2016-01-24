/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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
    JTextPane textPane;
    StyledDocument doc;
    Vector<Style> styles;
    
    public Monitor(HashMap<String, String> mem,JTextPane textPane){
        Memory = mem;
        this.textPane = textPane;
        for (int i = startAddress; i < endAddress; i+=2) {
            mem.put(memory.AddressAllocator.parse8DigitHex(i), "0");
            mem.put(memory.AddressAllocator.parse8DigitHex(i+1), "\u006a");
        }
        doc = textPane.getStyledDocument();
        styles = new Vector<Style>();
        for(int i = 0;i < 256;i++)
        {
            Color back = new Color((i & 0b01000000)/0b01000000 * 255,(i & 0b00100000)/0b00100000*255,(i & 0b00010000)/0b00010000*255);
            Color fore = new Color((i & 0b00000100)/0b00000100 * 255,(i & 0b00000010)/0b00000010*255,(i & 0b00000001)/0b00000001*255);
            
            Style s = textPane.addStyle(Integer.toHexString(i), null);
            StyleConstants.setForeground(s, fore);
            StyleConstants.setBackground(s, back);
            StyleConstants.setFontFamily(s, "Monospace");
            StyleConstants.setFontSize(s, 13);
            styles.add(s);
        }
        
        
    }
    
    
    public void updateMonitor(){
        String output="";
        textPane.setText("");
        Memory.put(memory.AddressAllocator.parse8DigitHex(753664),"A");
        Memory.put(memory.AddressAllocator.parse8DigitHex(753666),"L");
        Memory.put(memory.AddressAllocator.parse8DigitHex(753668),"I");
        int iter = 0;
        for (int i = startAddress; i < endAddress; i=i+2,iter++) {
            String Ch = Memory.get(memory.AddressAllocator.parse8DigitHex(i));
            String Col = Memory.get(memory.AddressAllocator.parse8DigitHex(i+1));
            
            int col = Col.charAt(0);
            
            try{
                if(iter%monitorWidth != 0 || iter==0){
                    doc.insertString(doc.getLength(), Ch, styles.elementAt(col));
                }
                else{
                    doc.insertString(doc.getLength(), "\n"+Ch, styles.elementAt(col));
                }
                
            }
            catch(Exception e)
            {
                
            }
        }
    }
    
}
