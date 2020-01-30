public class Question {

    public String type;
    public String text;

    public String[] answers;
    public int crrAnswer;

    //default constructor
    public Question()
    {
        this.type = "undefined";
        this.text = "undefined";
        this.answers = null;
        this.crrAnswer = -1;

    }

    //constructor
    public Question(String type, String text, String[] answers, int crrAnswer)
    {
        this.type = type;
        this.text = text;
        this.answers = answers;
        this.crrAnswer = crrAnswer;

    }

}
