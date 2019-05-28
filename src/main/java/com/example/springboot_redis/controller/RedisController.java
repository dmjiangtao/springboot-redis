package com.example.springboot_redis.controller;

import com.example.springboot_redis.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author JiangTao
 * @ClassName RedisController
 * @Description
 * @date
 * @Version 1.0
 */
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    RedisUtil redisUtil;

    /**
     * @auther: zhangyingqi
     * @date: 16:23 2018/8/29
     * @param: []
     * @return: org.springframework.ui.ModelMap
     * @Description: 执行redis写/读/生命周期
     */
    @RequestMapping(value = "getRedis",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getRedis(){

        redisUtil.set("VCode","这是一条测试数据",60);
        String res = (String)redisUtil.get("VCode").toString();
        return getModelMap(200, res, "执行成功");
    }

    @RequestMapping(value = "getVCode",method = RequestMethod.POST)
    @ResponseBody
    public ModelMap getVCode(){
        String res = (String)redisUtil.get("VCode");
        return getModelMap(200, res, "执行成功");
    }

    public ModelMap getModelMap(Integer code,Object res,String message ){
        ModelMap modelMap = new ModelMap();
        modelMap.put("code",code);
        modelMap.put("data",res);
        modelMap.put("msg",message);
        return modelMap;
    }


    public static void main(String[] args){

        Jedis jedis = new Jedis("192.168.2.103",6379);
        jedis.select(3);
        System.out.println("Connection to server sucessfully");
        //check whether server is running or not
        System.out.println("Server is running: "+jedis.ping());
        jedis.lpush("forezp-test", "Redis");
        jedis.lpush("forezp-test", "Mongodb");
        jedis.lpush("forezp-test", "Mysql");
        // Get the stored data and print it
        List<String> list = jedis.lrange("forezp-test", 0 ,5);
        for(int i=0; i<list.size(); i++) {
            System.out.println("Stored string in redis:: "+list.get(i));
        }

    }


}
