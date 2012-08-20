package mju.t3rd.sailingtext.ssidang.scene;

import android.graphics.Canvas;

/**
 * ���� ���¸� ��Ÿ���� Ŭ����.
 */
public abstract class Scene {
	/** ��� ������ */
	protected SceneManager mManager;
	/** ���� */
	protected Game mGame;
	/** ���³��� ������ ���� ������ */
	protected GameData mData;
	
	/**
	 * ���� ���� ����
	 */
	protected Scene() {}
	
	/**
	 * ������ ó��
	 */
	public void onFrame() {
		// pass
	}
	
	/**
	 * ȭ�� �׸���
	 * @param canvas
	 */
	public void onRender(Canvas canvas) {
		// pass
	}
	
	/**
	 * ���·� ������ ��
	 * @param prevState	���� ����
	 */
	public void onEnterState(GameData data, Scene prevState) {
		mData = data;
	}
	
	/**
	 * ���¸� �������� ��
	 */
	public void onLeaveState() {
		// pass
	}
}
