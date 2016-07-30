public class Cell {

	// holds the value and status of a specific cell.
	private Integer value;
	private Boolean revealed;
	private Boolean question;

	public Cell(int value) {
		this.value = value;
		this.revealed = false;
		this.question = false;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Boolean getRevealed() {
		return revealed;
	}

	public void setRevealed(Boolean revealed) {
		this.revealed = revealed;
	}

	public Boolean getQuestion() {
		return question;
	}

	public void setQuestion(Boolean question) {
		this.question = question;
	}

	public String toString() {
		if (question) {
			return "?";
		}
		return revealed ? Integer.toString(this.value) : "X";
	}
}
