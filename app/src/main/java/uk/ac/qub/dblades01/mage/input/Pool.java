package uk.ac.qub.dblades01.mage.input;

import java.util.ArrayList;
import java.util.List;

/* This generic collection facilitates instance reuse - objects created through the Pool object are
created normally as dictated by the PoolObjectFactory, then are freed from use to be added to an
internal list of unused objects. These free objects are then used if they are available, instead of
creating new objects. */
public class Pool<T> {
    /* Describes a T object creator, that will be used to bring new instances into the Pool. */
    public interface PoolObjectFactory<T> {
        T createObject();
    }

    private final int maxSize;
    private final PoolObjectFactory<T> objectFactory;
    private final List<T> freeObjects;

    /* Set the maximum number of free objects to keep stored, along with how new objects will be
    created. */
    public Pool(int maxSize, PoolObjectFactory<T> objectFactory) {
        this.maxSize = maxSize;
        this.objectFactory = objectFactory;
        freeObjects = new ArrayList<>(maxSize);
    }

    /* Create a new object if there are no free objects to reuse. */
    public T newObject() {
        if(freeObjects.isEmpty())
            return objectFactory.createObject();
        else
            return freeObjects.remove(freeObjects.size() - 1);
    }

    /* Free the passed in object from use, allowing it to be reused. */
    public void freeObject(T object) {
        if(freeObjects.size() < maxSize)
            freeObjects.add(object);
    }
}
