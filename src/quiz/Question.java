package quiz;

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

    @Override
    public String toString() {
        // needed for GUI Listviews, ComboBoxes etc...
        return this.text;
    }

    public void setText(String text) { this.text = text; }
    public void setType(String type) { this.type = type; }
    public void setAnswers(String[] answers) { this.answers = answers; }
    // overloaded:
    public void setCrrAnswer(int crrAnswer) { this.mcCrrAnswer = crrAnswer; }
    public void setCrrAnswer(String crrAnswer) { this.textCrrAnswer = crrAnswer; }

    public String getText() {return this.text;}
    public String getType() {return this.type;}
    public String[] getPossibleAnswers() {return this.answers;}
    public String getTextCrrAnswer() {return this.textCrrAnswer;}
    public int getMcCrrAnswer() {return this.mcCrrAnswer;}
    // overloaded:
    public Boolean isCrrAnswer(int answer) {
        // for multiple choice questions
        return answer == mcCrrAnswer;
    }
    public Boolean isCrrAnswer(String answer) {
        // for text questions

        if (Utility.isNumeric(answer)) {
            // if text answer is meant to be a number, do exact-compare
            return answer.equals(textCrrAnswer);
        }
        else {
            // if text answer is meant to be text, use levensthein distance to do a fuzzy-compare
            // levensthein method lowercases input strings
            int LD = Utility.levenstheinDistance(answer, textCrrAnswer);
            return LD < 3;
        }
    }
    public String getCrrAnswerString() {
        //returns correct answer in String type
        if (type.equals("txt")) {
            return getTextCrrAnswer();
        }
        else {
            return answers[mcCrrAnswer - 1];
        }
    }
    public String getId() {return this.id;}
    public void setId(String uuid) {
        this.id = uuid;
    }

}
