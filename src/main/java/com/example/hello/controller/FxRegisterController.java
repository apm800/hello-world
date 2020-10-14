package com.example.hello.controller;

import com.example.hello.JavaFxMainClass;
import com.example.hello.entity.Reader;
import com.example.hello.entity.User;
import com.example.hello.util.DataBaseUtil;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author zhoukai
 * @date 2020/8/3 16:44
 */
public class FxRegisterController implements Initializable {

    @FXML
    JFXRadioButton readerRadio;
    @FXML
    JFXRadioButton admin;
    @FXML
    JFXTextField tfId;
    @FXML
    JFXTextField tfUserName;
    @FXML
    JFXTextField tfEmail;
    @FXML
    JFXTextField tfPassword;
    @FXML
    JFXRadioButton manRadio;
    @FXML
    JFXRadioButton womanRadio;

    private JavaFxMainClass myApp;
    private Stage myStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("请输入编号");
        tfId.getValidators().add(validator);
        tfId.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfId.validate();
            }
        });

        validator.setMessage("请输入用户名");
        tfUserName.getValidators().add(validator);
        tfUserName.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfUserName.validate();
            }
        });

        validator.setMessage("请输入密码");
        tfPassword.getValidators().add(validator);
        tfPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfPassword.validate();
            }
        });

        validator.setMessage("请输入邮箱");
        tfEmail.getValidators().add(validator);
        tfEmail.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                tfEmail.validate();
            }
        });

    }

    public void setMyApp(JavaFxMainClass myApp) {
        this.myApp = myApp;
    }

    public void setStage(Stage myStage) {
        this.myStage = myStage;
    }

    @FXML
    public void goBackLogin() {
        myApp.showWindow();
        myStage.close();
    }

    @FXML
    public void confirm() {
        String userName = tfUserName.getText().trim();
        String passWord = tfPassword.getText().trim();
        String email = tfEmail.getText().trim();
        String id = tfId.getText().trim();
        String sex = "";
        if (manRadio.isSelected()) {
            sex = "男";
        } else {
            sex = "女";
        }
        if (!id.equals("") || !email.equals("") || !passWord.equals("") || !userName.equals("")) {
            boolean isok = false;
            if (readerRadio.isSelected()) {
                Reader reader = new Reader(id, userName, passWord, "学生", sex, 12, 30, 0);
                isok = DataBaseUtil.addNewReader(reader);
            } else {
                User user = new User(id, userName, passWord, email, 1);
                isok = DataBaseUtil.addNewUser(user);
            }
            if (isok) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("注册成功！");
                alert.showAndWait();
                myApp.showWindow();
                myStage.close();
            }
        } else {

            return;
        }
    }

}
