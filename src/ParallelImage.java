import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

public class ParallelImage extends RecursiveTask<BufferedImage> {

    private final int height;
    private final int width;
    private final double xMax;
    private final double iMax;
    private final double step;



    public ParallelImage(int width,int height,  double xMax, double iMax, double step) {
        this.height = height;
        this.width = width;
        this.xMax = xMax;
        this.iMax = iMax;
        this.step = step;
    }
    @Override
    protected BufferedImage compute() {

        GeneratePicture g = new GeneratePicture();
        PointArray point =new PointArray(width,height,xMax,iMax,step);
        int[][] pointArray =point.generatePointArray();
        return g.generate(pointArray);
    }

    public  BufferedImage generateImage() throws InterruptedException {

        int t = Runtime.getRuntime().availableProcessors();

        ForkJoinPool pool = new ForkJoinPool();
        Colors.createColors();
        BufferedImage[] images = new BufferedImage[t];
        ParallelImage[] im = new ParallelImage[t];
        double iMaxTemp = iMax;
        int h = height / t;

        for (int i = 0; i < t; i++) {
            if (i == t - 1) {
                h = height - h * (t - 1);
            }
            im[i] = new ParallelImage(width, h, xMax, iMaxTemp, step);
            iMaxTemp = iMax - h * step * (i + 1);
            pool.submit(im[i]);
        }

        pool.shutdown();

        if(pool.awaitTermination(10000, TimeUnit.SECONDS)){
            getImagesFromPool(t, images, im);
        }

        System.out.println("joining...");

        return combineImagesVertically(images, width, height);
    }

    private static void getImagesFromPool(int t, BufferedImage[] images, ParallelImage[] im) throws InterruptedException {
        for (int i = 0; i < t; i++) {
            try {
                images[i] = im[i].get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private static BufferedImage combineImagesVertically(BufferedImage[] images, int width, int height) {

        BufferedImage combinedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        java.awt.Graphics2D g2d = combinedImage.createGraphics();

        int currentY = 0;
        for (BufferedImage image : images) {
            g2d.drawImage(image, 0, currentY, null);
            currentY += image.getHeight();
        }

        g2d.dispose();

        return combinedImage;
    }



}
