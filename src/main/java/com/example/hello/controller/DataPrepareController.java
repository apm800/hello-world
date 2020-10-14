package com.example.hello.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.hello.DataPrepareApplication;
import com.example.hello.JavaFxMainClass;
import com.example.hello.entity.DataSpiltEntity;
import com.example.hello.entity.EtfEntity;
import com.example.hello.entity.QfIiEntity;
import com.example.hello.util.BigFileReader;
import com.linuxense.javadbf.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 结算数据拆分-总部交易清算数据准备
 *
 * @author zhoukai
 * @date 2020/8/5 14:30
 */
public class DataPrepareController implements Initializable {

    private static DataPrepareApplication mainApp;
    @FXML
    private TableView<QfIiEntity> qfIiTableView;
    @FXML
    private TableView<EtfEntity> etfTableView;
    @FXML
    private TableColumn<QfIiEntity, Integer> sequenceNum;
    @FXML
    private TableColumn<QfIiEntity, String> status, system, jsHy;
    @FXML
    private TableColumn<QfIiEntity, Long> tradeUnitOne, tradeUnitTwo, tradeUnitThree, tradeUnitFour;
    @FXML
    private TableColumn<EtfEntity, Integer> etfSequenceNum;
    @FXML
    private TableColumn<EtfEntity, String> etfStatus, admin, etfCode, etfName, cilType, cilSource;
    @FXML
    private Label sourcePath;
    @FXML
    private Label count;
    @FXML
    private Label dateTime;


    private static ObservableList<QfIiEntity> qfIiEntityList = FXCollections.observableArrayList();
    private static ObservableList<EtfEntity> etfEntityList = FXCollections.observableArrayList();
    private static final Logger LOG = LogManager.getLogger(DataPrepareController.class);

