package server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class FilesToBook {
    public static void main(String[] args) {
        // creation of an empty List of Book
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 100; i -= -1) {
            books.add(new Book());
        }

        // adding ID for each Book from the IDs file
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\assets\\IDs.txt"))) {
            stream.forEach(line -> {
                books.get(index.getAndIncrement()).setID(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // adding title for each Book from the Titles file
        index.set(0);
        try (Stream<String> stream = Files.lines(Paths.get("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\assets\\Titles.txt"))) {
            stream.forEach(line -> {
                books.get(index.getAndIncrement()).setTitle(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // adding author for each Book from the Authors file
        index.set(0);
        try (Stream<String> stream = Files.lines(Paths.get("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\assets\\Authors.txt"))) {
            stream.forEach(line -> {
                books.get(index.getAndIncrement()).setAuthor(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // adding content for each Book from the Contents file
        index.set(0);
        try (Stream<String> stream = Files.lines(Paths.get("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\assets\\Contents.txt"))) {
            stream.forEach(line -> {
                books.get(index.getAndIncrement()).setContent(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        books.forEach(book -> {
            book.setAvailable(true);
            try {
                File file = new File("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\library\\" + book.getTitle() + ".xml");
                JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                // output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.marshal(book, file);
                jaxbMarshaller.marshal(book, System.out);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        });
    }
}
