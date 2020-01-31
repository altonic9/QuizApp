public class Question {

    private String text;
    private String type;
    private String[] answers;
    private int crrAnswer;

    //default constructor
    public Question()
    {
        this.text = "undefined";
        this.type = "undefined";
        this.answers = null;
        this.crrAnswer = -1;
    }

    //constructor
    public Question(String text, String type, String[] answers, int crrAnswer)
    {
        this.type = type;
        this.text = text;
        this.answers = answers;
        this.crrAnswer = crrAnswer;
    }

    public void setText(String text) { this.text = text; }
    public void setType(String type) { this.type = type; }
    public void setAnswers(String[] answers) { this.answers = answers; }
    public void setCrrAnswer(int crrAnswer) { this.crrAnswer = crrAnswer; }
}
