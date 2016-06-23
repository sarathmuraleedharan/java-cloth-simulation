package org.sarath.cloth.simulation;import java.util.ArrayList;

public class Point {
	private float x;
	private float y;
	private float px;
	private float py;

	private float vx;
	private float vy;
	private Float pin_x;
	private Float pin_y;
	private ArrayList<Constraint> constraints;
	private Mouse mouse;
	private RendererPage rendererPage;

	public Point(float x, float y, RendererPage rendererPage) {
		this.x = x;
		this.y = y;
		this.px = x;
		this.py = y;
		this.vx = 0;
		this.vy = 0;
		this.pin_x = null;
		this.pin_y = null;
		this.constraints = new ArrayList<Constraint>();
		this.mouse = rendererPage.getMouse();
		this.rendererPage = rendererPage;
	}

	public void update(float delta) {
		if (rendererPage.mouse.isDown()) {

			float diff_x = this.x - mouse.getX();
			float diff_y = this.y - mouse.getY();
			float dist = (float) Math.sqrt(diff_x * diff_x + diff_y * diff_y);

			if (mouse.getButton() == 1) {
				if (dist < rendererPage.mouse_influence) {
					this.px = this.x - (float) ((mouse.getX() - mouse.getPx()) * 1.0f);
					this.py = this.y - (float) ((mouse.getY() - mouse.getPy()) * 1.0f);
				}

			} else if (dist < rendererPage.mouse_cut)
				this.constraints = new ArrayList<Constraint>();
		}

		this.add_force(0, rendererPage.gravity);

		delta *= delta;
		float nx = this.x + ((this.x - this.px) * .99f) + ((this.vx / 2) * delta);
		float ny = this.y + ((this.y - this.py) * .99f) + ((this.vy / 2) * delta);

		this.px = this.x;
		this.py = this.y;

		this.x = (float) nx;
		this.y = (float) ny;

		this.vy = this.vx = 0;
	}

	public void draw() {
		if (this.constraints.size() == 0)
			return;

		int i = this.constraints.size();
		while (0 != i--)
			this.constraints.get(i).draw();
	}

	public void resolve_constraints() {
		if (this.pin_x != null && this.pin_y != null) {
			this.x = this.pin_x;
			this.y = this.pin_y;
			return;
		}

		int i = this.constraints.size();
		while (0 != i--)
			this.constraints.get(i).resolve();

		if (this.x > rendererPage.boundsx) {
			this.x = 2 * rendererPage.boundsx - this.x;
		} else {
			if (1 > this.x) {
				this.x = 2 - this.x;
			}
		}
		if (this.y < 1) {
			this.y = 2 - this.y;
		} else {
			if (this.y > rendererPage.boundsy) {
				this.y = 2 * rendererPage.boundsy - this.y;
			}
		}
	}

	public boolean attach(Point point) {
		return this.constraints.add(new Constraint(this, point, rendererPage));
	}

	public void remove_constraint(Constraint constraint) {
		this.constraints.remove(this.constraints.indexOf(constraint));
	}

	public void add_force(float x, float y) {
		this.vx += x;
		this.vy += y;
	}

	public boolean pin(float pinx, float piny) {
		this.pin_x = pinx;
		this.pin_y = piny;
		return true;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getPx() {
		return px;
	}

	public void setPx(float px) {
		this.px = px;
	}

	public float getPy() {
		return py;
	}

	public void setPy(float py) {
		this.py = py;
	}

	public float getVx() {
		return vx;
	}

	public void setVx(float vx) {
		this.vx = vx;
	}

	public float getVy() {
		return vy;
	}

	public void setVy(float vy) {
		this.vy = vy;
	}

	public Float getPin_x() {
		return pin_x;
	}

	public void setPin_x(Float pin_x) {
		this.pin_x = pin_x;
	}

	public Float getPin_y() {
		return pin_y;
	}

	public void setPin_y(Float pin_y) {
		this.pin_y = pin_y;
	}

	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

}
