package club.wadreamer.cloudlearning.util;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName JsonUtils
 * @Description TODO
 * @Author bear
 * @Date 2020/3/5 22:58
 * @Version 1.0
 **/
public class JsonUtils {
    private static Gson gson = null;
    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private JsonUtils() {
    }

    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    public static <T> T json_to_object(String json, Class<T> t) {

        try {
            json = URLDecoder.decode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        T fromJson = gson.fromJson(json, t);

        return fromJson;
    }

    public static <T> List<T> json_to_list(String json, Class<T> t) {
        String decode = "";

        if (StringUtils.isBlank(json)) {
            return null;
        } else {
            try {
                decode = URLDecoder.decode(json, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            JSONArray fromObject2 = JSONArray.fromObject(decode);

            List<T> list_array = (List<T>) JSONArray.toCollection(fromObject2, t);

            return list_array;
        }

    }

    public static <T> String list_to_json(List<T> list) {

        Gson gson = new Gson();
        String json = gson.toJson(list);

        try {
            json = URLEncoder.encode(json, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return json;

    }

}
