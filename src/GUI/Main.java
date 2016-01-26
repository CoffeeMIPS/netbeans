/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.table.DefaultTableModel;
import Assembler.Assembler;
import Assembler.Instruction;
import FileHandler.FileIO;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import memory.SegmentDefragmenter;

/**
 *
 * @author Alirez
 */
public class Main extends javax.swing.JFrame {
    int count=0;
    String filePath = null;
    int lineOfInstructions;
    Monitor monitor;
    Computer computer;
    boolean loadLock = false;
    /**
     * Creates new form Main
     */
    public Main() {
        initComponents();
        
        assembleButton.setVisible(false);
        runButton.setVisible(false);
        nextIns.setVisible(false);
        execAll.setVisible(false);
        computer = new Computer();
        computer.me = this;
        
        computer.fix_memory_table(memoryTable);
        monitor = new Monitor(computer.aa.getMemory(),Simonitor);
        
        
        mipsCode.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged (TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                //String columnName = model.getColumnName(column);
                //Object data = model.getValueAt(row, column);
                if(!loadLock)
                {
                    updateMainFile();
                }
            }
        });
        
        loadLock = true;
        for (int i = 0; i < mipsCode.getRowCount(); i++) {
            mipsCode.setValueAt(Integer.toHexString(i*4), i, 0);
        }
        loadLock = false;
        
        setStyle(mipsCode);
        setStyle(program1);
        setStyle(program2);
        setStyle(program3);
        loadprograms();
}

    private void updateMainFile()
    {
        String lines = "";
        for (int i = 0; i < mipsCode.getRowCount(); i++) {
            DefaultTableModel model = (DefaultTableModel) mipsCode.getModel();            
            lines = lines.concat((String) model.getValueAt(i, 1)).concat("\n");
        }
        FileIO.TextTOFile(lines, filePath);
                
        updateMainProgram();

        
    }
    
    private void updateMainProgram()
    {
        assembleButton.setVisible(false);
        runButton.setVisible(false);
        nextIns.setVisible(false);
        execAll.setVisible(false);
        computer = new Computer();
        computer.me = this;

        computer.fix_memory_table(memoryTable);
        monitor = new Monitor(computer.aa.getMemory(),Simonitor);
        loadLock = true;
        for (int i = 0; i < mipsCode.getRowCount(); i++) {
            mipsCode.setValueAt(Integer.toHexString(i*4), i, 0);
        }
        setStyle(mipsCode);
        setStyle(program1);
        setStyle(program2);
        setStyle(program3);
        loadprograms();
        
        String file = FileIO.Fread(filePath.replace("\\", "/"));
        String[] line = file.split("\n");

        int rowCount = mipsCode.getModel().getRowCount();

        for (int i = rowCount - 1; i >= 0; i--) {
            ((DefaultTableModel)mipsCode.getModel()).removeRow(i);
        }
        for (int i = 0; i < line.length; i++) {
            DefaultTableModel model = (DefaultTableModel) mipsCode.getModel();
            model.addRow(new Object[]{"",line[i],"",""});
        }
        loadLock = false;
        assembleButton.setVisible(true);

    }
    
    
    private void loadprograms(){
        
        int rowCount = program1.getModel().getRowCount();
            
        for (int i = rowCount - 1; i >= 0; i--) {
            ((DefaultTableModel)program1.getModel()).removeRow(i);
        }
        
        rowCount = program2.getModel().getRowCount();
            
        for (int i = rowCount - 1; i >= 0; i--) {
            ((DefaultTableModel)program2.getModel()).removeRow(i);
        }
        
        rowCount = program3.getModel().getRowCount();
            
        for (int i = rowCount - 1; i >= 0; i--) {
            ((DefaultTableModel)program3.getModel()).removeRow(i);
        }
        
        HashMap<Integer, SegmentDefragmenter> programs= computer.aa.getPrograms();
//        System.out.println(programs.toString());
        SegmentDefragmenter sd = programs.get(0);
        ArrayList<String> starr = sd.getCode_seg();
        int start_address =Integer.parseInt(sd.getCode_seg_start_address(), 16);
        for (int i = 0; i < starr.size(); i++) {
            DefaultTableModel model = (DefaultTableModel) program1.getModel();
            model.addRow(new Object[]{Integer.toHexString(start_address+i*4),"",starr.get(i),""});
        }
        sd = programs.get(1);
        starr = sd.getCode_seg();
        start_address =Integer.parseInt(sd.getCode_seg_start_address(), 16);
        for (int i = 0; i < starr.size(); i++) {
            DefaultTableModel model = (DefaultTableModel) program2.getModel();
            model.addRow(new Object[]{Integer.toHexString(start_address+i*4),"",starr.get(i),""});
        }
        sd = programs.get(2);
        starr = sd.getCode_seg();
        start_address =Integer.parseInt(sd.getCode_seg_start_address(), 16);
        for (int i = 0; i < starr.size(); i++) {
            DefaultTableModel model = (DefaultTableModel) program3.getModel();
            model.addRow(new Object[]{Integer.toHexString(start_address+i*4),"",starr.get(i),""});
        }
        
    }
    
    private void setStyle(JTable table){
        table.setEnabled(true);
        table.getColumn("Assembled").setMinWidth(180);
        table.getColumn("Code").setMinWidth(170);
        table.getColumn("Add.").setMaxWidth(90);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        assemblyTab = new javax.swing.JTabbedPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        mipsCode =  new JTable(new DefaultTableModel(new Object[]{"Column1", "Column2","Column1", "Column2"},3));
        jScrollPane8 = new javax.swing.JScrollPane();
        program1 = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        program2 = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        program3 = new javax.swing.JTable();
        assembleButton = new javax.swing.JButton();
        monitors = new javax.swing.JTabbedPane();
        memoryTableContainer = new javax.swing.JScrollPane();
        memoryTable = new JTable(new DefaultTableModel(new Object[]{"Address", "Contain"},0));
        jScrollPane7 = new javax.swing.JScrollPane();
        Simonitor = new javax.swing.JTextPane();
        Table = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        regTable = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        cp0Table = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        cp1Table = new javax.swing.JTable();
        jScrollPane11 = new javax.swing.JScrollPane();
        otherTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        dataCacheMon = new javax.swing.JTextArea();
        runButton = new javax.swing.JButton();
        nextIns = new javax.swing.JButton();
        execAll = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Coffee MIPS");

        mipsCode.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Add.", "Code", "Assembled"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mipsCode.setToolTipText("");
        mipsCode.setAutoscrolls(false);
        mipsCode.setName(""); // NOI18N
        mipsCode.setRowHeight(20);
        mipsCode.setVerifyInputWhenFocusTarget(false);
        jScrollPane6.setViewportView(mipsCode);

        assemblyTab.addTab("Code", jScrollPane6);

        program1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Add.", "Code", "Assembled", "C"
            }
        ));
        jScrollPane8.setViewportView(program1);

        assemblyTab.addTab("Program 1", jScrollPane8);

        program2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Add.", "Code", "Assembled", "C"
            }
        ));
        jScrollPane9.setViewportView(program2);

        assemblyTab.addTab("Program 2", jScrollPane9);

        program3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Add.", "Code", "Assembled", "C"
            }
        ));
        jScrollPane10.setViewportView(program3);

        assemblyTab.addTab("Program 3", jScrollPane10);

        assembleButton.setText("Assemble");
        assembleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assembleButtonActionPerformed(evt);
            }
        });

        memoryTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Address", "Content"
            }
        ));
        memoryTableContainer.setViewportView(memoryTable);

        monitors.addTab("Memory", memoryTableContainer);

        Simonitor.setContentType("text/html"); // NOI18N
        Simonitor.setName("Simonitor"); // NOI18N
        jScrollPane7.setViewportView(Simonitor);

        monitors.addTab("Monitor", jScrollPane7);

        regTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0 zero", null, "8   t0", null, "16 s0", null, "24 t8", null},
                {"1*at", null, "9   t1", null, "17 s1", null, "25 t9", null},
                {"2 v0", null, "10 t2", null, "18 s2", null, "26*k0", null},
                {"3 v1", null, "11 t3", null, "19 s3", null, "27*k1", null},
                {"4 a0", null, "12 t4", null, "20 s4", null, "28 gp", null},
                {"5 a1", null, "13 t5", null, "21 s5", null, "29 sp", null},
                {"6 a2", null, "14 t6", null, "22 s6", null, "30 fp", null},
                {"7 a3", null, "15 t7", null, "23 s7", null, "31 ra", null}
            },
            new String [] {
                "Reg", "Value", "Reg", "Value", "Reg", "Value", "Reg", "Value"
            }
        ));
        regTable.setRequestFocusEnabled(false);
        regTable.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(regTable);

        Table.addTab("Registers", jScrollPane1);

        cp0Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0 Context", null, "8   BadVAddr", null, "16 Config", null, "24 DEPC", null},
                {"1 Random", null, "9   Count", null, "17 LLAddr", null, "25 PerfCtl", null},
                {"2 EntryLo0", null, "10 EntryHi", null, "18 WatchLo", null, "26 ECC", null},
                {"3 EntryLo1", null, "11 Compare", null, "19 WatchHi", null, "27 CacheErr", null},
                {"4 Context", null, "12 SR", null, "20", null, "28 TagLo", null},
                {"5 PageMask", null, "13 Cause", null, "21 ", null, "29 TagHi", null},
                {"6 Wired", null, "14 EPC", null, "22 ", null, "30 ErrorEPC", null},
                {"7 HWREna", null, "15 PRId", null, "23 Debug", null, "31 DESAVE", null}
            },
            new String [] {
                "Reg", "Value", "Reg", "Value", "Reg", "Value", "Reg", "Value"
            }
        ));
        jScrollPane4.setViewportView(cp0Table);

        Table.addTab("CP0", jScrollPane4);

        cp1Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"0 f0", null, "8   f8", null, "16 f16", null, "24 f24", null},
                {"1 f1", null, "9   f9", null, "17 f17", null, "25 f25", null},
                {"2 f2", null, "10 f10", null, "18 f18", null, "26 f26", null},
                {"3 f3", null, "11 f11", null, "19 f19", null, "27 f27", null},
                {"4 f4", null, "12 f12", null, "20 f20", null, "28 f28", null},
                {"5 f5", null, "13 f13", null, "21 f21", null, "29 f29", null},
                {"6 f6", null, "14 f14", null, "22 f22", null, "30 f30", null},
                {"7 f7", null, "15 f15", null, "23 f23", null, "31 f31", null}
            },
            new String [] {
                "Reg", "Value", "Reg", "Value", "Reg", "Value", "Reg", "Value"
            }
        ));
        jScrollPane5.setViewportView(cp1Table);

        Table.addTab("CP1", jScrollPane5);

        otherTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"PC", null, "", null, "", null, "", null},
                {"hi", null, "", null, "", null, "", null},
                {"lo", null, "", null, "", null, "", null},
                {"", null, "", null, "", null, "", null},
                {"", null, "", null, "", null, "", null},
                {"", null, "", null, "", null, "", null},
                {"", null, "", null, "", null, "", null},
                {"", null, "", null, "", null, "", null}
            },
            new String [] {
                "Reg", "Value", "Reg", "Value", "Reg", "Value", "Reg", "Value"
            }
        ));
        jScrollPane11.setViewportView(otherTable);

        Table.addTab("Other", jScrollPane11);

        dataCacheMon.setColumns(20);
        dataCacheMon.setRows(5);
        jScrollPane2.setViewportView(dataCacheMon);

        Table.addTab("Data Cache", jScrollPane2);

        runButton.setText("Run");
        runButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runButtonActionPerformed(evt);
            }
        });

        nextIns.setText("Next Step");
        nextIns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextInsActionPerformed(evt);
            }
        });

        execAll.setText("Execute All");
        execAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                execAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(assembleButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(runButton, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextIns)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(execAll))
                    .addComponent(assemblyTab, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Table)
                    .addComponent(monitors, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(assemblyTab)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(runButton)
                    .addComponent(nextIns)
                    .addComponent(execAll)
                    .addComponent(assembleButton))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(monitors, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Table, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Open File...");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Exit");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem3.setText("Document");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);
        jMenu2.add(jSeparator2);

        jMenuItem4.setText("About");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFileChooser input = new JFileChooser();
        int result = input.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            filePath = input.getSelectedFile().getAbsolutePath();
            updateMainProgram();
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("Cancel was selected");
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void execAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_execAllActionPerformed
        while (computer.currentLineOfInstructions < computer.lineOfInstructions) {
            computer.runSingleSycle();
        }
        computer.fix_memory_table(memoryTable);
        computer.Fix_regfile_table(regTable);
        dataCacheMon.setText(computer.get_cache_mem());
        execAll.setVisible(false);
        runButton.setVisible(true);
        nextIns.setVisible(false);
    }//GEN-LAST:event_execAllActionPerformed
    
    private void selectTrueInstruction(){
        int pc = (computer.getPC())*4; 
            mipsCode.clearSelection();
            program1.clearSelection();
            program2.clearSelection();
            program3.clearSelection();
            for(int i=0;i<mipsCode.getRowCount();i++){
                if(!((String) mipsCode.getValueAt(i, 0)).equals(""))
                if(Integer.parseInt((String) mipsCode.getValueAt(i, 0),16) == pc)
                {
                    assemblyTab.setSelectedIndex(0);
                    mipsCode.setRowSelectionInterval(i, i);
                }
            }
            for(int i=0;i<program1.getRowCount();i++){
                if(!((String) program1.getValueAt(i, 0)).equals(""))
                if(Integer.parseInt((String) program1.getValueAt(i, 0),16) == pc)
                {
                    assemblyTab.setSelectedIndex(1);
                    program1.setRowSelectionInterval(i, i);
                }
            }
            for(int i=0;i<program2.getRowCount();i++){
                if(!((String) program2.getValueAt(i, 0)).equals(""))
                if(Integer.parseInt((String) program2.getValueAt(i, 0),16) == pc)
                {
                    assemblyTab.setSelectedIndex(2);
                    program2.setRowSelectionInterval(i, i);
                }
            }
            for(int i=0;i<program3.getRowCount();i++){
                if(!((String) program3.getValueAt(i, 0)).equals(""))
                if(Integer.parseInt((String) program3.getValueAt(i, 0),16) == pc)
                {
                    assemblyTab.setSelectedIndex(3);
                    program3.setRowSelectionInterval(i, i);
                }
            }      
    }
    
    public void changeMonitor(int loc,int ch,int col)
    {
        char[] C = new char[2];
        C[0] = (char)ch;
        C[1] = (char)col;
        String s = new String(C);
        monitor.Memory.put(memory.AddressAllocator.parse8DigitHex(loc*2+monitor.startAddress),s.substring(0, 1));
        monitor.Memory.put(memory.AddressAllocator.parse8DigitHex(loc*2+monitor.startAddress+1),s.substring(1, 2));
        monitor.updateMonitor();
    }
    
    
    private void nextInsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextInsActionPerformed
        if (computer.runSingleSycle()) {
            computer.fix_memory_table(memoryTable);
            monitor.updateMonitor();
            
            selectTrueInstruction();
            computer.Fix_regfile_table(regTable);
            computer.update_other_table(otherTable);
            dataCacheMon.setText(computer.get_cache_mem());
        } else {
            nextIns.setVisible(false);
            runButton.setVisible(true);
        }
    }//GEN-LAST:event_nextInsActionPerformed

    private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runButtonActionPerformed
        if (computer.isRunable()) {
            
            computer.run_init(filePath,lineOfInstructions);
            computer.Fix_regfile_table(regTable);
            runButton.setVisible(false);
            nextIns.setVisible(true);
            execAll.setVisible(true);
        }
    }//GEN-LAST:event_runButtonActionPerformed

    private void assembleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assembleButtonActionPerformed
        if (!filePath.isEmpty() && filePath != null) {
            Assembler assemble = new Assembler();
            assemble.setModeBit(true);
            HashMap<Integer, Instruction> assembled = new HashMap<Integer, Instruction>(assemble.assembleFile(filePath));            
            assemble.setModeBit(false);
            this.lineOfInstructions = assembled.size();
            int code_number=0;

            loadLock = true;
            for (int i = 0; i < mipsCode.getRowCount(); i++) {
                String code = (String)mipsCode.getValueAt(i, 1);
                try{
	                if(code.indexOf(':')==-1){
	                    mipsCode.setValueAt(assembled.get(code_number).getAddress(), i, 0);
	                    mipsCode.setValueAt(assembled.get(code_number).getInstruction(), i, 2);
	                    code_number++;   
	                }
                }
                catch(Exception e)
                {
                	System.out.println("there was an error in:");
                	System.out.println(code);
                	throw e;
                }
                             
            }
            loadLock=false;
            runButton.setVisible(true);
            nextIns.setVisible(false);
        }
    }//GEN-LAST:event_assembleButtonActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        about about_UI = new about();
        about_UI.parent = this;
        about_UI.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane Simonitor;
    private javax.swing.JTabbedPane Table;
    private javax.swing.JButton assembleButton;
    private javax.swing.JTabbedPane assemblyTab;
    private javax.swing.JTable cp0Table;
    private javax.swing.JTable cp1Table;
    private javax.swing.JTextArea dataCacheMon;
    private javax.swing.JButton execAll;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTable memoryTable;
    private javax.swing.JScrollPane memoryTableContainer;
    private javax.swing.JTable mipsCode;
    private javax.swing.JTabbedPane monitors;
    private javax.swing.JButton nextIns;
    private javax.swing.JTable otherTable;
    private javax.swing.JTable program1;
    private javax.swing.JTable program2;
    private javax.swing.JTable program3;
    private javax.swing.JTable regTable;
    private javax.swing.JButton runButton;
    // End of variables declaration//GEN-END:variables
}


class MyTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    JComponent component = new JTextField();

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
        int rowIndex, int vColIndex) {

        System.out.println("FUCKING GIVE ME TEXT");
        ((JTextField) component).setText((String) value);

        return component;
    }
    @Override
    public Object getCellEditorValue() {
        return ((JTextField) component).getText();
    }
}
