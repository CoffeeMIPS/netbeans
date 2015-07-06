starter:
bne $k1,$zero,checkintrupt
j f095
j runP
checkintrupt:
addi $a1,$zero,1
beq $a1,$k1,isuserint
j runP
isuserint:
addi $a0,$zero,10
beq $a0,$v0,exitint
addi $a0,$zero,20
beq $a0,$v0,waitint
exitint:
j f093
j runP
waitint:
j f090
j runP
runP:
j f091
addi $a1,$zero,-1
beq $a1,$v0,ex
j f020
ex:
add $zero,$zero,$zero
