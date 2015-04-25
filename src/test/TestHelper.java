package test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import ModelWithoutParallelisum.Model;
import ModelWithoutParallelisum.Particle;

public class TestHelper {
	public static boolean verify(List<model.Particle> modelParallel){
		for(int i=0;i<modelParallel.size();i++){
			model.Particle current = modelParallel.get(i);
			for(int j = 0; j<modelParallel.size(); j++){
				if(i==j){continue;}
				model.Particle temp = modelParallel.get(j);
				if((current.x==temp.x)&&(current.y==temp.y)){
					return false;
				}			
			}
		}
		return true;
	}

	public static boolean verifyM(Model result){
		for(int i=0;i<result.p.size();i++){
			Particle current = result.p.get(i);
			for(int j = 0; j<result.p.size(); j++){
				if(i==j){continue;}
				Particle temp = result.p.get(j);
				if((current.x==temp.x)&&(current.y==temp.y)){
					return false;
				}			
			}
		}
		return true;
	}

	public static void testData(List<model.Particle> modelParallel,Model model){
		
		
		assertTrue(model.p.size()==modelParallel.size());
		assertTrue(verifyM(model));
		assertTrue(verify(modelParallel));
		//System.out.println("model size : "+result.p.size());
		//System.out.println("modelParallel size : "+modelParallel.size());
		
	}
}
