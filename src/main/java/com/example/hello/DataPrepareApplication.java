package com.example.hello;

import com.example.hello.controller.DataPrepareController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * 结算数据拆分-总部交易清算数据准备
 * Java开发笔记（一百四十五）FXML布局的伸展适配https://www.cnblogs.com/pinlantu/archive/2019/09/02/11448309.html
 *
 * @author zhoukai
 * @date 2020/8/5 14:14
 */
public class DataPrepareApplication extends Application {

    public Stage mainStage;
    /**
     * Pane是其它布局控件类的父类
     */
    private static Pane root;
    private static List<CheckBox> checkBoxList = new ArrayList<>();
    private static CheckBox allCheck = new CheckBox("全选");


//    初始代码
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        mainStage = primaryStage;
//        mainStage.setResizable(false);
//        //设置任务栏的图标
//        mainStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/picture/yinhe.png")));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/indexResizeable.fxml"));
//        Parent root = loader.load();
//        DataPrepareController dataPrepareController = loader.getController();
//        dataPrepareController.setApp(this);
//
//        Scene scene = new Scene(root, 1000, 800);
//        scene.getStylesheets().add(this.getClass().getResource("/main.css").toExternalForm());
//
//        primaryStage.setTitle("结算数据拆分-总部交易清算数据准备");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        mainStage.setResizable(true);
        //设置任务栏的图标
        mainStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/picture/yinhe.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/index.fxml"));
        root = loader.load();
        DataPrepareController dataPrepareController = loader.getController();
        dataPrepareController.setApp(this);

        allCheck.setSelected(true);
        dynamicLoadCheckBox();

        Scene scene = new Scene(root, 1000, 800);
        scene.getStylesheets().add(this.getClass().getResource("/main.css").toExternalForm());

        primaryStage.setTitle("结算数据拆分-总部交易清算数据准备");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    /**
     * 动态加载复选框
     *
     * @author zhoukai
     * @date 2020/8/7 8:41
     */
    private void dynamicLoadCheckBox() throws IOException {
        checkBoxList.clear();

        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream("config/dynamicConfig.properties"), "UTF-8"));
        String[] fileNames = properties.getProperty("fileName").split(",");

        HBox hBox = new HBox(10);
//        hBox.setStyle("-fx-background-color: greenyellow");
        hBox.setLayoutY(30.0);
        hBox.setPadding(new Insets(0, 15, 0, 10));

        allCheck.setTextFill(Paint.valueOf("BLACK"));
        allCheck.setOnAction((ActionEvent e) -> {
            isSelected(allCheck);
        });

        checkBoxList.add(allCheck);
        hBox.getChildren().add(allCheck);

        for (int i = 0; i < fileNames.length; i++) {
            CheckBox checkBox = new CheckBox();
            checkBox.setSelected(true);
            checkBox.setText(fileNames[i]);
            checkBox.setTextFill(Paint.valueOf("BLACK"));
            checkBoxList.add(checkBox);
            checkBox.setOnAction((ActionEvent e) -> {
                isSelected(checkBox);
            });
            hBox.getChildren().add(checkBox);
        }
        root.getChildren().add(hBox);
    }


    private void isSelected(CheckBox checkBox) {

        if (allCheck.getText().equals(checkBox.getText())) {
            boolean selected = allCheck.isSelected();
            if (selected) {
                for (CheckBox box : checkBoxList) {
                    box.setSelected(selected);
                    System.out.println(box.getText() + " - " + selected);
                }
            } else {
                for (CheckBox box : checkBoxList) {
                    box.setSelected(selected);
                    System.out.println(box.getText() + " - " + selected);
                }
            }
        } else {
            int count = 0;
            for (CheckBox box : checkBoxList) {
                if (box.isSelected()) {
                    if (allCheck.getText().equals(box.getText())) {
                        continue;
                    }
                    System.out.println(box.getText() + " - " + true);
                    count++;
                }
            }
            if (count == checkBoxList.size() - 1) {
                allCheck.setSelected(true);
            } else {
                allCheck.setSelected(false);
            }
        }
    }

    /**
     * 获取选中的复选框
     *
     * @author zhoukai
     * @date 2020/8/7 8:48
     */
    public List<CheckBox> getSelectedBox() {
        return checkBoxList;
    }

    public void hideWindow() {
        mainStage.hide();
    }
}
