package com.sun;

import java.util.HashMap;
import java.util.Iterator;

import com.ctc.wstx.util.WordSet;

public class WordCount {
	
	public static void main(String[] args) {
		String[] lines = {"Never give up",
						  "Never lose hope",
						  "Always have faith",
						  "It allows you to cope",
						  "Trying times will pass",
						  "As they always do",
						  "Just have patience",
						  "Your dreams will come true"};
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		for(String line:lines) {
			
			String[] words = line.split(" ");
			
			for(String word:words) 
				if(!hm.containsKey(word))
					hm.put(word, new Integer(1));
				else 
					hm.put(word, new Integer(hm.get(word)+1));
		}
		Iterator<String> words = hm.keySet().iterator();
		while (words.hasNext()) {
			String word = words.next();
			Integer numInteger = hm.get(word);
			System.out.println("<"+word+" : "+numInteger+">");
			
		}
	
	}
	
	
}
