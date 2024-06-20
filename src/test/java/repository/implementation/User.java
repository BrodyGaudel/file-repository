package repository.implementation;

import repository.Id;

public class User {
    @Id
    private String id;
    private String name;
    private Long mark;

    public User(String id, String name, Long mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    public User() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
