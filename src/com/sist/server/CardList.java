package com.sist.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* ��ü ī�带 ������ Ŭ���� , ���Ŀ� ������ �� ���� */
public class CardList {
	final private static int totalCardNumber = 80;
	private ArrayList<Integer> cardList;

	public CardList() {
		// TODO Auto-generated constructor stub
		int count = 1;
		int i = 0;
		boolean bCheck = true;
		cardList = new ArrayList<Integer>();
		while (true) {
			for (int j = 0; j < count; j++, i++) {
				if (i >= 80) {
					bCheck = false;
					break;
				}
				cardList.add(count);
			}
			if (bCheck)
				count++;
			else
				break;
			
		}
	}

	/* ī�带 ��� �������� ����� �޼��� */
	public void shuffleCardList() {
		ArrayList<Integer> shuffledCardList = new ArrayList<Integer>();
		int[] indexCard = new int[totalCardNumber];
		for (int i = 0; i < totalCardNumber;) {
			boolean bCheck = true;
			int tmp = (int) (Math.random() * totalCardNumber);
			for (int j = 0; j < i; j++) {
				if (tmp == indexCard[j]) {
					bCheck = false;
					break;
				}
			}
			if (bCheck)
				indexCard[i++] = tmp;
			else
				continue;
		}

		for (int i = 0; i < totalCardNumber; i++) {
			shuffledCardList.add(cardList.get(indexCard[i]));
		}

		cardList = shuffledCardList;
	}

	/* ���� ī�带 �޾ƿ��� �޼ҵ� */
	public ArrayList<Integer> getCardList(int userNumber, int radix) {
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for (int i = 0; i < totalCardNumber / userNumber; i++) {
			tmp.add(cardList.get(radix * totalCardNumber / userNumber + i));
		}
		return tmp;
	}
}
