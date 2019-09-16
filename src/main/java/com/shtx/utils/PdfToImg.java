package com.shtx.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/* pdfתͼƬ
*
* @param pdfPath PDF·��
* @imgPath img·��
*/
public class PdfToImg {
	

	private static final float DEFAULT_DPI = 300;
	
	//Ĭ��ת����ͼƬ��ʽΪjpg
	public static final String DEFAULT_FORMAT = "jpg";

	public static void pdfToImage(String pdfPath, String imgPath) {
	    try {
	        //ͼ��ϲ�ʹ�ò���
	        // �ܿ��
	        int width = 0;
	        // ����һ��ͼƬ�е�RGB����
	        int[] singleImgRGB;
	        int shiftHeight = 0;
	        //����ÿ��ͼƬ������ֵ
	        BufferedImage imageResult = null;
	        //����PdfBox����ͼ��
	        PDDocument pdDocument = PDDocument.load(new File(pdfPath));
	        PDFRenderer renderer = new PDFRenderer(pdDocument);
	        //ѭ��ÿ��ҳ��
	        for (int i = 0, len = pdDocument.getNumberOfPages(); i < len; i++) {

	                BufferedImage image = renderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
	                int imageHeight = image.getHeight();
	                int imageWidth = image.getWidth();
	               //����߶Ⱥ�ƫ����
	                //ʹ�õ�һ��ͼƬ���;
	                width = imageWidth;
	                //����ÿҳͼƬ������ֵ
	                imageResult = new BufferedImage(width, imageHeight, BufferedImage.TYPE_INT_RGB);
	                //�����и߶ȣ����Խ�imageHeight*len
	                  singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
	                  // д������
	                  imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width);

	               // дͼƬ
	                String img = imgPath + "/"+ i + ".jpg";
		        	ImageIO.write(imageResult, DEFAULT_FORMAT, new File(img));
	        }

	        pdDocument.close();      
	         
	    } catch (Exception e) {
	       
	        e.printStackTrace();
	    }
	    //OVER
	}
	
//	public static void main(String[] args) throws Exception {
//	   // pdfToImage("C:/Users/Administrator/Desktop/��˾�ļ�/211161900166��ϸ.pdf","C:/Users/Administrator/Desktop/img");
//	}

}
