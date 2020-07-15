package exercise7;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;

public class mainClass {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        start(TestClass.class);
    }

    public static void start(Class<TestClass> test) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Method[] methods = test.getDeclaredMethods();
        Object t = test.newInstance();
        int checkBS = 0;
        int checkAS = 0;
        ArrayList<Method> tests = new ArrayList<>();
        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) checkBS++;
            else if (m.isAnnotationPresent(AfterSuite.class)) checkAS++;
            else if (m.isAnnotationPresent(Test.class)) tests.add(m);
        }
        if (checkBS > 1 || checkAS > 1) {
            throw new RuntimeException("ERROR: Before- or AfterSuite method appends more then once");
        }

        Comparator<Method> methodComparator = Comparator.comparing(obj -> obj.getAnnotation(Test.class).priority());
        tests.sort(methodComparator);

        for (Method m : methods) {
            if (m.isAnnotationPresent(BeforeSuite.class)) tests.add(0, m);
            else if (m.isAnnotationPresent(AfterSuite.class)) tests.add(m);
        }

        for (Method m : tests) m.invoke(t);
    }

}
