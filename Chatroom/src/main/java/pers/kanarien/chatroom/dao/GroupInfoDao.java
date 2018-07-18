package pers.kanarien.chatroom.dao;


import pers.kanarien.chatroom.model.po.GroupInfo;

public interface GroupInfoDao {

    void loadGroupInfo();
    
    GroupInfo getByGroupId(String groupId);
}
