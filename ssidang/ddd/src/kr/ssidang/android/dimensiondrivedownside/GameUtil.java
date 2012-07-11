package kr.ssidang.android.dimensiondrivedownside;

import java.util.StringTokenizer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.FloatMath;

public class GameUtil {
	
	// 그리기 관련
	
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
		// 오른쪽 날개: +90도
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
	
	
	// 벡터 관련
	
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
	 * 벡터 연산: out = lhs + rhs * s
	 */
	public static PointF vec2AddScaled(PointF out, PointF lhs, PointF rhs, float s) {
		out.x = lhs.x + rhs.x * s;
		out.y = lhs.y + rhs.y * s;
		return out;
	}
	
	/**
	 * 벡터에 스칼라 값을 곱합니다. [inplace 연산]
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
	 * 2D 벡터의 길이를 1로 만듭니다. [inplace 연산]
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
	 * 2D 벡터의 내적을 구합니다.
	 */
	public static float vec2Dot(PointF lhs, PointF rhs) {
		return lhs.x * rhs.x + lhs.y * rhs.y;
	}
	
	/**
	 * 2D 벡터를 원점 기준으로 회전합니다.
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
	 * 두 선분의 교점을 찾습니다.
	 * 
	 * @param p0	선분1 시작점
	 * @param p1	선분1 끝점
	 * @param q0	선분2 시작점
	 * @param q1	선분2 끝점
	 * @param out	교점
	 * @return		교점이 있는지 여부
	 */
	public static boolean findLineIntersection(PointF p0,
			PointF p1, PointF q0, PointF q1, PointF out) {
		// 직선 방정식의 각 매개변수를 구함
		float a1 = p1.x - p0.x;
		float b1 = q0.x - q1.x;
		float c1 = q0.x - p0.x;
		float a2 = p1.y - p0.y;
		float b2 = q0.y - q1.y;
		float c2 = q0.y - p0.y;
		
		// 직선이 같거나 평행인지 검사
		float det = a1 * b2 - b1 * a2;
		if (Math.abs(det) <= Float.MIN_NORMAL)
			return false;
		
		// 매개변수 계산
		float t = (c1 * b2 - b1 * c2) / det;
		float s = (a1 * c2 - c1 * a2) / det;
		if ((t < 0 || t > 1) || (s < 0 || s > 1))
			return false;
		
		// 교점 찾기
		out.x = p0.x + (p1.x - p0.x) * t;
		out.y = p0.y + (p1.y - p0.y) * t;
		return true;
	}
	
	/**
	 * 벡터 v를 법선 벡터 N을 기준으로 뒤집습니다.
	 */
	public static PointF vec2Mirror(PointF v, PointF normal) {
		// R = v - 2 * (v . N) * N
		float dot = vec2Dot(v, normal);
		v.x -= 2 * dot * normal.x;
		v.y -= 2 * dot * normal.y;
		return v;
	}
}
