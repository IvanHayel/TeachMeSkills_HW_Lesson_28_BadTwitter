package by.teachmeskills.sweater.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class Entity implements Serializable {
    protected static final long serialVersionUID = 1L;

    protected int id;

    @Override
    public String toString() {
        return "id=" + id;
    }
}