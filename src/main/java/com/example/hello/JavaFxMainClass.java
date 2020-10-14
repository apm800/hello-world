package com.example.hello;

import com.example.hello.controller.FxLoginController;
import com.example.hello.controller.MainUiController;
import com.example.hello.controller.FxReaderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author zhoukai
 * @date 2020/7/28 17:50
 */
public class JavaFxMainClass extends Application {

    private Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setResizable(false);
        //设置任务栏的图标.
        mainStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/picture/logo.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("图书管理系统(左上角)");
        FxLoginController fxLoginController = loader.getController();
        fxLoginController.setApp(this);
        Scene scene = new Scene(root, 700, 460);
        scene.getStylesheets().add(JavaFxMainClass.class.getResource("/main.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void gotoMainUi(String userId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainUi.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            MainUiController controller = loader.getController();
            controller.setApp(this);
            controller.setMyName(userId);
            Scene scene = new Scene(root, 700, 500);
            scene.getStylesheets().add(JavaFxMainClass.class.getResource("/main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void gotoReaderUi(String id) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/readerUi.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            FxReaderController controller = loader.getController();
            controller.setApp(this);
            controller.setUserInfo(id);
            Scene scene = new Scene(root, 700, 460);
            scene.getStylesheets().add(JavaFxMainClass.class.getResource("/main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage() + e.toString());
        }

    }

    public void closeWindow() {
        mainStage.close();
    }

    public void hideWindow() {
        mainStage.hide();
    }

    public void showWindow() {
        mainStage.show();
    }


//    public static void main(String[] args) {
//        launch(args);
//    }

    public void gotoLoginUi() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = loader.load();
            mainStage.setTitle("图书管理系统");
            FxLoginController fxLoginController = loader.getController();
            fxLoginController.setApp(this);
            Scene scene = new Scene(root, 700, 460);
            scene.getStylesheets().add(JavaFxMainClass.class.getResource("/main.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.show();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }
}
