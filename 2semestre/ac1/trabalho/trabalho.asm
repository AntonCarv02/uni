.globl MAIN

.data
fin:   .asciz "/home/antonio/Documentos/ac1/trabalho/lena_imagem.rgb"		# filename for input
file_gray: .asciz "/home/antonio/Documentos/ac1/trabalho/file_gray.gray"	# filename for output

buffer: .space  786432
gray: 	.space 	262144
final: 	.space 	262144

vmatrix:.byte 	1, 2, 1, 0, 0, 0, -1, -2, -1
hmatrix:.byte 	1, 0, -1, 2, 0, -2, 1, 0, -1
vsobel:	.space 	262144
hsobel:	.space 	262144

gray_r: .float 0.30
gray_g: .float 0.59
gray_b: .float 0.11

.text
MAIN:	
	jal read_rgb_image
	
	
	la a0, buffer
	jal rgb_to_gray
	
	
	
	la a0, gray
	la a1, vmatrix
	la a2, vsobel	
	
	jal convolution
	
	
	
	la a0, gray
	la a1, hmatrix
	la a2, hsobel	
	
	jal convolution
	
	
	
	la a0, final
	la a1, vsobel
	la a2, hsobel
	
	jal contour
	
	jal write_gray_image
	
	li a7, 10
	ecall
	
	


	
#####################################
#read_rgb_image: lê um ficheiro com uma imagem no formato RGB para um array em
# memória. A função tem como parâmetros uma string com o nome do ficheiro a ler e
# o endereço de um buffer onde a imagem deverá ser escrita.
#
################################333
read_rgb_image:
	
  	li   a7, 1024     
  	la   a0, fin   
  	li   a1, 0        
  	ecall             
  	mv   s6, a0       
  
  	# read image just opened
  	li   a7, 63
  	la   a1, buffer
  	li   a2, 786432 
  
  	ecall
  
  	li   a7, 57       
  	mv   a0, s6       
  	ecall        


  	ret 

  
##################################     
#write_gray_image: escreve uma imagem em formato GRAY num ficheiro. A função
#tem como parâmetros o nome de um ficheiro, um buffer com a imagem e o comprimento
#do buffer.
################################## 
write_gray_image:
	
	li   a7, 1024     
 	la   a0, file_gray    
 	li   a1, 1        
 	ecall             
 	mv   s6, a0       
  
 	li   a7, 64       
  	mv   a0, s6      
 	la   a1, final
  	li   a2, 262144
  	
 	ecall
 	
 	li   a7, 57       
  	mv   a0, s6       
  	ecall        
 	
 	ret    
  		
###############################
#rgb_to_gray: converte uma imagem a cores RGB para uma imagem em tons de
#cinzento GRAY. A função tem como parâmetros um buffer com a imagem RGB,
#um buffer onde deve ser colocada a imagem em formato GRAY e o tamanho.
#
#
###############################
rgb_to_gray:
	
	li t3, 786432
	la t4, gray_r
	la t5, gray_g
	la t6, gray_b

	flw ft0, (t4)
	flw ft1, (t5)
	flw ft2, (t6)
	
	la a1, gray
	
	bb:
	#while(<786432)	
	beqz t3, BRANCH
	
	######
	lbu t0, 0(a0)
	lbu t1, 1(a0)
	lbu t2, 2(a0)
	
	
	fcvt.s.wu ft3, t0
	fcvt.s.wu ft4, t1
	fcvt.s.wu ft5, t2
			
	fmul.s ft3, ft3, ft2
	fmul.s ft4, ft4, ft1
	fmul.s ft5, ft5, ft0
	
	fcvt.wu.s t0, ft3
	fcvt.wu.s t1, ft4
	fcvt.wu.s t2, ft5
	
									
	add t1, t1, t2
	add t0, t0, t1

	sb t0, 0(a1)
	
	
	addi a0, a0, 3 
	addi a1, a1, 1
	addi t3, t3, -3
	j bb
	BRANCH:
	#end while
	ret
	
#############################
#convolution: calcula a convolução de uma imagem A com um operador Sobel (matriz
#3 × 3) e coloca o resultado numa matriz B. A função tem como parâmetros um buffer
#com a matriz A, um buffer com um dos operadores Sobel e um buffer que vai conter
#a imagem filtrada B.
####################################################
convolution:
	
	li s10, 0
	li s0 , 0	# i da imagem
	li s4, 512	#tamanho imagem 

	

o:
		bgt s0,s4, outer
	
		li s1, 0	# j da imagem
		
		
i:		bgt  s1,s4, inner
		
		
		beqz  s0, border
		beq s0, s4, border
	
		beqz s1, border
		beq s1, s4, border

		
		lbu t0, 0(a0)
		lb t1, 8(a1)
		mul t0, t0, t1
		
		lbu t2, 1(a0)
		lb t3, 7(a1)
		mul t2, t2, t3
		
		lbu t4, 2(a0)
		lb t5, 6(a1)
		mul t4, t4, t5
		
		add t2, t2, t0
		add t6, t4, t2
		######################################
		
		lbu t0, 3(a0)
		lb t1, 5(a1)
		mul t0, t0, t1
		
		lbu t2, 4(a0)
		lb t3, 4(a1)
		mul t2, t2, t3
		
		lbu t4, 5(a0)
		lb t5, 3(a1)
		mul t5, t4, t5
		
		add t2, t2, t0
		add t5, t5, t2
		add t6, t6, t5
		##########################################
		
		
		lbu t0, 6(a0)
		lb t1, 2(a1)
		mul t0, t0, t1
		
		lbu t2, 7(a0)
		lb t3, 1(a1)
		mul t2, t2, t3
		
		lbu t4, 8(a0)
		lb t5, 0(a1)
		mul t5, t4, t5
		
		add t2, t2, t0
		add t5, t5, t2
		add t6, t6, t5
		###################################
		
		
		
		j criar_pixel
		
	 	
border:		li t6, -1	#meter borda a preto


criar_pixel:	addi s10, s10, 1
		addi a0, a0, 1
		sb t6, 0(a2)	
		addi a2, a2, 1
		li t6, 0

	
		addi s1, s1, 1
		j i
	
inner:
		addi s0, s0, 1
		j o
outer:
	
		ret

	
	
################################
#contour: calcula a imagem final combinando as duas imagens convolvidas. A função
#tem como parâmetros dois buffers com as imagens a combinar e um buffer que vai
#conter o resultado
##########################
contour:

	li t0, 262144
	li t1, 255
	li t2, 2
	
c:
	beqz t0, cont
		lb t3, 0(a1)
		lb t4, 0(a2)
		
		
		fcvt.s.w f1, t3
		fcvt.s.w f2, t4
		fabs.s f1, f1
		fabs.s f2, f2
		fcvt.w.s t3, f1
		fcvt.w.s t4, f2
		
		li t5, 4
		div t3, t3, t5
		div t4, t4, t5
		
		
		add t3, t3, t4
		div t3, t3, t2
		
		sub t3, t1, t3
		
		sb t3, (a0)
		
		addi t0, t0, -1
		addi a1, a1, 1
		addi a2, a2, 1
		addi a0, a0, 1
		
		j c
	cont:
	
	ret
