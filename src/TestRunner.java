import annotations.Test;
import interfaces.UnitTest;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


public final class TestRunner {

    // TODO: Delete this list!
//    private static final List<Class<?>> TESTS = List.of(CalculatorTest.class);

    public static void main(String[] args) throws Exception {
        // Done: Make sure the user has passed in two arguments: one for the test directory, and one
        //       with the name of the test class to run.

        if(args.length != 2){
            System.out.println("Usage: TestRunner [/test directory] [test class]");
        }

        List<String> passed = new ArrayList<>();
        List<String> failed = new ArrayList<>();

        // Done: Rewrite this for-loop. Instead of using the TESTS list (which you should have deleted),
        //       locate the test using the command-line arguments and a custom ClassLoader. Then call
        //       Class.forName(className, true, loader) using the custom ClassLoader for the third
        //       parameter.
        //
        //       The code to record passed/failed tests should pretty much stay the same.

        Class<?> klass = getTestClass(args[0], args[1]);

        for (Method method : klass.getDeclaredMethods()) {

            if(method.getAnnotation(Test.class) == null){
                continue;
            }

            try {
                UnitTest test = (UnitTest) klass.getConstructor().newInstance();
                test.beforeEachTest();
                method.invoke(test);
                test.afterEachTest();
                passed.add(getTestName(klass, method));
            } catch (Throwable throwable) {
                failed.add(getTestName(klass, method));
            }
        }

        System.out.println("Passed tests: " + passed);
        System.out.println("FAILED tests: " + failed);
    }

    private static String getTestName(Class<?> klass, Method method) {
        return klass.getName() + "#" + method.getName();
    }

    private static Class<?> getTestClass(String folder, String className) throws Exception{
        URL testDir = Path.of(folder).toUri().toURL();
        URLClassLoader loader =new URLClassLoader(new URL[]{testDir});
        Class<?> klass = Class.forName(className, true, loader);
        System.out.println(klass.getName());
        if(!UnitTest.class.isAssignableFrom(klass)){
            throw new RuntimeException("Class " + klass + " does not implement UnitTest");
        }
        return klass;
    }
}
