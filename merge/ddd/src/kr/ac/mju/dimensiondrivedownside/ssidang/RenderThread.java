package kr.ac.mju.dimensiondrivedownside.ssidang;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class RenderThread extends Thread {
	public interface Renderable {
		void onRenderBegin();
		void onRender(Canvas canvas);
		void onRenderEnd();
	}
	
	private boolean running;
	
	private SurfaceHolder holder;
	private Renderable renderer;
	
	/**
	 * ������ SurfaceHolder�� ���� �׷��ִ� �����带 ����ϴ�.
	 * 
	 * @param holder		�׷��� SurfaceHolder
	 * @param renderer		�׷��� Renderer
	 */
	public RenderThread(SurfaceHolder holder, Renderable renderer) {
		this.holder = holder;
		this.renderer = renderer;
	}

	/**
	 * SurfaceHolder�� �����ݴϴ�.
	 */
	public SurfaceHolder getHolder() {
		return holder;
	}

	/**
	 * Renderer�� �����ݴϴ�.
	 */
	public Renderable getRenderer() {
		return renderer;
	}

	/**
	 * ���� ���¸� �����մϴ�.
	 * 
	 * @param running	���� ����
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * �������� �����մϴ�.
	 */
	@Override
	public void run() {
		renderer.onRenderBegin();
		while (running) {
			Canvas canvas;
			// canvas�� null ���� ���� �����Ƿ� ó����
			if ((canvas = holder.lockCanvas()) != null) {
				try {
					renderer.onRender(canvas);
				}
				finally {
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
		renderer.onRenderEnd();
	}

	@Override
	public synchronized void start() {
		running = true;
		super.start();
	}
	
	/**
	 * �������� ������ ���� ������ ����մϴ�.
	 */
	public void shutdown() {
		try {
			running = false;
			join();
		} catch (InterruptedException e) {
		}
	}
}
