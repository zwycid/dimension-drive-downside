package mju.t3rd.sailingtext.ssidang.scene;

import android.os.SystemClock;
import mju.t3rd.sailingtext.ssidang.game.Ball;
import mju.t3rd.sailingtext.ssidang.game.Bullet;
import mju.t3rd.sailingtext.ssidang.game.Marker;
import mju.t3rd.sailingtext.ssidang.game.Particle;
import mju.t3rd.sailingtext.zeraf29.sound.SoundManager;

public class ReadyScene extends Scene {

	public void onFrame() {
		// ��ƼŬ, �Ѿ� ����� �̸� �����Ӵϴ�.
		mData.particles = new Particle[GameData.MAX_PARTICLE];
		mData.bullets = new Bullet[GameData.MAX_BULLET];
		for (int i = 0; i < mData.particles.length; ++i)
			mData.particles[i] = new Particle();
		for (int i = 0; i < mData.bullets.length; ++i)
			mData.bullets[i] = new Bullet();
		
		// ���� �������� �Ӵϴ�.
		mData.ball = new Ball(mData.stage.start.pos.x, mData.stage.start.pos.y, 20);
		mData.marker = new Marker(-mData.screenWidth / 2, -mData.screenHeight / 2,
				mData.screenWidth / 2, mData.screenHeight / 2);
		
		// �ð��� �ʱ�ȭ�մϴ�.
		mData.timestamp = SystemClock.uptimeMillis();
		mData.playTime = 0;
		mData.baseTime = mData.timestamp;
		
		SoundManager.getInstance().play(6); // TODO aa
		mManager.replaceScene(GameData.PLAYING);
	}

}
