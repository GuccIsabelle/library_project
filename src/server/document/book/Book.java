/**
 * This Class extends the Item Class for obvious reasons.
 * It to have all the XML crap needed to be converted.
 *
 * @author Marius Vallas, Gabriel Arbane, Antoine Dedieu
 * @version 1.7
 */

package server.document.book;

import server.document.Item;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

@XmlRootElement
public class Book extends Item {
    private String title;
    private String author;
    private String content;

    /**
     * Empty constructor because of the XML integration.
     */
    public Book() {
        super();
    }

    /* Auto generated getter */
    public String getTitle() {
        return title;
    }

    /* Auto generated setter */
    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    /* Auto generated getter */
    public String getContent() {
        return content;
    }

    /* Auto generated setter */
    @XmlElement
    public void setContent(String content) {
        this.content = content;
    }

    /* Auto generated getter */
    public String getAuthor() {
        return author;
    }

    /* Auto generated setter */
    @XmlElement
    public void setAuthor(String autor) {
        this.author = autor;
    }

    /**
     * Auto generated toString method, but slightly modified.
     *
     * @return Nice looking String
     */
    @Override
    public String toString() {
        return "Book n°" + getID() + " {\n" +
                "  title  : " + title + "\n" +
                "  author : " + author + "\n" +
                ((isAvailable()) ? "} Available\n" : "} Not Available\n");
    }

    /**
     * Convert the book who calls it to an XML file.
     * Be careful of the path, you may need te modify it for your need.
     */
    public void toXML() {
        try {
            File file = new File("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\document\\book\\library\\" + this.getTitle() + ".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Book.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(this, file);
            jaxbMarshaller.marshal(this, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
