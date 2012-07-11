package kr.ssidang.android.dimensiondrivedownside;

import java.util.StringTokenizer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.FloatMath;

public class GameUtil {
	
	// �׸��� ����
	
	public static void drawTextMultiline(Canvas canvas, String str, float x,
			float y, Paint paint) {
		float lineHeight = (-paint.ascent() + paint.descent()) * 1.1f;
		float top = y + -paint.ascent();
		
		StringTokenizer tok = new StringTokenizer(str, "\n");
		while (tok.hasMoreTokens()) {
			canvas.drawText(tok.nextToken(), x, top, paint);
			top += lineHeight;
		}
	}

	public static void drawArrow(Canvas canvas, float startX, float startY,
			float stopX, float stopY, Paint paint) {
		// ������ ����: +90��
		float dx = (stopX - startX);
		float dy = (stopY - startY);
		float rad = (float) Math.atan2(dy, dx);
		
		canvas.drawLine(startX, startY, stopX, stopY, paint);
		canvas.drawLine(stopX, stopY,
				(float) (stopX + Math.cos(rad + Math.PI * 0.75) * 10),
				(float) (stopY + Math.sin(rad + Math.PI * 0.75) * 10), paint);
		canvas.drawLine(stopX, stopY,
				(float) (stopX + Math.cos(rad - Math.PI * 0.75) * 10),
				(float) (stopY + Math.sin(rad - Math.PI * 0.75) * 10), paint);
	}
	
	
	// ���� ����
	
	public static PointF clonePointF(PointF p) {
		return new PointF(p.x, p.y);
	}
	
	public static PointF vec2Add(PointF out, PointF lhs, PointF rhs) {
		out.x = lhs.x + rhs.x;
		out.y = lhs.y + rhs.y;
		return out;
	}
	
	public static PointF vec2Add(PointF lhs, PointF rhs) {
		return vec2Add(new PointF(), lhs, rhs);
	}
	
	public static PointF vec2Sub(PointF out, PointF lhs, PointF rhs) {
		out.x = lhs.x - rhs.x;
		out.y = lhs.y - rhs.y;
		return out;
	}
	
	public static PointF vec2Sub(PointF lhs, PointF rhs) {
		return vec2Sub(new PointF(), lhs, rhs);
	}
	
	/**
	 * ���� ����: out = lhs + rhs * s
	 */
	public static PointF vec2AddScaled(PointF out, PointF lhs, PointF rhs, float s) {
		out.x = lhs.x + rhs.x * s;
		out.y = lhs.y + rhs.y * s;
		return out;
	}
	
	/**
	 * ���Ϳ� ��Į�� ���� ���մϴ�. [inplace ����]
	 * @param v
	 * @param s
	 * @return
	 */
	public static PointF vec2Scale(PointF v, float s) {
		v.x *= s;
		v.y *= s;
		return v;
	}
	
	public static float vec2LengthSq(PointF v) {
		return v.x * v.x + v.y * v.y;
	}
	
	public static float vec2Length(PointF v) {
		return FloatMath.sqrt(vec2LengthSq(v));
	}
	
	/**
	 * 2D ������ ���̸� 1�� ����ϴ�. [inplace ����]
	 */
	public static PointF vec2Normalize(PointF v) {
		float length = vec2Length(v);
		v.x /= length;
		v.y /= length;
		return v;
	}
	
	public static PointF vec2SetLength(PointF v, float length) {
		float oldLength = vec2Length(v);
		v.x *= length / oldLength;
		v.y *= length / oldLength;
		return v;
	}
	
	/**
	 * 2D ������ ������ ���մϴ�.
	 */
	public static float vec2Dot(PointF lhs, PointF rhs) {
		return lhs.x * rhs.x + lhs.y * rhs.y;
	}
	
	/**
	 * 2D ���͸� ���� �������� ȸ���մϴ�.
	 */
	public static PointF vec2Rotation(PointF v, float angle) {
		float sinx = FloatMath.sin(angle);
		float cosx = FloatMath.cos(angle);
		float x = v.x;
		float y = v.y;
		v.x = x * cosx + y * sinx;
		v.y = y * cosx + x * sinx;
		return v;
	}

	/**
	 * �� ������ ������ ã���ϴ�.
	 * 
	 * @param p0	����1 ������
	 * @param p1	����1 ����
	 * @param q0	����2 ������
	 * @param q1	����2 ����
	 * @param out	����
	 * @return		������ �ִ��� ����
	 */
	public static boolean findLineIntersection(PointF p0,
			PointF p1, PointF q0, PointF q1, PointF out) {
		// ���� �������� �� �Ű������� ����
		float a1 = p1.x - p0.x;
		float b1 = q0.x - q1.x;
		float c1 = q0.x - p0.x;
		float a2 = p1.y - p0.y;
		float b2 = q0.y - q1.y;
		float c2 = q0.y - p0.y;
		
		// ������ ���ų� �������� �˻�
		float det = a1 * b2 - b1 * a2;
		if (Math.abs(det) <= Float.MIN_NORMAL)
			return false;
		
		// �Ű����� ���
		float t = (c1 * b2 - b1 * c2) / det;
		float s = (a1 * c2 - c1 * a2) / det;
		if ((t < 0 || t > 1) || (s < 0 || s > 1))
			return false;
		
		// ���� ã��
		out.x = p0.x + (p1.x - p0.x) * t;
		out.y = p0.y + (p1.y - p0.y) * t;
		return true;
	}
	
	/**
	 * ���� v�� ���� ���� N�� �������� �������ϴ�.
	 */
	public static PointF vec2Mirror(PointF v, PointF normal) {
		// R = v - 2 * (v . N) * N
		float dot = vec2Dot(v, normal);
		v.x -= 2 * dot * normal.x;
		v.y -= 2 * dot * normal.y;
		return v;
	}
}
