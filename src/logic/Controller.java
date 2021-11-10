package logic;

import java.io.File;
import java.util.ArrayList;

public class Controller {

    private static char [][] mapPicture;
    private static char [][] mapPictureFilter;
    private static MapData map;
    private static ArrayList<Node> res;
    private static String pathToFile;//"C:\\Users\\rayzo\\Desktop\\image1.png"
    private static File toFile;
    private static ArrayList<myPoint> pointsPath;
    private static DinamicLet let;


    public static void start(){
        Pixel obj = new Pixel(toFile);

        map = obj.createMap(obj.getPicture());
        //map.processing_test('9');
        mapPicture = map.getMap();
        mapPicture = MapData.resize(-2, mapPicture);
    }

    public static void setFile(File file){
        toFile = file;
    }

    public static char [][] filter(char lvl){
        map.processing_test(lvl);
        mapPictureFilter = map.getMap();
        mapPictureFilter = MapData.resize(-2, mapPictureFilter);
        return mapPictureFilter;
    }

    public static ArrayList<logic.Node> getPath(myPoint s, myPoint e){
        return getPath(s, e, mapPicture);
    }

    public static ArrayList<Node> getPathNode(){
        return res;
    }

    public static int status(myPoint pStart, myPoint pEnd){
        return status(pStart, pEnd, mapPicture);
    }

    public static int status(myPoint pStart, myPoint pEnd, char[][] mass){
        if (getPath(pStart, pEnd,  mass) == null){
            return 1;
        } else {
            pointsPath = new ArrayList<>();
            for (Node node : res) {
                pointsPath.add(node.getPosition());
            }

            return 0;
        }
    }

    public static ArrayList<myPoint> getPointsPath(){
        return pointsPath;
    }

    public static ArrayList<logic.Node> getPath(myPoint s, myPoint e, char[][] mass){
        PathFinder finder2 = new PathFinder(mass);
        ArrayList<Node> result;
        long m1 = System.currentTimeMillis();
        if(!finder2.checkAvalibleSaE(s, e) ){
            result = new ArrayList<>();
            result.add(new Node(e));
            result.get(0).setPastNode(new Node(s));
            result.get(0).getPastNode().setPastNode(null);
            result.add(result.get(0).getPastNode());
        } else {

            result = finder2.createPath2(s, e);//new logic.myPoint(19, 375), new logic.myPoint(1816, 525)
            System.out.println(result);
            //new logic.myPoint(10, 120), new logic.myPoint(600, 184)
            long m2 = System.currentTimeMillis();

            System.out.println("time = " + (m2 - m1));
        }

            res = result;

            MapData.paintMap(finder2.drawPath(result, s, e), "");

        return result;
    }

    public static ArrayList<myPoint> getPoints(){
        ArrayList<myPoint> p = MapData.normakizePath(res, mapPicture);
        System.out.println("size = " + p.size() + " p = " + p);
        return p;
    }

    public static ArrayList<myPoint> getDinamicZone(int x, int y, int r){
        let = new DinamicLet();
        return let.dinamicLet(new myPoint(x, y), r);
    }

    public static boolean checkFindEqPoints(ArrayList<myPoint> points, ArrayList<Node> path){
        return let.findPoints(points, path).size() == 0;
    }

    public static ArrayList<myPoint> findPathOutDinamicZone(ArrayList<myPoint> points, ArrayList<Node> path, char[][]mass){
        ArrayList<Node> ledNode = let.findPoints(points, path);
        if(ledNode.size() == 0){
           return null;
        }
        myPoint [] sae = let.analizeDinamicLet(ledNode, points, path);
        System.out.println("=======================================================================================================================================");
        System.out.println("s = " + sae[0] + " e = " + sae[1]);

        if(status(sae[0], sae[1], mass) == 1){
            return null;
        }
        return pointsPath;
    }

    public static boolean checkSaE(myPoint s, myPoint e, myPoint c, int r){
        System.out.println("====================================================================================================");
        System.out.println("The target is not available : " + (((s.getX()*s.getX())+(s.getY()*s.getY())) <= r*r || ((e.getX()*e.getX())+(e.getY()*e.getY())) <= r*r));
        System.out.println("x1*x1 = " + (Math.pow((s.getX() - c.getX()), 2) + "y1*y1 = " +  Math.pow((s.getY() - c.getY()), 2)) + "x2*x2 = " + (Math.pow((e.getX() - c.getX()), 2)) + "y2*y2 = " +  Math.pow((e.getY() - c.getY()), 2) + "r*r = " + r*r);
        return ((Math.pow((s.getX() - c.getX()), 2) + Math.pow((s.getY() - c.getY()), 2)) <= r*r || (Math.pow((e.getX() - c.getX()), 2) + Math.pow((e.getY() - c.getY()), 2)) <= r*r);
    }

    public static char[][]getMass(){
        MapData.paintMap(mapPicture, "\t");
        return mapPicture;
    }
}
