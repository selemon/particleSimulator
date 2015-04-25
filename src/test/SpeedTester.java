package test;

import org.junit.Test;

import model.ModelParallel;
import ModelWithoutParallelisum.Model;
import ModelWithoutParallelisum.SetLoader;
import datasets.DataSetLoader;

public class SpeedTester {

	ModelParallel[] m = {DataSetLoader.getRandomGrid(100, 800, 30),
			DataSetLoader.getRandomRotatingGrid(100, 800, 40),
			DataSetLoader.getRandomSet(100, 800, 1000),
			DataSetLoader.getRegularGrid(100, 800, 40)};
	
	Model[] md = {SetLoader.getRandomGrid(100, 800, 30),
			SetLoader.getRandomRotatingGrid(100, 800, 40),
			SetLoader.getRandomSet(100, 800, 1000),
			SetLoader.getRegularGrid(100, 800, 40)};
	
	
	@Test
	public void testParallel0(){
		ModelParallel model = m[0];
		long time = System.currentTimeMillis();
		while(model.p.size()>=10){
			model = model.step();
		}
		time = System.currentTimeMillis() - time;
		System.out.println("parallel executed Random Grid in : "+time);
	}
	@Test
	public void testParallel1(){
		ModelParallel model = m[1];
		long time = System.currentTimeMillis();
		while(model.p.size()>=10){
			model = model.step();
		}
		time = System.currentTimeMillis() - time;
		System.out.println("parallel executed Random Rotating Grid in : "+time);
	}
	@Test
	public void testParallel2(){
		ModelParallel model = m[2];
		long time = System.currentTimeMillis();
		while(model.p.size()>=10){
			model = model.step();
		}
		time = System.currentTimeMillis() - time;
		System.out.println("parallel executed Random Set in : "+time);
	}@Test
	public void testParallel3(){
		ModelParallel model = m[3];
		long time = System.currentTimeMillis();
		while(model.p.size()>=10){
			model = model.step();
		}
		time = System.currentTimeMillis() - time;
		System.out.println("parallel executed Regular Grid in: "+time);
	}
	@Test
	public void testNormalModel0(){
		Model model = md[0];
		long time = System.currentTimeMillis();
		while(model.p.size()>=10){
			model.step();
		}
		time = System.currentTimeMillis() - time;
		System.out.println("model executed Random Grid in : "+time);
	}
	@Test
	public void testNormalModel1(){
		Model model = md[1];
		long time = System.currentTimeMillis();
		while(model.p.size()>=10){
			model.step();
		}
		time = System.currentTimeMillis() - time;
		System.out.println("model executed Random Rotating Grid in : "+time);
	}
	@Test
	public void testNormalModel2(){
		Model model = md[2];
		long time = System.currentTimeMillis();
		while(model.p.size()>=10){
			model.step();
		}
		time = System.currentTimeMillis() - time;
		System.out.println("model executed Random Set in : "+time);
	}
	@Test
	public void testNormalModel3(){
		Model model = md[3];
		long time = System.currentTimeMillis();
		while(model.p.size()>=10){
			model.step();
		}
		time = System.currentTimeMillis() - time;
		System.out.println("model executed Regular Grid in : "+time);
	}
	
	
	
}
