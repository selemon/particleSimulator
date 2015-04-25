package test;

import java.util.List;

import org.junit.Test;

import ModelWithoutParallelisum.*;
import datasets.DataSetLoader;
import model.ModelParallel;
import model.Particle;

public class TestModelParallel  {
	ModelParallel[] m = {DataSetLoader.getRandomGrid(100, 800, 30),
			DataSetLoader.getRandomRotatingGrid(100, 800, 40),
			DataSetLoader.getRandomSet(100, 800, 1000),
			DataSetLoader.getRegularGrid(100, 800, 40)};
	
	Model[] md = {SetLoader.getRandomGrid(100, 800, 30),
			SetLoader.getRandomRotatingGrid(100, 800, 40),
			SetLoader.getRandomSet(100, 800, 1000),
			SetLoader.getRegularGrid(100, 800, 40)};
	
	
	@Test
	public void testModelParallel(){
		
		for(int i = 0; i<m.length; i++){
			
			//m = m.step();
//			this.md[i].step();
//			this.md[i].step();
			List<Particle> modelParallel = this.m[i].p;
			Model model = this.md[i];
			TestHelper.testData(modelParallel, model);
		}
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
}
