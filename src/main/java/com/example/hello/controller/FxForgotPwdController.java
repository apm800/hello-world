package com.example.hello.controller;

import com.example.hello.JavaFxMainClass;
import com.example.hello.util.DataBaseUtil;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author zhoukai
 * @date 2020/7/29 8:42
 */
public class FxForgotPwdController implements Initializable {

    @FXML
    JFXRadioButton readerRadio;
    @FXML
    JFXRadioButton admin;
    @FXML
    JFXTextField email;
    @FXML
    JFXTextField newPassword;
    @FXML
    Hyperlink register;
    @FXML
    JFXButton confirmBtn;
    @FXML
    Hyperlink goBackLoginLink;

    private JavaFxMainClass myApp;
    private Stage myStage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("请输入有效的邮箱...");
        email.getValidators().add(validator);
        email.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                email.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("请输入有效的密码...");
        newPassword.getValidators().add(validator2);
        newPassword.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                newPassword.validate();
            }
        });
    }

    public void setMyApp(JavaFxMainClass myApp) {
        this.myApp = myApp;
    }

    public void setStage(Stage myStage) {
        this.myStage = myStage;
    }

    /**
     * 点击确定,校验邮箱密码是否有效正确
     *
     * @author zhoukai
     * @date 2020/8/5 10:30
     */
    @FXML
    public void confirm() {
        boolean emailReady = false;
        boolean passReady = false;

        String emailText = email.getText().trim();
        String newPwd = newPassword.getText().trim();

        if (StringUtils.isNotBlank(emailText) && StringUtils.isNotBlank(newPwd)) {
            if (emailText.endsWith(".com") && emailText.contains("@")) {
                emailReady = true;
            } else {
                email.setText("");
                email.validate();
            }
            if (newPwd.length() >= 8) {
                passReady = true;
            } else {
                newPassword.setText("");
                newPassword.validate();
            }
            if (emailReady && passReady) {
                boolean isOk;
                if (readerRadio.isSelected()) {
                    isOk = DataBaseUtil.alterReaderPass(emailText, newPwd);
                } else {
                    isOk = DataBaseUtil.alterUserPass(emailText, newPwd);
                }
                if (isOk) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("修改成功！");
                    alert.showAndWait();
                    myApp.showWindow();
                    myStage.close();
                }
            }

        } else {
            email.setText("");
            newPassword.setText("");
            email.validate();
            newPassword.validate();
        }
        //myApp.showWindow();
        //myStage.close();
    }

    /**
     * 注册
     *
     * @author zhoukai
     * @date 2020/8/5 10:30
     */
    @FXML
    public void register() {
        Stage registerStage = new Stage();
        registerStage.setResizable(false);
        //设置窗口的图标.
        registerStage.getIcons().add(new Image(JavaFxMainClass.class.getResourceAsStream("/picture/logo.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FxRegisterController con = loader.getController();
        con.setMyApp(myApp);
        con.setStage(registerStage);
        registerStage.setTitle("注册");
        Scene scene = new Scene(root, 475, 400);
        scene.getStylesheets().add(JavaFxMainClass.class.getResource("/main.css").toExternalForm());
        registerStage.setScene(scene);
        registerStage.show();
        myStage.close();
    }

    /**
     * 返回首页
     *
     * @author zhoukai
     * @date 2020/8/5 10:30
     */
    @FXML
    public void goBackLogin() {
        myApp.showWindow();
        myStage.close();
    }
}
