package etu1821.helper;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.lang.reflect.Field;

public class JsonHelper {

   private static <T> String toJson(T object) {
      T obj = object;
      JSONObject jsonObject = new JSONObject(obj);
      JSONObject retour = new JSONObject();
      List<Field> fields = getFields(obj);
      fields.forEach(field -> {
         Class<?> superClass = field.getType().getSuperclass();
         if (List.class.isAssignableFrom(superClass))
            retour.put(field.getName(), jsonObject.get(field.getName()));
         else
            retour.put(field.getName(), jsonObject.getString(field.getName()));
      });
      return "{" + retour.toString() + "}";
   }

   private static <T> String getJSONFromArray(Object value) {
      JSONArray jsonArray = new JSONArray(value);
      return jsonArray.toString();
   }

   public static <T> String transformMapToJson(Map<String, Object> maps) {
      String stringReturn = "{";
      int i = 0;
      for (Map.Entry<String, Object> entry : maps.entrySet()) {
         String key = entry.getKey();
         Object value = entry.getValue();
         stringReturn = stringReturn + "\"" + key + "\":";
         try {
            String val = "";
            if (value.getClass().isArray() || List.class.isAssignableFrom(value.getClass())) {
               val = getJSONFromArray(value);
            } else {
               val = toJson(value);
            }
            stringReturn = stringReturn + val;
         } catch (JSONException | NullPointerException e) {
            if (maps.get(key) instanceof Number) {
               stringReturn = stringReturn + maps.get(key);
            } else {
               if (maps.get(key) == null) {
                  stringReturn = stringReturn + null;
               } else {
                  stringReturn = stringReturn + "\"" + maps.get(key) + "\"";
               }
            }
         }
         if (i != maps.size() - 1) {
            stringReturn += ",";
         }
         i++;
      }
      return stringReturn + "}";
   }

   private static <T> List<Field> getFields(T object) {
      return Arrays.asList(object.getClass().getDeclaredFields());
   }
}
