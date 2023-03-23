
# bge é o contrario de blt
# bge >= com os registos trocados <=
# blt <  "   "   "  >
#
##-----------------ex1)a
	bge t1, t0, SALTA

	addi, t0,t0,-1

SALTA:


##------------------ex1)b
	bge t0, zero, JUMP
	sub t1, zero, t0
	beq zero, zero E
	
JUMP:	add t1 , zero, t0
E:


##-------------------------ex1)c

	add t1, zero, zero
	addi t2, zero, 10
	addi t0,t0,1
	
WHILE: 	blt t2, t0, END

	add t1,t1,t0
	addi t0,t0,1
	beq zero, zero, WHILE
END:

#outra maneira ---- MAIS EFICIENTE
 
	blt t2, t0, END1
WHILE:
	add t1,t1,t0
	addi t0,t0,1
	bge t2, t0, WHILE
END1:



##-------------------------1)d
	addi t2, zero, 1
	beq t0, t2, CASE1
	
	addi t2, zero, 2
	beq t0, t2, CASE2
	
	beq zero, zero, DEFAULT

CASE1:
	addi t1, zero, 10
	beq zero, zero, SWITCH
CASE2:

	addi t1, zero, 15
	beq zero, zero, SWITCH
DEFAULT:
	addi t1, zero, zero 

SWITCH:



#-----------------------------------2)

#	int main()
#	{
 #		while(x>=0){
 #			x--;
 #		}
 #   		return 0;
#	}

 	blt t0, zero, B
 	
A:	addi t0, t0, -1
	bge t0, zero, A ##jal zero, A
B:

#-----------------------------4

	andi t0, a0, 1
	bne t0, zero, IMPAR

	srai  a0, a0, 1
	beq zero, zero, FIM
IMPAR: 	
	slli t0,a0, 1
	addi t0, t0,a0
	addi t0, t0, 1

FIM:

	


