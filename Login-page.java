package demologin;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.EventListener;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

//public class HelloApplication extends Application implements EventHandler<ActionEvent> {
public class DemoLogin extends Application {
    String user = "Barnaan";
    String pswd = "12345678";

    boolean userFlag = false;
    boolean passFlag = false;
    Stage window;
    Scene scene1, scene2, scene3;
    Button button = new  Button();

    // starts here
    public void start(Stage stage) {
        window = stage;
       
        window.setTitle("Welcome - Login");
       GridPane grid = new GridPane();
       grid.setAlignment(Pos.CENTER);
       grid.setHgap(10);
       grid.setVgap(10);
       grid.setPadding(new Insets(25, 25, 25, 25));
       
       // Login title
       Text scenetitle = new Text("Welcome again");
       scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
       grid.add(scenetitle, 0, 0, 2, 1);
 
       Label errorPassword = new Label();
       Label errorUserName = new Label();
 
       errorPassword.setTextFill(Color.rgb(255, 0, 0));
       errorUserName.setTextFill(Color.rgb(255, 0, 0));

       // LABELS
       Label userName = new Label("User Name:");
       grid.add(userName, 0, 1);
        
       TextField userText = new TextField();
       grid.add(userText, 1, 1);


       Label pw = new Label("Password:");
       grid.add(pw, 0, 2);

       PasswordField passText = new PasswordField();
       grid.add(passText, 1, 2);

       TextArea editText = new TextArea();
       editText.setPrefHeight(500);
        
               // LOGIN BUTTON
       Button login = new Button("log in");
       HBox hbBtn = new HBox(10);
       hbBtn.setAlignment(Pos.BOTTOM_LEFT);
       hbBtn.getChildren().add(login);
       grid.add(hbBtn, 1, 4);

       // SETTING ACTION ON LOGIN BUTTON
        login.setOnAction(e -> {
            String check = userText.getText();
            

                if (user.equals(check)) {
                    errorUserName.setText("");
                    userFlag = true;

                } else {
                    System.out.println("Incorrect username");
                    errorUserName.setText("Incorrect username");
                    window.setScene(scene1);
                    userFlag = false;

                }
            
            if (pswd.equals(passText.getText())) {
                errorPassword.setText("");
                passFlag = true;
               
            } else {
                System.out.println("Incorrect password");
                errorPassword.setText("Incorrect password");
                window.setScene(scene1);
                passFlag = false;

            }
            if (passFlag && userFlag) {
                System.out.println("Logged in");
                System.out.println(check);
                window.setScene(scene2);
                window.setTitle("Welcome - Notepad");
            } else {
                System.out.println("Username or password does not exist");
            }
        });

// LAYING OUT ELEMENTS OF LOGIN WINDOW/LAYOUT1
        VBox layout1 = new VBox(10);
        VBox buttons = new VBox(20);
        VBox boxFields = new VBox(25);
        buttons.getChildren().addAll(login);

        layout1.setPadding(new Insets(10));
        boxFields.setPadding(new Insets(10, 20, 10, 20));

        layout1.getChildren().addAll(errorUserName, userText, errorPassword, passText);
        boxFields.getChildren().addAll(layout1, buttons);
        
        // LAYOUT 2
        VBox layout2 = new VBox(25);
            MenuBar menuBar = new MenuBar();
            // MENUS
            Menu menuFile = new Menu("File");
            Menu menuEdit = new Menu("Edit");
            Menu menuView = new Menu("View");
            
            
            //MENU ITEMS
            MenuItem open = new MenuItem("Open");
            MenuItem save = new MenuItem("Save");
            MenuItem saveAs = new MenuItem("Save as");
            MenuItem exit = new MenuItem("Exit");
            
            menuBar.getMenus().addAll(menuFile,menuEdit, menuView);
            menuFile.getItems().addAll(open, save, saveAs, exit);
        FileChooser fileChooser = new FileChooser();

        open.setOnAction(e->{
       // OPENING FILE
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("pdf Files", "*.pdf")
        );

        // opening selected file on new window
        File selectedFile = fileChooser.showOpenDialog(window);
    String line;

    if (selectedFile != null) {
        editText.setText(" ");
            System.out.println(selectedFile.getAbsoluteFile());
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(selectedFile));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
            while (true) {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(line);
                editText.setText((line));
                editText.appendText(line);
            }

        }
    System.out.println("File opened");
});

          // SAVING FILE  
        save.setOnAction(e->{
                String sampleText = editText.getText();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);

                File file = fileChooser.showSaveDialog(window);
                if(file != null){
                    try {
                        saveTextToFile(sampleText, file);
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        exit.setOnAction(e -> {
            System.exit(0);

        });
        layout2.getChildren().addAll(menuBar, editText);

            scene2 = new Scene(layout2, 400, 500);
            scene1 = new Scene(boxFields, 400, 400);

            window.setScene(scene1);
            window.setTitle("Welcome - Login");
            window.show();

        }

    private void saveTextToFile(String sampleText, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.println(sampleText);
        writer.close();
    }

    public static void main(String[] args) {
        launch();
    }

   }



