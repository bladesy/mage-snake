package uk.ac.qub.dblades01.mage;

import java.util.ArrayList;
import java.util.List;

public class Pool<T> {
    public interface PoolObjectFactory<T> {
        T createObject();
    }

    private final int maxSize;
    private final PoolObjectFactory<T> objectFactory;
    private final List<T> freeObjects;

    public Pool(int maxSize, PoolObjectFactory<T> objectFactory) {
        this.maxSize = maxSize;
        this.objectFactory = objectFactory;
        freeObjects = new ArrayList<>(maxSize);
    }

    public T newObject() {
        if(freeObjects.isEmpty())
            return objectFactory.createObject();
        else
            return freeObjects.remove(freeObjects.size() - 1);
    }

    public void freeObject(T object) {
        if(freeObjects.size() < maxSize)
            freeObjects.add(object);
    }
}
