package otherDirectory;

import applications.SocialNetworkCircle.Friends;
import applications.SocialNetworkCircle.centralUser;

/**
 *  物体的关系类
 *  关系为单向边，包含权值
 */
public class Relation<L,E> {
    L obj1;  //关系边的起始点物体
    E obj2;  //关系边的终结点物体
    double weight; //类的权值
    public Relation(L obj1, E obj2,double weight) {
        this.obj1 = obj1;
        this.obj2 = obj2;
        this.weight = weight;
    }

    // 创建中心用户和一级好友之间的关系（由于后续应用内方法设计需要，创建了该静态方法）
    public static Relation<centralUser, Friends> create_Relation1(centralUser obj1, Friends obj2, double weight)
    {
        return new Relation<>(obj1,obj2,weight);
    }

    // 创建好友与好友之间的关系（由于后续应用内方法设计需要，创建了该静态方法）
    public static Relation<Friends, Friends> create_Relation2(Friends obj1, Friends obj2, double weight)
    {
        return new Relation<>(obj1,obj2,weight);
    }
    /**
     *  获取关系边的起始点物体
     * @return 关系边的起始点物体
     */
    public L getobj1() {
        return obj1;
    }

    /**
     *  获取关系边的终结点物体
     * @return 关系边的终结点物体
     */
    public E getobj2() {
        return obj2;
    }

    /**
     *  获取关系边的权值
     * @return 关系边的权值
     */
    public double getWeight() {
        return weight;
    }

    /**
     *  比较两个关系边是否为同一个
     * @param obj 比较的物体
     * @return 相同返回true，否则返回false
     */
    @Override
    public boolean equals(Object obj) {
        Relation<L,E> that = (Relation<L,E>) obj;
        return this.obj1.equals(that.obj1) && this.obj2.equals(that.obj2) && this.weight==that.weight;
    }

    /**
     *  重写hashcode
     * @return 新的hashcode值
     */
    @Override
    public int hashCode() {
        return this.obj1.hashCode()*31*31+this.obj2.hashCode()*31+(int)this.weight;
    }

}
