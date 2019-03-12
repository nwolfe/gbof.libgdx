package org.rnq.bindingoffenrir.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.rnq.bindingoffenrir.Constants;
import org.rnq.bindingoffenrir.Gleipnir;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = Constants.TITLE;
		config.width = Constants.WIDTH;
		config.height = Constants.HEIGHT;
		new LwjglApplication(new Gleipnir(), config);
	}
}
