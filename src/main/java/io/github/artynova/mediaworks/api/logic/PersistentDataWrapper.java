package io.github.artynova.mediaworks.api.logic;

public interface PersistentDataWrapper<T extends PersistentDataContainer> {
    T unwrap();
}
