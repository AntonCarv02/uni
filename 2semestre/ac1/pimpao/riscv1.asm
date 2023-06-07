.globl main
.data

    	input_filename: .asciz "/home/antonio/Documentos/uni/2semestre/ac1/trab23/starwars.rgb"    # Input image filename
    	output_filename: .asciz "/home/antonio/Documentos/uni/2semestre/ac1/trab23/final.rgb"  # Output image filename
  
	# Image dimensions
    	image_width:    .word 320
    	image_height:   .word 180

    	# File sizes
    	original_size: .word 172800    		# Total size of the original image 
    	output_size: .word 172800      		# Total size of the output image
    
    	# Indicator array size
     	indicator_size: .word 57600 

    	# Array to store indicator values
    	indicator_values:   .space 57600    	# Buffer size to store indicator values of each pixel
    
    	#Arrays to store image data
    	input_buffer: .space 172800   		# Buffer size to store input image data
    	output_buffer: .space 172800  		# Buffer size to store output image data 

    	# Other necessary variables and data
    	character_number:   .word 0         	# Character number for indicator function ( 0 for yoda, 1 for darth maul and 2 for mandalorian)
    	pixel_R:            .word 0         	# Red value of a pixel
    	pixel_G:            .word 0         	# Green value of a pixel
    	pixel_B:            .word 0         	# Blue value of a pixel
    	hue_result:         .word 0         	# Result of hue calculation
    	indicator_result:   .word 0         	# Result of indicator calculation

.text
main:
 	addi sp, sp, -28
    	# Call the read_rgb_image function
   	la a0, input_filename       		# Pass the image file name pointer 
    	la a1, input_buffer         		# Pass the buffer pointer 
    	jal read_rgb_image       		# Call the read_rgb_image function
    he image file name pointer 
    	la s0, indicator_values  		# Load address of indicator values arraythe buffer pointer 
    	la s1, output_buffer read_rgb_image function
    	la s2, indicator_result
    	la s3, pixel_Rress of indicator values array
    	la s4, pixel_G
    	la s5, pixel_B 
    	
    	sw ra, 24(sp)
    	sw s0, 20(sp)
    	sw s1, 16(sp)
    	sw s2, 12(sp)
    	sw s3, 8(sp)
    	sw s4, 4(sp)
    	sw s5, 0(sp)

    	# Loop to iterate over each pixel
    	li t3, 0                		# Initialize row counter
row_loop:
    	li t4, 0
    	li t5, 0                		# Initialize column countere row counter
column_loop:
    	la a2, image_width
    	la a3, image_heighte column counter
    	mul t1, t3, a2
    	add t2, t1, t5
    	add a1, a1, t2
    	add s1, s1, t2  
    	addi t5, t5, 3
    
    	# Access pixel RGB values from the image data or file
    	mv s6, a1
    
    	lb s3, 0(a1)				# Loads the Red value of the current pixelage data or file
    	lb s4, 1(a1)				# Loads the Green value of the current pixel
    	lb s5, 2(a1)				# Loads the Blue value of the current pixel
 of the current pixel
    	# Call the indicator functionue of the current pixel
    	la a0, character_number                	# Pass character number to the indicator functione of the current pixel
    	mv a1, s3           			# Pass red value to the indicator function
    	mv a2, s4         			# Pass green value to the indicator function
    	mv a3, s5           			# Pass blue value to the indicator function  	# Pass character number to the indicator function
    	jal indicator            		# Call the indicator functionue to the indicator function
    	mv s2, a4  				# Store the result of the indicator calculationue to the indicator function
    lue to the indicator function
    	beqz s2, black_pixel indicator function
    	sw s3, 0(s1)the indicator calculation
    	sw s4, 1(s1)
    	sw s5, 2(s1)
    	j store_indicator
    
black_pixel:
	li s3, 0
	li s4, 0
	li s5, 0
	sw s3, 0(s1)
	sw s4, 1(s1)
	sw s5, 2(s1)

