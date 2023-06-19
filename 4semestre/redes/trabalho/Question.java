package trabalho;

import java.io.Serializable;

public class Question implements Serializable{

    private String Question;
    private String Answer;
    

    public Question( String question, String answer) {
        setQuestion(question); 
        setAnswer(answer);
        
    }

    public String getQuestion() {
        return Question;
    }
    public void setQuestion(String question) {
        Question = question;
    }
    public String getAnswer() {
        if(Answer == null){
            return "NOT YET ANSWERED";
        }
        return Answer;
    }
    public void setAnswer(String answer) {
        Answer = answer;
    }
    

    
}
