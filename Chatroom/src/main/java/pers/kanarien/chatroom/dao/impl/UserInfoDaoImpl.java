package pers.kanarien.chatroom.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import pers.kanarien.chatroom.dao.UserInfoDao;
import pers.kanarien.chatroom.model.po.GroupInfo;
import pers.kanarien.chatroom.model.po.UserInfo;
import pers.kanarien.chatroom.util.Constant;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {

    /**
     * 这里使用死数据，不使用数据库
     */
    @Override
    public void loadUserInfo() {
        // 设置用户基本信息，共9个用户
        UserInfo userInfo = new UserInfo("001", "Member001", "001", "static/img/avatar/Member001.jpg");
        UserInfo userInfo2 = new UserInfo("002", "Member002", "002", "static/img/avatar/Member002.jpg");
        UserInfo userInfo3 = new UserInfo("003", "Member003", "003", "static/img/avatar/Member003.jpg");
        UserInfo userInfo4 = new UserInfo("004", "Member004", "004", "static/img/avatar/Member004.jpg");
        UserInfo userInfo5 = new UserInfo("005", "Member005", "005", "static/img/avatar/Member005.jpg");
        UserInfo userInfo6 = new UserInfo("006", "Member006", "006", "static/img/avatar/Member006.jpg");
        UserInfo userInfo7 = new UserInfo("007", "Member007", "007", "static/img/avatar/Member007.jpg");
        UserInfo userInfo8 = new UserInfo("008", "Member008", "008", "static/img/avatar/Member008.jpg");
        UserInfo userInfo9 = new UserInfo("009", "Member009", "009", "static/img/avatar/Member009.jpg");
        
        // 设置用户好友列表
        userInfo.setFriendList(generateFriendList("001"));
        userInfo2.setFriendList(generateFriendList("002"));
        userInfo3.setFriendList(generateFriendList("003"));
        userInfo4.setFriendList(generateFriendList("004"));
        userInfo5.setFriendList(generateFriendList("005"));
        userInfo6.setFriendList(generateFriendList("006"));
        userInfo7.setFriendList(generateFriendList("007"));
        userInfo8.setFriendList(generateFriendList("008"));
        userInfo9.setFriendList(generateFriendList("009"));
        
        // 设置用户群列表，共1个群
        GroupInfo groupInfo = new GroupInfo("01", "Group01", "static/img/avatar/Group01.jpg", null);
        List<GroupInfo> groupList = new ArrayList<GroupInfo>();
        groupList.add(groupInfo);
        userInfo.setGroupList(groupList);
        userInfo2.setGroupList(groupList);
        userInfo3.setGroupList(groupList);
        userInfo4.setGroupList(groupList);
        userInfo5.setGroupList(groupList);
        userInfo6.setGroupList(groupList);
        userInfo7.setGroupList(groupList);
        userInfo8.setGroupList(groupList);
        userInfo9.setGroupList(groupList);
        
        Constant.userInfoMap.put("Member001", userInfo);
        Constant.userInfoMap.put("Member002", userInfo2);
        Constant.userInfoMap.put("Member003", userInfo3);
        Constant.userInfoMap.put("Member004", userInfo4);
        Constant.userInfoMap.put("Member005", userInfo5);
        Constant.userInfoMap.put("Member006", userInfo6);
        Constant.userInfoMap.put("Member007", userInfo7);
        Constant.userInfoMap.put("Member008", userInfo8);
        Constant.userInfoMap.put("Member009", userInfo9);
    }

    @Override
    public UserInfo getByUsername(String username) {
        return Constant.userInfoMap.get(username);
    }
    
    @Override
    public UserInfo getByUserId(String userId) {
        UserInfo result = null;
        Iterator<Entry<String, UserInfo>> iterator = Constant.userInfoMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Entry<String, UserInfo> entry = iterator.next();
            if (entry.getValue().getUserId().equals(userId)) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }
    
    private List<UserInfo> generateFriendList(String userId) {
        UserInfo userInfo = new UserInfo("001", "Member001", "001", "static/img/avatar/Member001.jpg");
        UserInfo userInfo2 = new UserInfo("002", "Member002", "002", "static/img/avatar/Member002.jpg");
        UserInfo userInfo3 = new UserInfo("003", "Member003", "003", "static/img/avatar/Member003.jpg");
        UserInfo userInfo4 = new UserInfo("004", "Member004", "004", "static/img/avatar/Member004.jpg");
        UserInfo userInfo5 = new UserInfo("005", "Member005", "005", "static/img/avatar/Member005.jpg");
        UserInfo userInfo6 = new UserInfo("006", "Member006", "006", "static/img/avatar/Member006.jpg");
        UserInfo userInfo7 = new UserInfo("007", "Member007", "007", "static/img/avatar/Member007.jpg");
        UserInfo userInfo8 = new UserInfo("008", "Member008", "008", "static/img/avatar/Member008.jpg");
        UserInfo userInfo9 = new UserInfo("009", "Member009", "009", "static/img/avatar/Member009.jpg");
        List<UserInfo> friendList = new ArrayList<UserInfo>();
        friendList.add(userInfo);
        friendList.add(userInfo2);
        friendList.add(userInfo3);
        friendList.add(userInfo4);
        friendList.add(userInfo5);
        friendList.add(userInfo6);
        friendList.add(userInfo7);
        friendList.add(userInfo8);
        friendList.add(userInfo9);
        Iterator<UserInfo> iterator = friendList.iterator();
        while(iterator.hasNext()) {
            UserInfo entry = iterator.next();
            if (userId.equals(entry.getUserId())) {
                iterator.remove();
            }
        }
        return friendList;
    }


}
