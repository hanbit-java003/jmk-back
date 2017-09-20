package com.hanbit.there.api.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage; // image를 다룰때 사용하는 녀석.
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class ImageUtils {
	
	public static void rezize(InputStream inputStream,int width, String targetFilePath)
			throws IOException{  // 오버로딩 중복정의   // 폭만.
		
			Thumbnails.of(inputStream).width(width).toFile(targetFilePath);
	}
	
	public static void rezize(InputStream inputStream,int width, int height, String targetFilePath)
			throws IOException{  // 폭/ 높이 둘다.
		
			Thumbnails.of(inputStream).size(width, height).toFile(targetFilePath);
			
			
			//Thumbnails.of("/hanbit/cafe.jpg").width(100).toFile("/hanbit/resized.jpg"); //폭만 100
			//Thumbnails.of("/hanbit/cafe.jpg").height(100).toFile("/hanbit/resized.jpg"); //높이만 100
		
		
		/*try {
			BufferedImage image = ImageIO.read(new File("/hanbit/cafe.jpg"));
			                                  // Alpha/Red/gRean/blue
			int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
			
			BufferedImage resized = new BufferedImage(100, 100, type);
			Graphics2D g = resized.createGraphics();
			g.drawImage(image, 0, 0, 100,100, null); 
			g.dispose(); // 닫아준다.
			
			g.setComposite(AlphaComposite.Src); // 투명도있으면 줌 /9.12
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, 
					RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);
			
			ImageIO.write(resized, "jpg", new File("/hanbit/resized.jpg"));// 
			
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	
}
