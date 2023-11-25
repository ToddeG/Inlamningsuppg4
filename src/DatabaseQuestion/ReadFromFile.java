package DatabaseQuestion;

import POJOs.QuestionObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private int questionsPerRound;

    private ArrayList<String> categoryList = new ArrayList<>();

    public ReadFromFile(String path, int questionsPerRound){
        categoryList.add("Musik");
        categoryList.add("Historiens vingslag");
        categoryList.add("Läskedrycker");
        categoryList.add("Klassiska profiler");
        categoryList.add("Julquiz");
        categoryList.add("Blandat");
        categoryList.add("Ge nummer");
        categoryList.add("Städer");
        categoryList.add("Djur");
        this.questionsPerRound = questionsPerRound;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))){
            String fileInput;
            while((fileInput = reader.readLine()) != null){
                String[] question = fileInput.split("\\|");
                QuestionObject temp = new QuestionObject(question);
                switch (temp.getCategory()) {
                    case "Musik" -> musicArrayList.add(temp);
                    case "Historiens vingslag" -> historyArrayList.add(temp);
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

    public ArrayList<QuestionObject> getQuestionCategoryArrayList(String category) {
        List<QuestionObject> tempList = null;
        ArrayList<QuestionObject> tempList2 = new ArrayList<>();

        switch (category) {
            case "Musik":
                Collections.shuffle(musicArrayList);
                tempList = musicArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Musik");
                return tempList2;
            case "Historiens vingslag":
                Collections.shuffle(historyArrayList);
                tempList = historyArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Historiens vingslag");
                return tempList2;
            case "Läskedrycker":
                Collections.shuffle(drinksArrayList);
                tempList = drinksArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Läskedrycker");
                return tempList2;
            case "Klassiska profiler":
                Collections.shuffle(classicProfilesArrayList);
                tempList = classicProfilesArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Klassiska profiler");
                return tempList2;
            case "Julquiz":
                Collections.shuffle(xmasArrayList);
                tempList = xmasArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Julquiz");
                return tempList2;
            case "Blandat":
                Collections.shuffle(randomArrayList);
                tempList = randomArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Blandat");
                return tempList2;
            case "Ge nummer":
                Collections.shuffle(numbersArrayList);
                tempList = numbersArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Ge nummer");
                return tempList2;
            case "Städer":
                Collections.shuffle(cityArrayList);
                tempList = cityArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Städer");
                return tempList2;
            case "Djur":
                Collections.shuffle(animalArrayList);
                tempList = animalArrayList.subList(0,questionsPerRound);
                tempList2.addAll(tempList);
                categoryList.remove("Djur");
                return tempList2;
        }
        return null;
    }

    public ArrayList<String> getCategoryArrayList() {
        Collections.shuffle(categoryList);
        List<String> tempList = categoryList.subList(0,3);
        ArrayList<String> tempList2 = new ArrayList<>();

        tempList2.addAll(tempList);
        return tempList2;
    }
}
