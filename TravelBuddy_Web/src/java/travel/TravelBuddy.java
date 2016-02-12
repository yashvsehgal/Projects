/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.view.Viewer;

/**
 *
 * @author yash
 */
public class TravelBuddy {

    DAO dao;
    static HashMap<String, Integer> pathcost;
    static Graph graph;
    static SpriteManager sman;
    static ArrayList<String> edgeList;
    static String mode;
    static HashMap <String,String>imageMap ;
    static Connection con;
    static ArrayList<String> paths;
    static ArrayList<String> nodeList;
    static String source;
    static String destination;
    
    public TravelBuddy() {
        dao = new DAO();
        con = dao.getConnection();
        pathcost = new HashMap<>();
        graph = new SingleGraph("TravelBuddy");
        sman = new SpriteManager(graph);
        edgeList = new ArrayList<>();
        imageMap = new HashMap<>();
        nodeList = new ArrayList<>();
        graph.addAttribute("ui.stylesheet","url('http://localhost:8080/TravelBuddy_Web/resources/stylesheet')");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
       graph.display();
       
        
    }
    
    public static  ArrayList<String> splitter(String path) throws SQLException, InterruptedException {

        char[] dat = path.toCharArray();

        for (int i = 0; i < dat.length; i++) {

            if (i + 1 > dat.length - 1) {
                break;
            }

            String src = Character.toString(dat[i]);
            String des = Character.toString(dat[i + 1]);
            String id = src + des;
           
            if(!edgeList.contains(id)) {
            edgeList.add(id); 
            graph.addEdge(id, src, des,true);
            
            }
  
        }
       
        return edgeList;
    }


    public ArrayList<String> edgeCost(String path) throws SQLException, InterruptedException {
        
        int cost = 0;
        char[] dat = path.toCharArray();

        for (int i = 0; i < dat.length; i++) {

            if (i + 1 > dat.length - 1) {
                break;
            }

            cost = cost + getCost(dat[i], dat[i + 1]);

        System.out.println("Cost for path " + path + " is " + cost);
        String sql = "UPDATE route SET PATHCOST=" + "'" + cost + "'" + " WHERE PATH ='" + path + "'";
        con.prepareStatement(sql).executeUpdate();
        pathcost.put(path, cost);
        System.out.println("\n");
        
       
        }
         return edgeList;
    }
    
    
    public void createImageMap(String path) throws SQLException, InterruptedException {
        
        
        int cost = 0;
        Sprite s = null;
        String type =null;

        char[] dat = path.toCharArray();

        for (int i = 0; i < dat.length; i++) {

            if (i + 1 > dat.length - 1) {
                break;
            }

            String src = Character.toString(dat[i]);
            String des = Character.toString(dat[i + 1]);
            
            String sql1 = "select type from incidenttype where TYPEID IN (select INCIDENTTYPEID from incident where SOURCE = '" +src+"' AND DESTINATION ='"+des+"') AND MODE = '" +mode +"'";

            ResultSet rs = con.prepareStatement(sql1).executeQuery();
           
            while (rs.next()) {
            type = rs.getString("type");
 
            }
            
            String id = src + des;
            imageMap.put(id,type);
     
    }
       
        }

    
    public static void addSprite(HashMap  <String,String> imageMap) {
        
      for(Map.Entry<String,String> entry : imageMap.entrySet())  {
          
          String id = entry.getKey();
          String type = entry.getValue();
          
          sman.addSprite(id);
          sman.getSprite(id).attachToEdge(id);
          sman.getSprite(id).setAttribute("ui.class",type);
          sman.getSprite(id).setPosition(0.5);
          
          System.out.println(id + "  " +type);
      }
    }

    public ArrayList getPaths(String source, String desitination) throws SQLException {
        ArrayList<String> path = new ArrayList<>();

        try {
            ResultSet rs = con.prepareStatement("Select * from route where SOURCE = " + "'" + source + "'").executeQuery();

            while (rs.next()) {

                path.add(rs.getString("PATH"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(TravelBuddy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return path;
    }

    public int getCost(char source, char destination) {

       
        String sql = "select COST from incidenttype where TYPEID IN (select INCIDENTTYPEID from incident where SOURCE = '" + source + " ' AND DESTINATION = '" + destination + "')";

        //System.out.println(sql);
        int cost = 0;
        try {
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            rs.next();
            cost = Integer.parseInt(rs.getString("COST"));
            //System.out.println(cost);

        } catch (SQLException ex) {
            Logger.getLogger(TravelBuddy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cost;

    }
    
    public static void refresh () throws InterruptedException, SQLException {
       
        graph.clear();
        edgeList.clear();
        sman = new SpriteManager(graph);
        
        addNodes(paths);
        graph.addAttribute("ui.stylesheet","url('http://localhost:8080/TravelBuddy_Web/resources/stylesheet')");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        graph.getNode(source).addAttribute("ui.class","start");
        graph.getNode(destination).addAttribute("ui.class","end");
        
        
    }
    
    public static void addNodes (ArrayList <String> paths) throws SQLException, InterruptedException {
        
        
              for(String path: paths) {
            
                for(int j = 0 ; j < path.length();j++) {
            
                char node = path.charAt(j);

             if(!nodeList.contains(Character.toString(node))) {
                 
                 nodeList.add(Character.toString(node));
             }
            
        }

        }
        
              for (String node: nodeList) {
               
               graph.addNode(node);
               
               
           }
              
           for (Node node : graph) { //Giving name to all nodes.
     
                  node.addAttribute("ui.label","     " +node.getId());

              }
 
            for (String path : paths) { //Adding Edge to Graph
                  
                  splitter(path);
                  
              }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, InterruptedException, SQLException {

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        TravelBuddy b = new TravelBuddy();
        source = args[0];
        destination = args[1];
        mode = args[2];
        paths = b.getPaths(source,destination);
        
        b.addNodes(paths);

         graph.getNode(source).addAttribute("ui.class","start");
         graph.getNode(destination).addAttribute("ui.class","end");
            boolean start = true;  
        while(true) {
           
                   System.out.println("Running again");

                   for(String path: paths) {
                       
                       b.edgeCost(path);
                       b.createImageMap(path);
                   }
                   if(!start) {
                  refresh(); }
            addSprite(imageMap);           
                
start = false;
        Entry<String, Integer> min = null;
        for (Entry<String, Integer> entry : pathcost.entrySet()) {
            if (min == null || min.getValue() > entry.getValue()) {
                min = entry;
            }

        }

         String path = min.getKey();
         
         char[] dat = path.toCharArray();

        for (int i = 0; i < dat.length; i++) {

            if (i + 1 > dat.length - 1) {
                break;
            }
            
           String startNode = Character.toString(dat[i]);
           String endNode = Character.toString(dat[i+1]);
           
          String edge = startNode + endNode;
         
          graph.getEdge(edge).addAttribute("ui.class","cheapest");
          
        }

        System.out.println("The cheapest path to travel is " + min.getKey()); // 0.1
       Thread.sleep(30000);
      
    }
    }

}