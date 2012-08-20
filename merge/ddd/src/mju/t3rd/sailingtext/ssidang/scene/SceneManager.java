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
	 * 장면 스택에 장면을 추가합니다.
	 */
	public void pushScene(Scene scene) {
		scene.mGame = mGame;
		scene.mData = mData;
		scene.mManager = this;
		mScenes.add(scene);
	}
	
	/**
	 * 현재 장면을 장면 스택에서 지웁니다.
	 */
	public void popScene() {
		mScenes.remove(mScenes.size() - 1);
	}
	
	/**
	 * 현재 장면을 바꿉니다.
	 * 장면 스택이 비어있으면 장면을 추가합니다.
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
	 * 현재 장면이 어떤 장면인지 알려줍니다.
	 * 아무 장면도 없으면 null을 돌려줍니다.
	 */
	public Scene currentScene() {
		return (mScenes.isEmpty() ? null : mScenes.get(mScenes.size() - 1));
	}
	
	/**
	 * 모든 장면을 지웁니다.
	 */
	public void clear() {
		mScenes.clear();
	}
	
	public void stopScenePropagation() {
		mStopLayer = true;
		
	}
	
	public void doFrame() {
		// 처리는 위에서부터 합니다.
		// onFrame() 중에 장면을 바꿀 수 있으므로
		// for..each를 쓰지 않습니다.
		mStopLayer = false;
		for (int i = mScenes.size() - 1; !mStopLayer && i >= 0; --i) {
			mScenes.get(i).onFrame();
		}
	}
	
	public void doRender(Canvas canvas) {
		// 장면은 아래쪽부터 그려줍니다.
		mStopLayer = false;
		for (int i = 0; !mStopLayer && i < mScenes.size(); ++i) {
			mScenes.get(i).onRender(canvas);
		}
	}
}
