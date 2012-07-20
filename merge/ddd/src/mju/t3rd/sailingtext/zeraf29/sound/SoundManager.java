package mju.t3rd.sailingtext.zeraf29.sound;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private static SoundManager sManager;
	private SoundPool soundPool;
	private HashMap<Integer, Integer> map;
	private Context context;
	
	private SoundManager(){}
	public static SoundManager getInstance(){
			if(sManager==null){
				sManager = new SoundManager();
			}
			return sManager;
		}
	
	public void init(Context context){
		this.context = context;
		soundPool = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
		map = new HashMap<Integer,Integer>();
	}
	
	public void addSound(int index, int resId){
		int id = soundPool.load(context, resId,1);
		map.put(index, id);
	}
	
	public void play(int index){
		soundPool.play(map.get(index), 1, 1, 1, 0, 1);
	}
	
	public void stopSound(int index){
		soundPool.stop(map.get(index));
	}
}
