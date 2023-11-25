package DatabaseQuestion;

import POJOs.QuestionObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ReadFromFile implements Serializable {

    private final ArrayList<QuestionObject> musicArrayList = new ArrayList<>();
    private final ArrayList<QuestionObject> historyArrayList = new ArrayList<>();
    private final ArrayList<QuestionObject> drinksArrayList = new ArrayList<>();
    private final ArrayList<QuestionObject> classicProfilesArrayList = new ArrayList<>();
    private final ArrayList<QuestionObject> xmasArrayList = new ArrayList<>();
    private final ArrayList<QuestionObject> randomArrayList = new ArrayList<>();
    private final ArrayList<QuestionObject> numbersArrayList = new ArrayList<>();
    private final ArrayList<QuestionObject> cityArrayList = new ArrayList<>();
    private final ArrayList<QuestionObject> animalArrayList = new ArrayList<>();

    public ReadFromFile(String path){
        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String fileInput;
            while((fileInput = reader.readLine()) != null){
                String[] question = fileInput.split("\\|");
                QuestionObject temp = new QuestionObject(question);
                switch (temp.getCategory()) {
                    case "Musik" -> musicArrayList.add(temp);
                    case "Historierns vingslag" -> historyArrayList.add(temp);
                    case "Läskedrycker" -> drinksArrayList.add(temp);
                    case "Klassiska profiler" -> classicProfilesArrayList.add(temp);
                    case "Julquiz" -> xmasArrayList.add(temp);
                    case "Blandat" -> randomArrayList.add(temp);
                    case "Ge nummer" -> numbersArrayList.add(temp);
                    case "Städer" -> cityArrayList.add(temp);
                    case "Djur" -> animalArrayList.add(temp);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<QuestionObject> getQuestionCategoryArrayList(String category){
        return switch (category) {
            case "Musik" -> musicArrayList;
            case "Historia" -> historyArrayList;
            case "Läskedrycker" -> drinksArrayList;
            case "Klassiska profiler" -> classicProfilesArrayList;
            case "Julen" -> xmasArrayList;
            case "Blandat" -> randomArrayList;
            case "Ge nummer" -> numbersArrayList;
            case "Städer" -> cityArrayList;
            case "Djur" -> animalArrayList;
            default -> null;
        };
    }
}
