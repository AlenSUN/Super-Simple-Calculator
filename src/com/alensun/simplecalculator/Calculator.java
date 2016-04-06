package com.alensun.simplecalculator;

import java.math.BigDecimal;
import java.util.Stack;

public class Calculator {
	private Stack<String> exps;
	private boolean isJustEqualed = false;

	public Calculator() {
		exps = new Stack<String>();
	}

	public String sendNumber(char c) {
		if (isJustEqualed) {
			exps.pop();
			isJustEqualed = false;
		}
		if (exps.empty() || exps.size() == 2) {
			exps.push("0");
		}
		String item = exps.pop();

		switch (c) {
		case '0':
			if (!item.equals("0")) {
				item += c;
			}
			break;

		case '.':
			if (!item.contains(".")) {
				item += c;
			}
			break;

		case 'C':
			exps.clear();
			item = "0";
			break;

		case 'd':
			if (item.length() > 1) {
				item = item.substring(0, item.length() - 1);
			} else {
				item = "0";
			}
			break;

		default:
			if (item.equals("0")) {
				item = "";
			}
			item += c;
			break;
		}

		return exps.push(item);
	}

	public String sendOperation(char c) {
		switch (c) {
		case '+':
		case '-':
		case '*':
		case '/':
			if (exps.empty()) {
				exps.push("0");
			} else if (exps.size() >= 3) {
				calculate(exps);
			}
			if (exps.size() == 1) {
				exps.push("" + c);
			} else if (exps.size() == 2) {
				exps.pop();
				exps.push("" + c);
			}
			isJustEqualed = false;
			break;

		case '=':
			if (exps.size() >= 3) {
				calculate(exps);
			} else if (exps.empty()) {
				exps.push("0");
			} else if (exps.size() == 2) {
				exps.push(exps.firstElement());
				calculate(exps);
			}
			isJustEqualed = true;
			break;

		default:
			break;
		}

		return exps.firstElement();
	}

	private void calculate(Stack<String> exps) {
		String op = null;
		BigDecimal item1 = null, item2 = null, result = null;

		try {
			item2 = new BigDecimal(exps.pop());
			op = exps.pop();
			item1 = new BigDecimal(exps.pop());
		} catch (NumberFormatException e) {
			exps.push("³ö´í");
			return;
		}

		switch (op.charAt(0)) {
		case '+':
			result = item1.add(item2);
			break;

		case '-':
			result = item1.subtract(item2);
			break;

		case '*':
			result = item1.multiply(item2);
			break;

		case '/':
			try {
				result = item1.divide(item2, 8, BigDecimal.ROUND_HALF_UP);
			} catch (ArithmeticException e) {
				exps.push("³ö´í");
				return;
			}
			break;

		default:
			break;
		}

		result = result.stripTrailingZeros();
		exps.push(result.toString());
	}
}