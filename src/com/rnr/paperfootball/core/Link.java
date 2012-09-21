/**
 *
 */
package com.rnr.paperfootball.core;

/**
 * @author rodnover
 *
 */
public class Link {
	private Cell mA;
	private Cell mB;

	public Link(Cell a, Cell b) {
		this.mA = a;
		this.mB = b;
	}

	public Cell getA() {
		return mA;
	}

	/**
	 * @return the b
	 */
	public Cell getB() {
		return mB;
	}

}
