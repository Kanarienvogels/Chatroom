package pers.kanarien.chatroom.service;

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;

public interface ChatService {

    /**
     * 登记上线用户
     * @param param
     * @param ctx
     */
    public void register(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 向某个用户发送消息
     * @param param
     * @param ctx
     */
    public void singleSend(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 向某个群发送消息
     * @param param
     * @param ctx
     */
    public void groupSend(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 向某个用户发送文件
     * @param param
     * @param ctx
     */
    public void FileMsgSingleSend(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 群发文件
     * @param param
     * @param ctx
     */
    public void FileMsgGroupSend(JSONObject param, ChannelHandlerContext ctx);

    /**
     * 处理用户下线
     * @param ctx
     */
    public void remove(ChannelHandlerContext ctx);

    /**
     * 封装并发送“类型不存在”错误
     * @param ctx
     */
    public void typeError(ChannelHandlerContext ctx);
        
}
