package server.user;

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

public class UserDB {
    private List<User> userList = new ArrayList<>();

    public UserDB(String path) {
        try {
            Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList()).forEach(p -> {
                File file = new File(String.valueOf(p));
                JAXBContext jaxbContext = null;
                try {
                    jaxbContext = JAXBContext.newInstance(User.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    userList.add((User) jaxbUnmarshaller.unmarshal(file));
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User returnIfExist(String ID, String password) {
        return userList.stream()
                .filter(user -> user.getID().equals(ID) && user.getPassword().equals(password))
                .findAny().orElse(null);
    }

    public User findUserFromID(String ID) {
        return userList.stream()
                .filter(user -> user.getID().equals(ID))
                .findAny().orElse(null);
    }

    /* Auto generated getter */
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Auto generated toString method, but modified.
     *
     * @return Nice looking String
     */
    @Override
    public String toString() {
        return userList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining("\n", "User list :\n\n", "\n" + userList.size() + " users total\n"));
    }
}
