package com.alensun.simplecalculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

public class MainActivity extends Activity {
	private Calculator c;

	private GridLayout board;
	private EditText displayText;
	private String text = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		board = (GridLayout) findViewById(R.id.board);
		displayText = (EditText) findViewById(R.id.display);

		c = new Calculator();
		addClickListeners();
	}

	private void addClickListeners() {
		int n = board.getChildCount();
		for (int i = 0; i < n; i++) {
			board.getChildAt(i).setOnClickListener(mListener);
		}
	}

	private OnClickListener mListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.plus:
			case R.id.minus:
			case R.id.multiply:
			case R.id.divide:
			case R.id.equal:
				text = c.sendOperation(((String) v.getTag()).charAt(0));
				break;

			default:
				text = c.sendNumber(((Button) v).getText().charAt(0));
				break;
			}

			displayText.setText(text);
		}
	};
}
