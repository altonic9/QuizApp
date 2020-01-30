import java.util.ArrayList;

public class Topic {

    public String name;
    public ArrayList<Question> questions = new ArrayList<Question>();

    public Topic(String name)
    {
        this.name = name;
    }

    public void addQuestion(Question q)
    {
        questions.add(q);
    }
}
