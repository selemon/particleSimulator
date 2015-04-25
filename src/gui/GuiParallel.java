package gui;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import datasets.DataSetLoader;
import model.ModelParallel;
public class GuiParallel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ModelParallel model = new ModelParallel();
	public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
	private static final class BuildGui implements Runnable {
		//    ModelParallel m;BuildGui(ModelParallel m2){this.m=m2;}
		public void run() {
			final GuiParallel g = new GuiParallel();
			g.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			g.getRootPane().setLayout(new BorderLayout());
			JPanel p=new Canvas(model);
			//      System.out.println("Drawable : "+model.pDraw.size());
			g.getRootPane().add(p,BorderLayout.CENTER);
			g.pack();
			g.setVisible(true);
			scheduler.scheduleAtFixedRate(
					new Runnable(){public void run() {g.repaint();}},
					500,25, TimeUnit.MILLISECONDS);
		}
	}
	private static final class MainLoop implements Runnable {
		ModelParallel m;MainLoop(ModelParallel m2){this.m=m2;}
		public void run() {
			try{
				//long time = System.currentTimeMillis();
				while(true){
					//        	System.out.println("yesy");
					long ut=System.currentTimeMillis();
					m = m.step();
					model = m;
					//          System.out.println("Drawable : "+model.pDraw.size());
					//          System.out.println(m.p.size());
					//          if(m.p.size()<=10){
					//				//61934
					//				System.out.println(System.currentTimeMillis()-time);
					//			}
					//System.out.println(m.p.size());
					ut=System.currentTimeMillis()-ut;//used time
					long sleepTime=25-ut;
					if(sleepTime>1){
						//        	  System.out.println(sleepTime);
						Thread.sleep(sleepTime);}
				}
			}
			catch(Throwable t){
				t.printStackTrace();
				System.exit(0);//not a perfect solution
			}
		}
	}
	public static void main(String[] args) {
		model =DataSetLoader.getRegularGrid(100, 800, 40);//Try those configurations
		//model =DataSetLoader.getRandomRotatingGrid(100, 800, 40);
		//    model =DataSetLoader.getRandomSet(100, 800, 1000);
		//    model =DataSetLoader.getRandomGrid(100, 800, 30);
		scheduler.schedule(new MainLoop(model), 500, TimeUnit.MILLISECONDS);
		SwingUtilities.invokeLater(new BuildGui());
	}
}