package ru.viljinsky.project2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author viljinsky
 */
public class Values extends HashMap<String, Object> {
    
    public Values() {
    }

    public Values(String key, Object value) {
        put(key, value);
    }

    public Values(String[] key) {
        for (String k : key) {
            put(k, null);
        }
    }

    public void print() {
        for (String key : keySet()) {
            Object value = get(key);
            System.out.println(key + " = " + String.valueOf(value)+(value==null?"":" ("+value.getClass().getSimpleName()+")"));
        }
    }

    public Values values(String[] key) {
        Values result = new Values();
        for (String k : keySet()) {
            result.put(k, containsKey(k) ? get(k) : null);
        }
        return result;
    }
    
    public Values getValues(String... keys){
        Values values = new Values();
        for(String key: keys){
            values.put(key, containsKey(key)?get(key):null);
        }
        return values;
    }

    public boolean isNull(String key) {
        return containsKey(key) && (get(key)!=null);
    }
    
    public Object[] toArray(String... keys){
        List list = new ArrayList();
        for(String key:keys){
            list.add(get(key));
        }
        return list.toArray();
    }
    
}
