.globl main
.data
fin:   .asciz "/home/antonio/Documentos/uni/2semestre/ac1/trab23/starwars.rgb"        # filename for input
fout: .asciz "//home/antonio/Documentos/uni/2semestre/ac1/trab23/starwars2.rgb"    # filename for output
msg: .asciz "Diga que personagem pretende: 1-Yoda / 2-Darth Maul / 3-Mandalorian \n"

buffer: .space 172800
arr: .space 23


.text

#############################################
# Funcao: show_msg
# Descricao: permite escrever e ler do terminal a personagem escolhida 
# Argumentos:
# a0 - endereço da mensagem
#
###############################################


show_msg:
	
	li a7, 4
	
	ecall 
	
	li a7, 5
	
	ecall 

ret



#############################################
# Funcao: main
# Descricao: 
###############################################


main:

	addi sp, sp, -4
	sw ra, 0(sp)
	
	jal read_rgb_image
	
	la a0, msg
	jal show_msg
	
	addi sp, sp, -4
	sw a0, 0(sp)
	
	la a0, buffer
	li t0, 172800
	
	#while(<172800)	
while:	beqz t0, BRANCH
		
		addi sp, sp, -4
		sw t0, 0(sp)
		
		addi sp, sp, -4
		sw a0, 0(sp)
		
		#a0 - buffer
		jal hue
		
		mv a1, a0
		
		lw a0, 8(sp)
		
		la a2, arr
		##lw a4, 0(sp)
		
		jal location
	
		
		lw a0, 0(sp)
		addi a0, a0, 3
		
		lw t0, 4(sp)
		addi sp, sp, 8
		addi t0, t0, -3
	
		j while
		
	BRANCH:



	la a0, buffer
	la a1, arr
	
	jal draw_centro
	
	jal write_rgb_image
	
	lw ra, 0(sp)
	addi sp, sp, 4

ret




######################################################
# Funcao: read_rgb_image
# Descricao: Esta funcao carrega para a memória os bytes dos pixeis da imagem.
######################################################

read_rgb_image:
	
	li   a7, 1024     
  	la   a0, fin   
  	li   a1, 0        
  	ecall             
  	
  	addi sp, sp, -4
	sw a0, 0(sp)
  
  
  	
  	li   a7, 63
  	lw a0, 0(sp)
  	la   a1, buffer
  	li   a2, 172800 
  	ecall # read image opened

  	li   a7, 57
  	lw a0, 0(sp)

	addi sp, sp, 4  
  	
  	ecall        
	
  	ret 



######################################################
# Funcao: write_rgb_image
# Descricao: Escreve os bytes do buffer para a imagem.
######################################################

write_rgb_image:
	li   a7, 1024     
 	la   a0, fout    
 	li   a1, 1       
 	ecall
 	
 	
	mv t0, a0
	
	#write image 
 	li   a7, 64       
  	mv a0, t0
 	la   a1, buffer
  	li   a2, 172800
  	
 	ecall
 	
 	li   a7, 57       
  	mv a0, t0
  	   
  	ecall        
ret    



######################################################
# Funcao: hue
# Descricao: Tem como finalidade calcular o valor hue com base nos valores de R, G, B fornecidos
# Argumentos:
#	a0 - endereço do buffer da imagem
# Retorna:
#	a0 - valor da hue para o pixel atual

######################################################

hue:
	addi sp, sp, -4
	sw ra, 0(sp)

	
	lbu t1, 0(a0) 	#R
	lbu t2, 1(a0)	#G
	lbu t3, 2(a0)	#B
		
	
	ble t1, t2, elseif1 # R > G >= B --> R <= G < B
	blt t2, t3, elseif1
	
		sub t4, t2, t3
		sub t5, t1, t3
		
		li t6, 60
		mul t4, t4, t6
		div t4, t4, t5
	
		j out
		
	elseif1:
	blt t2, t1, elseif2 # G >= R > B --> G < R <= B
	ble t1, t3, elseif2
			
		sub t4, t1, t3
		sub t5, t2, t3
		
		li t6, 60
		mul t4, t4, t6
		div t4, t4, t5
		
		li t5, 120
		sub t4, t5, t4
	
	
		j out
	
	elseif2:
	ble t2, t3, elseif3 # G > B >= R --> G <= B < R
	blt t3, t1, elseif3
	
		sub t4, t3, t1
		sub t5, t2, t1
		li t6, 60
		mul t4, t4, t6
		div t4, t4, t5
		
		li t5, 120
		add t4, t5, t4
		j out
		
		
	elseif3:
	blt t3, t2, elseif4 # B>= G > R --> B < G <= R
	ble t2, t1, elseif4

		sub t4, t2, t1
		sub t5, t3, t1
		li t6, 60
		mul t4, t4, t6
		div t4, t4, t5
	
		li t5, 240
		sub t4, t5, t4
		j out
	
	
	elseif4:
	li a0, 0	
	j fim
	
	
	out:		
	mv a0, t4
	
	
	fim:
	lw ra, 0(sp)
	addi sp, sp, 4
	
