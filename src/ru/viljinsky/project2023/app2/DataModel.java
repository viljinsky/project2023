package ru.viljinsky.project2023.app2;

/**
 * Поля данных
 * @author viljinsky
 */
public interface DataModel {
    
    // day_list bell_list shift shift_type shift_detail
    
    public static final String DAY_LIST = "day_list";
    public static final String DAY_ID = "day_id";
    public static final String DAY_SHORT_NAME = "day_short_name";
    public static final String DAY_NAME = "day_name";
            
    public static final String BELL_LIST = "bell_list";
    public static final String BELL_ID = "bell_id";
    public static final String TIME_START = "time_start";
    public static final String TIME_END = "time_end";
    
    public static final String SHIFT_TYPE = "shift_type";
    public static final String SHIFT_TYPE_ID = "shift_type_id";
    public static final String SHIFT_TYPE_NAME = "shift_type_name";    
    
    public static final String SHIFT = "shift";
    public static final String SHIFT_NAME = "shift_name";
    public static final String SHIFT_ID = "shift_id";
    
    public static final String SHIFT_DETAIL = "shift_detail";
    
    // subject_domain,subject,profiele,profile_type,curriculum.curriculum_detail
    
    public static final String SUBJECT_DOMAIN = "subject_domain";
    public static final String SUBJECT_DOMAIN_ID = "subject_domain_id";
    public static final String SUBJECT_DOMAIN_NAME = "subject_domain_name";
   
    public static final String SUBJECT = "subject";
    public static final String SUBJECT_ID = "subject_id";
    public static final String SUBJECT_NAME = "subject_name";
    public static final String COLOR = "color";
    public static final String SORT_ORDER = "sort_order";
    public static final String HOUR_PER_DAY = "hour_per_day";
    public static final String HOUR_PER_WEEK = "hour_per_week";
    public static final String GROUP_TYPE = "group_type";
    public static final String GROUP_TYPE_ID = "group_type_id";
    public static final String DIFFICULTY = "difficulty";
    
    public static final String PROFILE_TYPE = "profile_type";
    public static final String PROFILE_TYPE_ID = "profile_type_id";
    public static final String PROFILE_TYPE_NAME = "profile_type_name";

    
    public static final String PROFILE = "profile";
    public static final String PROFILE_ID = "profile_id";
    public static final String PROFILE_NAME = "profile_name";
        
    public static final String PROFILE_DETAIL = "profile_detail";
    

    // skill,curriculum,curriculum_detail,depart,subject_group
    
    public static final String SKILL = "skill";
    public static final String SKILL_ID = "skill_id";
    public static final String SKILL_NAME = "skill_name";
    
    
    public static final String CURRICULUM = "curriculum";
    public static final String CURRICULUM_ID = "curriculum_id";
    public static final String CURRICULUM_NAME = "curriculum_name";
    
    
    public static final String CURRICULUM_DETAIL = "curriculum_detail";
    public static final String DEPART_COUNT = "depart_count";
    
    
    
    
    
//    public static final String CURRENT_VERSION = "2018-06-18";
    public static final String CURRENT_VERSION = "2019-06-07";
    public static final String[] OLD_VERSION = {"2017-08-29"/*,2018-12-06*/};
    

    public static final String MULTY = "multy";
    
    public static final int SPLIT_WEEK_ID = 0;
    public static final int FIRST_WEEK_ID = 1;
    public static final int SECOND_WEEK_ID = 2; 
    
    
    static final String WEEK_BEGIN = "week_begin";
    static final String WEEK_END = "week_end";
    
    
    public static final String IS_EMPTY = "is_empty";
    public static final String SUITABLE = "suitable";   
  
    public static final String GROUP_KEY = "group_key";
    public static final String COUNT_VALUE = "count_value";
    public static final String HOUR = "hour";
    public static final String CANCELED = "canceled";
    public static final String REPLACED = "replaced";
    public static final String HOLIDAY = "holiday";
        
    public static final String ERR_CODE = "err_code";
    public static final String ERR_DESCRIPTION = "err_description";

    /** Список выбранных уроков для модуля Replacement */
    public static final String LESSONS = "lessons";
    /**Один из возможных depart_id,teacher_id,room_id,skill_id,curriculum_id */
    public static final String ELEMENT = "element";
    public static final String ITEM_ID = "item_id";
    public static final String ITEM_TYPE = "item_type";
    public static final String SELECTED_ONLY ="selected_only";

    //     Аттрибуты

    public static final String VERSION = "version";
    public static final String SCHEDULE_TYTLE ="schedule_title";
    public static final String SCHEDULE_SPAN = "schedule_span";
    public static final String SCHEDULE_WEEK = "schedule_week";
    public static final String EDUCATIONAL_INSTITUTION = "educational_institution";
    public static final String COUNTRY = "country";
    public static final String ADDRESS = "address";
    public static final String REGION = "region";
    public static final String CREATE_BY = "create_by";
    public static final String SITE = "site";


    public static int SCHEDULE_STATE_NEW = 0;
    public static int SCHEDULE_STATE_WORK = 1;
    public static int SCHEDULE_STATE_ERROR = 2;
    public static int SCHEDULE_STATE_READY = 3;
    public static int SCHEDULE_STATE_PUBLISH = 4;

    public static final String PARAM_NAME = "param_name";
    public static final String PARAM_VALUE = "param_value";


