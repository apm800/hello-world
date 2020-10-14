package com.example.hello.controller;

import com.example.hello.JavaFxMainClass;
import com.example.hello.util.DataBaseUtil;
import com.example.hello.util.FileUtil;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

/**
 * @author zhoukai
 * @date 2020/7/31 15:50
 */
public class FxLoginController implements Initializable {

    private JavaFxMainClass myApp;
    @FXML
    private JFXTextField userName;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton loginBtn;
    @FXML
    private JFXProgressBar loginBar;
    @FXML
    private JFXCheckBox rememberInfo;
    @FXML
    private JFXRadioButton readerRadioButton;
    @FXML
    private JFXRadioButton adminRadioButton;
    @FXML
    public ToggleGroup identity;

    JFXDialog dialog = new JFXDialog();

    public void setApp(JavaFxMainClass myApp) {
        this.myApp = myApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //默认记住密码
        rememberInfo.setSelected(true);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("请输入用户名...");
        userName.getValidators().add(validator);
        userName.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                userName.validate();
            }
        });

        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        validator2.setMessage("请输入密码...");
        password.getValidators().add(validator2);
        password.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal) {
                password.validate();
            }
        });
        //默认勾选读者
        readerRadioButton.setSelected(true);
        //进度条
        loginBar.setVisible(false);
        //记录密码
        String str = FileUtil.getUserAndPass();
//        System.out.println(str);
//
//        Pattern p = Pattern.compile("[#]+");

        //改用json
        String[] result = str.split("#");
        if (result.length >= 1) {
            userName.setText(result[0]);
        }
        if (result.length >= 2) {
            password.setText(result[1]);
        }

    }

    /**
     * 登录
     *
     * @author zhoukai
     * @date 2020/8/5 11:00
     */
    @FXML
    public void loginIn() {
        loginBar.setVisible(true);
        //创建线程登录
        MyProgress myProgress = new MyProgress(loginBar);

        Thread thread = new Thread(myProgress);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();

        if (rememberInfo.isSelected()) {
            FileUtil.setUserAndPass(userName.getText(), password.getText());
        } else {
            FileUtil.setUserAndPass(userName.getText(), "");
        }
        //登录界面控件不可见
        setDisable(true);
        loginBar.setVisible(false);
        System.out.println("loginIn success");
    }

    /**
     * 登录期间组件的控制,登录界面控件是否可见
     *
     * @author zhoukai
     * @date 2020/8/5 11:00
     */
    public void setDisable(Boolean bool) {
        loginBtn.setDisable(bool);
        userName.setDisable(bool);
        password.setDisable(bool);
        rememberInfo.setDisable(bool);
    }


    /**
     * 检查账号信息并登陆
     *
     * @author zhoukai
     * @date 2020/8/3 16:14
     */
    private void checkAndLogIn() {
        //读者
        if (identity.getSelectedToggle() == readerRadioButton) {
            if (DataBaseUtil.checkReader(userName.getText().trim(), password.getText())) {
                myApp.gotoReaderUi(userName.getText());
            } else {
                setDisable(false);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("登录失败！");
                alert.show();
            }
        } else if (identity.getSelectedToggle() == adminRadioButton) {
            if (DataBaseUtil.checkUser(userName.getText().trim(), password.getText())) {
                myApp.gotoMainUi(userName.getText());
            } else {
                setDisable(false);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("登录失败！");
                alert.show();
            }
        }
    }

    /**
     * 忘记密码
     *
     * @author zhoukai
     * @date 2020/8/5 11:01
     */
    @FXML
    public void forgotPassword() {
        myApp.hideWindow();
        Stage myStage = new Stage();
        myStage.setResizable(false);
        //设置窗口的图标.
        myStage.getIcons().add(new Image(
                JavaFxMainClass.class.getResourceAsStream("/picture/logo.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/forgotPass.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FxForgotPwdController con = loader.getController();
        con.setMyApp(myApp);
        con.setStage(myStage);
        myStage.setTitle("忘记密码");
        Scene scene = new Scene(root, 475, 400);
        scene.getStylesheets().add(JavaFxMainClass.class.getResource("/main.css").toExternalForm());
        myStage.setScene(scene);
        myStage.show();

    }

    /**
     * 注册
     *
     * @author zhoukai
     * @date 2020/8/5 11:00
     */
    @FXML
    public void register() {
        myApp.hideWindow();
        Stage myRegisterStage = new Stage();
        myRegisterStage.setResizable(false);
        //设置窗口的图标.
        myRegisterStage.getIcons().add(new Image(JavaFxMainClass.class.getResourceAsStream("/picture/logo.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FxRegisterController con = loader.getController();
        con.setMyApp(myApp);
        con.setStage(myRegisterStage);
        myRegisterStage.setTitle("注册");
        Scene scene = new Scene(root, 475, 400);
        scene.getStylesheets().add(JavaFxMainClass.class.getResource("/main.css").toExternalForm());
        myRegisterStage.setScene(scene);
        myRegisterStage.show();
    }


    /**
     * 登录界面:点击登录按钮后,启用新的线程检查用户身份是否正确
     *
     * @author zhoukai
     * @date 2020/8/5 11:01
     */
    class MyProgress implements Runnable {
        private JFXProgressBar loginBar;

        MyProgress(JFXProgressBar loginBar) {
            this.loginBar = loginBar;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i <= 100; i++) {
                    loginBar.setProgress(i);
                }
//                sleep(3000);
                //更新JavaFX的主线程的代码放在此处
                Platform.runLater(FxLoginController.this::checkAndLogIn);
            } catch (Exception ignored) {
            }
        }
    }
}

