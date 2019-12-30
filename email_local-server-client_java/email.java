
public class email {

	private int pk;
	private String to, from, subject, body, location;
	
	private Object timeStamp;

	public email(int pk, String to, String from, String subject, String body, String location, Object timeStamp) {
		super();
		this.setPk(pk);
		this.setTo(to);
		this.setFrom(from);
		this.setSubject(subject);
		this.setBody(body);
		this.setLocation(location);
		this.setTimeStamp(timeStamp);
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Object getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Object timeStamp) {
		this.timeStamp = timeStamp;
	}
}