    public static final Integer TEACHER_PROFILE_TYPE_ID = 1;
    public static final Integer ROOM_PROFILE_TYPE_ID = 2;

    public static final int DEPART_SHIFT_TYPE_ID = 1;
    public static final int TEACHER_SHIFT_TYPE_ID = 2;
    public static final int ROOM_SHIFT_TYPE_ID = 3;



    // common
    public static final String ROOM_ID = "room_id";
    public static final String SHIFT_HOUR = "shift_hour";


    public static final String SUBJECT_GROUP = "subject_group";
    // subject
    
    public static final int MAX_DIFFICULTY = 20;

    public static final String ATTR = "attr";
    
    public static final String SUBJECT_CONDITION = "subject_condition";
    public static final String CURRICULUM_CONDITION = "curriculum_condition";
    // condition
    public static final String CONDITION = "condition";
    public static final String CONDITION_ID = "condition_id";
    public static final String CONDITION_LABEL = "condition_label";
    public static final String CONDITION_NAME = "condition_name";
    public static final String CONDITION_VALUE = "condition_value";
    


    // curriculum_detail

    public static final String GROUP_SEQUENCE = "group_sequence";
    public static final String GROUP_SEQUENCE_ID = "group_sequence_id";
    public static final String IS_STREAM = "is_stream";
    public static final String LESSON_TYPE = "lesson_type";
    public static final String LESSON_TYPE_ID = "lesson_type_id";
    public static final String LESSON_TYPE_NAME = "lesson_type_name";

   // subject_group

    public static final String GROUP_LABEL = "group_label";
    public static final String DEPART_ID = "depart_id";
    public static final String GROUP_ID = "group_id";
    public static final String TEACHER = "teacher";
    public static final String ROOM = "room";
    public static final String WEEK = "week";
    public static final String WEEK_ID = "week_id";
    public static final String WEEK_NAME = "week_name";
    public static final String STREAM_NAME = "stream_name";
    /**Кол. групп в потоке*/
    public static final String GROUP_COUNT = "group_count";
//    public static final String GROUP_IN_STREAM = "group_in_stream";
    public static final String PLACED = "placed";
    public static final String UNPLACED = "unplaced";
//        public static final String PUPIL_COUNT = "pupil_count";

    // teacher 

    
    public static final String TEACHER_FIO = "teacher_fio";
    public static final String LAST_NAME = "last_name";
    public static final String FIRST_NAME = "first_name";
    public static final String PATRONYMIC = "patronymic";
    public static final String ROOM_NAME = "room_name";

    public static final String BUILDING = "building";
    public static final String BUILDING_NAME = "building_name";
    public static final String TEACHER_ID = "teacher_id";
    public static final String PHOTO = "photo";
    public static final String COMMENTS = "comments";
    public static final String TOTAL_HOUR = "total_hour";
    public static final String SHIFT_IS_EMPTY = "shift_is_empty";
    public static final String PROFILE_IS_EMPTY = "profile_is_empty";

    // depart

    public static final String DEPART_LABEL = "depart_label";
//    public static final String CURRICULUM_NAME = "curriculum_name";
    public static final String MAIN_BUILDING = "main_building";
    public static final String BOY_COUNT = "boy_count";
    public static final String GERL_COUNT = "gerl_count";
    public static final String SCHEDULE_STATE_NAME = "schedule_state_name";
    public static final String SCHEDULE_STATE_ID = "schedule_state_id";
    public static final String PUPIL_COUNT = "pupil_count";
    public static final String SHIFT_COUNT = "shift_count";
    public static final String CURRICULUM_COUNT = "curriculum_count";

    public static final String LESSON_TIME = "lesson_time";
    public static final String READY = "ready";
    public static final String LESSON_NO = "lesson_no";

    // errors

    public static final String CODE_ID = "code_id";

    // stream

    public static final String STREAM = "stream";
    public static final String STREAM_GROUP = "stream_group";
    public static final String STREAM_ID = "stream_id";

    // journal

    public static final String JOURNAL_ID = "journal_id";
    public static final String DETAIL_ID = "detail_id";
    public static final String CREATE_TIME = "create_time";
    public static final String DATE_BEGIN = "date_begin";
    public static final String DATE_END = "date_end";
    public static final String FIRST_LESSON = "first_lesson";
    public static final String LAST_LESSON = "last_lesson";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String FLAG = "flag";
    public static final String FLAG_NAME = "flag_name";
    public static final String PARENT_ID = "parent_id";
    public static final String HOLIDAY_ID = "holiday_id";
    public static final String HAS_REPLACEMENT ="has_replacement";
    public static final String CHILD_ID = "child_id";

    public static final String CAPACITY = "capacity";
    public static final String BUILDING_ID = "building_id";

    public static final String GROUP_SEQUENCE_NAME = "group_sequence_name";

    public static final String GROUP_TYPE_NAME = "group_type_name";

    public static final String DATE = "date";

    public static final String REPLACE_ID = "replace_id";

    public static final String INSTEAD = "instead";
    public static final String HOLIDAY_DATE ="holiday_date";
    public static final String INSTEAD_DATE ="instead_date";
    public static final String HOLIDAY_NAME ="holiday_name";
    
    public static final String DEPART = "depart";
    public static final String SCHEDULE = "schedule";
    public static final String SCHEDULE_STATE = "schedule_state";
        
}
