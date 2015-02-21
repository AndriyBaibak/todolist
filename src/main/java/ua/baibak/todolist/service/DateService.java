package ua.baibak.todolist.service;

import java.util.Date;

public class DateService {

    public static Date changeSqlDateToUtilDate(java.sql.Date sqldate) {
        Date utildate = new Date(sqldate.getTime());
        return utildate;
    }

    public static java.sql.Date changeUtilDateToSqlDate(Date utildate) {
        java.sql.Date sqldate = new java.sql.Date(utildate.getTime());
        return sqldate;
    }


}
