package com.ktdsuniversity.edu.common.handler;

import org.springframework.stereotype.Component;

@Component
public class ReplaceHandler {

	public String replaceTag(String replaceStr) {
		if (replaceStr != null) {
			replaceStr = replaceStr.replace("<", "&lt")
								   .replace(">", "&gt");
			return replaceStr;
		}
		return null;
	}

}
