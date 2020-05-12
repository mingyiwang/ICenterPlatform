package com.icenter.core.client.primitive;

import com.icenter.core.client.lambda.Action;
import com.icenter.core.client.lambda.IntAction;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public final class CollectionStream<T>{

    private Collection<T> elements;

    private CollectionStream(Collection<T> elements){
        this.elements = elements;
    }

    public static <T> CollectionStream<T> of(Collection<T> collection){
        return new CollectionStream<>(collection);
    }

    public CollectionStream<T> forEach(Action<T> action){
        Objects.requireNonNull(action);

        Iterator<T> iter = elements.iterator();
        while(iter.hasNext()){
              action.run(iter.next());
        }
        return this;
    }

    public CollectionStream<T> forEach(IntAction<T> action){
        Objects.requireNonNull(action);

        Iterator<T> iter = elements.iterator();
        int index = 0;
        while(iter.hasNext()){
            int tmpIndex = index; // in case changes happens in consumer.
            action.run(tmpIndex, iter.next());
            index++;
        }
        return this;
    }

}