store_indicator:
    	# Store the indicator value in the respective array
    	sw s2, 0(t0) 				# Store indicator value in the indicator values array
	addi t0, t0, 4         			# Move to the next indicator array index
	spective array
	mv a1, s6lue in the indicator values array
	ext indicator array index
    	# Increment column counter
    	addi t4, t4, 1				# Moves to the next column in the current row
    	la t6, image_width             		# Loads the width of the image to t5
    	blt t4, t6, column_loop 		# Repeat column loop if not yet at the end of the row
 column in the current row
    	# Increment row counterads the width of the image to t5
    	addi t3, t3, 1				# Moves to the next row	    lumn loop if not yet at the end of the row
    	la t6, image_height     		# Loads the height of the image to a4
    	blt t3, t6, row_loop    		# Repeat row loop if not yet at the end of the image
     row	    
    	# Call the location function height of the image to a4
    	mv a0, s6w loop if not yet at the end of the image
    	la a1, image_width
    	la a2, image_height 
    	la a3, indicator_values
    	la a4, indicator_size 
    	jal location              		# Call the location function
    	
    	mv t0, a4				# t0 is the row
    	mv t1, a5				# t1 is the columne location function
    	la a0, image_width
    	la a1, image_height
    	
    	mul t2, t0, a0
    	add t2, t2, t1				# Adress of center of mass
    	
    	li s3, 0
    	li s4, 255of mass
    	li s5, 0
    	
    	lw s1, 24(sp)
    	
    	add s1, s1, t2
    	sw s3, 0(s1)
    	sw s4, 1(s1)
    	sw s5, 2(s1)
    	
    	li s3, 255
    	li s4, 0
    	li s5, 0
    	
    	addi t3, t2, -9
    	addi t4, t2, 9
    	
cycle_hback:	
    	add s1, t2, t3
    	sw s3, 0(s1)
    	sw s4, 1(s1)
    	sw s5, 2(s1)
    	addi t3, t3 , 3
    	bnez t3, cycle_hback
    	
cycle_hfront:
	add s1, t2, t4
	sw s3, 0(s1)
	sw s4, 1(s1)
	sw s5, 2(s1)
	addi t4, t4, -3
	bnez t4, cycle_hfront 

	addi t3, t2, -960  
	addi t4, t2, 960
	
cycle_vback:
	add s1, t2, t3
	sw s3, 0(s1)
	sw s4, 1(s1)
	sw s5, 2(s1)
	addi t3 t3, 320
	bnez t3, cycle_vback

cycle_vfront:
	add s1, t2, t4
	sw s3, 0(s1)
	sw s4, 1(s1)
	sw s5, 2(s1)
	addi t4, t4, -320
	bnez t4, cycle_vfront

    	# Call the write_rgb_image function
    	la a0, output_filename               	# Pass the image file name pointer 
    	la a1, output_buffer     		# Pass the buffer pointer 
    	jal write_rgb_image      		# Call the write_rgb_image function
    	# Pass the image file name pointer 
    	lw ra, 20(sp) buffer pointer 
    	lw s0, 16(sp) write_rgb_image function
    	lw s2, 12(sp)
    	lw s3, 8(sp)
    	lw s4, 4(sp)
    	lw s5, 0(sp)
    	addi sp, sp, 28
    	ret

##############################################################
#Function: read_rgb_image
#Description: This function opens an image file, reads its contents into a buffer, and then closes the file.
#Arguments:####################
#a0 - Pointer to the image file name 
#a1 - Pointer to the buffer for storing image data file, reads its contents into a buffer, and then closes the file.
##############################################################
read_rgb_image:
    	mv t0, a0age data
    	mv t1, a1####################
    
    	# Open the image file for reading
    	li a7, 1024             		# Specify the open system call number
    	mv a0, t0
    	li a1, 0
    	ecallhe open system call number
    	mv s0, a0               		# Store the file descriptor in s0

    	# Read the image and store the information in "input_buffer"
    	li a7, 63               		# Specify the read system call number file descriptor in s0
    	mv a0, s0               		# File descriptor of the opened file
    	mv a1, t1               		# Address of the bufferation in "input_buffer"
    	la a2, original_size			# Size of the initial bufferhe read system call number
    	ecallriptor of the opened file
    f the buffer
    	# Close the opened fileinitial buffer
    	li a7, 57
    	mv a0, s6 
    	ecall
    
    	ret

######################################################
write_rgb_image:
    	mv t0, a0
    	mv t1, a1############
    
	# Open a file that does not exist for writing
	li a7, 1024
	mv a0, t0
	li a1, 1ting
	ecall
	mv s0, a0
	
	# Write to the file that just opened
	li a7, 64
	mv a0, s6
	mv a1, t1
	la a2, output_size
	ecall
	
	#Close the opened file
	li a7, 57
	mv a0, s0
	ecall

	ret
	
