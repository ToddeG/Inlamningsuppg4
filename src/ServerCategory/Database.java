package ServerCategory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Database {

    private final ArrayList<QuestionObject> questionObjectArrayList = new ArrayList<>();

    public Database(){}

    public ArrayList<QuestionObject> Category(String path){
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String fileInput;
            while((fileInput = reader.readLine()) != null){
                String[] question = fileInput.split("\\|");
                QuestionObject temp = new QuestionObject(question);
                questionObjectArrayList.add(temp);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return questionObjectArrayList;
    }

}
