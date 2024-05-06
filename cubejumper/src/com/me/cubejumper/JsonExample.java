package com.me.cubejumper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.me.cubejumper.utilities.FileManager;

public class JsonExample {
	Player player;

	public void pause() {
		JSONObject obj = new JSONObject();
		obj.put("positionx", player.getPosition().x);
		obj.put("positiony", player.getPosition().y);
		obj.put("velocityx", player.getVelocity().x);
		obj.put("velocityy", player.getVelocity().y);
		obj.put("angvelocity", player.getAngVelocity());
		obj.put("canjump", Player.isCanJump());

		FileHandle file1 = Gdx.files.local("savedata/pause.json");
		file1.writeString(obj.toJSONString(), false);
	}

	public void resume() {
		JSONParser parser = new JSONParser();

		new FileManager("savedata", "savedata/encrypted.json",
				"savedata/decrypted.json", 1);
		FileManager.deleteFile("savedata/encrypted.json");

		try {
			FileHandle file1 = Gdx.files.local("savedata/pause.json");
			Object obj = parser.parse(new FileReader(file1.toString()));

			JSONObject jsonObject = (JSONObject) obj;

			// file1.delete();

			float positionx = Float.valueOf(jsonObject.get("positionx")
					.toString());
			float positiony = Float.valueOf(jsonObject.get("positiony")
					.toString());
			float angvel = Float.valueOf(jsonObject.get("angvelocity")
					.toString());
			Vector2 coords = new Vector2(positionx, positiony);
			player.setPositionAndAngVelocity(coords, angvel);

			float velocityx = Float.valueOf(jsonObject.get("velocityx")
					.toString());
			float velocityy = Float.valueOf(jsonObject.get("velocityy")
					.toString());
			String canjump = (String) jsonObject.get("canjump");

			coords.set(velocityx, velocityy);
			player.setVelocity(coords);
			Player.setCanJump(canjump);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
