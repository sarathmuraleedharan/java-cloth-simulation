package org.sarath.cloth.simulation;
public class Constraint {
	private Point p1;
	private Point p2;
	private float length;
	private RendererPage rendererPage;

	public Constraint(Point p1, Point p2, RendererPage rendererPage) {
		this.p1 = p1;
		this.p2 = p2;
		this.rendererPage = rendererPage;
		this.length = rendererPage.spacing;
	}

	public void resolve() {
		float diff_x = this.p1.getX() - this.p2.getX();
		float diff_y = this.p1.getY() - this.p2.getY();
		float dist = (float) Math.sqrt(diff_x * diff_x + diff_y * diff_y);
		float diff = (this.length - dist) / dist;

		if (dist > rendererPage.tear_distance)
			this.p1.remove_constraint(this);

		float px = (float) (diff_x * diff * 0.5f);
		float py = (float) (diff_y * diff * 0.5f);

		this.p1.setX(this.p1.getX() + px);
		this.p1.setY(this.p1.getY() + py);
		this.p2.setX(this.p2.getX() - px);
		this.p2.setY(this.p2.getY() - py);
	}

	public void draw() {
		rendererPage.path2d.moveTo(this.p1.getX(), this.p1.getY());
		rendererPage.path2d.lineTo(this.p2.getX(), this.p2.getY());
	}
}