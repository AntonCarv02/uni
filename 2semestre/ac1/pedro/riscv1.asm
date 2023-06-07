.globl main
.data
in:   .asciz "/home/antonio/Documentos/uni/2semestre/ac1/pedro/starwars.rgb"        # imagem original
final: .asciz "/home/antonio/Documentos/uni/2semestre/ac1/pedro/final.rgb"    # imagem final
text: .asciz "escolha a personagem: \n1-Yoda \n2-Darth Maul \n3-Mandalorian \n"

buffer: .space 172800
array:  .space 20

.text

main:
    addi sp, sp, -4
    sw ra, 0(sp)

    jal read_rgb_image
	
	jal ask_user
	
    addi sp, sp, -4
	sw a0, 0(sp)
	
	la a0, buffer
	li t0, 57600

    while:  
    beqz t0, end_while

        addi sp, sp, -8
        sw a0, 4(sp)
        sw t0, 0(sp)

        lw a1, 8(sp)
        jal indicator

        la a1, array
        lw a2, 0(sp)
        lw a3, 4(sp)
        jal location


        lw t0, 0(sp)
        lw a0, 4(sp)
        addi sp, sp ,8

        addi a0, a0, 3
        addi t0, t0, -1

    j while
    end_while:
    
    la a0, buffer
    la a1, array
    jal find_centro_De_massa

    jal desenhar_centro

    jal write_rgb_image

    lw   ra, 4(sp)
    addi sp, sp, 8

ret 



ask_user:
	la a0, text
	li a7, 4
	
	ecall 
	
	li a7, 5
	
	ecall 


ret

#####################################
#read_rgb_image: l um ficheiro com uma imagem no formato RGB para um array em
# memria. A Função tem como parmetros uma string com o nome do ficheiro a ler e
# o endereço de um buffer onde a imagem dever ser escrita.
#
################################3
read_rgb_image:

	
    li   a7, 1024     
  	la   a0, in   
  	li   a1, 0        
  	ecall             
  	mv   s6, a0       
  
  	# read image just opened
  	li   a7, 63
  	la   a1, buffer
  	li   a2, 172800 
  
  	ecall
  
  	li   a7, 57       
  	mv   a0, s6       
  	ecall        


  	ret 

  
##################################     
#write_rgb_image: escreve uma imagem em formato RGB num ficheiro. A Função
#tem como parmetros o nome de um ficheiro, um buffer com a imagem e o comprimento
#do buffer.
################################## 
write_rgb_image:
	
	li   a7, 1024     
 	la   a0, final   
 	li   a1, 1        
 	ecall             
 	mv   s6, a0       
  
 	li   a7, 64       
  	mv   a0, s6      
 	la   a1, buffer
  	li   a2, 172800
  	
 	ecall
 	
 	li   a7, 57       
  	mv   a0, s6       
  	ecall        
 	
 	ret    
 	



################################3
# hue: Calcula a componente Hue a partir das componentes R, G e B de um pixel
# Argumentos: a0 - pixel da imagem
# Retorna: a0 - Hue (0-359)

hue:

 	lbu t0, 0(a0)     #R
    lbu t1, 1(a0)    #G
    lbu t2, 2(a0)    #B


  # Encontre o minimo e maximo
      blt t1, t0, skip1
      mv t3, t0
      j skip2
    skip1:
      mv t3, t1
    skip2:
      blt t2, t3, skip3
      mv t3, t2
    skip3:
      bgt t1, t0, skip4
      mv t4, t0
      j skip5
    skip4:
      mv t4, t1
    skip5:
      bgt t2, t4, skip6
      mv t4, t2
    skip6:


    hue_non_zero_hue:
    # Calcule hue = MAX - MIN
    sub t5, t4, t3
    beqz t5,zero_hue


    # Se MAX = R
    bne t4, t0, hue_max_g
    sub t1, t1, t2   # t6 = G - B
    li t6, 60        # t6 = 60
    mul t1, t1, t6   # t6 = 60 * (G - B)
    div a0, t6, t5   # Hue = 60 * (G - B) / (max-min)
    j hue_done



    # Se MAX = G
    hue_max_g:
    bne t4, t1, hue_max_b
    bne t3, t0, min_b

    #min - r
        sub t2, t2, t0   # t6 = B - R
        li t6, 60      # t6 = 60
        mul t2, t6, t2   # t6 = 60 * (B - R)
        div a0, t2, t5   # Hue = 120 + (B - R) / (max-min)
        addi a0, a0, 120
        j hue_done

    min_b:
        sub t0, t0, t2   # t6 = R - B
        li t6, 60      # t6 = 60
        mul t6, t6, t0   # t6 = 60 * (R - B)
        li t2 , 120
        div a0, t6, t5   # Hue = 120 - 60 * (R - B) / (max-min) 
        sub a0, t2, a0
    j hue_done

    # Se MAX = B
    hue_max_b:
    sub t0, t0, t1   # t6 = R - G
    li t6,60      # t6 = 60
    mul t0, t0, t6   # t6 =60  * (R - G)
    li t6 , 240
    div a0, t0, t5   # Hue = 120 - 60 * (R - B) / (max-min) 
    sub a0, t6, a0
	j hue_done


    zero_hue:
    li a0, 0     # Hue = 0
    ret

    hue_done:

