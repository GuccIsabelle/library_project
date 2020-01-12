# Library Project

A Java project using Sockets, Multi-threading, Object-XML conversion and a lot more.

The project is overall well documented, so I'll not explain the code here, if you want to know how a specific part works, just go to the file. Each file has a little tree of method at the beginning.

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

The `assets` folder contain a bunch of `.txt` files used to form the document (in this case, all of the books). Every file contain one type of Class field, like `ID` or `author` , and then the `FilesToBookIntoXML` Class converts everything into `.xml` and store them in the `library` folder.

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

# Authors

* **Marius Vallas** - *Git management, all the XML conversion crap, `Sockets` programming and overall refactoring / commenting code*
* **Gabriel Arbane** - *Multi threading and both `Client` and `Server` programming*
* **Antoine Dedieu** - *Multi threading and `Server` programming and `user` crap*

## License

This project is licensed under the MIT License - see the [License](LICENSE) file for details

## Acknowledgments

Bonjour M. Brette, vous allez bien mdr ?

