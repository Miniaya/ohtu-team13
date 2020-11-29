package Scenes;

import Database.SqlDbBookDao;
import Database.SqlDbUrlDao;
import Service.VinkkiService;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public abstract class CreateBookmarkScene {

    Label title;
    Button returnButton;
    List<TextField> fields;
    Label errorMessage;
    Button submitButton;
    ChooseAddScene chooseAddScene;
    VinkkiService vinkkiService;

    public CreateBookmarkScene(ChooseAddScene chooseAddScene) {
        this.chooseAddScene = chooseAddScene;

        this.title = new Label();
        title.setId("title_label");

        this.returnButton = new Button("Return");
        returnButton.setId("returnButton_btn");

        this.fields = new ArrayList<>();

        this.errorMessage = new Label();
        this.errorMessage.setId("errorMessage_label");

        this.submitButton = new Button();
        this.submitButton.setId("submitButton_btn");

        try {
            this.vinkkiService = new VinkkiService(
                    new SqlDbBookDao(), new SqlDbUrlDao()
            );
        } catch (Exception e) {
            this.vinkkiService = null;
            this.errorMessage.setText(
                    "Error in database connection: " + e.getMessage()
            );
        }
    }

    public Scene createScene() {
        VBox vbox = new VBox();
        vbox.setId("elements");
        vbox.setPadding(new Insets(80, 50, 50, 100));
        vbox.setSpacing(5);

        vbox.getChildren().add(title);
        vbox.getChildren().add(returnButton);

        setBookmarkInputFields();

        for (TextField field: fields) {
            vbox.getChildren().add(field);
        }

        vbox.getChildren().add(errorMessage);
        vbox.getChildren().add(submitButton);

        returnButtonFunction();
        submitButtonFunction();

        Scene creationScene = new Scene(vbox, 600, 400);

        return creationScene;
    }

    protected abstract void setBookmarkInputFields();

    protected abstract boolean bookmarkCreation();

    protected void submitButtonFunction() {
        submitButton.setOnAction(e -> {
            try {
                boolean added = bookmarkCreation();
                if (added) {
                    for (TextField field: fields) {
                        field.setText("");
                    }
                    errorMessage.setText("");
                    chooseAddScene.returnHere();
                } else {
                    if (errorMessage.getText().equals("")) {
                        errorMessage.setText(
                            "Error adding bookmark to database");
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }    
        });
    }

    protected void returnButtonFunction() {
        returnButton.setOnAction(e -> {
            try {
                chooseAddScene.returnHere();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    protected int convertToInteger(String s) {
        s = s.trim();
        int value;
        if (s.length() == 0) {
            value = -9999;
        } else {
            try {
                value = Integer.valueOf(s);
            } catch (NumberFormatException e) {
                value = -9999;
            }
        }
        return value;
    }

    protected String checkString(String s) {
        s.trim();
        if (s.isEmpty()) {
            return null;
        }
        return s;
    }
}
