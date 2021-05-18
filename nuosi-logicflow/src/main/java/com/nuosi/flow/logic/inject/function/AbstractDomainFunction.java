package com.nuosi.flow.logic.inject.function;

import com.ai.ipu.basic.reflect.ReflectUtil;
import com.nuosi.flow.logic.model.action.Function;
import com.nuosi.flow.logic.model.action.Param;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>desc: 业务域的函数功能抽象类 </p>
 * <p>date: 2021/5/17 23:28 </p>
 *
 * @author nuosi fsofs@163.com
 * @version v1.0.0
 */
public abstract class AbstractDomainFunction implements IDomainFunction{
    private static final Map<String, Class<?>> typeClass = new HashMap();

    static {
        typeClass.put("string", String.class);
        typeClass.put("map", Map.class);
        typeClass.put("int", Integer.class);
        typeClass.put("list", List.class);
    }

    private Map<String, Method> methodMap = new ConcurrentHashMap<String, Method>();

    public Object invoke(Map<String, Object> databus, Function function) throws Exception {
        List<Param> params = function.getParams();
        int size = params.size();
        Object[] args = new Object[size];  //入参数据
        Class[] argsClass = new Class[size];    //入参类型
        int index = 0;
        for(Param param : params){
            args[index] = param.getValue()!=null?param.getValue():databus.get(param.getKey());
            if(args[index]!=null){
                if(!args[index].getClass().equals(typeClass.get(param.getType()))){
                    //判断args[index]类型。
                    // "函数调用异常：[%s]入参类型不一致错误，[%s]和[%s]不一致"。
                    // param.getKey(),args[index].getClass(),typeClass.get(param.getType())。
                }
            }
            argsClass[index] = typeClass.get(param.getType());
            index++;
        }

        Method method = getMethod(function.getName(), argsClass);
        return method.invoke(this, args);
    }

    private Method getMethod(String methodName, Class ... cls){
        String key = methodName + Arrays.hashCode(cls);
        Method method = methodMap.get(key);
        if(method==null){
            synchronized (methodName){
                if(method==null){
                    method = ReflectUtil.getMethod(this.getClass(), methodName, cls);
                    methodMap.put(key, method);
                }
            }
        }
        return method;
    }
}
