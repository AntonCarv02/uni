create table cliente(
    cliente_id varchar(50) Primary key,
    nome varchar(50)
)

create table cliente_morada(
    cliente_id varchar(50),
    nome_rua varchar(50),
    codigo_postal varchar(10),
    pais varchar(50),
    primary key(cliente_id)
)

create table cliente_cartão(
    clienteId incteger references cliente(liente_id),
    numero_cartão integer,
    
    )





create table condutor(
    condutor_id integer Primary key,
    nome varchar(50),
    condutor_nif integer Unique,
    condutor_nib integer,
    condutor_residencia varchar(100),
    condutor_nacionalidade varchar(50)
)

create table condutor_telefone(
    condutor_id integer references condutor(condutor_id),
    telefone integer unique
)

create table veiculo(
    capacidade integer,
    tipo varchar(20),
    matricula varchar(10) primary key
)


create table condutor_veiculo(
    condutor_id integer references condutor(condutor_id),
    matricula integer references veiculo(matricula),
)
create table servico(
    servico_id int not NULL ,
    cliente_id int NOT NULL ,
    condutor_id int not null ,
    origem varchar(100),
    destino varchar(100),
    data_inicio Timestamp,
    date_fim Timestamp,
    valor decimal(3,3),
    PRIMARY KEY (servico_id),
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id),
    FOREIGN KEY (condutor_id) REFERENCES condutor(condutor_id)
);

INSERT INTO cliente
VALUES
  (1, 'Maria Silva', 272198200),
  (2, 'Manuel Santos', 244885451),
  (3, 'Jordan Carter', 252403770),
  (4, 'Kanye West', 246187247),
  (5, 'Siza Vieira', 294933352),
  (6, 'Teresa Carvalho', 287255980),
  (7, 'Maria Aleixo', 234376490),
  (8, 'Mafalda Trindade', 255176490),
  (9, 'Jose Mario Branco', 262993376),
  (10, 'Nina Simone', 249381044);




INSert into condutor
VALUES
(1,'João Carlos', 207678421,00100,'Lisboa','Portugues'),
(2,'Joaquim Gomes',987654321,00369,'Porto','Portugues'),
(3,'Juan Cortez', 456789012,01234, 'Badajoz','Espanhol'),
(4,'José Dias', 876543210, 07655, 'Alcochete','Portugues'),
(5,'Manuel Texeira',321098765, 09876, 'Rendufas','Brasileiro');

insert into veiculo
values
    (4,'Pequeno','13ABC3567'),
	(4,'Pequeno','89DEF0313'),
	(4,'Pequeno','21GHI3438'),
	(6,'Grande','79JKL5433'),
	(6,'Grande','31ZXC0003');
INSERT INTO condutor_veiculo
VALUES
	(1, '89DEF0313'),
	(4, '21GHI3438'),
	(3, '79JKL5433'),
	(2, '31ZXC0003'),
	(5, '13ABC3567');



 
   /* cliente_id =1 */
INSERT INTO servico
VALUES
  (1, 'Evora', 'Lisboa', '2023-10-19 10:23:54', '2023-10-19 12:23:54', 20,1,1),
  (2, 'Evora', 'Lisboa', '2023-10-20 10:23:54', '2023-10-20 12:23:54', 20,2,1),
  (3,'Evora', 'Lisboa', '2023-10-21 10:23:54', '2023-10-21 12:23:54', 20,3,1),
  (4, 'Evora', 'Lisboa', '2023-10-22 10:23:54', '2023-10-22 12:23:54', 20,4,1),
  (5, 'Evora', 'Lisboa', '2023-10-23 10:23:54', '2023-10-23 12:23:54', 20,5,1),
  (6, 'Evora', 'Lisboa', '2023-10-24 10:23:54', '2023-10-24 12:23:54', 20,1,1),
  (7, 'Evora', 'Lisboa', '2023-10-25 10:23:54', '2023-10-25 12:23:54', 20,2,1),
  (8, 'Evora', 'Lisboa', '2023-10-26 10:23:54', '2023-10-26 12:23:54', 20,4,1),
  (9, 'Evora', 'Lisboa', '2023-10-27 10:23:54', '2023-10-27 12:23:54', 20,4,1),
  (10,'Evora', 'Lisboa', '2023-10-28 10:23:54', '2023-10-28 12:23:54', 20,5,1);






/* cliente id - 2 */
INSERT INTO servico
VALUES
  (11,'Lisboa','Evora','2023-11-19 10:23:54','2023-11-19 12:23:54',30,1,2),
  (12,'Lisboa','Viana do Castelo','2023-12-20 8:23:54','2023-12-20 12:23:54',60,2,2),
  (13,'Lisboa','Evora','2023-11-21 10:23:54','2023-11-21 12:23:54',30,3,2),
  (14,'Lisboa','Evora','2023-11-22 10:23:54','2023-11-22 12:23:54',30,4,2),
  (15,'Lisboa','Evora','2023-11-23 10:23:54','2023-11-23 12:23:54',30,5,2),
  (16,'Evora','Montemor','2023-11-24 10:23:54','2023-11-24 12:23:54',30,1,2),
  (17,'Evora','Montemor','2023-11-25 10:23:54','2023-11-25 12:23:54',30,2,2),
  (18,'Lisboa','Montemor','2023-11-26 10:23:54','2023-11-26 12:23:54',30,3,2),
  (19,'Lisboa','Montemor','2023-11-27 10:23:54','2023-11-27 12:23:54',30,4,2);


