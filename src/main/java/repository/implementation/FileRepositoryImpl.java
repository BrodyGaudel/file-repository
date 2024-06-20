package repository.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import repository.FileRepository;
import repository.Id;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileRepositoryImpl<T, I> implements FileRepository<T, I> {

    private final File file;
    private final Class<T> type;
    private final ObjectMapper objectMapper;

    public FileRepositoryImpl(String filePath, Class<T> type) {
        this.file = new File(filePath);
        this.type = type;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public T save(T object) {
        List<T> objects = findAll();
        Optional<T> existingObject = findById(getIdValue(object));
        existingObject.ifPresent(objects::remove);
        objects.add(object);
        writeFile(objects);
        return object;
    }

    @Override
    public List<T> saveAll(List<T> objects) {
        for (T object : objects) {
            save(object);
        }
        return objects;
    }

    @Override
    public void deleteById(I id) {
        List<T> objects = findAll();
        objects.removeIf(obj -> id.equals(getIdValue(obj)));
        writeFile(objects);
    }

    @Override
    public void deleteAll(List<T> objects) {
        List<I> ids = new ArrayList<>();
        for (T object : objects) {
            ids.add(getIdValue(object));
        }
        List<T> allObjects = findAll();
        allObjects.removeIf(obj -> ids.contains(getIdValue(obj)));
        writeFile(allObjects);
    }

    @Override
    public void deleteAll() {
        writeFile(new ArrayList<>());
    }

    @Override
    public Optional<T> findById(I id) {
        return findAll()
                .stream()
                .filter(obj -> id.equals(getIdValue(obj)))
                .findFirst();
    }

    @Override
    public List<T> findAll() {
        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }
        try {
            CollectionType javaType = objectMapper.getTypeFactory()
                    .constructCollectionType(List.class, type);
            return objectMapper.readValue(file, javaType);
        } catch (IOException e) {
            throw new WriteFileException("Failed to read file", e);
        }
    }

    private void writeFile(List<T> objects) {
        try {
            objectMapper.writeValue(file, objects);
        } catch (IOException e) {
            throw new WriteFileException("Failed to write file", e);
        }
    }

    private I getIdValue(T object) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                try {
                    return (I) field.get(object);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to get id value", e);
                }
            }
        }
        throw new AnnotationException("No field annotated with @Id found");
    }

}
