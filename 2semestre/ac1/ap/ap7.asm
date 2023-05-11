.globl main
.data
A: .string "hello world"
B: .string "Ola Mundo"


.text
to_upper:
	li t1, 97
	li t2, 122
	
	lb t0, 0(a0)
	
	
	beqz t0, END1
WHILE:
	lb t0, 0(a0)
	beqz t0, END1
	blt t0,t1, j
	bgt t0,t2, j
	
	
		addi t0, t0, -32
		sb t0, 0(a0)
	j:
		

	addi a0, a0, 1
	j WHILE
	
END1:

	ret
	
	
	
main:
	addi sp,sp,-4
	sw ra , 0(sp)
	
	la a0, A
	jal to_upper
	
	la a0, B
	jal  to_upper
	
	lw ra, 0(sp)
	addi sp, sp, 4
	ret