package applications.TrackGame;

import physicalObject.ConcretePObject;
import physicalObject.PhysicalObject;

public class Runner extends ConcretePObject {
    private int id;
    private int age;
    private double bestScore;
    private String country;
    // Abstraction function:
    //   TODO  name：运动员的名字  id：运动员的编号
    //         age：运动员的年龄  bestScore：运动员的最好成绩
    //         country：运动员所属国家
    // Representation invariant:
    //   TODO  id，age，bestScore均不为0，country，name不为null
    // Safety from rep exposure:
    //   TODO 用private限定name，id，age，bestScore，country，防止表示泄露

    // TODO checkRep
    private void checkRep() {
        assert this.getName() != null;
        assert this.country != null;
        assert this.age != 0;
        assert this.id != 0;
        assert this.bestScore != 0;
    }

    public static Runner getInstance(String obName,int id,int age,double bestScore,String country) {
        return new Runner(obName, country,id,age,bestScore);
    }

    // TODO constructor
    public Runner(String name,String country,int id,int age ,double bestScore) {
        super(name);
        this.bestScore = bestScore;
        this.country = country;
        this.age = age;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public double getBestScore() {
        return bestScore;
    }

    public String getCountry() {
        return country;
    }

    /**
     *  比较两个运动员的最好成绩
     * @param that 另一个运动员
     * @return 返回1如果该运动成绩更好，返回0如果两个运动员的最好成绩一样，否则返回-1
     */
    public int compareToWithScoreACS(Runner that) {
        if(this.getBestScore()<that.getBestScore()) {
            return -1;
        } else if(this.getBestScore()==that.getBestScore()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     *  重写比较函数
     * @param obj 另一个要比较的运动员
     * @return true如果两个运动员相同，否则返回false
     */
    @Override
    public boolean equalsObject(Object obj) {
        //保证输入的物体为一个运动员
        if(!(obj instanceof Runner)){
            return false;
        }
        Runner that = (Runner) obj;
        return super.equalsObject(obj)
                && this.age == that.age
                && this.id == that.id
                && this.country.equals(that.country)
                && this.bestScore == that.bestScore;
    }
}
