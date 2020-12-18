package com.example.hello.controller;

import com.example.hello.commons.R;
import com.example.hello.kafka.KafkaConsumer;
import com.example.hello.kafka.KafkaProducer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author zk
 * @date 2020/11/25 9:41
 */
@Api(value = "KafkaDemoController", tags = "kafka Demo 接口")
@RestController
@RequestMapping("kafkaDemoController")
public class KafkaDemoController {
    @Autowired
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaConsumer kafkaConsumer;

    @ApiOperation(value = "发送kafka信息")
    @RequestMapping(method = RequestMethod.POST, value = "/sendKafkaMsg")
    public R sendKafkaMsg(@RequestBody String msg) {
        kafkaProducer.sendKafkaMsg(msg);
        String message = kafkaConsumer.getMsg();
        return R.ok().put("result", message);
    }
}
