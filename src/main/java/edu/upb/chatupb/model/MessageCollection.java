package edu.upb.chatupb.model;
import edu.upb.chatupb.server.comandos.Chat;

import java.util.List;

public class MessageCollection implements MessageIterator {
    private List<Chat> messages;
    private int index;

    public MessageCollection(List<Chat> messages) {
        this.messages = messages;
        this.index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < messages.size();
    }

    @Override
    public Chat next() {
        if (hasNext()) {
            Chat chat = messages.get(index);
            index++;
            return chat;
        }
        return null;
    }
}
