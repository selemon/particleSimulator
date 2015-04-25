package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ModelParallel extends RecursiveTask<ModelParallel>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final ForkJoinPool pool = new ForkJoinPool();

	public static final double size=900;
	public static final double gravitationalConstant=0.1; //this variable holds the constant for the gravity
	public static final double lightSpeed=10;
	public static final double timeFrame=1;
	public final List<Particle> p= Collections.synchronizedList(new ArrayList<Particle>()); //list of particles
	//list of particle that can be drawn
	public volatile List<DrawableParticle> pDraw=Collections.synchronizedList(new ArrayList<DrawableParticle>());


	public ModelParallel(){}
	public ModelParallel(List<Particle> p, List<DrawableParticle> pDraw){
		this.p.addAll(p);
		this.pDraw.addAll(pDraw);
	}

	public ModelParallel(List<Particle> p) {
		this.p.addAll(p);
	}
	@Override
	protected ModelParallel compute() {

		//System.out.println(p.size());
		if(this.p.size()<=10){return step1();}
		else{
			int middle = p.size() / 2;
			int m = pDraw.size()/2;
			//			System.out.println(middle);
			List<Particle> l = p.subList(0, middle);
			List<Particle> r = p.subList(middle, p.size());
			List<DrawableParticle> lp = pDraw.subList(0, m);
			List<DrawableParticle> rp = pDraw.subList(m, pDraw.size());
			ModelParallel left = new ModelParallel(l, lp);
			//		    left.fork();
			ModelParallel right = new ModelParallel(r, rp);

			// System.out.println("forkjoin boiiii");
			invokeAll(left, right);
			List<Particle> result;
			List<DrawableParticle> drawP;
			
			left = left.step1();
			right = right.step1();
			
			result = merge(left.p, right.p);
			drawP = merge(left.pDraw, right.pDraw);
			
//			System.out.println(result.size());

//			p.clear();
//			p.addAll(result);
			//pDraw = drawP;
			
			return new ModelParallel(result, drawP);
		}
	}

	private <T extends Object> List<T> merge(List<T> compute, List<T> join) {
		// TODO Auto-generated method stub
		List<T> result = new ArrayList<>();
		for(int i =0; i<compute.size();i++){
			result.add(compute.get(i));
		}
		for(int i =0; i<join.size();i++){
			result.add(join.get(i));
		}
		return result;
	}
	private ModelParallel step1() {
		for(Particle p:this.p){p.interact(this);}
		mergeParticles();
		for(Particle p:this.p){p.move(this);}
		updateGraphicalRepresentation();
//				System.out.println(pDraw.size());
		return new ModelParallel(this.p, this.pDraw);
	}
	private void updateGraphicalRepresentation() {
		//				System.out.println(pDraw.size());
		ArrayList<DrawableParticle> d=new ArrayList<DrawableParticle>();
		Color c=Color.ORANGE;
		for(Particle p:this.p){
			d.add(new DrawableParticle((int)p.x, (int)p.y, (int)Math.sqrt(p.mass),c ));
		}

		this.pDraw = (d);//atomic update
		//				System.out.println(d.size());
	}
	private void mergeParticles() {
		Stack<Particle> deadPs=new Stack<Particle>();
		//System.out.println();
		for(Particle p:this.p){ if(!p.impacting.isEmpty()){deadPs.add(p);}; }
		this.p.removeAll(deadPs);
		while(!deadPs.isEmpty()){
			//System.out.println("impacted gyeah boi");
			Particle current=deadPs.pop();
			Set<Particle> ps=getSingleChunck(current);
			deadPs.removeAll(ps);
			this.p.add(mergeParticles(ps));
		}

	}
	public Particle mergeParticles(Set<Particle> ps) {
		double speedX=0;
		double speedY=0;
		double x=0;
		double y=0;
		double mass=0;
		for(Particle p:ps){  mass+=p.mass; }
		for(Particle p:ps){
			x+=p.x*p.mass;
			y+=p.y*p.mass;
			speedX+=p.speedX*p.mass;
			speedY+=p.speedY*p.mass;
		}
		x/=mass;
		y/=mass;
		speedX/=mass;
		speedY/=mass;
		return new Particle(mass,speedX,speedY,x,y);
	}


	public Set<Particle> getSingleChunck(Particle current) {
		Set<Particle> impacting=new HashSet<Particle>();
		impacting.add(current);
		while(true){
			Set<Particle> tmp=new HashSet<Particle>();
			for(Particle pi:impacting){tmp.addAll(pi.impacting);}
			boolean changed=impacting.addAll(tmp);
			if(!changed){break;}
		}
		//now impacting have all the chunk of collapsing particles
		return impacting;
	}
	public ModelParallel step(){
		//		System.out.println(this.pDraw.size());
		return pool.invoke(this);
	}

}
