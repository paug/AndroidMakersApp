package fr.paug.androidmakers.util;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.paug.androidmakers.model.Partners;
import fr.paug.androidmakers.model.Ribbon;
import fr.paug.androidmakers.model.SocialNetworkHandle;

public final class MapUtil {

    private MapUtil() {
        // no instance
    }

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

    public static List<SocialNetworkHandle> getSocialItems(Map map, String key) {
        final Object object = map.get(key);
        if (object instanceof List) {
            List<SocialNetworkHandle> socialNetworkHandles = null;
            for (Object socialItem : ((List) object)) {
                if (socialItem instanceof Map) {
                    final String name = getString((Map) socialItem, "name");
                    final String link = getString((Map) socialItem, "link");
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(link)) {
                        if (socialNetworkHandles == null) {
                            socialNetworkHandles = new ArrayList<>();
                        }
                        socialNetworkHandles.add(new SocialNetworkHandle(name, link));
                    }
                }
            }
            return socialNetworkHandles;
        } else {
            return null;
        }
    }

    public static List<Ribbon> getRibbonItems(Map map, String key) {
        final Object object = map.get(key);
        if (object instanceof List) {
            List<Ribbon> ribbonList = null;
            for (Object socialItem : ((List) object)) {
                if (socialItem instanceof Map) {
                    final String name = getString((Map) socialItem, "abbr");
                    final String title = getString((Map) socialItem, "title");
                    final String link = getString((Map) socialItem, "url");
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(link)) {
                        if (ribbonList == null) {
                            ribbonList = new ArrayList<>();
                        }
                        ribbonList.add(new Ribbon(name, title, link));
                    }
                }
            }
            return ribbonList;
        } else {
            return null;
        }
    }

    public static List<Partners> getPartnerList(Map map, String key) {
        final Object object = map.get(key);
        if (object instanceof List) {
            List<Partners> partnersList = null;
            for (Object partner : ((List) object)) {
                if (partner instanceof Map) {
                    final String name = getString((Map) partner, "name");
                    final String link = getString((Map) partner, "link");
                    final String imageUrl = getString((Map) partner, "imageUrl");
                    final String description = getString((Map) partner, "description");
                    if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(imageUrl)) {
                        if (partnersList == null) {
                            partnersList = new ArrayList<>();
                        }
                        partnersList.add(new Partners(name, imageUrl, link, description));
                    }
                }
            }
            return partnersList;
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