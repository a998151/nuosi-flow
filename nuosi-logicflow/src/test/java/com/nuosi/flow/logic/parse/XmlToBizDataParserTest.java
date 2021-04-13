package com.nuosi.flow.logic.parse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nuosi.flow.logic.model.domain.DomainModel;
import com.nuosi.flow.logic.parse.backup.BizDataParser;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * <p>desc: 业务传输对象解析单元测试</p>
 * <p>date: 2021/4/7 16:50</p>
 * @author nuosi fsofs@163.com
 * @version v1.0.0
 */
public class XmlToBizDataParserTest {

    @Test
    public void testGetBeanJson(){
        try{
            String flowConfig = "model/goods_info.xml";
            InputStream is = getClass().getClassLoader().getResourceAsStream(flowConfig);
            XmlToBizDataParser parser = new XmlToBizDataParser(is);
            JSONObject beanJson = parser.getBeanJson();
            System.out.println("beanJson==="+beanJson);
            DomainModel domainModel = parser.getDomainModel();
            System.out.println("domainModel==="+JSON.toJSONString(domainModel));
            Assert.assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testDomainModelIsEquals(){
        try{
            String flowConfig = "model/goods_info.xml";
            InputStream is1 = getClass().getClassLoader().getResourceAsStream(flowConfig);
            XmlToBizDataParser parser = new XmlToBizDataParser(is1);
            DomainModel domainModel1 = parser.getDomainModel();
            System.out.println("domainModel1==="+JSON.toJSONString(domainModel1));

            InputStream is2 = getClass().getClassLoader().getResourceAsStream(flowConfig);
            DomainModel domainModel2 = new BizDataParser().parser(is2);
            System.out.println("domainModel2==="+JSON.toJSONString(domainModel2));

            Assert.assertEquals("两个DomainModel转换格式不一致",
                    JSON.toJSONString(domainModel1), JSON.toJSONString(domainModel2));
        }catch (Exception e){
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }
}
