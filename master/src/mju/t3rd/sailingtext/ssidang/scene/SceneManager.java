package mju.t3rd.sailingtext.ssidang.scene;

import java.util.ArrayList;

import android.graphics.Canvas;

public class SceneManager {
	private Game mGame;
	private GameData mData;
	private ArrayList<Scene> mScenes = new ArrayList<Scene>();
	
	private boolean mStopLayer;
	
	public SceneManager(Game game, GameData data) {
		mGame = game;
		mData = data;
	}
	
	/**
	 * ��� ���ÿ� ����� �߰��մϴ�.
	 */
	public void pushScene(Scene scene) {
		scene.mGame = mGame;
		scene.mData = mData;
		scene.mManager = this;
		mScenes.add(scene);
	}
	
	/**
	 * ���� ����� ��� ���ÿ��� ����ϴ�.
	 */
	public void popScene() {
		mScenes.remove(mScenes.size() - 1);
	}
	
	/**
	 * ���� ����� �ٲߴϴ�.
	 * ��� ������ ��������� ����� �߰��մϴ�.
	 */
	public void replaceScene(Scene scene) {
		if (! mScenes.isEmpty())
			popScene();
		pushScene(scene);
//		if (mState != null)
//			mState.onLeaveState();
//		if (scene != null)
//			scene.onEnterState(mData, mState);
//		mState = scene;
	}
	
	/**
	 * ���� ����� � ������� �˷��ݴϴ�.
	 * �ƹ� ��鵵 ������ null�� �����ݴϴ�.
	 */
	public Scene currentScene() {
		return (mScenes.isEmpty() ? null : mScenes.get(mScenes.size() - 1));
	}
	
	/**
	 * ��� ����� ����ϴ�.
	 */
	public void clear() {
		mScenes.clear();
	}
	
	public void stopScenePropagation() {
		mStopLayer = true;
		
	}
	
	public void doFrame() {
		// ó���� ���������� �մϴ�.
		// onFrame() �߿� ����� �ٲ� �� �����Ƿ�
		// for..each�� ���� �ʽ��ϴ�.
		mStopLayer = false;
		for (int i = mScenes.size() - 1; !mStopLayer && i >= 0; --i) {
			mScenes.get(i).onFrame();
		}
	}
	
	public void doRender(Canvas canvas) {
		// ����� �Ʒ��ʺ��� �׷��ݴϴ�.
		mStopLayer = false;
		for (int i = 0; !mStopLayer && i < mScenes.size(); ++i) {
			mScenes.get(i).onRender(canvas);
		}
	}
}
