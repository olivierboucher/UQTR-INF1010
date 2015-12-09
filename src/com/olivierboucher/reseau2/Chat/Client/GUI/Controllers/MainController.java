package com.olivierboucher.reseau2.Chat.Client.GUI.Controllers;

import com.olivierboucher.reseau2.Chat.Client.ChatClient;
import com.olivierboucher.reseau2.Chat.Client.GUI.Views.NicknameSelectDialog;
import com.olivierboucher.reseau2.Chat.Client.IClientMessageHandler;
import com.olivierboucher.reseau2.Chat.ClientMain;
import com.olivierboucher.reseau2.Chat.Common.Command;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;

public class MainController implements IClientMessageHandler {
    @FXML
    private ListView<String> chatListView;
    @FXML
    private TextArea input;
    @FXML
    private Button sendButton;

    private ObservableList<String> chatHistory = FXCollections.observableArrayList();
    private ChatClient chatClient;
    private ClientMain mainApp;

    public MainController() {
        try {
            chatClient = new ChatClient("127.0.0.1", 1337, this);

            TextInputDialog dialog = new NicknameSelectDialog();

            Optional<String> nickname = dialog.showAndWait();

            if(!nickname.isPresent()){
                System.out.println("Failed to choose a nickname");
                System.exit(0);
            }

            chatClient.sendNickCommand(nickname.get());

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @FXML
    private void initialize() {
        chatListView.setItems(chatHistory);

        sendButton.setOnAction(evt -> {
            chatClient.sendCommand(input.getText());
            input.clear();
        });
    }

    public void setMainApp(ClientMain mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void handleCommand(Command command) {
        Platform.runLater(() -> {
            chatHistory.add(String.format("[%s]: %s", command.getSender(), command.getMessage().replace(Command.NEWLINE, "\r\n")));
        });
    }
}