ret




######################################################
# Funcao: indicator
# Descricao: Indica se o pixel atual pertence à personagem
# Argumentos:
#	a0 - personagem escolhida
#	a1 - valor da hue
# Retorna:
#	a0 - valor 0/1 que indica se o pixel atual pertence à personagem
######################################################

indicator:
	li t0, 1
	beq t0, a0, SEGUE1
        li t0, 2
	beq t0, a0, SEGUE2
        li t0, 3
	beq t0, a0, SEGUE3
	j STOP
	
	SEGUE1:
  	li t0, 40
  	li t1, 80	
    	blt a1, t0, STOP   
	bge t1, a1, CONTA
	j STOP

	SEGUE2:
        li t0, 1
        li t1, 15
        blt a1, t0, STOP
        bge t1, a1, CONTA
        j STOP
        
	SEGUE3:
        li t0, 160
        li t1, 180
        blt a1, t0, STOP
        bge  t1, a1, CONTA
        


	STOP:
       	li a0, 0
       	j END

	CONTA: 
       	li a0, 1

	END:
ret # pertence 1 ou 0



######################################################
# Funcao: location
# Descricao: Para o pixel atual calcula se pertencer à personagem incrementa os valores do cx e cy com os valores de x e y atuais.
# Argumentos:
#	a0 - personagem escolhida
#	a1 - valor da hue
#	a2 - endereço do array para o calculo do centro de massa

######################################################

location:
	addi sp, sp, -4
	sw ra, 0(sp)


	jal indicator
	
	
	lw t0, 2(a2) 	#N
	lw t1, 6(a2)	#x
	lw t2, 10(a2)	#y
	lw  t3, 14(a2) 	#cx
	lw t4, 18(a2)	#cy
	

	li t6, 320

	
	for:
    	beq t6, t1, end_for_linha
    
    		beqz  a0, branchI
    			
    			addi t0, t0, 1
    			add t3, t3, t1
    			add t4, t4, t2  
    				  						
				sw t0, 2(a2) 	#N
				sw  t3, 14(a2) 	#cx
				sw  t4, 18(a2)	#cy				
														#j black
		branchI:
														#sb zero, 0(a4)
    			addi t1, t1, 1 							#sb zero, 1(a4)   
    			j end_for								#sb zero, 2(a4)
														#black:

	end_for_linha:
		li t1,  0   
    	addi t2, t2, 1 
		j for	     
    
    
	end_for:
	

	
	sw t1, 6(a2)	#x
	sw t2, 10(a2)	#y
	

	
	lw ra, 0(sp)
	addi sp, sp, 4
	

ret

######################################################
# Funcao: draw_centro
# Descricao: encontra o centro de massa e pinta a mira no centro de massa.
# Argumentos:
#	a1 - endereço do array para o calculo do centro de massa
#	a0 - endereço do buffer da imagem

######################################################


draw_centro:


	lw t0, 14(a1) 	#cx
	lw t1, 18(a1)	#cy
	lw t2 , 2(a1)	#N

	div t0, t0, t2
	div t1, t1, t2

	find:	
	beqz t1, ciclo

		addi a0, a0, 960
		addi t1, t1, -1
		j find
	ciclo:

	find2:	
	beqz t0, ciclo2

		addi a0, a0, 3
		addi t0, t0, -1
		j find2
	ciclo2:

	
	addi sp, sp, -4
	sw a0, 0(sp)

	##		
	## alterar os pixeis a volta do ponto (a0) para vermelho 
	##


	li t3, 255
	
	li t2, 4800
	sub a0, a0, t2
	slli t2, t2,1

	
	miracicle:
	bltz  t2,ciclomira

		li t4, 1
		mira:	beqz t4, cicle
		
			sb t3, 0(a0)
			sb zero, 1(a0)
			sb zero, 2(a0)

			addi t4, t4, -1
			addi a0, a0, 3
			addi t2, t2, -3
			j mira

		cicle:	
			addi t2, t2, -957
			addi a0, a0, 957

		j miracicle
	ciclomira:			
	
	
	
	lw a0, 0(sp)
	
	li t2, 15
	sub a0, a0, t2
	slli t2, t2,1
	addi t2, t2, 1
	
	
	mira_hor:
	bltz t2, fim_hor
		
		sb t3, 0(a0)
		sb zero, 1(a0)
		sb zero, 2(a0)
		addi a0, a0, 3
		addi t2, t2, -3
		j mira_hor
		
	fim_hor:
	
	
	lw a0, 0(sp)
	sb t3, 0(a0)
	sb t3, 1(a0)
	sb t3, 2(a0)
	
	addi sp, sp, 4
ret
	
