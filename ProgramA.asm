.code
addi $a0,$a0,10
test:
addi $v0,$zero,20
addi $v1,$zero,3
syscall
addi $t0,$zero,10
addi $v0,$zero,10
syscall
.data
11
12
13
14
15