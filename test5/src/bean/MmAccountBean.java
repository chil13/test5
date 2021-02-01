package bean;

public class MmAccountBean {

	private int u_id;
	private String name;
	private String pass;

	public int getU_id() {
		return u_id;
	}

	public void setU_id(int u_id) {
		this.u_id = u_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String pass() {
		return pass;
	}

	public void pass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "MmAccountBean [u_id=" + u_id + ", name=" + name + ", pass=" + pass + "]";
	}
}
