package com.windyroad.nghia.common.sqlite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Nghia-PC on 8/3/2015.
 * Hàm có ít khi chạy Database
 */
public class DatabaseUtil {
    public static final int QUERY_FAIL = -1;
    //region Hướng dẫn sơ
    /* Data type:
     NULL. Giá trị Null
     INTEGER. Giá trị số nguyên.
     REAL. Giá trị số thực Float.
     TEXT. Giá trị Text (văn bản).
     BLOB. Giá trị blob of data (Lưu ảnh, file), stored exactly as it was input.

     Boolean: Lưu kiểu Integer(0-false and 1-true)
     Date and Time: Lưu kiểu text (ISO8601 strings ("YYYY-MM-DD HH:MM:SS.SSS"))
     */

    /* Câu lệnh cơ bản:
    CREATE TABLE t1(
        t  TEXT,     -- text affinity by rule 2
        nu NUMERIC,  -- numeric affinity by rule 5
        i  INTEGER,  -- integer affinity by rule 1
        r  REAL,     -- real affinity by rule 4
        no BLOB      -- no affinity by rule 3
    );
    ALTER TABLE database_name.table_name RENAME TO new_table_name;
    ALTER TABLE database_name.table_name ADD COLUMN new_table_name TEXT/INTEGER;

    -- Values stored as TEXT, INTEGER, INTEGER, REAL, REAL.
    DELETE FROM t1;
    INSERT INTO t1 VALUES(500.0, 500.0, 500.0, 500.0, 500.0);
    SELECT typeof(t), typeof(nu), typeof(i), typeof(r), typeof(no) FROM t1;
    text|integer|integer|real|real
    SELECT a < 40,   a < 60,   a < 600 FROM t1;

    CROSS JOIN:
        SELECT ... FROM t1 CROSS JOIN t2 ...
    INNER JOIN:
        SELECT ... FROM t1 [INNER] JOIN t2 ON t1.id=t2.t1_id
    OUTER JOIN:
        SELECT ... FROM t1 OUTER JOIN t2 ON t1.id=t2.t1_id
    UNION/UNION ALL:
        SELECT column1 [, column2 ]
        FROM table1 [, table2 ]
        [WHERE condition]

        UNION/UNION ALL

        SELECT column1 [, column2 ]
        FROM table1 [, table2 ]
        [WHERE condition]
     */

    /* Comparison Expressions:
    =, ==, <, <=, >, >=, !=, <>, IN, NOT IN, BETWEEN, IS, IS NOT
    IS NULL, IS NOT NULL
    CAST(expr AS type)
    a BETWEEN b AND c
    AND, OR
    ORDER BY, GROUP BY,
    UNION, INTERSECT, EXCEPT
     */

    /* Operators: +, -, *, /, %, <<, >>, &, | */

    //endregion

    /**
     * Tạo mã kiểu String, mã Hex theo thời gian thực
     * (kiểu: yyyyMMddHHmmssSSS(3 số) ngẫu nhiên) => Hex**/
    public static String getCodeGenerationByTime(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        long longId = Long.valueOf(dateFormat.format(new Date()));
        String strId = Long.toHexString(longId);

        String rand = new Random().nextInt(999) + "";

        return strId + rand;
    }
}
