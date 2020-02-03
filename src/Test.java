import java.io.File;

public class Test {

    public static void main(String[] args) {
        File f = new File("Resources/länder.top");
        Topic t = Topic.loadFromFile(f);

        for (Question q : t.getAllQuestions()) {
            System.out.println(q.getText());
        }
    }
}
