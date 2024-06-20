Voici la version anglaise mise à jour du fichier `README.md` avec les informations supplémentaires sur l'annotation `@Id` et l'exemple d'utilisation :

```markdown
# File Repository Library

`file-repository` is a Java library for performing CRUD (Create, Read, Update, Delete) operations on files. This library provides a generic interface for managing entities in files.

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [FileRepository Interface](#filerepository-interface)
- [Example Implementation](#example-implementation)
- [Contributing](#contributing)
- [License](#license)

## Installation

To use this library in your project, add the following dependency to your `pom.xml` if you are using Maven:

```xml
<dependency>
    <groupId>org.mounanga</groupId>
    <artifactId>file-repository</artifactId>
    <version>1.0</version>
</dependency>
```

## Usage

### FileRepository Interface

The `FileRepository` interface defines the following methods:

```java
public interface FileRepository<T, I> {

    T save(T object);

    List<T> saveAll(List<T> objects);

    void deleteById(I id);

    void deleteAll(List<T> objects);

    void deleteAll();

    Optional<T> findById(I id);

    List<T> findAll();
}
```

#### Methods

- `save(T object)`: Saves a given entity.
- `saveAll(List<T> objects)`: Saves all given entities.
- `deleteById(I id)`: Deletes the entity with the given identifier.
- `deleteAll(List<T> objects)`: Deletes all given entities.
- `deleteAll()`: Deletes all entities.
- `findById(I id)`: Retrieves an entity by its identifier.
- `findAll()`: Retrieves all entities.

### `@Id` Annotation

For this library to work correctly, each entity must have an identifier annotated with `@Id` from the `repository` package. For example:

```java
import repository.Id;

public class User {
    @Id
    private String id;
    private String name;
    
    // getters and setters
}
```

### Example Usage

Here is an example of how to use the library with a `User` entity:

```java
FileRepository<User, String> fileRepository = new FileRepositoryImpl<>("C:/Users/user.txt", User.class);

// Save a user
User user = new User();
user.setId("1");
user.setName("John Doe");
fileRepository.save(user);

// Find a user by ID
Optional<User> foundUser = fileRepository.findById("1");

// Delete a user by ID
fileRepository.deleteById("1");

// Retrieve all users
List<User> users = fileRepository.findAll();
```

## Example Implementation

Here is an example implementation of the `FileRepository` interface for a `Person` entity:

```java
import repository.FileRepository;
import repository.Id;
import repository.implementation.FileRepositoryImpl;

import java.util.UUID;

public class Person {
    @Id
    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    // getters and setters
}

public class PersonFileService {

    private final FileRepository<Person, String> fileRepository = new FileRepositoryImpl<>(
            "C:/Users/brody/Desktop/person.txt", Person.class
    );


    @Override

    public Person save(Person person) {
        person.setId(UUID.randomUUID().toString());
        return fileRepository.save(person);
    }

    @Override
    public List<Person> saveAll(List<Person> persons) {
        for(Person p: persons){
            p.setId(UUID.randomUUID().toString());
        }
        return fileRepository.saveAll(persons);
    }

    @Override
    public void deleteById(String id) {
        fileRepository.deleteById(id);
    }

    @Override
    public void deleteAll(List<Person> persons) {
        fileRepository.deleteAll(persons);
    }

    @Override
    public void deleteAll() {
        fileRepository.deleteAll();
    }

    @Override
    public Optional<Person> findById(String id) {
        return fileRepository.findById(id).orElse(null);
    }

    @Override
    public List<Person> findAll() {
        return fileRepository.findAll();
    }
}
```

## Contributing

Contributions are welcome! Please submit pull requests and report issues on the [GitHub repository](https://github.com/BrodyGaudel/file-repository).

## License

`file-repository` is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
```