######################################################
# Function: hue
# Description: Calculates the hue component from the RGB values of a pixel.
# Arguments:############
#   a0 - R (red) value
#   a1 - G (green) valuet from the RGB values of a pixel.
#   a2 - B (blue) value
# Returns:
#   a5 - Hue component
######################################################
hue:
    	# Convert RGB values to integers
    	mv t0, a0      				# Move red value to t0############
    	mv t1, a1      				# Move green value to t1
    	mv t2, a2      				# Move blue value to t2
o t0
    	# Calculate the maximum and minimum of RGB values to t1
    	blt t0, t1, check_green         	# Compare red and greento t2
    	mv t3, t0                       	# Move red to t3
    	mv t4, t1f RGB values
    	j check_blue                    	# Jump to compare red and bluempare red and green
ve red to t3
check_green:
    	mv t3, t1                       	# Move green to t3, which is the variable that stores the max valuemp to compare red and blue
    	mv t4, t0

check_blue:ve green to t3, which is the variable that stores the max value
    	bgt t3, t2, max_min_dif         	# Compare max with blue
    	mv t3, t2                       	# Move blue to t3
    	bgt t4, t2, min_set
    	sub t4, t3, t4mpare max with blue
    	j calculate_hue                 	# Jump to calculate hueve blue to t3
    
min_set:
	mv t4, t2				# Stores the min value mp to calculate hue

max_min_dif:
    	sub a4, t3, t4				# a4 is the variable that stores max - min
    
    
calculate_hue:e that stores max - min
   	# Calculate the hue component
    	li a6, 60
    	sub t5, t0, t1                  	# Calculate red - green
    	sub t6, t1, t2                  	# Calculate green - blue
    	sub t0, t2, t0                  	# Calculate blue - red
    	sub t1, zero, t0			# Calculate red - bluelculate red - green
    	sub t2, zero, t6			# Calulate blue - greenlculate green - blue
    	sub t3, zero, t5			# Calculate green - redlculate blue - red
    blue
    	# If Red is max green
    	beq t3, a0, red_max- red
    	# If green is max
    	beq t3, a1, green_max
    	# Otherwise, blue is max
    	li a5, 240
    	beq t4, a0, red_min_B			# Checks if red is min, otherwise green is min
   	div t5, t5, a4				# Calculates red - green difference divided by max - min difference
    	mul t5, a6, t5				# Multiplies 60 by the calculation made before
    	add a5, a5 , t5				# Calculates the final hue valueed is min, otherwise green is min
    	j donereen difference divided by max - min difference
red_min_B:				the calculation made before
    	div t3, t3, a4				# Calculates green - red difference divided by max - min differenceinal hue value
    	mul t3, a6, t3				# Multiplies 60 by the calculation made before
    	sub a5, a5, t3				# Calculates the final hue value
	j done- red difference divided by max - min difference
red_max:the calculation made before
	beq t4, a2, blue_min_R			# Checks if blue is min, otherwise green is minnal hue value
	li a5, 360
	div t2, t2, a4
	mul t2, a6, t2 is min, otherwise green is min
	sub a5, a5, t2
	j done
blue_min_R:
	div t6, t6, a4
	mul a5, a6, t6
	j done
green_max:
	li a5, 120
	beq t4, a2, blue_min_G			# Checks if blue is min, otherwise red is min
	div t0, t0, a4
	mul t0, a6, t0
	add a5, a5, t0 is min, otherwise red is min
	j done
blue_min_G:
	div t1, t1, a4
	mul t1, a6, t1
	sub a5, a5, t1

done:
    	ret
	
######################################################
# Function: indicator
# Determines if a pixel belongs to a certain character.
# Arguments: a0 - Character number (p)############
#            a1 - R (red) value
#            a2 - G (green) valuein character.
#            a3 - B (blue) value
# Returns:   a4 - 1 if pixel belongs to the character, 0 otherwise
######################################################

indicator:e character, 0 otherwise
	addi sp, sp, -16############
	sw ra, 12(sp)
	sw a1, 8(sp)
	sw a2, 4(sp)
	sw a3, 0(sp)
	
    	mv t0, a0             			# Move character number (p) to t0
    	mv t1, a1             			# Move R to t1
    	mv t2, a2             			# Move G to t2
    	mv t3, a3             			# Move B to t3cter number (p) to t0
