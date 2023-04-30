package applications.SocialNetworkCircle;

import physicalObject.ConcretePObject;
import physicalObject.PhysicalObject;

/**
 *  保存社交网络的轨道上的朋友的对象类，继承自轨道物体具体对象类
 */
public class Friends extends ConcretePObject {
    private String gender; //保存朋友的性别
    private int age; //保存朋友的年龄

    //TODO constructor
    public Friends(String name) {
        super(name);
    }
    
    /**
     *  朋友对象类的有参构造
     * @param name 朋友对象的名字
     * @param gender 朋友对象的性别
     * @param age 朋友对象的年龄
     */
    public Friends(String name,String gender,int age) {
        super(name);
        this.gender = gender;
        this.age = age;
    }
    /**
     * 判断两个朋友对象是否值相等
     * @return 相等返回true，否则返回false
     */
    @Override
    public boolean equalsObject(Object obj) {
        Friends that = (Friends) obj;
        return this.getName().equals(that.getName())
                && this.getAge() == that.getAge()
                && this.getGender().equals(that.getGender());
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(PhysicalObject that) {
        return this.getName().compareTo(that.getName());
    }
}
