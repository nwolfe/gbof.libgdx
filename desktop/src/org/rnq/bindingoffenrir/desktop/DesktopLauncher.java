package org.rnq.bindingoffenrir.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.rnq.bindingoffenrir.Gleipnir;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "G L E I P N I R :: Binding Of Fenrir";
		config.width = 640;
		config.height = 480;
		new LwjglApplication(new Gleipnir(), config);
	}
}
