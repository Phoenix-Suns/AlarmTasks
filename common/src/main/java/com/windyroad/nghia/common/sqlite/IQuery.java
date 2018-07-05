package com.windyroad.nghia.common.sqlite;

/**
 * Created by Imark-N on 12/7/2015.
 * Dùng để truy vấn, có ví dụ
 */
public interface IQuery {

    //region TYPE
    /**
     * The value is a NULL value
     * Giá trị NULL
     */
    String NULL_TYPE = " NULL ";

    /**
     * The value is a signed integer, stored in 1, 2, 3, 4, 6, or 8 bytes depending on the magnitude of the value.
     * INT - INTEGER - TINYINT - SMALLINT - MEDIUMINT - BIGINT - UNSIGNED BIG INT - INT2 - INT8
     * Datetime: Số giây từ 1970-01-01 00:00:00 UTC (cộng thêm)
     * Số nguyên có dấu (-+)
     */
    String INTEGER_TYPE = " INTEGER ";

    /**
     * The value is a floating point value, stored as an 8-byte IEEE floating point number.
     * REAL - DOUBLE - DOUBLE PRECISION - FLOAT
     * Datetime: Số ngày từ Greenwich November 24, 4714 B.C
     * Số thực dấu chấm động
     */
    String REAL_TYPE = " REAL ";

    /**
     * NUMERIC - DECIMAL(10,5) - BOOLEAN - DATE - DATETIME
     * Bollean: true=1, 0=false
     */
    String NUMERIC = " NUMERIC ";

    /**
     * The value is a text string, stored using the database encoding (UTF-8, UTF-16BE or UTF-16LE).
     * CHARACTER(20) - VARCHAR(255) - VARYING CHARACTER(255) - NCHAR(55) - NATIVE CHARACTER(70) - NVARCHAR(100) - TEXT - CLOB
     * Datetime: định dạng "YYYY-MM-DD HH:MM:SS.SSS"
     * String, encoding của CSDL
     */
    String TEXT_TYPE = " TEXT ";

    /**
     * The value is a blob of data, stored exactly as it was input.
     * IMAGE - OBJECT - SOUND
     * Nhập thế nào lưu chính xác thế đó
     */
    String BLOB_TYPE = " BLOB ";
    //endregion

    String COMMA_SEP = ",";
    String DOT_SEP = ".";

    String CREATE = " CREATE ";
    String ALTER = " ALTER ";
    String DROP = " DROP ";

    String IS = " IS ";
    String IS_NOT = " IS_NOT ";
    String BEGIN = " BEGIN ";
    String END = " END ";
    String PRAGMA = " PRAGMA ";
    String RELEASE = " RELEASE ";  // Syntax: RELEASE savepoint_name
    String INDEX = " INDEX ";  // tăng hiệu xuất SELECt. Syntax: CREATE INDEX index_name ON table_name (column_name) hay CREATE INDEX index_name ON table_name;
    String REINDEX = " REINDEX ";  // Syntax: REINDEX database_name.index_name|database_name.table_name|collation_name;
    String SAVEPOINT = " SAVEPOINT ";  // Syntax: SAVEPOINT savepoint_name;
    String ROLLBACK = " ROLLBACK ";  // Syntax: ROLLBACK; hay ROLLBACK TO SAVEPOINT savepoint_name;
    String VACUUM = " VACUUM ";



    //region CREATE TABLE
    /**
     * DEMO CREATE TABLE:
     * CREATE TABLE table_name(
     column1 datatype  PRIMARY_KEY ,
     column2 datatype  AUTOINCREMENT  NOT_NULL  UNIQUE,
     column3 datatype  DEFAULT 1,
     .....
     columnN datatype,
     PRIMARY KEY( one or more columns )
     );

     Demo Update Table:
     UPDATE table_name
     SET column1 = value1, column2 = value2....columnN=valueN
     [ WHERE  CONDITION ];
     */
    String TABLE = " TABLE ";
    String PRIMARY_KEY = " PRIMARY KEY "; // khóa chính
    String AUTOINCREMENT = " AUTOINCREMENT "; // tự động tăng
    String DEFAULT = " DEFAULT "; // giá trị mặc định
    String NOT_NULL = " NOT NULL "; // không rỗng
    String UNIQUE = " UNIQUE "; // duy nhất
    //endregion


