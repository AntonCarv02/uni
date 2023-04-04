li t0, 0x89abcdef
li t1, 0x7fffefe0
sw t0, 0(t1)


lui t0, 0x89abd
addi t0, t0, -0x211 #def em C2

lui t0, 0x7fffe
addi t0, t0, -0x020 #fe0 em C2 1111 1110 0000 -- 0000 0010 0000 0x020

sw t1, -0x020(t0)
