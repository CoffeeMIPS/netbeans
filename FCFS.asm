starter:
bne $k1,$zero,checkintrupt
j f095
j runP
checkintrupt:
addi $a0,$zero,10
addi $a1,$zero,1
beq $a1,$k1,isuserint
j runP
isuserint:
beq $a0,$v0,exitint
exitint:
j f093
runP:
j f094
addi $a1,$zero,-1
beq $a1,$v0,ex
j f020
ex:
add $zero,$zero,$zero
