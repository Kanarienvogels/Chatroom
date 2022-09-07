package pers.kanarien.chatroom.service;

import pers.kanarien.chatroom.model.vo.ResponseJson;

public interface UserInfoService {

    ResponseJson getByUserId(String userId);
}
