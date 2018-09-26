package io.github.artenes.speedbro;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
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

    /**
     * Get a document for the give file
     *
     * @param testClass the instance of the test class
     * @param fileName the name of the file (just the name, not the path)
     * @return the html document
     * @throws IOException in case of a read error
     */
    public static Document getDocument(Object testClass, String fileName) throws IOException {
        File file = getFile(testClass, fileName);
        return Jsoup.parse(file, "UTF-8");
    }

}