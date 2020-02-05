public class Question {

    private String text;
    private String type; // either "mc" or "txt"
    private String[] answers; // possible answers for Multiple Choice
    private int mcCrrAnswer; // correct answer for Multiple Choice
    private String textCrrAnswer; // correct answer for Text-Questions

    private String id;

    //default constructor
    public Question()
    {
        this.mcCrrAnswer = -1;
        this.id = Utility.generateUUID();
    }

    //constructor for Multiple Choice question
    public Question(String text, String type, String[] answers, int crrAnswer)
    {
        this.type = type;
        this.text = text;
        this.answers = answers;
        this.mcCrrAnswer = crrAnswer;

        this.id = Utility.generateUUID();
    }

    //constructor for text question
    public Question(String text, String type, String crrAnswer)
    {
        this.type = type;
        this.text = text;
        this.textCrrAnswer = crrAnswer;

        this.mcCrrAnswer = -1;
        this.id = Utility.generateUUID();
    }

    public void setText(String text) { this.text = text; }
    public void setType(String type) { this.type = type; }
    public void setAnswers(String[] answers) { this.answers = answers; }
    // overloaded:
    public void setCrrAnswer(int crrAnswer) { this.mcCrrAnswer = crrAnswer; }
    public void setCrrAnswer(String crrAnswer) { this.textCrrAnswer = crrAnswer; }

    public String getText() {return this.text;}
    public String getType() {return this.type;}
    public String[] getAnswers() {return this.answers;}
    // overloaded:
    public Boolean isCrrAnswer(int answer) {
        return answer == mcCrrAnswer;
    }
    public Boolean isCrrAnswer(String answer) {
        return answer.toLowerCase().equals(textCrrAnswer.toLowerCase());
    }
    public String getId() {return this.id;}
}
