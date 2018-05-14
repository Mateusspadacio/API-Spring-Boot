package com.mateus.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static List<Integer> decodeIntList(String s) {
		List<Integer> list;
		
		try {
			list = Arrays.asList(s.split(",")).stream()
					.map(n -> Integer.parseInt(n.trim())).collect(Collectors.toList());
		} catch(Exception e) {
			list = new ArrayList<>();
		}
		return list;
	}
	
}
