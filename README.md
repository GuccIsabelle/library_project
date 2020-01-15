# Library Project

A Java project using Sockets, Multi-threading, Object-XML conversion and a lot more.

The project is overall well documented, so I'll not explain the code here, if you want to know how a specific part works, just go to the file.

*I'll show a lil' bit of code when appropriate tho.*

## Project's structure

``` 
src
├───client
└───server
    ├───document
    │   └───book
    │       ├───assets
    │       └───library
    └───user
        ├───assets
        └───database
```

* [Client](src/client) - Contain `Client.java` , that's it... 
* [Server](src/server) - Server folder.*duh*. Contain everything the server need to run correctly.
    - [document](src/server/document) - Store the outline of the document and every subtype of document, like `Book` , and can easily be extended.
    - [user](src/server/user) - Store the `User` Class and everything it need.

## Document

### Files

``` 
document
│   iDocument.java
│   Item.java
└───book
    │   Book.java
    │   FilesToBookIntoXML.java
    │   Library.java
    │
    ├───assets
    │   │   .txt files
    │   ┆   ...
    │
    └───library
        │   .xml files
        ┆   ...
```

To form a new type of `Document` you have to extends `Item` (an abstract Class), who itself implements the `iDocument` interface. Then place it into a sub-folder like the `Book` Class.

#### `assets` and `library` sub-folders

The `assets` folders contain a bunch of `.txt` files used to form the document (in this case, all of the books). Every file contain one type of Class field, like `ID` or `author` , and then the `FilesToBookIntoXML` Class converts everything into `.xml` and store them in the `library` folder.

## User

### Files

``` 
user
│   FilesToUserIntoXML.java
│   User.java
│   UserDB.java
│
├───assets
│   │   .txt files
│   ┆   ...
│
└───database
    │   .xml files
    ┆   ...
```

The `user` Class is very similar to a `document` , it doesn't implements nor extends any Class but still have an `assets` and `library` -like folder ( `database` ).

## How it works

We will cover the `client` side first because it's smaller and it simplify the `server` side explanation.

### Client side

When executing the `User` Class, you first have to enter your user's credentials like so :

``` 
Please, enter your credentials :
"your_ID" "your_password"
```

If the authentication is successful, display user's data : (it's more for the programmer's convenience but it's good to have)

``` 
Authentication successful.
User n°0259558340 {
  Pass  : **********
  Name  : Jean-François Brette
  eMail : jean-francois.brette@parisdescartes.fr
  Age   : 47 (I guess... ¯\_(ツ)_/¯)
}
```

If not, then print error message and exit.

### Server side

The server work is divided between five threads, each thread then calls a sub-thread to do the work and die.

The `Server.java` file is structured like so :

``` 
main
│   creating the library and user database
│
├───booking thread
│   │   check for connection
│   └───sub-thread
│       │   reserve the book, then die
│
├───borrowing thread
│   │   check for connection
│   └───sub-thread
│       │   borrow the book, then die
│
├───returning thread
│   │   check for connection
│   └───sub-thread
│       │   return the book, then die
│
├───authentication thread
│   │   check for connection
│   └───sub-thread
│       │   check if user exists, then die
│
└───catalogue thread
    │   check for connection
    └───sub-thread
        │   send the catalogue, then die
```

#### Contractual threads

All the threads are very similar, but the three "book's" ones are almost identical.
They check for a connection, create the sub-thread who then ask for the `user` 's and `book` 's ID. With that, check for the `book` in the `library` and then call the concerned method with the correct `user` as parameter (the `UserDB` Class has a method to find it).

``` java
// find the book
Book b = Objects.requireNonNull(library.getCatalog().stream()
    .filter(book -> book.getID().equals(bookID))
    .findAny().orElse(null));
// execute the method 
b.method(userDB.findUserFromID(userID));
```

The `.orElse(null)` is thwart by the `Object.requireNonNull` at the beginning.

#### Authentication thread

The authentication thread check just if the user exist, if yes : send it to the `client` , else : send a `null` that the `Client` Class can understand as an error.

#### Catalogue thread

This thread check just for a connection, then the sub-thread can send the catalogue to the `client` . It just call the `toString()` method of the `library` and add a timestamp.

The `.toString()` method of the `library` then call the same method for each `book` , their `.toString()` automatically tells if the `book` is either available, reserved or unavailable like this :

``` 
Book n°6267855912 {
  title  : Children Without Desire
  author : January Wick
} Available
```

# How we've handled things

## Multi-threading and data access

The number one problem with Threads is always data accesses. How can we reduce the risks of simultaneous writing or avoid it completely ?

### What we've done

To handle multiple accesses to data in the same time, a lot of solutions exist but the one we've choose is to write `almost-pure methods` . They are *almost*-pure because they lack pure-function's second properties, which is to have no side-effects on other part of the data. We want to modify the data so of course we will break that rule.

### How we did it

The logic behind our code is as follows.

This is what happen :

