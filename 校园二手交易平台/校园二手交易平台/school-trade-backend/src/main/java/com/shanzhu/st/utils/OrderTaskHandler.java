package com.shanzhu.st.utils;

import com.shanzhu.st.service.OrderService;

import java.util.concurrent.DelayQueue;
/*
 *延时取消订单
 * 在一定时间内用户没有完成支付，就会自动取消订单
 */

//订单任务处理类，用于处理订单的延迟取消任务
public class OrderTaskHandler {

    //静态的OrderService对象，用于调用订单服务的方法
    public static OrderService orderService=null;

    //静态的DelayQueue，用于存储OrderTask类型的订单任务
    private static DelayQueue<OrderTask> delayQueue = new DelayQueue<>();

    //启用一个线程来处理订单任务
    public static void run(){
        //启用一个新的线程
        new Thread(() -> {
            //无限循环，持续检查队列中的任务
            while (true) {
                //当orderService !=null 且队列中有任务时
                if(orderService!=null&&delayQueue.size() >0){
                    //从队列中取出一个任务
                    OrderTask orderTask = delayQueue.poll();
                    //如果成功取出任务
                    if (orderTask != null) {
                        //调用orderService的updateOrder方法尝试取消订单
                        if(orderService.updateOrder(orderTask.getOrderModel())){
                            //取消成功，输出日志信息
                            System.out.println("成功取消订单："+orderTask.getOrderModel());
                        }else {
                            System.out.println("取消任务："+orderTask.getOrderModel());
                        }

                    }
                }
            }
        }).start();

    }

    //向延迟队列中添加一个订单任务
    public static void addOrder(OrderTask o){
        //输出添加任务的日志信息
        System.out.println("添加任务："+o);
        //将任务添加到延迟队列中
        delayQueue.put(o);
    }
}
