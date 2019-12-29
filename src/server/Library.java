/**
 * This Class is used for converting
 *
 * @author Marius Vallas, Gabriel Arbane, Antoine Dedieu
 * @version 2.4
 */

package server;

import server.document.Book;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> catalog = new ArrayList<>();

    /**
     * Constructor for the books catalog.
     * Need a folder with only correctly formed XML files.
     *
     * @param path Path to the XML files' folder
     */
    public Library(String path) {
        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList()).forEach(p -> {
                File file = new File(String.valueOf(p));
                JAXBContext jaxbContext = null;
                try {
                    jaxbContext = JAXBContext.newInstance(Book.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    catalog.add((Book) jaxbUnmarshaller.unmarshal(file));
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Auto generated getter */
    public List<Book> getCatalog() {
        return catalog;
    }

    /**
     * Auto generated toString method, but slightly modified.
     *
     * @return Nice looking String
     */
    @Override
    public String toString() {
        return catalog.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n", "Library catalog :\n\n", "\n" + catalog.size() + " books total\n"));
    }
}
