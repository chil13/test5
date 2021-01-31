package bean;

public class QuestionBean {


	private int q_id;
	private String question;
	private String intenstion;


	public int getQ_id() {
		return q_id;
	}

	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getIntention() {
		return intenstion;
	}

	public void setIntention(String intenstion) {
		this.intenstion = intenstion;
	}

	@Override
	public String toString() {
		return "QuestionBean [q_id=" + q_id + ", question=" + question + ", intenstion=" + intenstion + "]";
	}
}