ret



# Funçao indicator
# Recebe um personagem (e.g. 1,2,3) e um pixel com componentes R, G, B
# Indica se esse pixel pertence ou no  personagem
# Endereço dos parmetros passados na pilha
# a1 = Personagem
# a0 - pixel da imagem



indicator:
    addi sp, sp, -8
    sw   ra, 4(sp)
    sw a1 , 0(sp)
    # Calcula o valor de Hue para o pixel
    jal hue
	lw a1, 0(sp)
    # Verifica se o valor de Hue est dentro do intervalo do personagem escolhido
    # Personagem 1 (Yoda): Intervalo [40, 80]
    # Personagem 2 (Darth Maul): Intervalo [1, 15]
    # Personagem 3 (Mandalorian): Intervalo [160, 180]
    li t0, 1
	beq t0, a1, P1
    li t0, 2
	beq t0, a1, P2
    li t0, 3
	beq t0, a1, P3
	j not_matching

    # Personagem 1 (Yoda)
    P1:
    li t1, 40     # Valor mnimo de Hue para Yoda
    li t2, 80     # Valor mximo de Hue para Yoda
    blt a0, t1, not_matching   # Se Hue < 40, no pertence  personagem
    bgt a0, t2, not_matching   # Se Hue > 80, no pertence  personagem
    j out_

    
    # Personagem 2 (Darth Maul)
    P2:
    li t1, 1      # Valor mnimo de Hue para Darth Maul
    li t2, 15     # Valor mximo de Hue para Darth Maul
    blt a0, t1, not_matching   # Se Hue < 1, no pertence  personagem
    bgt a0, t2, not_matching   # Se Hue > 15, no pertence  personagem
    j out_
    # Personagem 3 (Mandalorian)
    P3:
    li t1, 160    # Valor mnimo de Hue para Mandalorian
    li t2, 180    # Valor mximo de Hue para Mandalorian
    blt a0, t1, not_matching   # Se Hue < 160, no pertence  personagem
    bgt a0, t2, not_matching   # Se Hue > 180, no pertence  personagem

    out_:
    li a0, 1
    lw   ra, 4(sp)
    addi sp, sp, 8
    ret

    not_matching:       # O pixel no pertence  personagem escolhida
    li a0, 0

    lw   ra, 4(sp)
    addi sp, sp, 8
    ret



# Função location
# Argumentos:
#   a0 - indcator
#   a1 - array 
#   a2 - pixeis restantes
#   a3 - imagem
#


location:
    
    
	lw t0, 2(a1) 	#N
	lw t1, 6(a1)	#x
	lw t2, 10(a1)	#y
	lw t3, 14(a1) 	#cx
	lw t4, 18(a1)	#cy


    beqz a0, increment

        addi t0, t0, 1
        add t3, t3, t1
    	add t4, t4, t2
j black
        	
    increment:
    
											
	
	sb zero, 0(a3)
   sb zero, 1(a3)   
   sb zero, 2(a3)
    black:


    li t5, 320
    for:
    	beq t5, t1, fim_linha
    		

		addi t1, t1, 1 	
            j end													

	fim_linha:
		li t1,  0   
    	addi t2, t2, 1 
		j for	     
    
	end:

    sw t0, 2(a1) 	#N
    sw t1, 6(a1)	
	sw t2, 10(a1)	
	sw  t3, 14(a1) 	#cx
	sw  t4, 18(a1)	#cy		


    addi a2, a2, -1
    bgtz  a2, not_zero

        div t3, t3, t0
        div t4, t4, t0

        sw  t3, 14(a1) 	#cx
	    sw  t4, 18(a1)	#cy		

    not_zero:

ret


#	a0 - endereço da imagem
#   a1 - array 

find_centro_De_massa:
    
    lw t0, 14(a1) 	#cx
	lw t1, 18(a1)	#cy

    percorrer_linhas:
    beqz t1, while1

		addi a0, a0, 960
		addi t1, t1, -1
		j percorrer_linhas
	while1:

    percorrer_colunas:
    beqz t0, while2

		addi a0, a0, 3
		addi t0, t0, -1
		j percorrer_colunas
	while2:

    ret



#	a0 - endereço centro de massa da imagem


desenhar_centro:

    add s0, zero, a0
    li t0, 255

    li t1, 3
    sub a0, a0, t1

mira: beqz t1, fimm
        sb zero, 0(a0)
        sb t0, 1(a0)
	    sb zero, 2(a0)
	    addi a0, a0, 3
        addi t1, t1, -1
    j mira
    fimm:


    addi a0, s0, 0

    li t1, 959
    sub a0, a0, t1

  mira2:  bltz t1, fim2
        sb zero, 0(a0)
        sb t0, 1(a0)
	    sb zero, 2(a0)
	    addi a0, a0, 960
        addi t1, t1, -320
    j mira2
    fim2:

    ret




