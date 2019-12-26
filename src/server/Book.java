package server;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book extends Item {
    private String title;
    private String author;
    private String content;

    public Book() {
        super();
    }

    public String getTitle() {
        return title;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    @XmlElement
    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    @XmlElement
    public void setAuthor(String autor) {
        this.author = autor;
    }

    @Override
    public String toString() {
        return "Book n°" + getID() + " {\n" +
                "  title  : " + title + "\n" +
                "  author : " + author + "\n" +
                ((isAvailable()) ? "} Available\n" : "} Not Available\n");
    }
}