/* cliente id - 3 */
INSERT INTO servico
VALUES
  (20,'Lisboa','Evora','2023-12-19 10:23:54','2023-12-19 12:23:54',30,3,3),
  (21,'Lisboa','Evora','2023-12-20 10:23:54','2023-12-20 12:23:54',30,5,3),
  (22,'Lisboa','Evora','2023-12-21 10:23:54','2023-12-21 12:23:54',30,3,3),
  (23,'Lisboa','Evora','2023-12-22 10:23:54','2023-12-22 12:23:54',30,4,3),
  (24,'Lisboa','Evora','2023-12-23 10:23:54','2023-12-23 12:23:54',30,5,3),
  (25,'Lisboa','Evora','2023-12-24 10:23:54','2023-12-24 12:23:54',30,1,3),
  (26,'Lisboa','Evora','2023-12-25 10:23:54','2023-12-25 12:23:54',30,2,3),
  (27,'Lisboa','Evora','2023-12-26 10:23:54','2023-12-26 12:23:54',30,3,3);


/* cliente id - 4 */
 insert into servico 
values
 (28,'Lisboa','Porto','2023-9-19 10:23:54','2023-9-19 12:23:54',30,1,4),
 (29,'Lisboa','Porto','2023-9-20 10:23:54','2023-9-20 12:23:54',30,5,4),
 (30,'Lisboa','Porto','2023-9-21 10:23:54','2023-9-21 12:23:54',30,3,4),
 (31,'Lisboa','Porto','2023-9-22 10:23:54','2023-9-22 12:23:54',30,4,4),
 (32,'Lisboa','Porto','2023-9-23 10:23:54','2023-9-23 12:23:54',30,5,4),
 (33,'Lisboa','Porto','2023-9-24 10:23:54','2023-9-24 12:23:54',30,1,4),
 (34,'Lisboa','Porto','2023-9-25 10:23:54','2023-9-25 12:23:54',30,2,4);




/* cliente id - 5 */
insert into servico
values
(35,'Lisboa','Beja','2023-8-19 10:23:54','2023-8-19 12:23:54',30,1,5),
 (36,'Lisboa','Beja','2023-8-20 10:23:54','2023-8-20 12:23:54',30,5,5),
 (37,'Lisboa','Beja','2023-8-21 10:23:54','2023-8-21 12:23:54',30,3,5),
 (38,'Lisboa','Beja','2023-8-22 10:23:54','2023-8-22 12:23:54',30,4,5),
 (39,'Lisboa','Porto','2023-8-23 10:23:54','2023-8-23 12:23:54',30,5,5),
 (40,'Lisboa','Beja','2023-8-24 10:23:54','2023-8-24 12:23:54',30,1,5);


/* cliente id - 6 */
insert into servico
 values
 (41,'Lisboa','Setubal','2023-7-19 10:23:54','2023-7-19 12:23:54',30,1,6),
 (42,'Lisboa','Setubal','2023-7-20 10:23:54','2023-7-20 12:23:54',30,2,6),
 (43,'Lisboa','Setubal','2023-7-21 10:23:54','2023-7-21 12:23:54',30,3,6),
 (44,'Lisboa','Setubal','2023-7-22 10:23:54','2023-7-22 12:23:54',30,4,6),
 (45,'Lisboa','Setubal','2023-7-23 10:23:54','2023-7-23 12:23:54',30,5,6);

/* cliente id - 7 */
insert into servico 
values
 (46,'Lisboa','Evora','2023-6-19 10:23:54','2023-6-19 12:23:54',30,1,7),
 (47,'Lisboa','Evora','2023-6-20 10:23:54','2023-6-20 12:23:54',30,5,7),
 (48,'Lisboa','Evora','2023-6-21 10:23:54','2023-6-21 12:23:54',30,3,7),
 (49,'Lisboa','Evora','2023-6-22 10:23:54','2023-6-22 12:23:54',30,4,7);

/* cliente id - 8 */
insert into servico
 values
 (50,'Beja','Setubal','2023-5-19 10:23:54','2023-5-19 12:23:54',30,1,8),
 (51,'Beja','Setubal','2023-5-20 10:23:54','2023-5-20 12:23:54',30,5,8),
 (52,'Beja','Setubal','2023-5-21 10:23:54','2023-5-21 12:23:54',30,3,8);

/* cliente id - 9 */
insert into servico
 values
 (53,'Beja','Porto','2023-4-19 10:23:54','2023-4-19 12:23:54',30,1,9),
 (54,'Beja','Porto','2023-4-20 10:23:54','2023-4-20 12:23:54',30,5,9);

/* cliente id - 10 */
insert into servico values
 (55,'Porto','Setubal','2023-3-19 10:23:54','2023-3-19 12:23:54',30,1,10);


