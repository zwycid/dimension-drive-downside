package mju.t3rd.sailingtext.ssidang.scene;

import android.graphics.Canvas;

/**
 * 게임 상태를 나타내는 클래스.
 */
public abstract class Scene {
	/** 장면 관리자 */
	protected SceneManager mManager;
	/** 게임 */
	protected Game mGame;
	/** 상태끼리 공유할 게임 데이터 */
	protected GameData mData;
	
	/**
	 * 직접 생성 금지
	 */
	protected Scene() {}
	
	/**
	 * 프레임 처리
	 */
	public void onFrame() {
		// pass
	}
	
	/**
	 * 화면 그리기
	 * @param canvas
	 */
	public void onRender(Canvas canvas) {
		// pass
	}
	
	/**
	 * 상태로 진입할 때
	 * @param prevState	이전 상태
	 */
	public void onEnterState(GameData data, Scene prevState) {
		mData = data;
	}
	
	/**
	 * 상태를 빠져나갈 때
	 */
	public void onLeaveState() {
		// pass
	}
}
