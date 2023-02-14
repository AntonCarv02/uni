package exame2022;

public class WordFreq {
int count;
String word;

    public WordFreq(int i, String string) {
        setCount(i);
        setWord(string);
}

    public int getCount() {
    return count;
}

public void setCount(int count) {
    this.count = count;
}

public String getWord() {
    return word;
}

public void setWord(String word) {
    this.word = word;
}
@Override
public String toString(){
    return "WordFreq [count="+count+" word = "+word+"]";
}

    public static void sortWords(WordFreq[] wf){

        String auxStr= new String();
        int auxCnt=0;
        for (int i = 0; i < wf.length-1; i++) {
            if(wf[i].getCount() < wf[i+1].getCount()){
                auxCnt = wf[i].getCount();
                wf[i].setCount(wf[i+1].getCount());

                auxStr  = wf[i].getWord();
                wf[i].setWord(wf[i+1].getWord());
                
                wf[i+1].setCount(auxCnt);
                wf[i+1].setWord(auxStr);
                
            }
        }
    }
    public static void main(String[] args) {
        WordFreq[] wf= 
        {new WordFreq(12,"aaa"),new WordFreq(13,"aaa")};
        for (WordFreq wordFreq : wf) {
            System.out.println(wordFreq.toString());
         
        }
        WordFreq.sortWords(wf);
        for (WordFreq wordFreq : wf) {
            System.out.println(wordFreq.toString());
         
        }
    }
}


