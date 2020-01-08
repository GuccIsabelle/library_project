/**
 * This Class is not needed for the project,
 * I was just to lazy and use it to create all users quickly
 * and because I already do it for the books.
 *
 * @author Marius Vallas
 * @version 2.1
 */

package server.user;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FilesToUserIntoXML {
    public static void main(String[] args) {
        // creating an empty List of User
        List<User> users = new ArrayList<>();
        userInitialisation(users);

        // adding everything useful to all users
        String assetPath = "C:\\Users\\Marius\\Documents\\Code\\JAVA\\library_project\\src\\server\\user\\assets\\";
        addID(users, Paths.get(assetPath + "IDs.txt"));
        addPassword(users, Paths.get(assetPath + "Passwords.txt"));
        addName(users, Paths.get(assetPath + "Names.txt"));
        addAge(users, Paths.get(assetPath + "Ages.txt"));

        // creating all XML files
        users.forEach(User::toXML);
    }

    /**
     * Initialize an array of empty User.
     *
     * @param users Empty ArrayList of User
     */
    private static void userInitialisation(List<User> users) {
        for (int i = 0; i < 10; i -= -1) {
            users.add(new User());
        }
    }

    /**
     * Adding ID to all Users from the given file.
     *
     * @param users ArrayList of User
     * @param path  Path to the IDs' file
     */
    private static void addID(List<User> users, Path path) {
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            stream.forEach(line -> {
                users.get(index.getAndIncrement()).setID(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding Password to all Users from the given file.
     *
     * @param users ArrayList of User
     * @param path  Path to the Passwords' file
     */
    private static void addPassword(List<User> users, Path path) {
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            stream.forEach(line -> {
                users.get(index.getAndIncrement()).setPassword(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding Name to all Users from the given file.
     *
     * @param users ArrayList of User
     * @param path  Path to the Names' file
     */
    private static void addName(List<User> users, Path path) {
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            stream.forEach(line -> {
                users.get(index.getAndIncrement()).setName(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adding Age to all Users from the given file.
     *
     * @param users ArrayList of User
     * @param path  Path to the Ages' file
     */
    private static void addAge(List<User> users, Path path) {
        AtomicInteger index = new AtomicInteger(0);
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(path)))) {
            stream.forEach(line -> {
                users.get(index.getAndIncrement()).setAge(Integer.parseInt(line));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
