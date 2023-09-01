/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023.app2;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Recordset;

/**
 *
 * @author viljinsky
 */
class AppDB2 extends HashMap<String, Recordset> implements DB, DataModel {

    private static final String[] tables = {
        DAY_LIST, BELL_LIST, SHIFT, SHIFT_TYPE, SHIFT_DETAIL, SKILL, CURRICULUM, CURRICULUM_DETAIL, DEPART, TEACHER, ROOM, BUILDING,
        SUBJECT, SUBJECT_GROUP, WEEK, STREAM, SCHEDULE, LESSON_TYPE, GROUP_TYPE,
        SUBJECT_DOMAIN, CONDITION, CURRICULUM_CONDITION, ATTR, GROUP_SEQUENCE};

    private static final Map<String, String[]> columns = new HashMap<>();

    static {
        columns.put(ATTR, new String[]{PARAM_NAME, PARAM_VALUE});
        columns.put(BELL_LIST, new String[]{BELL_ID, TIME_START, TIME_END});
        columns.put(BUILDING, new String[]{BUILDING_ID, BUILDING_NAME});
        columns.put(CONDITION, new String[]{CONDITION_ID, CONDITION_NAME, CONDITION_LABEL});
        columns.put(CURRICULUM, new String[]{CURRICULUM_ID, CURRICULUM_NAME});
        columns.put(CURRICULUM_CONDITION, new String[]{CURRICULUM_ID, SKILL_ID, SUBJECT_ID, CONDITION_ID});
        columns.put(CURRICULUM_DETAIL, new String[]{SKILL_ID, CURRICULUM_ID, SUBJECT_ID, HOUR_PER_WEEK, HOUR_PER_DAY, DIFFICULTY, GROUP_TYPE_ID, GROUP_TYPE_ID, LESSON_TYPE_ID});
        columns.put(DAY_LIST, new String[]{DAY_ID, DAY_NAME, DAY_SHORT_NAME});
        columns.put(DEPART, new String[]{DEPART_ID, SKILL_ID, CURRICULUM_ID, SHIFT_ID, DEPART_LABEL, TEACHER_ID, ROOM_ID, BOY_COUNT, GERL_COUNT, SCHEDULE_STATE_ID});
        columns.put(GROUP_TYPE, new String[]{GROUP_TYPE_NAME, GROUP_TYPE_ID});
        columns.put(LESSON_TYPE, new String[]{LESSON_TYPE_ID, LESSON_TYPE_NAME});
        columns.put(STREAM, new String[]{STREAM_ID, STREAM_NAME, SUBJECT_ID, SKILL_ID, ROOM_ID, TEACHER_ID});
        columns.put(WEEK, new String[]{WEEK_ID, WEEK_NAME});
        columns.put(SUBJECT_CONDITION, new String[]{SUBJECT_ID, CONDITION_ID});
        columns.put(SHIFT_TYPE, new String[]{SHIFT_TYPE_NAME, SHIFT_TYPE_ID});
        columns.put(SHIFT, new String[]{SHIFT_ID, SHIFT_TYPE_ID, SHIFT_NAME});
        columns.put(SHIFT_DETAIL, new String[]{SHIFT_ID, BELL_ID});
        columns.put(PROFILE_TYPE, new String[]{PROFILE_TYPE_ID, PROFILE_TYPE_NAME, PROFILE_ID});
        columns.put(PROFILE, new String[]{PROFILE_ID, PROFILE_NAME});
        columns.put(PROFILE_DETAIL, new String[]{PROFILE_ID, SUBJECT_ID});
        columns.put(SKILL, new String[]{SKILL_ID, SKILL_NAME});
        columns.put(SUBJECT_DOMAIN, new String[]{SUBJECT_DOMAIN_ID, SUBJECT_DOMAIN_NAME});
        columns.put(SUBJECT, new String[]{SUBJECT_ID, SUBJECT_NAME, SUBJECT_DOMAIN_ID, GROUP_TYPE_ID, HOUR_PER_WEEK, HOUR_PER_DAY, DIFFICULTY, SORT_ORDER, COLOR});
        columns.put(SUBJECT_GROUP, new String[]{DEPART_ID, GROUP_ID, SUBJECT_ID, SKILL_ID, CURRICULUM_ID, TEACHER_ID, ROOM_ID, WEEK_ID, STREAM_ID, PUPIL_COUNT});
        columns.put(SCHEDULE, new String[]{DAY_ID, BELL_ID, DEPART_ID, GROUP_ID, SUBJECT_ID, TEACHER_ID, ROOM_ID});
        columns.put(SCHEDULE_STATE, new String[]{SCHEDULE_STATE_ID, SCHEDULE_STATE_NAME});
        columns.put(TEACHER, new String[]{TEACHER_ID, LAST_NAME, FIRST_NAME, PATRONYMIC, PROFILE_ID, SHIFT_ID, ROOM_ID});
        columns.put(ROOM, new String[]{ROOM_ID, BUILDING_ID, ROOM_NAME, SHIFT_ID, PROFILE_ID});
        columns.put(GROUP_SEQUENCE, new String[]{GROUP_SEQUENCE_ID, GROUP_SEQUENCE_NAME});
    }
    private static final Map<String, String[]> primary = new HashMap<>();

