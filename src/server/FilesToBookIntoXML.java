/**
 * This Class is not needed for the project,
 * I was just to lazy and use it to create all books quickly.
 *
 * @author Marius Vallas
 * @version 2.1
 */

package server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FilesToBookIntoXML {
    public static void main(String[] args) {
        // creating an empty List of Book
        List<Book> books = new ArrayList<>();
        bookInitialisation(books);

        // adding everything useful to all books
        addID(books, Paths.get("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\assets\\IDs.txt"));
        addTitle(books, Paths.get("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\assets\\Titles.txt"));
        addAuthor(books, Paths.get("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\assets\\Authors.txt"));
        addContent(books, Paths.get("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\assets\\Contents.txt"));

        // creating XML files
        books.forEach(Book::toXML);
    }

    /**
     * Initialize an array of empty Book and set them to available.
     *
     * @param books Empty ArrayList of Book
     */
    private static void bookInitialisation(List<Book> books) {
        for (int i = 0; i < 100; i -= -1) {
            Book b = new Book();
            b.setAvailable(true);
            b.setReserved(false);
            books.add(b);
        }
    }

    /**
     * Adding ID to all Books from the given file.
     *
     * @param books ArrayList of Book
     * @param path  Path to the IDs' file
     */
    private static void addID(List<Book> books, Path path) {
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            stream.forEach(line -> {
                books.get(index.getAndIncrement()).setID(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding Title to all Books from the given file.
     *
     * @param books ArrayList of Book
     * @param path  Path to the Titles' file
     */
    private static void addTitle(List<Book> books, Path path) {
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            stream.forEach(line -> {
                books.get(index.getAndIncrement()).setTitle(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding Author to all Books from the given file.
     *
     * @param books ArrayList of Book
     * @param path  Path to the Authors' file
     */
    private static void addAuthor(List<Book> books, Path path) {
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            stream.forEach(line -> {
                books.get(index.getAndIncrement()).setAuthor(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding Content to all Books from the given file.
     *
     * @param books ArrayList of Book
     * @param path  Path to the Contents' file
     */
    private static void addContent(List<Book> books, Path path) {
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            stream.forEach(line -> {
                books.get(index.getAndIncrement()).setContent(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
