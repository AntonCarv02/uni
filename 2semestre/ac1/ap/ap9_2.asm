.globl main
.data
text: .asciz "ola"

.text
main:
	addi sp, sp, -4
	sw ra, 0(sp)
	
	la, a0, text
	jal strlen_rec
	
	lw ra, 0(sp)
	addi sp, sp, 4
	
	ret
	
	
strlen:

	addi sp, sp, -4
	sw ra, 0(sp)
	
	
	
	ju:	lb t0, 0(a0)
	beqz t0, bra
	
		addi t1, t1,1 
		addi a0, a0,1 
	j ju
	
	bra:
	mv a0, t1
	
	lw ra, 0(sp)
	addi sp, sp, 4
	
	ret
	
	
	
strlen_rec:
	addi sp, sp, -4
	sw ra, 0(sp)
	
	lbu t0, 0(a0)
	bne t0, zero, jump
		
		li a0, -1
		ret
	jump:
	
	addi a0, a0, 1
	jal strlen_rec
	
	addi a0, a0, 1
	
	lw ra, 0(sp)
	addi sp, sp, 4
	
	ret
