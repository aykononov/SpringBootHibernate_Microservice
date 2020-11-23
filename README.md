<small>

# Создание базового модуля REST API для обслуживания фронта интернет-магазина.

### Цель проекта.

Реализовать приложение на базе фреймворка Spring Boot 2 и Java минимум 8 версии с использованием базы данных Oracle через Hibernate. 

### Технологии:
- Spring Boot;
- Hibernate;
- Oracle;
- Maven.

### Начальные условия.

```
Пример структуры таблиц в БД Oracle.
1. Таблица товар. Хранит название товара.
   Колонки: id, name.
2. Таблица цена товара. Хранит цену на товар и дату с которой цена актуальная. 
   По каждому товару может быть несколько цен с разными датами.
   Колонки: id, price, date, product_id.

Таблицы должны создаваться автоматически в БД при старте приложения или в приложении 
должен быть приложен файл со скриптом создания необходимых сущностей.

Функционал приложения.
1. Загрузка товаров и цен из csv-файла.
   Приложение должно уметь загружать данные из csv-файла. 
   Путь директории с файлами настраивается в конфигурационном файле приложения. 
   Пример формат данных csv-файла:

       product_id; product_name; price_id; price; price_date

   В логах должен быть отмечен факт старта обработки файла и результат обработки файла 
   с количеством обработанных записей(товаров и цен).
   Загрузка файла стартует при появлении файла в указанной директории.

2. Получение списка товаров и актуальных цен.
   Приложение должно предоставлять Rest метод, возвращающий из БД список товаров с актуальными ценами, 
   на указанный в запросе день.

       GET /products?date=yyyy-mm-dd 

   Формат данных ответа - json. Список {"name": "Товар 1", "price": 100.99} 

3. Получение статистики.
   Приложение должно предоставлять Rest метод, возвращающий статистику по загруженным товарам и ценам.

       GET /products/statistic 

   Формат данных ответа - json.

   Параметры статистики:
   - Количество товаров в БД. Формат - просто цифра.
   - Как часто менялась цена товара. Группировка по товарам. Формат - список {"name": "Товар 1", "frequency": 2} 
   - Как часто менялась цена товара. Группировка по дням. Формат - список {"date": "yyyy-mm-dd", "frequency": 6} 
   Каждый параметр статистики необходимо запрашивать в отдельном параллельном потоке.
```

### Применение

- Запустите приложение и перейдите по адресу http://localhost:8081/  
    `(порт 8080 может использовать Oracle Apex)`

- В POSTMAN используйте следующие URL-адреса для вызова методов контроллеров и просмотра взаимодействия с базой данных:
    * POST `http://localhost:8081/addProducts`: добавить продукты 
        JSON контент:
        ```json
        [
         {"id":"1","name":"phone"},
         {"id":"2","name":"battery"},  
         {"id":"3","name":"case"}
        ]
         ```
    * GET `http://localhost:8081/products`: получить все продукты
    * DELETE `http://localhost:8081/delete/1`: удалить по индексу 1

<details><summary>Скрипты структуры БД ...</summary>

>Приложение автоматически создает структуру в БД, а скрипты на всякий случай...
>
>```sql
>/* таблица Продукты */
>DROP TABLE products PURGE;
>/
>CREATE TABLE products
>(
>  id   NUMBER(10,0) NOT NULL,
>  name VARCHAR2(255),
>  PRIMARY KEY (id)
>);
>/
>/* таблица Цены */
>DROP TABLE prices PURGE;
>
>CREATE TABLE prices
>(
>  id   NUMBER(10,0) NOT NULL,
>  price      VARCHAR2(255),
>  pdate DATE DEFAULT SYSDATE,
>  product_id  NUMBER(10,0) NOT NULL,
>  PRIMARY KEY (id)
>);
>/
>/* проверка */
>SELECT * 
>  FROM products pd, 
>       prices   pr 
> WHERE pd.id  = pr.product_id;
>```
</details></small>

<details><summary>Скрипты структуры БД ...</summary>

```sql
DROP TABLE products PURGE;
/
CREATE TABLE products
(
  id   NUMBER(10,0) NOT NULL,
  name VARCHAR2(255),
	PRIMARY KEY (id)
);
/

/* таблица Цены */
DROP TABLE prices PURGE;
/
CREATE TABLE prices
(
  id    NUMBER(10,0) NOT NULL,
  price NUMBER,
	pdate DATE  DEFAULT SYSDATE,
	product_id NUMBER(10,0),
	PRIMARY KEY (id),
	CONSTRAINT fk_product_id FOREIGN KEY (PRODUCT_ID)
  REFERENCES PRODUCTS (ID)
);
/
/* проверка */
SELECT * 
  FROM products pd, 
	     prices   pr 
 WHERE pd.id = pr.product_id(+);
```

</details>