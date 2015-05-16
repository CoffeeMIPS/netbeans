.code
addi $a1,$zero,10
jr $a1
jal test
slt $t0,$t1,$t2
bne $t0,$t1,test
addi $t0,$t0,1
addi $t0,$t0,2
test:
addi $t0,$t0,3
addi $t0,$t0,4
addi $t0,$t0,5
addi $t0,$t0,6
.data
1
2
3
4
5