``` java
// at time t
Thread threadA
└───want to modify Object objX
    │   calls the objX.method

// at the same time t
Thread threadB
└───want to modify Object objX
    │   calls the objX.method
```

and what happen with pure-methods, who are directly codded into the targeted Object, is this :

1. 2 Threads ask to access the same method
2. The JVM "choose" who's first
3. The first Thread execute the method, the Object change is own data
4. After that the Object will be executed by the second Thread with the new data

In our case that's something like this :

1. Two users want to take a book at the same time
2. They draw straws to see who's first
3. The "winner" take the book successfully
4. The "looser" try to take it but he's told the book isn't available

## Timers and time-based tasks

The booking task imply to watch over those who reserve their Items. What if someone books an Item and never borrows it ? This is our task to prevent this kind of behavior.

### What we've done

For this task, we ended up using the Java Classes `Timer` and `TimerTask` . We create a `Timer` at the server's launch and then we can add it as much `TimerTask` as we need.

### How we did it

First a `Timer` is instanced at the server's start like said before.

``` java
Timer timer = new Timer();
```

Then `TimerTask` are added when needed, like so :

``` java
/* after the booking occurs */

timer.schedule(() -> {
    if (b.isAvailable()) { // if the book isn't borrowed by the user who reserved
        b.reset(); // then make the book available again
        System.out.println("book n°" + bookID + " has been reset.");
    }
     cancel(); // cancel this Task after the stuff is done

}, 120000); // setting the delay to 2 minutes

/**
 * Note that this is a lambda expression not supported in Java 8 !
 * IntelliJ support it only for visual purpose, you can extend it 
 * to see the full code of the TimerTask's instantiation.
 */
```

# BretteSoft© certifications

Please note that we didn't implement those certification, yet. We will only talk about how they can be implemented in our project, with bits of code.

## "Géronimo"

### Description

Some subscribers return books late (sometimes very late), others degrade the books they borrow. A subscriber exceeding a delay of more than 2 weeks and or who deteriorate the book will be prohibited from borrowing for 1 month.

### Solution

First, we can add a field in the `User` Class to know if the user is prohibited.
After that, we still have to check if the user is late.

For that we can use the `LocalDate` and `Period` Classes like so :

``` java
// setting the deadline to two weeks ahead
LocalDate deadline = LocalDate.now().plusWeeks(2);

// checking if the deadline is passed
if (LocalDate.new().isAfter(deadline))
    /* ban the user */
else
    /* tels him to have a good day */
```

Same goes for a ban user :

``` java
// setting the end of the banning to one month ahead
LocalDate endOfBan = LocalDate.now().plusMonths(1);

// checking if the ban date is passed
if (LocalDate.new().isAfter(endOfBan))
    /* unban the user */
else
    /* tels him he's still ban */
```

## "Cochise"

### Description

We add DVDs to the documents of this library (which becomes a media library). Some DVDs are reserved for people over 12 or 16 years old.

### Solution

Almost implemented without knowing, there is a `age` field in the `User` Class.

You just have to override the `booking` and `borrowing` methods in the `DVD` Class, who obviously extends the `Item` Class, like that :

``` java
@Override
public void method(User user) throws BookingException {
    if (user.getAge() >= this.age)
        /* same as before */
    else
        /* tells the user to come back older */
}
```

Of course we need to add the DVD's library at the start of the `server` , exactly like the books.

## "Sitting bull"

### Description

When making a reservation, if the book is not available offer to place an email alert notifying the user when the book has been returned.

### Solution

We can add another field in the `Item` Class, like `nextUser` for example. And then we add this code in the `returning` method :

``` java
@Override
public void returning(User user) throws ReturnException {
    /* same as before */
    notify(this.nextUser);
}
```

Another solution is to modify the field `user` to a List of `user` . Doing that enable the possibility to queue users. Here's the code of the `returning` method :

``` java
@Override
public void returning(User user) throws ReturnException {
    /* same as before */
    this.user.remove(0);
    notify(this.user(0));
}
```

And we need to modify the `booking` method like so :

``` java
@Override
public void booking(User user) {
    assert user != null : "User can't be null."; // panic if user not in database
    this.user.add(user);
}
```

... and in the `borrowing` method, modify the 

``` java
if (this.reserved)
```

to 

``` java
if (this.user.size() == 0)
```

You can see here that we no longer need the `reserved` field in the `Item` Class because now multiple peoples can reserve an item. Instead if we need, we can check if the List's length.

# Authors

* **Marius Vallas** - *Git management, all the XML conversion crap, `Sockets` programming and overall refactoring / commenting code*
* **Gabriel Arbane** - *Multi threading and both `Client` and `Server` programming*
* **Antoine Dedieu** - *Multi threading and `Server` programming and `user` crap*

## License

This project is licensed under the MIT License - see the [License](LICENSE) file for details.

## Acknowledgments

Bonjour M. Brette, vous allez bien mdr ?

