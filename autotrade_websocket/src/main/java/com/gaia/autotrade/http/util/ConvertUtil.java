package com.gaia.autotrade.http.util;

public class ConvertUtil {
	
	/**
	 * Tick最小变动精度 转换 Digit
	 * @return Dight
	 */
	public static int tickToDigit(double tick) {
		if(tick == 0) {
			return 0;
		}
		double quotient = 1 / tick;
		double root = Math.log10(quotient);
		return (int)root;
	}
}
