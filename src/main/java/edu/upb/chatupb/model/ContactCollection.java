package edu.upb.chatupb.model;

import edu.upb.chatupb.server.Contact;

import java.util.ArrayList;

public class ContactCollection<T> implements ContactIter<Contact> {

    private ArrayList<Contact> contactList = new ArrayList<>();
    private int position = 0;

    public void addContact(Contact contact) {
        contactList.add(contact);
    }

    @Override
    public boolean hasNext() {
        return position < contactList.size();
    }

    @Override
    public Contact getNext() {
        if (!this.hasNext())
            return null;

        Contact contact = contactList.get(position);
        position++;
        return contact;
    }

    @Override
    public boolean isEmpty() {
        return contactList.isEmpty();
    }

    @Override
    public int size() {
        return contactList.size();
    }
}
