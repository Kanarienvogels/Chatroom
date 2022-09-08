package pers.kanarien.chatroom.service;

import javax.servlet.http.HttpSession;

import pers.kanarien.chatroom.model.vo.ResponseJson;

public interface SecurityService {

    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    ResponseJson login(String username, String password, HttpSession session);

    /**
     * 用户退出登录
     * @param session
     * @return
     */
    ResponseJson logout(HttpSession session);
}
