package com.olivierboucher.reseau2.Chat.Server;

/**
 * Created by olivier on 2015-10-06.
 */
public interface IClientDelegate {
    void newCommandRecievedFromClient(ServerClient serverClient, Command command);
}
