package edu.upb.chatupb.server;

import lombok.*;

import java.io.Serializable;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contact implements Serializable {
    private String code;
    private String name;
    private String ip;
    private boolean stateConnect = false;
    private SocketClient socketClient;

    public String toString() {
        return name;
    }
}
