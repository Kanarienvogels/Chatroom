package pers.kanarien.chatroom.service;

import pers.kanarien.chatroom.model.vo.ResponseJson;

public interface UserInfoService {

    /**
     * 通过用户 ID 获取用户信息
     * @param userId
     * @return
     */
    ResponseJson getByUserId(String userId);
}
