package fr.paug.androidmakers.util;

import java.util.List;
import java.util.Map;

/**
 * Created by stan on 18/03/2017.
 */

public class MapUtil {
    
    public static int getInt(Map map, String key, int defaultValue) {
        return getInt(map.get(key), defaultValue);
    }

    public static int getInt(Object object, int defaultValue) {
        if (object instanceof Integer) {
            return (Integer) object;
        } else if (object instanceof Long) {
            return ((Long) object).intValue();
        } else {
            return defaultValue;
        }
    }

    public static String getString(Map map, String key) {
        Object object = map.get(key);
        if (object instanceof String) {
            return (String) object;
        } else {
            return null;
        }
    }

    public static int[] getIntArray(Map map, String key) {
        Object object = map.get(key);
        if (object instanceof List) {
            List list = (List) object;
            int[] result = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                int value = getInt(list.get(i), -1);
                if (value < 0) {
                    return null;
                }
                result[i] = value;
            }
            return result;
        } else {
            return null;
        }
    }
}
