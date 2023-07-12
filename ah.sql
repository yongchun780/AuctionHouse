DROP TABLE IF EXISTS AH_USER;
DROP TABLE IF EXISTS AH_ITEM;
DROP TABLE IF EXISTS AH_PROFILE;

-- create user table
  CREATE TABLE AH_USER
   (USERNAME VARCHAR(100), 
  PASSWORD VARCHAR(100), 
  ID int,
  PROFILE_ID int
   );

-- create item table
  CREATE TABLE AH_ITEM 
   (  ID int,
  NAME VARCHAR(100),
  STARTING_PRICE int, 
  CURRENT_PRICE int, 
  PAY_OFF_PRICE int,
  EXPIRE_TIME TIMESTAMP, 
  INFO VARCHAR(500),
  SOLD BOOLEAN,
  BUYER_ID int,
  SELLER_ID int
   );

-- create profile table
  CREATE TABLE AH_PROFILE 
   (ID int, 
  TYPE VARCHAR(100)
   );

--  DDL for Sequence AH_ITEM_SEQ
--------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS public.AH_ITEM_SEQ
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.AH_ITEM_SEQ
    OWNER TO postgres;

--  DDL for Sequence AH_USER_SEQ
--------------------------------------------------------
CREATE SEQUENCE IF NOT EXISTS public.AH_USER_SEQ
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.AH_USER_SEQ
    OWNER TO postgres;

--  DDL for Index AH_ITEM_PK
--------------------------------------------------------
ALTER TABLE AH_ITEM ADD PRIMARY KEY (ID);

--  DDL for Index AH_USER_PK
--------------------------------------------------------
ALTER TABLE AH_USER ADD PRIMARY KEY (ID);

--  DDL for Index AH_PROFILE_PK
--------------------------------------------------------
ALTER TABLE AH_PROFILE ADD PRIMARY KEY (ID);

-- TODO: Trigger. 不会写

Insert into AH_PROFILE (ID,TYPE) values (1,'BUYER');
Insert into AH_PROFILE (ID,TYPE) values (2,'SELLER');
Insert into AH_PROFILE (ID,TYPE) values (3,'ADMIN');
Insert into AH_PROFILE (ID,TYPE) values (4,'BANNED');