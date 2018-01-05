package org.roof.fileupload.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.*;

/**
 * 图片工具
 * 
 * @author liuxin
 * 
 */
public class ImageUtils {

	private static final Logger logger = LoggerFactory.getLogger(ImageUtils.class);

	/**
	 * 压缩图片为指定的高宽
	 * 
	 * @param source
	 *            源图像
	 * @param width
	 *            需要的宽度
	 * @param height
	 *            需要的高度
	 * @return 压缩后的图像
	 */
	public static BufferedImage compress(BufferedImage source, int width,
			int height) {
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(source, 0, 0, width, height, null);
		return tag;
	}

	public static BufferedImage compress(BufferedImage source, double width,
			double height) {
		return compress(source, (int) width, (int) height);
	}

	/**
	 * 压缩图片可以最大限度的放入制定的容器大小内
	 * 
	 * @param source
	 *            源图像
	 * @param boxWidth
	 *            容器的宽度
	 * @param boxHeight
	 *            容器的高度
	 * @return
	 */
	public static BufferedImage compressToBox(BufferedImage source,
			int boxWidth, int boxHeight) {
		double widthProp = 0;
		double heightProp = 0;
		double zoom = 0;
		int width = source.getWidth();
		int height = source.getHeight();
		widthProp = boxWidth / (double) width;
		heightProp = boxHeight / (double) height;
		zoom = widthProp;
		if (zoom > heightProp) {
			zoom = heightProp;
		}
		return compress(source, width * zoom, height * zoom);
	}

	/**
	 * 根据起始坐标和结束坐标截取图片
	 * 
	 * @param source
	 *            原始图片
	 * @param xfrom
	 *            起始x坐标
	 * @param yfrom
	 *            起始y坐标
	 * @param xto
	 *            结束x坐标
	 * @param yto
	 *            结束y坐标
	 * @return 截取后的图片
	 */
	public static BufferedImage cut(BufferedImage source, int xfrom, int yfrom,
			int xto, int yto) {
		int width = xto - xfrom;
		int height = yto - yfrom;
		return cutBySize(source, xfrom, yfrom, width, height);
	}

	/**
	 * 根据起始坐标和需要的尺寸截取图片
     *
     * @param source 原始图片
	 * @param x
	 *            起始x坐标
	 * @param y
	 *            起始y坐标
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @return
	 */
	public static BufferedImage cutBySize(BufferedImage source, int x, int y,
			int width, int height) {
		CropImageFilter cropImageFilter = new CropImageFilter(x, y, width,
				height);
		Image image = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(source.getSource(), cropImageFilter));
		BufferedImage tag = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		tag.getGraphics().drawImage(image, 0, 0, null);
		return tag;
	}

	/**
	 * 写出图片
     *
     * @param source
	 *            需要写出的图片
	 * @param out
	 *            输出流
	 * @throws IOException
	 */
	public static void write(BufferedImage source, OutputStream out)
			throws IOException {
		ImageIO.write(source, "JPEG", out);
	}

    /**
     * 写出图片到文件
     *
     * @param source 需要写出的图片
     * @param out    文件
     * @throws IOException
     */
    public static void write(BufferedImage source, File out) throws IOException {
        OutputStream outputStream = new FileOutputStream(out);
        try {
            write(source, outputStream);
        } catch (Exception e) {
            logger.error("错误:", e.getCause());
        } finally {
            outputStream.close();
        }
    }

	/**
	 * 从输入流读入图片
	 * 
	 * @param in
	 *            输入流
	 * @return BufferedImage 对象
	 * @throws IOException
	 */
	public static BufferedImage read(InputStream in) throws IOException {
        return ImageIO.read(in);
    }

    public static BufferedImage read(File in) throws IOException {
        InputStream inputStream = new FileInputStream(in);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = read(inputStream);
        } catch (Exception e) {
            logger.error("错误:", e.getCause());
        } finally {
            inputStream.close();
        }
        return bufferedImage;
    }

    /**
     * 添加图片水印
     *
     * @param targetImg  目标图片
     * @param waterImage 水印图片
     * @param x          水印相对于目标图片的x偏移
     * @param y          水印相对于目标图片的y偏移
     * @param alpha      透明度 0-1
     * @return
     */
    public static Image addImageWeaterMark(Image targetImg, Image waterImage, int x, int y, float alpha) {
        int width = targetImg.getWidth(null);
        int height = targetImg.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(targetImg, 0, 0, width, height, null);
        int width_1 = waterImage.getWidth(null);
        int height_1 = waterImage.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

        int widthDiff = width - width_1;
        int heightDiff = height - height_1;
        if (x < 0) {
            x = widthDiff / 2;
        } else if (x > widthDiff) {
            x = widthDiff;
        }
        if (y < 0) {
            y = heightDiff / 2;
        } else if (y > heightDiff) {
            y = heightDiff;
        }
        g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
        g.dispose();
        return bufferedImage;
    }


    /**
     * 添加文字水印
     *
     * @param targetImg 目标图片路径
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName  字体名称， 如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize  字体大小，单位为像素
     * @param color     字体颜色
     * @param x         水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y         水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha     透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static Image addTextWeatermark(Image targetImg, String pressText, String fontName, int fontStyle,
                                          int fontSize, Color color, int x, int y, float alpha) {
        try {
            int width = targetImg.getWidth(null);
            int height = targetImg.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(targetImg, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                    alpha));

            int width_1 = fontSize * getLength(pressText);
            int widthDiff = width - width_1;
            int heightDiff = height - fontSize;
            if (x < 0) {
                x = widthDiff / 2;
            } else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0) {
                y = heightDiff / 2;
            } else if (y > heightDiff) {
                y = heightDiff;
            }

            g.drawString(pressText, x, y + fontSize);
            g.dispose();
            return bufferedImage;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     *
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }

    /**
     * 图片缩放
     *
     * @param bufferedImage 图片
     * @param height        高度
     * @param width         宽度
     * @param bb            比例不对时是否需要补白
     */
    public static Image resize(BufferedImage bufferedImage, int height, int width, boolean bb) {
        double ratio = 0; // 缩放比例
        Image itemImage = bufferedImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((bufferedImage.getHeight() > height) || (bufferedImage.getWidth() > width)) {
            if (bufferedImage.getHeight() > bufferedImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue() / bufferedImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue() / bufferedImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
            itemImage = op.filter(bufferedImage, null);
        }
        if (bb) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = image.createGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, width, height);
            if (width == itemImage.getWidth(null))
                g.drawImage(itemImage, 0, (height - itemImage.getHeight(null)) / 2, itemImage.getWidth(null),
                        itemImage.getHeight(null), Color.white, null);
            else
                g.drawImage(itemImage, (width - itemImage.getWidth(null)) / 2, 0, itemImage.getWidth(null),
                        itemImage.getHeight(null), Color.white, null);
            g.dispose();
            itemImage = image;
        }
        return itemImage;
    }

}
