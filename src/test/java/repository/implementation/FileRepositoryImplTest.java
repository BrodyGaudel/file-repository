package repository.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.FileRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileRepositoryImplTest {

    private FileRepository<User, String> repository;


    @BeforeEach
    void setUp() {
        repository = new FileRepositoryImpl<>(
                "C:/Users/princ/Documents/workspace/data.txt", User.class
        );
    }

    @Test
    void save() {
        User user = new User("1000","Name", 999L);
        User savedUser = repository.save(user);
        assertNotNull(savedUser);
    }

    @Test
    void saveAll() {
        User user1 = new User("1000","Name 1", 999L);
        User user2 = new User("2000","Name 2", 999L);
        User user3 = new User("3000","Name 3", 999L);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        List<User> savedUsers = repository.saveAll(users);
        assertNotNull(savedUsers);
        assertFalse(savedUsers.isEmpty());
    }

    @Test
    void deleteById() {
        String id = UUID.randomUUID().toString();
        User user = new User(id,"Name 1", 999L);
        repository.save(user);
        repository.deleteById(id);
        User deletedUser = repository.findById(id).orElse(null);
        assertNull(deletedUser);
    }

    @Test
    void deleteAll() {
        User user1 = new User("1000","Name 1", 999L);
        User user2 = new User("2000","Name 2", 999L);
        User user3 = new User("3000","Name 3", 999L);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        List<User> savedUsers = repository.saveAll(users);
        repository.deleteAll(savedUsers);
        List<User> foundUsers = repository.findAll();
        assertNotNull(foundUsers);
    }

    @Test
    void deleteAll_WithoutParameters() {
        User user1 = new User("1000","Name 1", 999L);
        User user2 = new User("2000","Name 2", 999L);
        User user3 = new User("3000","Name 3", 999L);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        repository.saveAll(users);
        repository.deleteAll();
        List<User> foundUsers = repository.findAll();
        assertNotNull(foundUsers);
        assertTrue(foundUsers.isEmpty());

    }

    @Test
    void findById() {
        String id = UUID.randomUUID().toString();
        User user = new User(id,"Name 1", 999L);
        repository.save(user);
        User foundUser = repository.findById(id).orElse(null);
        assertNotNull(foundUser);
        assertEquals(id, foundUser.getId());
    }

    @Test
    void findAll() {
        User user1 = new User("1000","Name 1", 999L);
        User user2 = new User("2000","Name 2", 999L);
        User user3 = new User("3000","Name 3", 999L);
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        repository.saveAll(users);
        List<User> foundUsers = repository.findAll();
        assertNotNull(foundUsers);
        assertFalse(foundUsers.isEmpty());
    }
}