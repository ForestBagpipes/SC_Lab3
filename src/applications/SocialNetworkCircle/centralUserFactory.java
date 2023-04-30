package applications.SocialNetworkCircle;

import applications.SocialNetworkCircle.centralUser;

/**
 *  创建中心用户的工厂类
 */
public class centralUserFactory {
    public centralUser createCentralUser_2(String name,String gender,int age){
        return new centralUser(name,gender,age);
    }
    public centralUser createCentralUser_1(String name){
        return new centralUser(name);
    }
}
