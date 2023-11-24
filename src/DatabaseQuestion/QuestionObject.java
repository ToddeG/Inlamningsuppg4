package DatabaseQuestion;

import java.io.Serializable;

public class QuestionObject implements Serializable {

    private String category;
    private String question;
    private String[] optionList = new String[4];
    private String rightOption;

    QuestionObject(String[] fileInfo){
        category = fileInfo[0].trim();
        question = fileInfo[1].trim();
        optionList[0] = fileInfo[2].trim();
        optionList[1] = fileInfo[3].trim();
        optionList[2] = fileInfo[4].trim();
        optionList[3] = fileInfo[5].trim();
        rightOption = fileInfo[6].trim();
    }

    public String getCategory() {
        return category;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptionList(){
        return optionList;
    }
    public String getRightOption() {
        return rightOption;
    }
}
