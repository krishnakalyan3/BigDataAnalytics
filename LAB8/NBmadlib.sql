DROP TABLE IF EXISTS myschema.NBmdlib;
CREATE TABLE myschema.NBmdlib (
     attr INTEGER[],
     class INTEGER )
     DISTRIBUTED BY (class);
INSERT INTO myschema.NBmdlib (attr[1],attr[2],attr[3],class)
SELECT   
     t1.age age,
     t1.sex sex,
     t1.educ educ ,
     t1.income income
FROM 
     (
     SELECT 
         CASE 
            WHEN inctot BETWEEN 10000 AND 50000 THEN 1 
            WHEN inctot BETWEEN  50000+1 AND 80000 THEN 2
            WHEN inctot > 80000  THEN 3
                ELSE 0
           END income ,
          CASE 
            WHEN age BETWEEN 20 AND 30 THEN 1
            WHEN age BETWEEN 31 AND 45 THEN 2
            WHEN age > 45 THEN 3
                ELSE 0      
          END age, 
         CASE WHEN sex = 1  THEN 1
            WHEN sex = 2 THEN 2
             ELSE 0
         END  sex,
         CASE WHEN educ >14 THEN 1
            WHEN educ BETWEEN 12 AND 14 THEN 2
            WHEN educ <12 THEN 3
              ELSE 0
         END educ
FROM 
    persons) AS t1

WHERE 
   not (income = 0 OR age = 0 OR sex = 0)
;

DROP TABLE IF EXISTS myschema.nb_feature_probs;
DROP TABLE IF EXISTS myschema.nb_class_priors;
SELECT madlib.create_nb_prepared_data_tables( 
'myschema.NBmdlib', 'class', 'attr', 3, 'myschema.nb_feature_probs', 'myschema.nb_class_priors');
SELECT * FROM myschema.nb_feature_probs;
SELECT * FROM myschema.nb_class_priors;


DROP TABLE IF EXISTS myschema.NBmdlib_test;
CREATE TABLE myschema.NBmdlib_test (
     id SERIAL,
     attr INTEGER[],
     original_data INTEGER
     ) DISTRIBUTED BY (id);
INSERT INTO myschema.NBmdlib_test (attr[1],attr[2],attr[3],original_data)
SELECT       
     t1.age 
     ,t1.sex 
     ,t1.educ 
     ,t1.income income
FROM 
     (
     SELECT 
         CASE 
            WHEN inctot BETWEEN 10000 AND 50000 THEN 1 
            WNEN inctot BETWEEN  50000+1 AND 80000 THEN 2
            WNEN inctot > 80000  THEN 3
                ELSE 0
           END income ,
          CASE 
            WHEN age BETWEEN 20 AND 30 THEN 1
            WHEN age BETWEEN 31 AND 45 THEN 2
            WHEN age > 45 THEN 3
                ELSE 0      
          END age, 
         CASE WHEN sex = 1  THEN 1
            WHEN sex = 2 THEN 2
             ELSE 0
         END  sex,
         CASE WHEN educ >14 THEN 1
            WHEN educ BETWEEN 12 AND 14 THEN 2
            WHEN educ <12 THEN 3
              ELSE 0
         END educ
FROM 
    persons) t1

WHERE 
   NOT (income = 0 OR age = 0 OR sex = 0)
ORDER BY RANDOM ()
LIMIT 10 
;

SELECT * from myschema.NBmdlib_test ORDER BY id;

DROP TABLE IF EXISTS myschema.nb_classify_view_fast;
DROP TABLE IF EXISTS myschema.nb_probs_view_fast;

SELECT madlib.create_nb_classify_view (
'myschema.nb_feature_probs', 'myschema.nb_class_priors', 'myschema.NBmdlib_test', 'id', 'attr', 3, 'myschema.nb_classify_view_fast');
SELECT * FROM myschema.nb_classify_view_fast ORDER BY key;
SELECT madlib.create_nb_probs_view (
'myschema.nb_feature_probs', 'myschema.nb_class_priors', 'myschema.NBmdlib_test', 'id', 'attr', 3, 'myschema.nb_probs_view_fast');
SELECT * FROM myschema.nb_probs_view_fast ORDER BY key,class;




