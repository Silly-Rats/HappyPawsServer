package org.silly.rats.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class ImageUtil {
	public static String loadImage(String path, String name) {
		return loadImage(path + "/" + name);
	}

	private static String loadImage(String fileName) {
		try {
			byte[] bytes = Files.readAllBytes(Path.of(fileName));
			String base64bytes = Base64.getEncoder().encodeToString(bytes);
			return "data:image/png;base64," + base64bytes;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String saveImage(String image, String path, String name) {
		String fileName = path + "/" + name + ".png";

		String[] parts = image.split(",");
		if (parts.length != 2 || !parts[0].startsWith("data:image")) {
			throw new IllegalArgumentException("Invalid Data URL format");
		}
		image = parts[1];

		try {
			byte[] imageData = Base64.getDecoder().decode(image);
			BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));
			BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(),
					bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);

			newImage.getGraphics().drawImage(bufferedImage, 0, 0, null);
			ImageIO.write(newImage, "png", new File(fileName));

			return loadImage(fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
