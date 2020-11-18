/* ������� �������� */
DROP TABLE products PURGE;
/
CREATE TABLE products
(
  id   NUMBER(10,0) NOT NULL,
  name VARCHAR2(255),
	PRIMARY KEY (id)
);
/

/* ������� ���� */
DROP TABLE prices PURGE;
/
CREATE TABLE prices
(
  id    NUMBER(10,0) NOT NULL,
  price VARCHAR2(255),
	dates DATE  DEFAULT SYSDATE,
	PRIMARY KEY (id)
);
/
/* �������� */
SELECT * 
  FROM products pd, 
	     prices   pr 
 WHERE pd.id = pr.id;