t1
    	# Calculate the hue valuet2
    	jal hue                 			# Call the hue function and return it in the t4 variablet3
    	mv t4, a5               			# Move the result (hue) to t4

    	# Check what chatacter the pixel belongs based on the hue value hue function and return it in the t4 variable
    	beqz t0, check_yoda     			# Check if the character is Yoda result (hue) to t4
    	addi t0, t0, -1
    	beqz t0, check_darth_maul			# Check if the character is Darth Maulngs based on the hue value
    	j check_mandalorian 			# Else, character is mandalorian the character is Yoda

check_yoda:f the character is Darth Maul
    	li t5, 40                  			# Lower threshold for Yoda's hue valueter is mandalorian
    	li t6, 80                 			# Upper threshold for YOda's hue value
    	blt t4, t5, not_character
    	bgt t4, t6, not_character threshold for Yoda's hue value
threshold for YOda's hue value
    	# Pixel belongs to Yoda
    	li a4, 1
    	j end_indicator

check_darth_maul:
    	li t5, 1                			# Lower threshold for Darth Maul's hue value
    	li t6, 15               			# Upper threshold for Darth Maul's hue value
    	blt t4, t5, not_character
	bgt t4, t6, not_characterreshold for Darth Maul's hue value
reshold for Darth Maul's hue value
    	# Pixel belongs to Dath Maul
    	li a4, 1
    	j end_indicator

check_mandalorian:
    	li t5, 160              			# Lower threshold for Mandalorian's hue value
    	li t6, 180              			# Upper threshold for Mandalorian's hue value
    	blt t4, t5, not_character
    	bgt t4, t6, not_characterreshold for Mandalorian's hue value
reshold for Mandalorian's hue value
    	# Pixel belongs to Mandalorian
    	li a4, 1
    	j end_indicator

not_character:
	li a4, 0
end_indicator:
	lw ra, 12(sp)
	lw a1, 8(sp)
	lw a2, 4(sp)
	lw a3, 0(sp)
	addi sp, sp, 16
    	ret

######################################################
# Function: location
# Calculates the center of mass (cx, cy) for a character in the image.
# Arguments:############
#   a0 - Image pointer (address)
#   a1 - Image widthor a character in the image.
#   a2 - Image height
#   a3 - Indicator pointer (address)
#   a4 - Indicator size
# Returns:
#   a4 - cx (center of mass x-coordinate)
#   a5 - cy (center of mass y-coordinate)
######################################################

location:
    # Initialize variables############
    li t0, 0         # Total number of pixels (N)
    mv t1, a1
    mv t2, a2
    mv s1, a3els (N)
    mv a6, a4
    li a2, 0
    li a4, 0
 
pixel_counter:
	lbu t6, 0(a3)
	addi a5, a5, 1
	addi a3, a3, 1
	bgt a5, a6, calculate_cm 
	beqz t6, pixel_counter
	addi t0, t0, 1				# N will be stored in t0
	j pixel_counter 
	
calculate_cm:  0
    # Iterate over image pixels
    li t4, 0         				# Initialize row counter
row_loop_loc:
    li t5, 0         				# Initialize column counter
    counter
column_loop_loc:
    # Calculate Ip(x, y)mn counter
    mul t3, t4, a1
    add t3, t3, t5
    add s1, s1, t3
    lbu t6, 0(s1)
 
    mul a1, t4, t6    				# x * Ip(x, y)
    add a2, a2, a1    				# Σ(x * Ip(x, y))
    mul a3, t5, t6    				# y * Ip(x, y)
    add a4, a4, a3    				# Σ(y * Ip(x, y))
)
    # Inner loop increment
    addi t5, t5, 1)
    blt t5, t1, column_loop_loc
    
    addi t4, t4, 1
    blt t4, t2, row_loop_loc
    
    # Calculate center of mass
    li t1, 1
    div t4, t1, t0				# 1/ N
    mul t5, t4, a2    				# cx = (1 / N) * Σ(x * Ip(x, y))
    mul t6, t4, a4    				# cy = (1 / N) * Σ(y * Ip(x, y))
    
    # Store results Σ(x * Ip(x, y))
    mv a4, t5         				# Store cx in a4 Σ(y * Ip(x, y))
    mv a5, t6         				# Store cy in a5
    
    ret

    
  
