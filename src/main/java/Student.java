//Класс Студент для выполнения над ним сериализации

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String name;
    private int score;

    public Student(int id, String name, int score){
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public void info(){
        System.out.println("ID #" + id + " Студент " + name + ", студенческие очки: " + score);
    }

    //сеттер для внесения студ.очков
    public void setScore(int score){
        this.score = score;
    }
}
