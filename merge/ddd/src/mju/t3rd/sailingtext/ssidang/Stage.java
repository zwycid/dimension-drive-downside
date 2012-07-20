package mju.t3rd.sailingtext.ssidang;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Stage {
	public float width;		// stage 너비
	public float height;	// stage 높이
	
	public Portal start;	// 출발점
	public Portal goal;		// 도착점
	public List<Obstacle> obstacles = new ArrayList<Obstacle>();
	public List<Attractor> attractors = new ArrayList<Attractor>();
	public List<Sentry> sentries = new ArrayList<Sentry>();
	public List<Coin> coins = new ArrayList<Coin>();
	
	/**
	 * 직접 만들 수 없습니다.
	 */
	private Stage() {}
	
	/**
	 * 유틸리티 메서드
	 */
	private static float nextFloat(StringTokenizer tok) {
		return Float.parseFloat(tok.nextToken());
	}
	
	/**
	 * 맵 데이터 문자열로부터 Stage를 만듭니다.
	 * 
	 * @param data
	 * @return
	 */
	public static Stage fromData(String data) {
		// 샘플:
		// [mapsize],800,800/[s],100,400/[f],750,750/[b],400,300,700,500/
		StringTokenizer tok = new StringTokenizer(data, "/,");
		Stage stage = new Stage();
		
		while (tok.hasMoreTokens()) {
			String item = tok.nextToken();
			if (item.length() == 0) {
				// 끝
				break;
			}
			else if (item.startsWith("[") && item.endsWith("]")) {
				// [] 블록
				if (item.equals("[mapsize]")) {
					// 맵 크기
					stage.width = nextFloat(tok);
					stage.height = nextFloat(tok);
				}
				else if (item.equals("[s]")) {
					// 시작점
					stage.start = new Portal(nextFloat(tok), nextFloat(tok), 10);
				}
				else if (item.equals("[f]")) {
					// 골
					stage.goal = new Portal(nextFloat(tok), nextFloat(tok), 40);
				}
				else if (item.equals("[a]")) {
					// 끌개; attractor
					stage.attractors.add(new Attractor(nextFloat(tok), nextFloat(tok), 150, 3000));
				}
				else if (item.equals("[c]")) {
					// sentry
					stage.sentries.add(new Sentry(nextFloat(tok), nextFloat(tok), 200));
				}
				else if (item.equals("[b]")) {
					// 장애물
					stage.obstacles.add(new Obstacle(nextFloat(tok),
							nextFloat(tok), nextFloat(tok), nextFloat(tok)));
				}
				else if (item.equals("[i]")) {
					// 동전
					stage.coins.add(new Coin(nextFloat(tok), nextFloat(tok)));
				}
			}
		}
		
		return stage;
	}
}
