package edu.upb.chatupb.model;

import edu.upb.chatupb.server.comandos.Chat;

public interface MessageIterator {

    boolean hasNext();
    Chat next();
}
