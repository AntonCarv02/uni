.globl main
.data
A: .byte 1, 5, -2, 4 


.text

max:
	

	ret

main:
	addi sp,sp,-4
	sw ra , 0(sp)
	
	la a0, A
	jal max
	
	lw ra, 0(sp)
	addi sp, sp, 4
	ret