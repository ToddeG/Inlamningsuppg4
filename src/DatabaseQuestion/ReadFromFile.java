package DatabaseQuestion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
            List<QuestionObject> shuffledlist = new ArrayList<>(musicArrayList);
            Collections.shuffle(shuffledlist);
            ArrayList<QuestionObject> randomlist = new ArrayList<>(shuffledlist.subList(0, 3));
            return randomlist;
        }
        else if (category.equals("Historia")){
            List<QuestionObject> shuffledlist = new ArrayList<>(historyArrayList);
            Collections.shuffle(shuffledlist);
            ArrayList<QuestionObject> randomlist = new ArrayList<>(shuffledlist.subList(0, 3));
            return randomlist;
        }
        return null;
    }
}