    public void setApp(DataPrepareApplication dataPrepareApplication) {
        mainApp = dataPrepareApplication;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> dateTime.setText(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"))),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        qfIiEntityList.clear();
//        sequenceNum.setCellValueFactory(new PropertyValueFactory("sequenceNum"));
//        status.setCellValueFactory(new PropertyValueFactory("status"));
//        system.setCellValueFactory(new PropertyValueFactory("system"));
//        tradeUnitOne.setCellValueFactory(new PropertyValueFactory("tradeUnitOne"));
//        tradeUnitTwo.setCellValueFactory(new PropertyValueFactory("tradeUnitTwo"));
//        tradeUnitThree.setCellValueFactory(new PropertyValueFactory("tradeUnitThree"));
//        tradeUnitFour.setCellValueFactory(new PropertyValueFactory("tradeUnitFour"));
//        for (int i = 0; i < 10; i++) {
//            QfIiEntity qfIiEntity = new QfIiEntity();
//            qfIiEntity.setSequenceNum(i);
//            qfIiEntity.setStatus("已到");
//            qfIiEntity.setSystem("QFII");
//            qfIiEntity.setTradeUnitOne(9999L);
//            qfIiEntityList.add(qfIiEntity);
//        }
//        qfIiTableView.setItems(qfIiEntityList);

        try {
            String absolutePath = ResourceUtils.getFile("config/dynamicConfig.properties").getPath();
            sourcePath.setText(absolutePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        etfEntityList.clear();
        etfSequenceNum.setCellValueFactory(new PropertyValueFactory("etfSequenceNum"));
        etfStatus.setCellValueFactory(new PropertyValueFactory("etfStatus"));
        admin.setCellValueFactory(new PropertyValueFactory("admin"));
        etfCode.setCellValueFactory(new PropertyValueFactory("etfCode"));
        etfName.setCellValueFactory(new PropertyValueFactory("etfName"));
        cilType.setCellValueFactory(new PropertyValueFactory("cilType"));
        cilSource.setCellValueFactory(new PropertyValueFactory("cilSource"));
        for (int i = 1; i < 15; i++) {
            EtfEntity qfIiEntity = new EtfEntity();
            qfIiEntity.setEtfSequenceNum(i);
            qfIiEntity.setEtfStatus("已到");
            qfIiEntity.setAdmin("管理人" + i);
            qfIiEntity.setEtfCode("ETF代码" + i);
            qfIiEntity.setEtfName("ETF名称" + i);
            qfIiEntity.setCilType("类型");
            qfIiEntity.setCilSource("路径");
            etfEntityList.add(qfIiEntity);
        }
        etfTableView.setItems(etfEntityList);
        count.setText(etfEntityList.size() + "");

        System.out.println("初始化");
    }

    @FXML
    public void abcLink() {
        //这里有两个属性，visible表示是否可见，并不会真正的隐藏，
        //而managed这个属性则表示是否隐藏，这两个属性必须同时使用
//        zcGlCheckBox.setVisible(true);
//        zcGlCheckBox.setManaged(true);

        for (CheckBox box : mainApp.getSelectedBox()) {
            System.out.println(box.getText() + " - " + box.isSelected());
        }

        System.out.println("abcLink 点击事件");
    }

    @FXML
    private void help() {
        mainApp.hideWindow();
        Stage helpStage = new Stage();
        helpStage.setResizable(false);
        //设置窗口的图标.
        helpStage.getIcons().add(new Image(JavaFxMainClass.class.getResourceAsStream("/picture/yinhe.png")));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/help.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        FxForgotPwdController con = loader.getController();
//        con.setMyApp(mainApp);
//        con.setStage(helpStage);
        helpStage.setTitle("忘记密码");
        Scene scene = new Scene(root, 475, 400);
        scene.getStylesheets().add(JavaFxMainClass.class.getResource("/main.css").toExternalForm());
        helpStage.setScene(scene);
        helpStage.show();
    }

    /**
     * https://blog.csdn.net/u010689849/article/details/90340745
     * https://blog.csdn.net/changerzhuo_319/article/details/53426012
     *
     * @author zhoukai
     * @date 2020/8/20 16:03
     */
    @FXML
    public void dataSpilt() throws Exception {
        StringBuffer buffer = new StringBuffer();
        for (CheckBox checkBox : mainApp.getSelectedBox()) {
            buffer.append(checkBox.getText()).append(" ").append(checkBox.isSelected()).append(";");
        }
        System.out.println(buffer);

        /*
         * 1.获取勾选的文件类型,获取对应的源文件
         * 2.对数据按照指定字段进行拆分,Map<String,List<对象>>
         * 3.考虑写入dbf时字段的类型
         * 4.多线程
         */

        sequenceNum.setCellValueFactory(new PropertyValueFactory("sequenceNum"));
        status.setCellValueFactory(new PropertyValueFactory("status"));
        system.setCellValueFactory(new PropertyValueFactory("system"));
        tradeUnitOne.setCellValueFactory(new PropertyValueFactory("tradeUnitOne"));
        tradeUnitTwo.setCellValueFactory(new PropertyValueFactory("tradeUnitTwo"));
        tradeUnitThree.setCellValueFactory(new PropertyValueFactory("tradeUnitThree"));
        tradeUnitFour.setCellValueFactory(new PropertyValueFactory("tradeUnitFour"));
        jsHy.setCellValueFactory(new PropertyValueFactory("jsHy"));

        qfIiEntityList.clear();
        qfIiTableView.setItems(qfIiEntityList);

//        String filePath = "D:/桌面/银河/需求文件/数据拆分/sjsmx1.dbf";
        String filePath = "D:/桌面/银河/需求文件/数据拆分/SJSMX10911.DBF";
//        Map<String, List<Map<String, Object>>> resultMap = readDbf(filePath);
        readDbf(filePath);
//        for (Map.Entry<String, List<Map<String, Object>>> entry : resultMap.entrySet()) {
//            String path = "dbf/MYJYDY" + entry.getKey() + ".DBF";
//            createDbf(path, entry.getValue(), "GBK");
//        }
    }

    @FXML
    public void refresh() throws Exception {
        mainApp.start(mainApp.mainStage);
    }

    public static void main(String[] args) {
        long begin = System.nanoTime();
//        writeDBF("test.dbf");
//        try {
////            readDbf("D:/桌面/银河/需求文件/数据拆分/SJSJG.dbf", 0);
////            readDbf("D:/桌面/银河/需求文件/数据拆分/sjsmx1.dbf", 0);
//            readDbf("D:/桌面/银河/需求文件/数据拆分/SJSMX10911.DBF");
////            readDbf("test.dbf", 0);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (DBFException e) {
//            e.printStackTrace();
//        }


        AtomicLong counter = new AtomicLong(0);
//        String bigFilePath = "D:/桌面/银河/需求文件/数据拆分/sjsmx1.dbf";
//        String bigFilePath = "D:/桌面/银河/需求文件/数据拆分/SJSMX10911.DBF";
        String bigFilePath = "dbf/20200922122728.dbf";
//        String bigFilePath = "D:/桌面/银河/需求文件/数据拆分/YHHISSTOCKINFO.csv";
//        String bigFilePath = "D:/桌面/银河/需求文件/数据拆分/YHREDUCEINFO.csv";
//        BigFileReader.Builder builder = new BigFileReader.Builder(bigFilePath, line -> System.out.println(String.format("total record: %s,line is: %s", counter.incrementAndGet(), line.trim()
//        )));
//        BigFileReader bigFileReader = builder
//                .threadPoolSize(100)
//                .charset(StandardCharsets.UTF_8)
//                .bufferSize(1024 * 1024).build();
//        bigFileReader.start();
//        System.out.println("end");

//        try {
//            readDbf("dbf/20200922122728.dbf");
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        //rw : 设置模式为读写模式
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(bigFilePath, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("当前记录指针位置：" + raf.getFilePointer());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = new byte[1024];
        int len = 0;
        while (true) {
            try {
                len = raf.read(buf);
//                if (!(len != -1)){
                if (len == -1) {
                    break;
                }
                System.out.println("当前记录指针位置：" + raf.getFilePointer());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(buf));
            System.out.println("===");
        }
        System.out.println("用时:" + (System.nanoTime() - begin) / 1000000000 + "s");
    }


    public static void writeDBF(String path) {
        OutputStream fos = null;
        try {
            // 定义DBF文件字段
            DBFField[] fields = new DBFField[3];
            // 分别定义各个字段信息，setFieldName和setName作用相同,
            // 只是setFieldName已经不建议使用
            fields[0] = new DBFField();
            // fields[0].setFieldName("emp_code");
            fields[0].setName("semp_code");
            fields[0].setDataType(DBFField.FIELD_TYPE_C);
            fields[0].setFieldLength(10);
            fields[1] = new DBFField();
            // fields[1].setFieldName("emp_name");
            fields[1].setName("emp_name");
            fields[1].setDataType(DBFField.FIELD_TYPE_C);
            fields[1].setFieldLength(20);
            fields[2] = new DBFField();
            // fields[2].setFieldName("salary");
            fields[2].setName("salary");
            fields[2].setDataType(DBFField.FIELD_TYPE_N);
            fields[2].setFieldLength(12);
            fields[2].setDecimalCount(2);
            // DBFWriter writer = new DBFWriter(new File(path));
            // 定义DBFWriter实例用来写DBF文件
            DBFWriter writer = new DBFWriter();
            // 把字段信息写入DBFWriter实例，即定义表结构
            writer.setFields(fields);
            // 一条条的写入记录
            Object[] rowData = new Object[3];
            rowData[0] = "1000";
            rowData[1] = "John";
            rowData[2] = new Double(5000.00);
            writer.addRecord(rowData);
            rowData = new Object[3];
            rowData[0] = "1001";
            rowData[1] = "Lalit";
            rowData[2] = new Double(3400.00);
            writer.addRecord(rowData);
            rowData = new Object[3];
            rowData[0] = "1002";
            rowData[1] = "Rohit";
            rowData[2] = new Double(7350.00);
            writer.addRecord(rowData);
            // 定义输出流，并关联的一个文件
            fos = new FileOutputStream(path);
            // 写入数据
            writer.write(fos);
            // writer.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * 创建dbf
     *
     * @param path        文件路径
     * @param dataList    字段
     * @param charsetName 编码字符集
     * @throws IOException
     */
    public static void createDbf(String path, List<Map<String, Object>> dataList, String charsetName)
            throws IOException {

        DBFField[] fields = new DBFField[0];

        int index = 0;
        for (Map<String, Object> dataMap : dataList) {
            fields = new DBFField[dataMap.size()];
            DBFField field = new DBFField();
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                field.setName(entry.getKey());
                fields[index] = field;
                index++;
            }
//            //字段名称
//            field.setName(dataMap.get("name"));
//            //指定字段类型为字符串
//            field.setType(DBFDataType.CHARACTER);
//            //指定长度
//            field.setLength(Integer.valueOf(dataMap.get("length")));
        }
        //定义DBFWriter实例用来写DBF文件
        DBFWriter dbfWriter = new DBFWriter(new FileOutputStream(path), Charset.forName(charsetName));
        //设置字段
        dbfWriter.setFields(fields);
        //写入dbf文件并关闭
        dbfWriter.close();
    }

    /**
     * 读取DBF文件
     *
     * @param path 文件路径
     * @author zk
     * @date 2020/9/15 10:49
     */
    private static Map<String, List<JSONObject>> readDbf(String path) throws FileNotFoundException, DBFException {
        long begin = System.nanoTime();
        if (StringUtils.isBlank(path) || !path.endsWith(".DBF") || !path.endsWith(".dbf")) {
            LOG.error("该dbf文件不存在： " + path);
        }
        File file = new File(path);
        if (!file.exists()) {
            LOG.error("该dbf文件不存在： " + path);
        }

//        Map<String, List<Map<String, Object>>> resultMap = new HashMap<>(16);
        Map<String, List<JSONObject>> resultMap = new HashMap<>(16);

        InputStream fis = null;
        DBFReader reader;
        try {
            fis = new FileInputStream(file);
            // 读取DBF文件信息
            reader = new DBFReader(fis);
            System.out.println(reader.getRecordCount());
            //存放当前行中的值
            Object[] rowValues;

            String mxJyDy = null;
            String keyWord = "MXJYDY";
            List<JSONObject> newList = new ArrayList<>();

            while ((rowValues = reader.nextRecord()) != null) {
//                Map<String, Object> map = new HashMap<>(rowValues.length);
//                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < rowValues.length; i++) {
                    String fieldName = reader.getField(i).getName();
//                    jsonObject.put(fieldName, rowValues[i]);
//                    System.out.println(fieldName + "-" + rowValues[i]);
                    if (fieldName.equals(keyWord)) {
                        mxJyDy = rowValues[i].toString().trim();
                    }
                }
//                newList.add(jsonObject);
//                if (resultMap.containsKey(mxJyDy)) {
////                    resultMap.get(mxJyDy).add(jsonObject);
//                } else {
//                    List<JSONObject> newList = new ArrayList<>();
////                    newList.add(jsonObject);
//                    resultMap.put(mxJyDy, newList);
//                }

//                map.clear();
                System.out.println("========== " + mxJyDy + " end ==========");
            }
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                LOG.error("流关闭失败", e);
            }
        }
        System.out.println(resultMap.size());
        long end = System.nanoTime();
        System.out.println("耗时:" + (end - begin) / 1000000000 + "秒");
        return resultMap;
    }
}
