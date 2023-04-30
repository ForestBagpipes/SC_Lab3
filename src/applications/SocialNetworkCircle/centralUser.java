package applications.SocialNetworkCircle;

import centralObject.ConcreteCObject;

/**
 *  社交网络的中心对象类
 */
public class centralUser extends ConcreteCObject {

    private String gender; //保存中心用户的性别
    private int age; //保存中心用户的年龄

    // TODO constructor
    public centralUser(String name) {
        super(name);
    }

    /**
     *  中心用户类的有参构造
     * @param name 中心用户的名字
     * @param gender 中心用户的性别
     * @param age 中心用户的年龄
     */
    public centralUser(String name,String gender,int age) {
        super(name);
        this.gender = gender;
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
