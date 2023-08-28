package com.ijys.effectivejava.item18;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ForwardingSet<E> implements Set<E> {
    private final Set<E> e;

    public ForwardingSet(Set<E> e) {
        this.e = e;
    }

    public void clear() {
        e.clear();
    }

    public boolean contains(Object o) {
        return e.contains(o);
    }

    public boolean isEmpty() {
        return e.isEmpty();
    }

    public int size() {
        return e.size();
    }

    public Iterator<E> iterator() {
        return e.iterator();
    }

    public boolean add(E e) {
        return this.e.add(e);
    }

    public boolean remove(Object o) {
        return e.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return e.containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        return e.addAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return e.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return e.retainAll(c);
    }

    public Object[] toArray() {
        return e.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return e.toArray(a);
    }

    @Override
    public boolean equals(Object o) {
        return e.equals(o);
    }

    @Override
    public String toString() {
        return e.toString();
    }

    @Override
    public int hashCode() {
        return e.hashCode();
    }
}
