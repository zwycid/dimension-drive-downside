package mju.t3rd.sailingtext.ssidang;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Stage {
	public float width;		// stage �ʺ�
	public float height;	// stage ����
	
	public Portal start;	// �����
	public Portal goal;		// ������
	public List<Obstacle> obstacles = new ArrayList<Obstacle>();
	public List<Attractor> attractors = new ArrayList<Attractor>();
	public List<Sentry> sentries = new ArrayList<Sentry>();
	public List<Coin> coins = new ArrayList<Coin>();
	
	/**
	 * ���� ���� �� �����ϴ�.
	 */
	private Stage() {}
	
	/**
	 * ��ƿ��Ƽ �޼���
	 */
	private static float nextFloat(StringTokenizer tok) {
		return Float.parseFloat(tok.nextToken());
	}
	
	/**
	 * �� ������ ���ڿ��κ��� Stage�� ����ϴ�.
	 * 
	 * @param data
	 * @return
	 */
	public static Stage fromData(String data) {
		// ����:
		// [mapsize],800,800/[s],100,400/[f],750,750/[b],400,300,700,500/
		StringTokenizer tok = new StringTokenizer(data, "/,");
		Stage stage = new Stage();
		
		while (tok.hasMoreTokens()) {
			String item = tok.nextToken();
			if (item.length() == 0) {
				// ��
				break;
			}
			else if (item.startsWith("[") && item.endsWith("]")) {
				// [] ���
				if (item.equals("[mapsize]")) {
					// �� ũ��
					stage.width = nextFloat(tok);
					stage.height = nextFloat(tok);
				}
				else if (item.equals("[s]")) {
					// ������
					stage.start = new Portal(nextFloat(tok), nextFloat(tok), 10);
				}
				else if (item.equals("[f]")) {
					// ��
					stage.goal = new Portal(nextFloat(tok), nextFloat(tok), 40);
				}
				else if (item.equals("[a]")) {
					// ����; attractor
					stage.attractors.add(new Attractor(nextFloat(tok), nextFloat(tok), 150, 3000));
				}
				else if (item.equals("[c]")) {
					// sentry
					stage.sentries.add(new Sentry(nextFloat(tok), nextFloat(tok), 200));
				}
				else if (item.equals("[b]")) {
					// ��ֹ�
					stage.obstacles.add(new Obstacle(nextFloat(tok),
							nextFloat(tok), nextFloat(tok), nextFloat(tok)));
				}
				else if (item.equals("[i]")) {
					// ����
					stage.coins.add(new Coin(nextFloat(tok), nextFloat(tok)));
				}
			}
		}
		
		return stage;
	}
}
