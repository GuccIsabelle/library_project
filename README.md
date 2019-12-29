# Library Project

A Java project using Sockets, Multi-threading and Object-XML conversion.

## Structure

* [Assets](src/server/document/assets) - Files used to create all books.
* [Client](src/client) - Everything in relation with the client side is here.
* [server.document.library](src/server/document/library) - This is where books end up after being converted to XML files.
* [server](src/server) - Server folder. *duh*

### Assets

There is a .txt file for each book's characteristics :
* Authors.txt
* Contents.txt
* IDs.txt
* Titles.txt

Every file contains 100 times what the title says (you get what I mean).

### Client

Not that much here, only what the client app need so just one Class.

### Library

All XML files are here, you can technically add one yourself if you get the structure right.

### Server

All the server's needs are here and more. Classes used to create XML files and converting back to Objects are here too.
Furthermore, the Item Class is here because it seems logical to store items more on the server side rather than on the client side (like products stored in a store).

## Authors

* **Marius Vallas** - *Git management and all the XML conversion crap, Sockets and code refactoring and commenting*
* **Gabriel Arbane** - *Multi threading and Client side*
* **Antoine Dedieu** - *Multi threading and Server side*

## License

This project is licensed under the MIT License - see the [License](LICENSE) file for details

## Acknowledgments

Bonjour M. Brette, vous allez bien mdr ?
