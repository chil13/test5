package bean;

public class RetryBean {

	private int u_id;
	private int q_id;

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public int getQ_id() {
		return q_id;
	}

	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}

	@Override
	public String toString() {
		return "RetryBean [q_id=" + q_id + "]";
	}
}
