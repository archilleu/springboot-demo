package com.hoya.app.demo.redis;

import java.io.UnsupportedEncodingException;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class RedisChannelListener implements MessageListener {

	@Override
	public void onMessage(Message message, byte[] pattern) {
		byte[] channel = message.getChannel();
		byte[] bs = message.getBody();
		try {
			String content = new String(bs, "UTF-8");
			String p = new String(channel, "UTF-8");
			System.out.println("get " + content + " from " + p);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
