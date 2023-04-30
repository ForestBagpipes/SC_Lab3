package applications.SocialNetworkCircle;

/**
 *  创建朋友对象类的工厂
 */
public class friendsFactory {
    public Friends createFriends_2(String name,String gender,int age){
        return new Friends(name,gender,age);
    }
    public Friends createFriends_1(String name){
        return new Friends(name);
    }
}
