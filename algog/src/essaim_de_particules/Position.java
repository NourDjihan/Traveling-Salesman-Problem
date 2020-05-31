package essaim_de_particules;

public class Position {
	public double x, y;

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Position(Position position) {
		this.x = position.x;
		this.y = position.y;
	}

	void setX(double x) {
		this.x = x;
	}

	void setY(double y) {
		this.y = y;
	}

	Position() {
	};
}
