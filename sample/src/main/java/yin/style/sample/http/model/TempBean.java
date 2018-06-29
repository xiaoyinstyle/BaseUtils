package yin.style.sample.http.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class TempBean<T> {
    private Class<T> entityclass;

    public TempBean() {
        try {
            Class<?> clazz = getClass(); //获取实际运行的类的 Class
            Type type = clazz.getGenericSuperclass(); //获取实际运行的类的直接超类的泛型类型
            if (type instanceof ParameterizedType) { //如果该泛型类型是参数化类型
                Type[] parameterizedType = ((ParameterizedType) type).getActualTypeArguments();//获取泛型类型的实际类型参数集
//                entityClass = (Class<T>) parameterizedType[0]; //取出第一个(下标为0)参数的值
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Type为： " + entityclass.getSimpleName());
    }

    /**
     * result : -5
     * message : 密签不能为空
     * data : []
     * page : {}
     */

    private int result;
    private T message;
    private PageBean page;
    private List<?> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public static class PageBean {
    }
}
