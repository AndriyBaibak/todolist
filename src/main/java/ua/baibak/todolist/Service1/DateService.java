package ua.baibak.todolist.Service1;

import java.util.Date;

public class DateService {

    public static Date changeSqlDateToUtilDate(java.sql.Date sqldate) {
        java.util.Date utildate = new java.util.Date(sqldate.getTime());
        return utildate;
    }

    public static java.sql.Date changeUtilDateToSqlDate(java.util.Date utildate) {
        java.sql.Date sqldate = new java.sql.Date(utildate.getTime());
        return sqldate;
    }


}
