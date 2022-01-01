public class LearnCard {
    private final String question;
    private final String answer;

    public LearnCard(String q, String a){
        question = q;
        answer = a;
    }
    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }
}
