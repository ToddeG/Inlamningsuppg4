package DatabaseQuestion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ReadFromFile implements Serializable {

    private ArrayList<QuestionObject> musicArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> historyArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> drinksArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> classicProfilesArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> xmasArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> randomArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> numbersArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> cityArrayList = new ArrayList<>();
    private ArrayList<QuestionObject> animalArrayList = new ArrayList<>();

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
                else if (temp.getCategory().equals("Läskedrycker")){
                    drinksArrayList.add(temp);
                }
                else if (temp.getCategory().equals("Klassiska profiler")){
                    classicProfilesArrayList.add(temp);
                }
                else if (temp.getCategory().equals("Julquiz")){
                    xmasArrayList.add(temp);
                }
                else if (temp.getCategory().equals("Blandat")){
                    randomArrayList.add(temp);
                }
                else if (temp.getCategory().equals("Ge nummer")){
                    numbersArrayList.add(temp);
                }
                else if (temp.getCategory().equals("Städer")){
                    cityArrayList.add(temp);
                }
                else if (temp.getCategory().equals("Djur")){
                    animalArrayList.add(temp);
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
        else if (category.equals("Läskedrycker")){
            return drinksArrayList;
        }
        else if (category.equals("Klassiska profiler")){
            return classicProfilesArrayList;
        }
        else if (category.equals("Julen")){
            return xmasArrayList;
        }
        else if (category.equals("Blandat")){
            return randomArrayList;
        }
        else if (category.equals("Ge nummer")){
            return numbersArrayList;
        }
        else if (category.equals("Städer")){
            return cityArrayList;
        }
        else if (category.equals("Djur")){
            return animalArrayList;
        }
        return null;
    }
}
