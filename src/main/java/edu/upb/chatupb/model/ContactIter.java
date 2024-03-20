package edu.upb.chatupb.model;

import edu.upb.chatupb.server.Contact;

public interface ContactIter<T> {
    public boolean hasNext();
    public Contact getNext();

    public boolean isEmpty();

    public int size();
}
