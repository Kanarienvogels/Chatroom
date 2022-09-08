package pers.kanarien.chatroom.dao;


import pers.kanarien.chatroom.model.po.GroupInfo;

public interface GroupInfoDao {

    void loadGroupInfo();

    /**
     * 通过 groupId 得到组信息
     * @param groupId
     * @return
     */
    GroupInfo getByGroupId(String groupId);
}
