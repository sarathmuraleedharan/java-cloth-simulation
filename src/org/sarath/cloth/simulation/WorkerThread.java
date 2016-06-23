package org.sarath.cloth.simulation;public class WorkerThread extends Thread {
	private RendererPage rendererPage;

	public WorkerThread(RendererPage rendererPage) {
		super();
		this.rendererPage = rendererPage;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
			rendererPage.repaint();
		}
	}

}
