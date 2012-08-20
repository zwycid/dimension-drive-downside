package mju.t3rd.sailingtext.ssidang.engine;

import java.util.StringTokenizer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class GameUtil {
	
	/**
	 * ���ڿ� ���� ���� ����մϴ�. baseline ������ �ƴ�, �»����� �������� �մϴ�.
	 * 
	 * @param canvas	�׷��� ĵ����
	 * @param str		�׸� ���ڿ�
	 * @param x
	 * @param y
	 * @param paint
	 */
	public static void drawTextMultiline(Canvas canvas, String str,
			float x, float y, Paint paint) {
		float lineHeight = (-paint.ascent() + paint.descent()) * 1.1f;
		float top = y + -paint.ascent();
		
		// �� �پ� �׷��ݴϴ�.
		StringTokenizer tok = new StringTokenizer(str, "\n");
		while (tok.hasMoreTokens()) {
			canvas.drawText(tok.nextToken(), x, top, paint);
			top += lineHeight;
		}
	}

	/**
	 * ȭ��ǥ�� �׷��ݴϴ�.
	 * 
	 * @param canvas
	 * @param startX
	 * @param startY
	 * @param stopX
	 * @param stopY
	 * @param paint
	 */
	public static void drawArrow(Canvas canvas, float startX, float startY,
			float stopX, float stopY, Paint paint) {
		// ������ ����: +90��
		float dx = (stopX - startX);
		float dy = (stopY - startY);
		float rad = (float) Math.atan2(dy, dx);
		
		canvas.drawLine(startX, startY, stopX, stopY, paint);
		canvas.drawLine(stopX, stopY,
				(float) (stopX + Math.cos(rad + Math.PI * 0.75) * 20),
				(float) (stopY + Math.sin(rad + Math.PI * 0.75) * 20), paint);
		canvas.drawLine(stopX, stopY,
				(float) (stopX + Math.cos(rad - Math.PI * 0.75) * 20),
				(float) (stopY + Math.sin(rad - Math.PI * 0.75) * 20), paint);
	}
	
	public static void transform(Matrix mat, float x, float y, float degrees,
			float scale, int width, int height) {
		// (1) ������ �������� ���߰�
		// (2) ���ϴ� ������ ȸ����Ų ��
		// (3) ���ϴ� ũ��� ������ ����
		// (4) �� ��ġ�� �����ϴ�.
		mat.setRotate(degrees);
		mat.preTranslate(-width / 2.f, -height / 2.f);
		mat.postScale(scale, scale);
		mat.postTranslate(x, y);
	}
	
	public static void transformImage(Matrix mat, float x, float y,
			float radius, float degrees, Bitmap image) {
		float length = (image.getWidth() + image.getHeight()) / 2;
		GameUtil.transform(mat, x, y, degrees, radius * 2 / length,
				image.getWidth(), image.getHeight());
	}
	
}
