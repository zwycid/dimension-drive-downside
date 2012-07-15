package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.PointF;
import android.util.FloatMath;

public class Vector2D extends PointF {
	public Vector2D() {
	}
	
	public Vector2D(float x, float y) {
		super(x, y);
	}
	
	public Vector2D(PointF p) {
		super(p.x, p.y);
	}


	// 기본 연산들

	/**
	 * 벡터를 더합니다.
	 * 
	 * @param v	더할 벡터
	 * @return	자기 자신
	 */
	public Vector2D add(PointF v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	/**
	 * 크기 조절한 벡터를 더합니다.
	 * 
	 * @param v			더할 벡터
	 * @param factor	크기 조절할 값
	 * @return			자기 자신
	 */
	public Vector2D add(PointF v, float factor) {
		x += v.x * factor;
		y += v.y * factor;
		return this;
	}
	
	/**
	 * 두 벡터를 더합니다.
	 * 
	 * @param v1
	 * @param v2
	 * @return		결과 벡터
	 */
	public static Vector2D add(PointF v1, PointF v2) {
		return new Vector2D(v1.x + v2.x, v1.y + v2.y);
	}
	
	/**
	 * 두 벡터를 더합니다.
	 * 
	 * @param v1
	 * @param v2
	 * @param factor	크기 조절할 값
	 * @return			결과 벡터
	 */
	public static Vector2D add(PointF v1, PointF v2, float factor) {
		return new Vector2D(v1.x + v2.x * factor, v1.y + v2.y * factor);
	}
	
	/**
	 * 벡터를 뺍니다.
	 * 
	 * @param v	뺄 벡터
	 * @return	자기 자신
	 */
	public Vector2D subtract(PointF v) {
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	/**
	 * 크기 조절한 벡터를 뺍니다.
	 * 
	 * @param v			뺄 벡터
	 * @param factor	크기 조절할 값
	 * @return			자기 자신
	 */
	public Vector2D subtract(PointF v, float factor) {
		x -= v.x * factor;
		y -= v.y * factor;
		return this;
	}
	
	/**
	 * 두 벡터를 뺍니다.
	 * 
	 * @param v1
	 * @param v2
	 * @return		결과 벡터
	 */
	public static Vector2D subtract(PointF v1, PointF v2) {
		return new Vector2D(v1.x - v2.x, v1.y - v2.y);
	}
	
	/**
	 * 두 벡터를 뺍니다.
	 * 
	 * @param v1
	 * @param v2
	 * @param factor	크기 조절할 값
	 * @return			결과 벡터
	 */
	public static Vector2D subtract(PointF v1, PointF v2, float factor) {
		return new Vector2D(v1.x - v2.x * factor, v1.y - v2.y * factor);
	}
	
	/**
	 * 벡터의 길이를 늘리거나 줄입니다.
	 * 
	 * @param s	크기 조절할 값
	 * @return	자기 자신
	 */
	public Vector2D scale(float s) {
		x *= s;
		y *= s;
		return this;
	}
	
	/**
	 * 벡터의 길이를 늘이거나 줄입니다.
	 * 
	 * @param v	조절할 벡터
	 * @param s 크기 조절할 값
	 * @return	결과 벡터
	 */
	public static Vector2D scale(PointF v, float s) {
		return new Vector2D(v.x * s, v.y * s);
	}
	
	/**
	 * 벡터 길이의 제곱값을 구합니다.
	 * (벡터 길이는 PointF.length() 이용)
	 * 
	 * @see super#length()
	 * @return	벡터 길이의 제곱값
	 */
	public float lengthSq() {
		return (x * x + y * y);
	}

	/**
	 * 벡터 길이를 1로 만듭니다.
	 * 
	 * @return	자기 자신
	 */
	public Vector2D normalize() {
		float l = length();
		x /= l;
		y /= l;
		return this;
	}
	
	/**
	 * 벡터 길이를 바꿉니다.
	 * 
	 * @param length	바꿀 길이
	 * @return			자기 자신
	 */
	public Vector2D setLength(float length) {
		float oldL = length();
		x = x * length / oldL;
		y = y * length / oldL;
		return this;
	}
	
	/**
	 * 벡터 방향과 길이를 지정하여 새 벡터를 지정합니다.
	 * 
	 * @see	super#set(PointF)
	 * 
	 * @param direction	벡터 방향
	 * @param length	벡터 길이
	 * @return			자기 자신
	 */
	public Vector2D set(PointF direction, float length) {
		float dirL = direction.length();
		x = direction.x * length / dirL;
		y = direction.y * length / dirL;
		return this;
	}
	
	/**
	 * 벡터의 내적을 구합니다.
	 * 
	 * @param v
	 * @return	내적 값
	 */
	public float dotProd(PointF v) {
		return dotProd(this, v);
	}
	
	/**
	 * 두 벡터의 내적을 구합니다.
	 * 
	 * @param v1
	 * @param v2
	 * @return	내적 값
	 */
	public static float dotProd(PointF v1, PointF v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	/**
	 * 벡터를 회전시킵니다.
	 * 
	 * @param rad	회전 각도
	 * @return		자기 자신
	 */
	public Vector2D rotate(float rad) {
		float sin = FloatMath.sin(rad);
		float cos = FloatMath.cos(rad);
		float x = this.x;
		float y = this.y;
		
		this.x = x * cos + y * sin;
		this.y = x * sin + y * cos;
		return this;
	}
	
	/**
	 * 이 벡터에 대한 법선을 구합니다.
	 * X축에 대한 법선은 Y축이며, 벡터를 90도 회전한 것과 같습니다.
	 * 
	 * @return	결과 벡터
	 */
	public Vector2D getNormal() {
		// 단순히 x, y를 바꾸면 된다.
		return new Vector2D(y, x).normalize();
	}
	
	/**
	 * 법선 벡터를 기준으로 뒤집습니다.
	 * 
	 * @param normal	법선 벡터
	 * @return			자기 자신
	 */
	public Vector2D mirror(PointF normal) {
		// R = v - 2 * (v . N) * N
		float dot = dotProd(normal);
		x -= 2 * dot * normal.x;
		y -= 2 * dot * normal.y;
		return this;
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
		
		// 직선 매개변수 검사해서 선분 범위 안인지 확인
		float t = (c1 * b2 - b1 * c2) / det;
		float s = (a1 * c2 - c1 * a2) / det;
		if ((t < 0 || t > 1) || (s < 0 || s > 1))
			return false;
		
		// 교점 찾기
		out.x = p0.x + (p1.x - p0.x) * t;
		out.y = p0.y + (p1.y - p0.y) * t;
		return true;
	}

}
