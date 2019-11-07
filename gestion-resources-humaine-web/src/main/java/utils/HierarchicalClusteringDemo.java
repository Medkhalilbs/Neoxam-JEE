package utils;

/*******************************************************************************
 * Copyright (c) 2010 Haifeng Li
 *   
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interfaces.AdminServiceRemote;
import interfaces.UserServiceRemote;
import smile.clustering.HierarchicalClustering;
import smile.clustering.linkage.CompleteLinkage;
import smile.clustering.linkage.SingleLinkage;
import smile.clustering.linkage.UPGMALinkage;
import smile.clustering.linkage.UPGMCLinkage;
import smile.clustering.linkage.WPGMALinkage;
import smile.clustering.linkage.WPGMCLinkage;
import smile.clustering.linkage.WardLinkage;
import smile.plot.Palette;
import smile.plot.PlotCanvas;
import smile.math.Math;
import smile.plot.Dendrogram;
import smile.plot.ScatterPlot;

import org.rosuda.JRI.Rengine;
import org.rosuda.REngine.*;
import org.rosuda.REngine.Rserve.*;
/**
 *
 * @author Haifeng Li
 */
@SuppressWarnings("serial")
public class HierarchicalClusteringDemo extends ClusteringDemo {
    JComboBox<String> linkageBox;

    public HierarchicalClusteringDemo() {
        linkageBox = new JComboBox<>();
        linkageBox.addItem("Single");
        linkageBox.addItem("Complete");
        linkageBox.addItem("UPGMA");
        linkageBox.addItem("WPGMA");
        linkageBox.addItem("UPGMC");
        linkageBox.addItem("WPGMC");
        linkageBox.addItem("Ward");
        linkageBox.setSelectedIndex(0);

        optionPane.add(new JLabel("Linkage:"));
        optionPane.add(linkageBox);
    }

