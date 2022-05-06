package start;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionExample {

    public static void retrieveProperties(Object object) {

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value;
            try {
                value = field.get(object);
                System.out.println(field.getName() + "=" + value);

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public static ArrayList<Object> getValues(Object o){
        int i=0;
        ArrayList<Object> objects = new ArrayList<>();
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                objects.add(field.get(o));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objects;
    }

    public static List<String> getFields(Object o){
        ArrayList<String> fields = new ArrayList<>();
        Class<?> c = o.getClass();
        for(Field field:c.getDeclaredFields()){
            fields.add(field.getName());
        }
        return fields;
    }
}



