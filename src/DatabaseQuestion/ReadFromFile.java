package DatabaseQuestion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ReadFromFile implements Serializable {

    private ArrayList<QuestionObject> musicArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> historyArrayList = new ArrayList<>();

    public ReadFromFile(String path){
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String fileInput;
            while((fileInput = reader.readLine()) != null){
                String[] question = fileInput.split("\\|");
                QuestionObject temp = new QuestionObject(question);
                if (temp.getCategory().equals("Musik")){
                    musicArrayList.add(temp);
                }
                else if (temp.getCategory().equals("Historierns vingslag")){
                    historyArrayList.add(temp);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<QuestionObject> getQuestionCategoryArrayList(String category){
        if (category.equals("Musik")){
            return musicArrayList;
        }
        else if (category.equals("Historia")){
            return historyArrayList;
        }
        return null;
    }
}