    @Override
    public JComponent learn() {
        long clock = System.currentTimeMillis();
        double[][] data = dataset[datasetIndex];
        int n = data.length;
        double[][] proximity = new double[n][];
        for (int i = 0; i < n; i++) {
            proximity[i] = new double[i+1];
            for (int j = 0; j < i; j++)
                proximity[i][j] = Math.distance(data[i], data[j]);
        }
//        System.out.println("proximity=");
//       
//        for (double[] ligne: proximity) {
//            for(double colonne : ligne) {
//               System.out.print(colonne);
//               System.out.print(" ");
//            }
//            System.out.println("*********************** nouvelle ligne");
//        }
        
        HierarchicalClustering hac = null;
        switch (linkageBox.getSelectedIndex()) {
            case 0:
                hac = new HierarchicalClustering(new SingleLinkage(proximity));
                break;
            case 1:
                hac = new HierarchicalClustering(new CompleteLinkage(proximity));
                break;
            case 2:
                hac = new HierarchicalClustering(new UPGMALinkage(proximity));
                break;
            case 3:
                hac = new HierarchicalClustering(new WPGMALinkage(proximity));
                break;
            case 4:
                hac = new HierarchicalClustering(new UPGMCLinkage(proximity));
                break;
            case 5:
                hac = new HierarchicalClustering(new WPGMCLinkage(proximity));
                break;
            case 6:
                hac = new HierarchicalClustering(new WardLinkage(proximity));
                break;
            default:
                throw new IllegalStateException("Unsupported Linkage");
        }
        System.out.format("Hierarchical clusterings %d samples in %dms\n", dataset[datasetIndex].length, System.currentTimeMillis()-clock);

        int[] membership = hac.partition(clusterNumber);
        int[] clusterSize = new int[clusterNumber];
        for (int i = 0; i < membership.length; i++) {
            clusterSize[membership[i]]++;
        }

        JPanel pane = new JPanel(new GridLayout(1, 3));
        PlotCanvas plot = ScatterPlot.plot(dataset[datasetIndex], pointLegend);
        plot.setTitle("Data");
        pane.add(plot);

        for (int k = 0; k < clusterNumber; k++) {
            double[][] cluster = new double[clusterSize[k]][];
            for (int i = 0, j = 0; i < dataset[datasetIndex].length; i++) {
                if (membership[i] == k) {
                    cluster[j++] = dataset[datasetIndex][i];
                }
            }

            plot.points(cluster, pointLegend, Palette.COLORS[k % Palette.COLORS.length]);
            System.out.println("color code="+"#"+Integer.toHexString( Palette.COLORS[k % Palette.COLORS.length].getRGB()).substring(2));

        }

        
        //plot = Dendrogram.plot("Dendrogram", hac.getTree(), hac.getHeight());
        //*********************************************************************
        try {
			//Runtime.getRuntime().exec("C:\\Program Files\\RStudio\\bin\\rstudio.exe C:\\Users\\X\\Desktop\\rscript.R");
			RConnection c = new RConnection("localhost", 6311);
			System.out.println(c.eval("R.version.string").asString());
			//Rengine re = new Rengine (new String[]{"--no-save"}, false, null); 
			if(c.isConnected()) {
	            System.out.println("Connected to RServe.");
	            if(c.needLogin()) {
	                System.out.println("Providing Login");
	                c.login("username", "password");
	            }
	            
	            c.eval("vwidth="+1024);
	            c.eval("vheight="+2160);
	            
	            REXP x;
	            System.out.println("Reading script...");
	            File file = new File("C:\\rscript.R");
	            try(BufferedReader br = new BufferedReader(new FileReader(file))) {
	                for(String line; (line = br.readLine()) != null; ) {
	                    System.out.println(line);
	                    x = c.eval(line);         // evaluates line in R
	                    System.out.println(x);    // prints result
	                }
	            } catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	        } else {
	            System.out.println("Rserve could not connect");
	        }

	        c.close();
	        System.out.println("Session Closed");
        } catch ( RserveException | REXPMismatchException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

        
        plot = Dendrogram.plot("Dendrogram", hac.getTree(), hac.getHeight());
        System.out.println("hac.getTree()="+Arrays.toString(hac.getTree()));
        
        
       	System.out.println("hac.getHeight()="+Arrays.toString(hac.getHeight()));		
       	
        String path = System.getProperty("jboss.home.dir")+"\\standalone\\deployments\\gestion-resources-humaine-ear.ear\\gestion-resources-humaine-web.war\\uploads\\dendrogramme.png";
        System.out.println(path);
        File file = new File(path);
        try {
			plot.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
//        Dendrogram dendrogram = new Dendrogram(hac.getTree(), hac.getHeight());
//        dendrogram.setID("Dendrogram");
//        
//        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = image.createGraphics();
//        printAll(g);
//        g.dispose();
//        try { 
//            ImageIO.write(image, "png", new File([location goes here]); 
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        

        
        
        //*********************************************************************

        plot.setTitle("Dendrogram");
        pane.add(plot);
        return pane;
    }

    @Override
    public String toString() {
        return "Hierarchical Agglomerative Clustering";
    }

    public static void main(String argv[]) throws NamingException {

		
        ClusteringDemo demo = new HierarchicalClusteringDemo();
        JFrame f = new JFrame("Agglomerative Hierarchical Clustering");
        f.setSize(new Dimension(1000, 1000));
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(demo);
        f.setVisible(true);
//        int[][][] test = {
//                {
//                 {1, -2, 3}, 
//                 {2, 3, 4}
//                }, 
//                { 
//                 {-4, -5, 6, 9}, 
//                 {1}, 
//                 {2, 3}
//                } 
//       };
//        System.out.println(test[0][0][0]);//1
//
//        System.out.println(test[1][0][0]);//-4
//        
//        System.out.println(test[1][0][1]);//-5
        
        
        
        
        

    }
}