    static {
        primary.put(DAY_LIST, new String[]{DAY_ID});
        primary.put(BELL_LIST, new String[]{BELL_ID});
        primary.put(SKILL, new String[]{SKILL_ID});
        primary.put(DAY_LIST, new String[]{DAY_ID});
        primary.put(DEPART, new String[]{DEPART_ID});
        primary.put(TEACHER, new String[]{TEACHER_ID});
        primary.put(BUILDING, new String[]{BUILDING_ID});
        primary.put(ROOM, new String[]{ROOM_ID});
        primary.put(SUBJECT_GROUP, new String[]{DEPART_ID, SUBJECT_ID, GROUP_ID});

        primary.put(CURRICULUM_DETAIL, new String[]{SKILL_ID, CURRICULUM_ID, SUBJECT_ID});
        primary.put(CURRICULUM, new String[]{CURRICULUM_ID});
        primary.put(SCHEDULE, new String[]{DAY_ID, BELL_ID, WEEK_ID, DEPART_ID, GROUP_ID, SUBJECT_ID});
    }

    private static final Map<String, String> columnHeaders = new HashMap<>();

    static {
        columnHeaders.put(DAY_NAME, "День");
        columnHeaders.put(DAY_SHORT_NAME, "День(кратко)");
        columnHeaders.put(TIME_START, "Начало");
        columnHeaders.put(TIME_END, "Окончание");
        columnHeaders.put(DEPART_LABEL, "Класс");
        columnHeaders.put(GROUP_LABEL, "Группа");
    }

    public String columnHeader(String tableName, String columnName) {
        return columnHeaders.containsKey(columnName) ? columnHeaders.get(columnName) : columnName;
    }

    public Class<?> columnCalss(String tableName, String clomnName) {
        return Object.class;
    }

    Recordset createRecordset(String tableName) {
        if (columns.containsKey(tableName)) {
            return new Recordset(tableName, columns.get(tableName));
        } else {
            throw new RuntimeException(String.format("table \"%s\"has not defined", tableName.toUpperCase()));
        }
    }


    File file;
    Connection con;

    Recordset recordset(String tableName) throws Exception {

        Recordset recordset = createRecordset(tableName);
        Statement stmt = con.createStatement();

        ResultSet res = stmt.executeQuery("select * from " + tableName);
        ResultSetMetaData meta = res.getMetaData();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            if (meta.getColumnTypeName(i).equals("integer")) {
                String columnName = meta.getColumnName(i);
                recordset.classMap.put(recordset.columnIndex(columnName), Integer.class);
            } else {
                System.out.println("" + meta.getColumnTypeName(i));
            }
        }

        while (res.next()) {
            Object[] p = new Object[recordset.columnCount()];
            for (int column = 0; column < p.length; column++) {
                p[column] = res.getObject(recordset.columnName(column));

            }
            recordset.add(p);
        }
        return recordset;
    }

    Connection conection(File file) throws Exception {
        return DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
    }

    public AppDB2() throws Exception{
        this(null);
        
    }
    
    public AppDB2(File file) throws Exception {
        Class.forName("org.sqlite.JDBC");
        for (String tableName : tables) {
            put(tableName, createRecordset(tableName));
        }
        this.file = file;
    }
    
    @Override
    public Iterable<Recordset> tables() {
        List list = new ArrayList();
        for (String tableName : keySet()) {
            list.add(get(tableName));
        }
        return list;
    }

    @Override
    public Recordset table(String tableName) {
        return containsKey(tableName) ? get(tableName) : null;
    }

    @Override
    public Recordset query(String sql, Object... args) {
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i+1, args[i]);
            }
            
            ResultSet res = stmt.executeQuery();
            ResultSetMetaData meta = res.getMetaData();
            String[] columns = new String[meta.getColumnCount()];
            for(int i=0;i<columns.length;i++){
                columns[i] = meta.getColumnName(i+1);
            }            
            Recordset recordset = new Recordset(columns);
            while(res.next()){
                Object[] p = new Object[columns.length];
                for(int i=0;i<p.length;i++){
                    p[i]=res.getObject(i+1);
                }
                recordset.add(p);
            }
            return recordset;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void execute(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void commit() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void rallback() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void open(){
        try{
            con = conection(file);
            for(Recordset r: this.values()){
                Recordset tmp = recordset(r.getName());
                r.addAll(tmp);
            }
            
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void close() throws Exception {
        if (con != null) {
            con.close();
            con = null;
        }
    }

    @Override
    public String[] primary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
