package com.sist.client;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/*
 * textfield 입력 텍스트 제한 클래스
 */
public class JTextFieldLimit extends PlainDocument {
	private int limit;
	private boolean toUppercase = false;

	JTextFieldLimit(int limit) {
		super();
		this.limit = limit;
	}

	JTextFieldLimit(int limit, boolean upper) {
		super();
		this.limit = limit;
		this.toUppercase = upper;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}

		if ((getLength() + str.length()) <= limit) {
			if (toUppercase) {
				str = str.toUpperCase();
			}
			super.insertString(offset, str, attr);
		}

	}
}