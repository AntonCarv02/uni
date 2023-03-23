
slli t1, t0, 24

srli t4, t0, 24

srli t2, t0,8
andi t2,t2, 0x0ff
srli t2,t2,16

srli t3, t0, 16
andi t3,t3, 0x0ff
srli t2,t2,8

or t0, zero, zero

or t0, t0, t1
or t0, t0, t2
or t0, t0, t3
or t0, t0, t4

## y = !x --> sltui