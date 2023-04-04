.globl main
.text

# definicao de argumentos e chamadas das funcoes


max:
	bge a0, a1, L1
	add a0, zero, a1
	L1:
	ret
	
###############################################

abs:
	#bgez a0, l2
	#sub a1, zero, a0
	#l2:
	#ret
	
###############################################
	
	sub a1, zero, a0
	
	addi  sp, sp, -4
	sw ra, 0(sp)
	
	jal max
	
	lw ra, 0(sp)
	addi sp, sp, 4

	ret
	
###############################################

max3:

	addi  sp, sp, -8
	sw ra, 0(sp)
	sw a2, 4(sp)
		
	jal max
	
	lw a1, 4(sp)
	
	jal max
	
	lw ra, 0(sp)
	addi sp, sp, 8

	ret


##############################################
main:
	addi sp, sp, -4
	sw ra, 0(sp)
	
	#li, a0, 3
	#li, a1, 5
	#jal max
	

	#li , a0, -5
	#jal abs
	
	li a0, 3
	li, a1, 7
	li, a2, 10
	
	jal max3
	
	
	li a0, 1
	li a1, 5
	
	
	lw ra, 0(sp)
	addi sp, sp, 4
	
	ret
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	