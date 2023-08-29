/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.viljinsky.project2023.app2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sound.midi.Receiver;
import ru.viljinsky.project2023.DB;
import ru.viljinsky.project2023.Recordset;

/**
 *
 * @author viljinsky
 */
class AppDB2 extends HashMap<String, Recordset> implements DB, DataModel {

    private static final Map<String, String[]> primary = new HashMap<>();
    static {
        primary.put(DAY_LIST, new String[]{DAY_ID});
        primary.put(BELL_LIST, new String[]{BELL_ID});
        primary.put(SKILL, new String[]{SKILL_ID});
        primary.put(DAY_LIST, new String[]{DAY_ID});
    }
    private static final Map<String, String[]> columns = new HashMap<>();
    static {
        columns.put(DAY_LIST, new String[]{DAY_ID, DAY_NAME, DAY_SHORT_NAME});
        columns.put(BELL_LIST, new String[]{BELL_ID, TIME_START, TIME_END});
        columns.put(SHIFT_TYPE, new String[]{SHIFT_TYPE_ID, SHIFT_TYPE_NAME});
        columns.put(SHIFT, new String[]{SHIFT_ID, SHIFT_TYPE_ID, SHIFT_NAME});
        columns.put(SHIFT_DETAIL, new String[]{SHIFT_ID, BELL_ID});
        columns.put(PROFILE_TYPE, new String[]{PROFILE_TYPE_ID, PROFILE_TYPE_NAME, PROFILE_ID});
        columns.put(PROFILE, new String[]{PROFILE_ID, PROFILE_NAME});
        columns.put(PROFILE_DETAIL, new String[]{PROFILE_ID, SUBJECT_ID});
        columns.put(SKILL, new String[]{SKILL_ID, SKILL_NAME});
        columns.put(CURRICULUM, new String[]{CURRICULUM_ID, CURRICULUM_NAME});
        columns.put(CURRICULUM_DETAIL, new String[]{SKILL_ID, CURRICULUM_ID, SUBJECT_ID, HOUR_PER_WEEK, HOUR_PER_DAY});
        columns.put(SUBJECT_DOMAIN, new String[]{SUBJECT_DOMAIN_ID, SUBJECT_DOMAIN_NAME});
        columns.put(SUBJECT, new String[]{SUBJECT_ID, SUBJECT_DOMAIN_ID, SUBJECT_NAME});
        columns.put(DEPART, new String[]{DEPART_ID, SKILL_ID, CURRICULUM_ID, DEPART_LABEL});
        columns.put(SUBJECT_GROUP, new String[]{DEPART_ID, GROUP_ID, SUBJECT_ID});
        columns.put(SCHEDULE, new String[]{DAY_ID, BELL_ID, WEEK_ID, DEPART_ID, GROUP_ID, SUBJECT_ID});
        columns.put(SCHEDULE_STATE, new String[]{SCHEDULE_STATE_ID, SCHEDULE_STATE_NAME});
        columns.put(TEACHER, new String[]{TEACHER_ID, LAST_NAME, FIRST_NAME, PATRONYMIC, PROFILE_ID, SHIFT_ID});
        columns.put(BUILDING, new String[]{BUILDING_ID, BUILDING_NAME});
        columns.put(ROOM, new String[]{ROOM_ID, BUILDING_ID, ROOM_NAME, SHIFT_ID, PROFILE_ID});
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
    private static final String[] tables = {DAY_LIST, BELL_LIST, SHIFT, SHIFT_TYPE, SHIFT_DETAIL, SKILL, CURRICULUM, DEPART, TEACHER, ROOM, BUILDING, SUBJECT, SUBJECT_DOMAIN};

    public AppDB2() {
        for (String tableName : tables) {
            put(tableName, createRecordset(tableName));
        }
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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

    @Override
    public void close() throws Exception {
        for(Recordset recordset:this.values()){
            recordset.clear();
        }
    }

    @Override
    public String[] primary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
