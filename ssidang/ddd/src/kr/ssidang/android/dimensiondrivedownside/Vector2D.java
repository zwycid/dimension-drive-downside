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


	// �⺻ �����

	/**
	 * ���͸� ���մϴ�.
	 * 
	 * @param v	���� ����
	 * @return	�ڱ� �ڽ�
	 */
	public Vector2D add(PointF v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	/**
	 * ũ�� ������ ���͸� ���մϴ�.
	 * 
	 * @param v			���� ����
	 * @param factor	ũ�� ������ ��
	 * @return			�ڱ� �ڽ�
	 */
	public Vector2D add(PointF v, float factor) {
		x += v.x * factor;
		y += v.y * factor;
		return this;
	}
	
	/**
	 * �� ���͸� ���մϴ�.
	 * 
	 * @param v1
	 * @param v2
	 * @return		��� ����
	 */
	public static Vector2D add(PointF v1, PointF v2) {
		return new Vector2D(v1.x + v2.x, v1.y + v2.y);
	}
	
	/**
	 * �� ���͸� ���մϴ�.
	 * 
	 * @param v1
	 * @param v2
	 * @param factor	ũ�� ������ ��
	 * @return			��� ����
	 */
	public static Vector2D add(PointF v1, PointF v2, float factor) {
		return new Vector2D(v1.x + v2.x * factor, v1.y + v2.y * factor);
	}
	
	/**
	 * ���͸� ���ϴ�.
	 * 
	 * @param v	�� ����
	 * @return	�ڱ� �ڽ�
	 */
	public Vector2D subtract(PointF v) {
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	/**
	 * ũ�� ������ ���͸� ���ϴ�.
	 * 
	 * @param v			�� ����
	 * @param factor	ũ�� ������ ��
	 * @return			�ڱ� �ڽ�
	 */
	public Vector2D subtract(PointF v, float factor) {
		x -= v.x * factor;
		y -= v.y * factor;
		return this;
	}
	
	/**
	 * �� ���͸� ���ϴ�.
	 * 
	 * @param v1
	 * @param v2
	 * @return		��� ����
	 */
	public static Vector2D subtract(PointF v1, PointF v2) {
		return new Vector2D(v1.x - v2.x, v1.y - v2.y);
	}
	
	/**
	 * �� ���͸� ���ϴ�.
	 * 
	 * @param v1
	 * @param v2
	 * @param factor	ũ�� ������ ��
	 * @return			��� ����
	 */
	public static Vector2D subtract(PointF v1, PointF v2, float factor) {
		return new Vector2D(v1.x - v2.x * factor, v1.y - v2.y * factor);
	}
	
	/**
	 * ������ ���̸� �ø��ų� ���Դϴ�.
	 * 
	 * @param s	ũ�� ������ ��
	 * @return	�ڱ� �ڽ�
	 */
	public Vector2D scale(float s) {
		x *= s;
		y *= s;
		return this;
	}
	
	/**
	 * ������ ���̸� ���̰ų� ���Դϴ�.
	 * 
	 * @param v	������ ����
	 * @param s ũ�� ������ ��
	 * @return	��� ����
	 */
	public static Vector2D scale(PointF v, float s) {
		return new Vector2D(v.x * s, v.y * s);
	}
	
	/**
	 * ���� ������ �������� ���մϴ�.
	 * (���� ���̴� PointF.length() �̿�)
	 * 
	 * @see super#length()
	 * @return	���� ������ ������
	 */
	public float lengthSq() {
		return (x * x + y * y);
	}

	/**
	 * ���� ���̸� 1�� ����ϴ�.
	 * 
	 * @return	�ڱ� �ڽ�
	 */
	public Vector2D normalize() {
		float l = length();
		x /= l;
		y /= l;
		return this;
	}
	
	/**
	 * ���� ���̸� �ٲߴϴ�.
	 * 
	 * @param length	�ٲ� ����
	 * @return			�ڱ� �ڽ�
	 */
	public Vector2D setLength(float length) {
		float oldL = length();
		x = x * length / oldL;
		y = y * length / oldL;
		return this;
	}
	
	/**
	 * ���� ����� ���̸� �����Ͽ� �� ���͸� �����մϴ�.
	 * 
	 * @see	super#set(PointF)
	 * 
	 * @param direction	���� ����
	 * @param length	���� ����
	 * @return			�ڱ� �ڽ�
	 */
	public Vector2D set(PointF direction, float length) {
		float dirL = direction.length();
		x = direction.x * length / dirL;
		y = direction.y * length / dirL;
		return this;
	}
	
	/**
	 * ������ ������ ���մϴ�.
	 * 
	 * @param v
	 * @return	���� ��
	 */
	public float dotProd(PointF v) {
		return dotProd(this, v);
	}
	
	/**
	 * �� ������ ������ ���մϴ�.
	 * 
	 * @param v1
	 * @param v2
	 * @return	���� ��
	 */
	public static float dotProd(PointF v1, PointF v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}
	
	/**
	 * ���͸� ȸ����ŵ�ϴ�.
	 * 
	 * @param rad	ȸ�� ����
	 * @return		�ڱ� �ڽ�
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
	 * �� ���Ϳ� ���� ������ ���մϴ�.
	 * X�࿡ ���� ������ Y���̸�, ���͸� 90�� ȸ���� �Ͱ� �����ϴ�.
	 * 
	 * @return	��� ����
	 */
	public Vector2D getNormal() {
		// �ܼ��� x, y�� �ٲٸ� �ȴ�.
		return new Vector2D(y, x).normalize();
	}
	
	/**
	 * ���� ���͸� �������� �������ϴ�.
	 * 
	 * @param normal	���� ����
	 * @return			�ڱ� �ڽ�
	 */
	public Vector2D mirror(PointF normal) {
		// R = v - 2 * (v . N) * N
		float dot = dotProd(normal);
		x -= 2 * dot * normal.x;
		y -= 2 * dot * normal.y;
		return this;
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
		
		// ���� �Ű����� �˻��ؼ� ���� ���� ������ Ȯ��
		float t = (c1 * b2 - b1 * c2) / det;
		float s = (a1 * c2 - c1 * a2) / det;
		if ((t < 0 || t > 1) || (s < 0 || s > 1))
			return false;
		
		// ���� ã��
		out.x = p0.x + (p1.x - p0.x) * t;
		out.y = p0.y + (p1.y - p0.y) * t;
		return true;
	}

}