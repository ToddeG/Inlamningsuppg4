public class QuestionObject {

    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    String rightOption;

    QuestionObject(String[] fileInfo){
        question = fileInfo[0];
        option1 = fileInfo[1];
        option2 = fileInfo[2];
        option3 = fileInfo[3];
        option4 = fileInfo[4];
        rightOption = fileInfo[5];
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getRightOption() {
        return rightOption;
    }
}
