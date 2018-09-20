package io.github.artenes.speedbro;

import java.io.File;
import java.net.URL;

/**
 * Utility methods for the test classes
 */
public class TestUtils {

    /**
     * Get a file from the resources directory
     *
     * @param testClass the instance of the test class
     * @param fileName  the name of the file (just the name, not the path)
     * @return the file
     */
    public static File getFile(Object testClass, String fileName) {
        ClassLoader classLoader = testClass.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return new File(resource.getPath());
    }

}