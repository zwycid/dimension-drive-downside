package kr.ssidang.android.dimensiondrivedownside;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Stage {
	public float width;
	public float height;
	
	public Portal start;
	public Portal goal;
	public List<Obstacle> obstacles = new ArrayList<Obstacle>();
	public List<Attractor> attractors = new ArrayList<Attractor>();
	
	// 외부에서 만들어줘야만 함
	private Stage() {}
	
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
					// TODO 끌개; attractor
					stage.attractors.add(new Attractor(nextFloat(tok), nextFloat(tok), 150, 3000));
				}
				else if (item.equals("[c]")) {
					// TODO sentry
				}
				else if (item.equals("[b]")) {
					// 장애물
					stage.obstacles.add(new Obstacle(nextFloat(tok),
							nextFloat(tok), nextFloat(tok), nextFloat(tok)));
				}
			}
		}
		
		return stage;
	}
}
