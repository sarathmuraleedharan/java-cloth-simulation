package org.sarath.cloth.simulation;


import java.util.ArrayList;

public class Cloth {
	public ArrayList<Point> points;
	private RendererPage rendererPage;

	public Cloth(RendererPage rendererPage) {
		points = new ArrayList<Point>();
		this.rendererPage = rendererPage;
		float start_x = 560 / 2 - rendererPage.cloth_width * rendererPage.spacing / 2;
		for (float y = 0; y <= rendererPage.cloth_height; y++) {
			for (float x = 0; x <= rendererPage.cloth_width; x++) {
				Point p = new Point(start_x + x * rendererPage.spacing, rendererPage.start_y + y * rendererPage.spacing,
						rendererPage);
				if (x != 0)
					p.attach(this.points.get(this.points.size() - 1));
				if (y == 0)
					p.pin(p.getX(), p.getY());
				if (y != 0)
					p.attach(this.points.get((int) (x + (y - 1) * (rendererPage.cloth_width + 1))));

				this.points.add(p);
			}
		}
	}

	public void update() {
		int i = rendererPage.physics_accuracy;

		while (0 != i--) {
			int p = this.points.size();
			while (0 != p--)
				this.points.get(p).resolve_constraints();
		}

		i = this.points.size();
		while (0 != i--)
			this.points.get(i).update(.026f);
	}

	public void draw() {
		rendererPage.path2d.reset();

		int i = rendererPage.cloth.points.size();
		while (0 != i--)
			rendererPage.cloth.points.get(i).draw();

	}
}
