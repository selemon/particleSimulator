package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import model.DrawableParticle;
import model.ModelParallel;
import model.Particle;

public class Canvas extends JPanel{
  ModelParallel m; Canvas(ModelParallel m2){this.m=m2;}
  @Override public void paint(Graphics gg){
    Graphics2D g=(Graphics2D)gg;
    g.setBackground(Color.DARK_GRAY);
    g.clearRect(0, 0, getWidth(),getHeight());
//    System.out.println(GuiParallel.model.pDraw.size());
    for(DrawableParticle p: GuiParallel.model.pDraw){p.draw(g);}
  }
  @Override public Dimension getPreferredSize(){
    return new Dimension((int)ModelParallel.size, (int)ModelParallel.size);
    }
}
