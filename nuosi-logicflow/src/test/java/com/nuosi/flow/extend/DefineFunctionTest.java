package com.nuosi.flow.extend;

import com.ai.ipu.data.JMap;
import com.ai.ipu.data.impl.JsonMap;
import com.nuosi.flow.logic.LogicFlowEngine;
import com.nuosi.flow.logic.inject.function.FunctionManager;
import com.nuosi.flow.util.LogicFlowUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * <p>desc: 自定义方法使用范例 </p>
 * <p>date: 2022/1/17 15:48 </p>
 *
 * @author nuosi fsofs@163.com
 * @version v1.0.0
 */
public class DefineFunctionTest {

    @Test
    public void testInvokeFunctionTest(){
        FunctionManager.registerDomainFunction("Define", new DefineFunction());
        JMap param = new JsonMap();
        param.put("user_id", 123456);
        try {
            LogicFlowEngine.execute("define_function", param);
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("校验信息：" + e.getMessage());
            Assert.assertTrue(false);
        }
    }

    @Before
    public void before() throws IOException {
        String[] flowConfigs = {
                "extend/function/define_function.xml"
        };
        LogicFlowUtil.loadLogicFlows(flowConfigs);
    }
}
