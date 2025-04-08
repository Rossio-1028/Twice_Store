package com.shanzhu.st.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shanzhu.st.entity.Order;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/*
 * 封装一个订单任务并提供延迟执行的能力
 */

//定义OrderTask类，实现Delayed接口，用于在延迟队列中表示一个订单任务
public class OrderTask implements Delayed {
    //使用JsonFormat注解指定时间格式，用于JSON序列化显示时间
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    //任务的执行时间，以毫秒为单位
    private long time;
    //关联的订单对象
    private Order order;
    //无参构造函数
    public OrderTask(){

    }

    //有参构造函数，接收一个订单对象和延迟对象（以秒为单位）
    public OrderTask(Order order, long time) {
        //初始化订单对象
        this.order = order;
        //计算任务的执行时间，当前时间加上延迟时间（转换为毫秒）
        this.time = System.currentTimeMillis()+1000*time;
    }

    //实现Delayed接口的getDelay方法，用于获取任务的剩余延迟时间
    @Override
    public long getDelay(TimeUnit unit) {
        //计算当前时间到任务执行时间的剩余时间
        long diff=time-System.currentTimeMillis();
        //将剩余时间转换为指定的时间单位
        return  unit.convert(diff,TimeUnit.MILLISECONDS);
//        return time - System.currentTimeMillis();
    }

    //实现Delayed接口的compareTo方法，用于比较两个任务的执行时间
    @Override
    public int compareTo(Delayed o) {
        //将Delayed对象转换为OrderTask
        OrderTask Order = (OrderTask) o;
        //计算两个任务执行时间的差值
        long diff = this.time - Order.time;
        //如果当前任务的执行时间早于另一个任务，返回-1
        if (diff <= 0) {
            return -1;
        } else {
            //否则返回1
            return 1;
        }
    }

    //获取任务的执行时间
    public long getTime() {
        return time;
    }

    //设置任务的执行时间
    public void setTime(long time) {
        this.time = time;
    }

    //获取关联的订单对象
    public Order getOrderModel() {
        return order;
    }

    //设置关联的订单对象
    public void setOrderModel(Order order) {
        this.order = order;
    }

    //重写toString方法，用于返回对象的字符串表现形式
    @Override
    public String toString() {
        //使用StringBuilder 构建字符串
        final StringBuilder sb = new StringBuilder("{");
        //添加时间信息
        sb.append("\"time\":")
                .append(time);
        //添加订单对象信息
        sb.append(",\"orderModel\":")
                .append(order);
        sb.append('}');
        return sb.toString();
    }
}