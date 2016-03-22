package com.alensun.simplecalculator;

import java.util.Stack;

public class Calculator {
	private Stack<String> exps;
	private boolean isJustEqualed = false;
	
	public Calculator() {
		exps = new Stack<String>();
	}
	
	public String sendNumber(String c) {
		if (isJustEqualed) {
			exps.pop();
			isJustEqualed = false;
		}
		if (exps.empty() || exps.size() == 2) {
			exps.push("0");
		}
		String item = exps.pop();
		
		switch (c.charAt(0)) {
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
	
	public String sendOperation(String c) {
		switch (c.charAt(0)) {
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
				exps.push(c);
			} else if (exps.size() == 2) {
				exps.pop();
				exps.push(c);
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
		
		String result = exps.firstElement();
		if (result.contains(".")) {
			while (result.endsWith("0")) {
				result = result.substring(0, result.length() - 1);
			}
			if (result.endsWith(".")) {
				result = result.substring(0, result.length() - 1);
			}
		}
		return result;
	}
	
	private void calculate(Stack<String> exps) {
		double item2 = Double.valueOf(exps.pop());
		String op = exps.pop();
		double item1 = Double.valueOf(exps.pop());
		double result = 0;
		
		switch (op.charAt(0)) {
		case '+':
			result = item1 + item2;
			break;
			
		case '-':
			result = item1 - item2;
			break;
			
		case '*':
			result = item1 * item2;
			break;
			
		case '/':
			result = item1 / item2;
			break;

		default:
			break;
		}
		
		exps.add("" + result);
	}
}