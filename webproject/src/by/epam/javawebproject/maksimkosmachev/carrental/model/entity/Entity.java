package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

public abstract class Entity {
    private int id;

    public Entity(int id) {
        this.id = id;
    }

    public Entity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
