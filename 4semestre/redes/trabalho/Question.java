package trabalho;

import java.io.Serializable;

public class Question implements Serializable{

    private String Question;
    private String Answer;
    //private int ID;

    public Question( String question, String answer) {
        setQuestion(question); 
        setAnswer(answer);
        //setID(iD);
    }

    public String getQuestion() {
        return Question;
    }
    public void setQuestion(String question) {
        Question = question;
    }
    public String getAnswer() {
        return Answer;
    }
    public void setAnswer(String answer) {
        Answer = answer;
    }
    /*public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }*/

    
}
