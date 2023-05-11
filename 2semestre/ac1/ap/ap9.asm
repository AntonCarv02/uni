.globl main

.text
main:
	addi sp, sp, -4
	sw ra, 0(sp)
	
	#jal logit
	
	li a0, 8
	jal logrec
	
	
	
	lw ra, 0(sp)
	addi sp, sp, 4
	
	ret
	
	
	

logit:
	li, t0, 1
	ciclo:	bge t0, a0, bra
	
		srli a0, a0, 1
		addi t1, t1,1
	
		j ciclo
	bra:
	
	mv a0, t1
	ret
	
	
	
	
logrec:
	addi sp, sp, -4
	sw ra, 0(sp)
	
	li, t0, 1
	bne a1, t0, jump
		
		li a0, 0
		ret
	jump:
	
	srli a0, a0, 1
	jal logrec
	addi a0, a0, 1
	
	lw ra, 0(sp)
	addi sp, sp, 4
	
	ret
	
	