    //region UPDATE TABLE
    String RENAME_TO = " RENAME TO ";
    //endregion


    //region INSERT ROW
    /**
     * Demo insert row:
     * INSERT INTO table_name( column1, column2....columnN)
     VALUES ( value1, value2....valueN);
     */
    String INSERT_INTO = " INSERT INTO ";  // Chèn vào
    String VALUES = " VALUES ";
    //endregion

    // UPDATE ROW

    //region DELETE ROW
    /**
     * demo delete row:
     * DELETE FROM table_name
        WHERE  {CONDITION};
     */
    String DELETE = " DELETE ";
    //endregion


    // QUERY ROW
    String SELECT = " SELECT ";
    String FROM = " FROM ";
    String WHERE = " WHERE ";
    String GROUP_BY = " GROUP BY ";  // nhóm. Syntax: SELECT SUM(column_name) ... GROUP BY column_name;
    String HAVING = " HAVING "; // điều kiện kèm GroupBy. Syntax: GROUP BY name HAVING count(name) < 2;
    String ORDER_BY = " ORDER BY "; // Sắp xếp theo. Syntax: ORDER BY column_name {ASC|DESC}

    String ON = " ON ";
    String CROSS_JOIN = " CROSS JOIN ";  // Lấy hết. Syntax: ...FROM company CROSS JOIN department;
    String INNER_JOIN = " INNER JOIN ";  // Lấy phần chung. Syntax: ...FROM company INNER JOIN department ON company.id = department.emp_id;
    String LEFT_OUTER_JOIN = " LEFT OUTER JOIN ";  // Lấy bảng LEFT. Syntax: ...FROM company LEFT OUTER JOIN department ON company.id = department.emp_id;

    String ASC = " ASC ";
    String DESC = " DESC ";

    String AND = " AND ";  // syntax: _ and _
    String OR = " OR ";  // syntax: _ or _
    String BETWEEN = " BETWEEN ";  // syntax: BETWEEN _ AND _
    String COUNT = " COUNT ";  // syntax: SELECT COUNT(column)
    String DISTINCT = " DISTINCT ";  // Không trùng. syntax: SELECT DISTINCT _
    String EXISTS = " EXISTS "; // Kiểm tra tồn tại. Syntax: WHERE  column_name EXISTS (SELECT * FROM   table_name );
    String EXPLAIN = " EXPLAIN "; // không biết ))
    String GLOB = " GLOB ";  // không biết luôn ))
    String IN = " IN "; // Tồn tại trong truy vấn. Syntax: WHERE  column_name IN (val-1, val-2,...val-N)
    String NOT_IN = " NOT IN "; // không tồn tại trong. Syntax: WHERE  column_name NOT IN (val-1, val-2,...val-N)
    String LIKE = " LIKE "; // Trùng, giống =, dùng cho chuỗi. Syntax: WHERE  column_name LIKE { PATTERN }; // pattern: '%chuoi%'

    //region TRIGGER

    /**
     * CREATE TRIGGER DEMO:
     * CREATE TRIGGER database_name.trigger_name
     BEFORE INSERT ON table_name FOR EACH ROW
     BEGIN
         stmt1;
         stmt2;
         ....
     END;
     */
    String TRIGGER = " TRIGGER ";
    String BEFORE_INSERT_ON = " BEFORE INSERT ON ";
    String FOR_EACH_ROW = " FOR EACH ROW ";

    //endregion


    //region VIEW
    /**
     * CREATE VIEW DEMO:
     * CREATE VIEW database_name.view_name  AS
     SELECT statement....;
     */
    String VIEW = " VIEW ";
    //endregion


    //region VIRTUAL TABLE

    /**
     * Create demo:
     * CREATE VIRTUAL TABLE database_name.table_name USING weblog( access.log );
     or
     CREATE VIRTUAL TABLE database_name.table_name USING fts3( );
     */
    String VIRTUAL_TABLE = " VIRTUAL TABLE ";
    //endregion


    String DROP_TABLE_IF_EXISTS = " DROP TABLE IF EXISTS ";
}
