# 互联网实时聊天系统 (Spring + Netty + Websocket)

## 0. 前言
最近一段时间在学习Netty网络框架，又趁着计算机网络的课程设计，决定以Netty为核心，以WebSocket为应用层通信协议做一个互联网聊天系统，整体而言就像微信网页版一样，但考虑到这个聊天系统的功能非常多，因此只打算实现核心的聊天功能，包括单发、群发、文件发送，然后把项目与Spring整合做成开源、可拓展的方式，给大家参考、讨论、使用，欢迎大家的指点。

关于Netty
> Netty 是一个利用 Java 的高级网络的能力，隐藏其背后的复杂性而提供一个易于使用的 API 的客户端/服务器框架。

详细的可参考阅读该书的电子版
* [Essential Netty in Action 《Netty 实战(精髓)》](https://legacy.gitbook.com/book/waylau/essential-netty-in-action/details)

关于WebSocket通信协议
> WebSocket是为了解决HTTP协议中通信只能由客户端发起这个弊端而出现的，WebSocket基于HTTP5协议，借用HTTP进行握手、升级，能够做到轻量的、高效的、双向的在客户端和服务端之间传输文本数据。

## 1. 技术准备
* IDE：MyEclipse 2016
* JDK版本：1.8.0_121
* 浏览器：谷歌浏览器、360浏览器（极速模式）（涉及网页前端设计，后端开发表示很苦闷）
* 涉及技术：
  * Netty 4
  * WebSocket + HTTP
  * Spring MVC + Spring
  * JQuery
  * Bootstrap 3 + Bootstrap-fileinput
  * Maven 3.5
  * Tomcat 8.0

## 2. 整体说明
### 2.1 设计思想
整个通信系统**以Tomcat作为核心服务器运行，其下另开一个线程运行Netty WebSocket服务器**，Tomcat服务器主要处理客户登录、个人信息管理等的HTTP类型请求（通常的业务类型），端口为8080，Netty WebSockt服务器主要处理用户消息通信的WebSocket类型请求，端口为3333。用户通过浏览器登录后，浏览器会维持一个Session对象（有效时间30分钟）来保持登录状态，Tomcat服务器会返回用户的个人信息，同时记录在线用户，根据用户id建立一条WebSocket连接并保存在后端以便进行实时通信。当一个用户向另一用户发起通信，服务器会根据消息内容中的对话方用户id，找到保存的WebSocket连接，通过该连接发送消息，对方就能够收到即时收到消息。当用户注销或退出时，释放WebSocket连接，清空Session对象中的登录状态。

事实上Netty也可以用作一个HTTP服务器，而这里使用Spring MVC处理HTTP请求是出于熟悉的缘故，也比较接近传统开发的方式。

### 2.2 系统结构
系统采用B/S（Browser/Server），即浏览器/服务器的结构，主要事务逻辑在服务器端（Server）实现。借鉴MVC模式的思想，从上至下具体又分为视图层（View）、控制层（Controller）、业务层（Service）、模型层（Model）、数据访问层（Data Access）

### 2.3 项目结构
项目后端结构：
![项目结构（后端）](http://kanarien-1254133416.cosgz.myqcloud.com/Image%20Bed/%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84.JPG)

项目前端结构：
![项目结构（前端）](http://kanarien-1254133416.cosgz.myqcloud.com/Image%20Bed/%E9%A1%B9%E7%9B%AE%E7%BB%93%E6%9E%84%EF%BC%88%E5%89%8D%E7%AB%AF%EF%BC%89.JPG)

### 2.4 系统功能模块
系统只包括两个模块：登录模块和聊天管理模块。

* 登录模块：既然作为一个系统，那么登录的角色认证是必不可少的，这里使用简单、传统的Session方式维持登录状态，当然也有对应的注销功能，但这里的注销除了清空Session对象，还要释放WebSocket连接，否则造成内存泄露。
* 聊天管理模块：系统的核心模块，这部分主要使用Netty框架实现，功能包括信息、文件的单条和多条发送，也支持表情发送。
* 其他模块：如好友管理模块、聊天记录管理、注册模块等，我并没有实现，有兴趣的话可以自行实现，与传统的开发方式类似。


由于本系统涉及多个用户状态，有必要进行说明，下面给出本系统的用户状态转换图。
![状态转换图](https://kanarien-1254133416.cos.ap-guangzhou.myqcloud.com/Image%20Bed/%E8%81%8A%E5%A4%A9%E5%AE%A4%20-%20%E7%94%A8%E6%88%B7%E7%8A%B6%E6%80%81%E8%BD%AC%E6%8D%A2%E5%9B%BE.png)

### 2.6 系统界面
系统聊天界面如下：
![聊天界面](http://kanarien-1254133416.cosgz.myqcloud.com/Image%20Bed/%E8%81%8A%E5%A4%A9%E7%95%8C%E9%9D%A2.png)

![聊天界面](http://kanarien-1254133416.cosgz.myqcloud.com/Image%20Bed/%E8%81%8A%E5%A4%A9%E7%95%8C%E9%9D%A202.png)

## 3. 核心编码
这里只说明需要注意的地方，详细的请看源码

### 3.1 Netty服务器启动与关闭
**当关闭Tomcat服务器时，也要释放Netty相关资源，否则会造成内存泄漏**，关闭方法如下面的``close()``，如果只是使用``shutdownGracefully()``方法的话，关闭时会报内存泄露Memory Leak异常（但IDE可能来不及输出到控制台）
```Java
/**
 * 描述: Netty WebSocket服务器
 *      使用独立的线程启动
 * @author Kanarien
 * @version 1.0
 * @date 2018年5月18日 上午11:22:51
 */
public class WebSocketServer implements Runnable{

        /**
	 * 描述：启动Netty Websocket服务器
	 */
	public void build() {
	    // 略，详细请看源码
	}
     
      /**
	 * 描述：关闭Netty Websocket服务器，主要是释放连接
	 *     连接包括：服务器连接serverChannel，
	 *     客户端TCP处理连接bossGroup，
	 *     客户端I/O操作连接workerGroup
	 *
	 *     若只使用
	 *         bossGroupFuture = bossGroup.shutdownGracefully();
	 *         workerGroupFuture = workerGroup.shutdownGracefully();
	 *     会造成内存泄漏。
	 */
	public void close(){
	    serverChannelFuture.channel().close();
		Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();

        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
        }
	}

}
```

## 4. 效果及操作演示
### 4.1 登录操作
登录入口为：http://localhost:8080/WebSocket/login 或 http://localhost:8080/WebSocket/
当前系统用户固定为9个，群组1个，包括9人用户。
* 用户1  用户名：Member001  密码：001
* 用户2  用户名：Member002  密码：002
* ······
* 用户9  用户名：Member009  密码：009

![登录入口](http://kanarien-1254133416.cosgz.myqcloud.com/Image%20Bed/%E8%81%8A%E5%A4%A9%E5%AE%A4%E7%99%BB%E5%BD%95url.png)

### 4.2 聊天演示
![聊天演示](http://kanarien-1254133416.cosgz.myqcloud.com/Image%20Bed/%E8%81%8A%E5%A4%A9%E6%BC%94%E7%A4%BA.gif)


---

> Copyright © 2018, GDUT CSCW back-end Kanarien, All Rights Reserved
