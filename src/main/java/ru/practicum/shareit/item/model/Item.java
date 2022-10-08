package ru.practicum.shareit.item.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;

/**
 * TODO Sprint add-controllers.
 */

@Entity
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "available")
    private Boolean available;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (getId() != null ? !getId().equals(item.getId()) : item.getId() != null) return false;
        if (getName() != null ? !getName().equals(item.getName()) : item.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(item.getDescription()) : item.getDescription() != null)
            return false;
        if (getAvailable() != null ? !getAvailable().equals(item.getAvailable()) : item.getAvailable() != null)
            return false;
        return getOwner() != null ? getOwner().equals(item.getOwner()) : item.getOwner() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getAvailable() != null ? getAvailable().hashCode() : 0);
        result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", available=" + available +
                ", owner=" + owner +
                '}';
    }
}
