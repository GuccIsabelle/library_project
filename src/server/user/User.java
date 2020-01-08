/**
 * What can I said... It's the User Class. duh.
 * This Class have all the XML crap needed to be converted.
 *
 * @author Marius Vallas, Gabriel Arbane, Antoine Dedieu
 * @version 1.4
 */

package server.user;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

@XmlRootElement
public class User implements java.io.Serializable {
    private String ID;
    private String password;
    private String name;
    private int age;

    /**
     * Empty constructor because of the XML integration.
     */
    public User() {
    }

    /* Auto generated getter */
    public String getID() {
        return ID;
    }

    /* Auto generated setter */
    @XmlAttribute
    public void setID(String ID) {
        this.ID = ID;
    }

    /* Auto generated getter, modified to package-private */
    String getPassword() {
        return password;
    }

    /* Auto generated setter, modified to package-private */
    @XmlElement
    void setPassword(String password) {
        this.password = password;
    }

    /* Auto generated getter */
    public String getName() {
        return name;
    }

    /* Auto generated setter */
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    /* Auto generated getter */
    public int getAge() {
        return age;
    }

    /* Auto generated setter */
    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Auto generated toString method, but slightly modified.
     *
     * @return Nice looking String
     */
    @Override
    public String toString() {
        return "User n°" + getID() + " {\n" +
                "  Pass : ******\n" +
                "  Name : " + getName() + "\n" +
                "  Age  : " + getAge() + "\n}";
    }

    /**
     * Convert the book who calls it to an XML file.
     * Be careful of the path, you may need te modify it for your need.
     */
    public void toXML() {
        try {
            File file = new File("C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\user\\database\\" + this.getName().replaceAll("\\s+", "_") + ".xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
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
