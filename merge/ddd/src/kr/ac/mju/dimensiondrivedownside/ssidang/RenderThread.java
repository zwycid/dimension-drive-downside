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
	 * 지정한 SurfaceHolder를 통해 그려주는 스레드를 만듭니다.
	 * 
	 * @param holder		그려줄 SurfaceHolder
	 * @param renderer		그려줄 Renderer
	 */
	public RenderThread(SurfaceHolder holder, Renderable renderer) {
		this.holder = holder;
		this.renderer = renderer;
	}

	/**
	 * SurfaceHolder를 돌려줍니다.
	 */
	public SurfaceHolder getHolder() {
		return holder;
	}

	/**
	 * Renderer를 돌려줍니다.
	 */
	public Renderable getRenderer() {
		return renderer;
	}

	/**
	 * 실행 상태를 지정합니다.
	 * 
	 * @param running	실행 여부
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * 렌더링을 수행합니다.
	 */
	@Override
	public void run() {
		renderer.onRenderBegin();
		while (running) {
			Canvas canvas;
			// canvas가 null 나올 때가 있으므로 처리함
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
	 * 렌더링을 마무리 지을 때까지 대기합니다.
	 */
	public void shutdown() {
		try {
			running = false;
			join();
		} catch (InterruptedException e) {
		}
	}
}
