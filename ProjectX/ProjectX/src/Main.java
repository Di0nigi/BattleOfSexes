import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.awt.Color.*;

public class Main implements MouseListener {
    public Random rd = new Random();
    public static int lifeexpectancy = 100000000;
    public static int a = 50;
    public static int b = 40;
    public static int c = 50;
    public static ItsTime timelord;
    public static int timepassed;
    public static int cicles= b+c;
    public static int population=5;
    public static int movements;
    public static int s;
    public static int fastmen=1;
    public static int fastwomen=1;
    public static int slowmen=0;
    public static int slowwomen=0;
    public static boolean allowed = true;

    public static boolean dominantgene=true;
    public static boolean rand=false;
    int width= 30;
    int height = 30;
    int amountmen = 30;
    int amountwomen = 10;
    ArrayList<Tile> Tlist= new ArrayList<Tile>();
    static ArrayList<Person> Alive=new ArrayList<Person>();
    ArrayList<Person> Moving=new ArrayList<Person>();
    static ArrayList<Person> Prison=new ArrayList<Person>();
    ArrayList<Tile> Forbidden=new ArrayList<>();
    ArrayList<int[]> popGrowth = new ArrayList<>();
    float totPeople =  (float) (fastmen+fastwomen+slowmen+slowwomen);
    float n1 = (float) slowwomen;
    float n2 = (float) fastwomen;
    float n3 = (float) fastmen;
    float n4 = (float) slowmen;


    MyFrame frame = new MyFrame();
    JPanel map = new JPanel();
    JLabel city = new JLabel();
    ImageIcon mapCity = new ImageIcon("ProjectX/ProjectX/src/cityImage.jpg");
    LowerPanel buttonPanel = new LowerPanel(frame);
    JLabel stats1 = new JLabel();
    JLabel stats2 = new JLabel();
    JLabel stats3 = new JLabel();
    JLabel stats4 = new JLabel();
    PieChart pieChart = new PieChart(totPeople,n1,n2,n3,n4, stats1, stats2, stats3, stats4, frame);
    GridLayout grid = new GridLayout(height, width);
    Tile graveyard= new Tile(0,0,new Color(0xFF000000));


    public Main() {
        ItsTime timehascome = new ItsTime(false);
        Main.timelord = timehascome;
        timehascome.start();
        frame.setLayout(null);
        frame.setVisible(true);
        frame.add(map);
        frame.add(pieChart);
        frame.add(graveyard);
        graveyard.setBounds(900,800,100,100);
        graveyard.setVisible(true);
        pieChart.setBounds(920,10,500,300);
        city.setIcon(mapCity);
        city.setBounds(10,10,880,880);
        frame.add(city);
        frame.add(buttonPanel);
        buttonPanel.setBounds(920,350,450,500);
        map.setBounds(10,10,880,880);
        map.setOpaque(false);
        map.setLayout(grid);
        for (int i=0; i<width; i++){
            for (int n=0; n<height; n++){
                Tile p = new Tile(i, n, BLACK);
                map.add(p);
                //p.setLayout(g);
                Tlist.add(p);

            }
        }
        int y=0;
        //Man Ancestor =new Man(0,0,this);
        //Ancestor.fast=true;
        //Woman Ancestor2= new Woman(7,7,this);
        /*for (int m = 0; m < population; m++) {
            Person r=God(Ancestor,Ancestor2);
            Thread provvisorio=r.getRunningon();
            provvisorio.start();
           // System.out.println(y++);
        }*/
        initialize(fastmen,fastwomen,slowmen,slowwomen);





        }

    public  int[][] timemove (clockTile t){
        movements++;
        if(movements%10==0){
            timepassed++;
            ItsTime timehascome = new ItsTime(this,true);
            timehascome.start();
            timelord = timehascome;
            movements=0;
        }

        int x= t.meetingtile.coor_x;
        int y= t.meetingtile.coor_y;
        int[][] pos={{x+0,y+0},{x+1,y+0},{x+(-1),y+0},{x+0,y+1},{x+0,y+(-1)},{x+1,y+1},{x+1,y+(-1)},{x+(-1),y+1},{x+(-1),y+(-1)}};
        if(x==0){
            pos[2][0]=x;
            pos[7][0]=x;
            pos[8][0]=x;
        }
        else if (x== width-1) {
            pos[1][0]=x;
            pos[5][0]=x;
            pos[6][0]=x;
        }
        if(y==0){
            pos[4][1]=y;
            pos[6][1]=y;
            pos[8][1]=y;
        }
        else if(y== width-1){
            pos[3][1]=y;
            pos[5][1]=y;
            pos[7][1]=y;
        }

        int ind= (int) (Math.random()* pos.length);

        //if (t.meetingtile.tileon!=null){
        //  t.meetingtile.tileon.popoccupants(t);}
        Tile tile=getcoor(pos[ind][0],pos[ind][1]);
        //for (Tile tile: Tlist) {
        //if (tile.coor_x == pos[ind][0] && tile.coor_y == pos[ind][1]){
        //System.out.println("udfhdffdhdghdgifh");

        t.meetingtile.tileon=tile;
        tile.add(t.meetingtile);
        //tile.setwhite();
        //tile.addoccupants(t);
        int xt= tile.coor_x;
        int yt =tile.coor_y;
        t.meetingtile.coor_x=xt;
        t.meetingtile.coor_y=yt;
        frame.repaint(); frame.revalidate();
        int[][] pos2={{xt+0,yt+0},{xt+1,yt+0},{xt+(-1),yt+0},{xt+0,yt+1},{xt+0,yt+(-1)},{xt+1,yt+1},{xt+1,yt+(-1)},{xt+(-1),yt+1},{xt+(-1),yt+(-1)}};
        return pos2;
        //}
        //}
        //return pos;
    }

    public  int[][] move (Man t){

        int x= t.meetingtile.coor_x;
        int y= t.meetingtile.coor_y;
        int[][] pos={{x,y},{x+1,y},{x+(-1),y},{x,y+1},{x,y+(-1)},{x+1,y+1},{x+1,y+(-1)},{x+(-1),y+1},{x+(-1),y+(-1)}};
       /* if (x==0 && y>0){
            pos[2]= new int[]{x, y};
            pos[7]=new int[] {x,y+1};
            pos[8]= new int[] {x, y+(-1)};

        }
        else if (y==0 && x>0){
            pos[4]=new int[]{x,y};
            pos[6]=new int[]{x+1,y};
            pos[8]=new int[]{x+(-1),y};
        }
        else if (y==0 && x==0){
            pos[2]=new int[]{x,y};
            pos[4]=new int[]{x,y};
            pos[6]=new int[]{x+1,y};
            pos[7]=new int[]{x,y+1};
            pos[8]=new int[]{x,y};
        }
        if(x==width-1 && y<width-1){
            pos[1]=new int[]{x, y};
            pos[5]= new int[]{x,y+1};
            pos[6]= new int[]{x, y+(-1)};

        }
        else if(x<width-1 && y==width-1){
            pos[3]=new int[]{x,y};
            pos[5]=new int[]{x+1,y};
            pos[7]= new int[]{x+(-1),y};
        }
        else if(x==width-1 && y==width-1){
            pos[1]=new int[]{x, y};
            pos[5]= new int[]{x+(-1),y+(-1)};
            pos[6]= new int[]{x, y+(-1)};
            pos[3]=new int[]{x,y};
            pos[7]= new int[]{x+(-1),y};


        }*/
        if(x==0){
            pos[2][0]=x;
            pos[7][0]=x;
            pos[8][0]=x;
        }
        else if (x== width-1) {
            pos[1][0]=x;
            pos[5][0]=x;
            pos[6][0]=x;
        }
        if(y==0){
            pos[4][1]=y;
            pos[6][1]=y;
            pos[8][1]=y;
        }
        else if(y== width-1){
            pos[3][1]=y;
            pos[5][1]=y;
            pos[7][1]=y;
        }

        int ind= (int) (Math.random()* pos.length);

            if (t.meetingtile.tileon!=null){
                t.meetingtile.tileon.popoccupants(t);}
            Tile tile=getcoor(pos[ind][0],pos[ind][1]);
            //for (Tile tile: Tlist) {
                //if (tile.coor_x == pos[ind][0] && tile.coor_y == pos[ind][1]){
                    //System.out.println("udfhdffdhdghdgifh");

            t.meetingtile.tileon=tile;
            tile.add(t.meetingtile);
            //tile.setwhite();
            tile.addoccupants(t);
            int xt= tile.coor_x;
            int yt =tile.coor_y;
            t.meetingtile.coor_x=xt;
            t.meetingtile.coor_y=yt;
            frame.repaint(); frame.revalidate();
            int[][] pos2={{xt,yt},{xt+1,yt},{xt+(-1),yt},{xt,yt+1},{xt,yt+(-1)},{xt+1,yt+1},{xt+1,yt+(-1)},{xt+(-1),yt+1},{xt+(-1),yt+(-1)}};
            return pos2;
                //}
            //}
        //return pos;
    }
    public synchronized void checker(){

        for (Person p:Prison) {
            //System.out.println("sdsd");
            p.Startagain(timepassed);
            //System.out.println(timepassed);
        }
    }
    public  int[][] move(Woman t){
        int x= t.meetingtile.coor_x;
        int y= t.meetingtile.coor_y;
        int[][] pos={{x+0,y+0},{x+1,y+0},{x+(-1),y+0},{x+0,y+1},{x+0,y+(-1)},{x+1,y+1},{x+1,y+(-1)},{x+(-1),y+1},{x+(-1),y+(-1)}};
       /* if (x==0 && y>0){
            pos[2]= new int[]{x, y};
            pos[7]=new int[] {x,y+1};
            pos[8]= new int[] {x, y+(-1)};

        }
        else if (y==0 && x>0){
            pos[4]=new int[]{x,y};
            pos[6]=new int[]{x+1,y};
            pos[8]=new int[]{x+(-1),y};
        }
        else if (y==0 && x==0){
            pos[2]=new int[]{x,y};
            pos[4]=new int[]{x,y};
            pos[6]=new int[]{x+1,y};
            pos[7]=new int[]{x,y+1};
            pos[8]=new int[]{x,y};
        }
        if(x==width-1 && y<width-1){
            pos[1]=new int[]{x, y};
            pos[5]= new int[]{x,y+1};
            pos[6]= new int[]{x, y+(-1)};

        }
        else if(x<width-1 && y==width-1){
            pos[3]=new int[]{x,y};
            pos[5]=new int[]{x+1,y};
            pos[7]= new int[]{x+(-1),y};
        }
        else if(x==width-1 && y==width-1){
            pos[1]=new int[]{x, y};
            pos[5]= new int[]{x+(-1),y+(-1)};
            pos[6]= new int[]{x, y+(-1)};
            pos[3]=new int[]{x,y};
            pos[7]= new int[]{x+(-1),y};


        }*/
        if(x==0){
            pos[2][0]=x;
            pos[7][0]=x;
            pos[8][0]=x;
        }
        else if (x== width-1) {
            pos[1][0]=x;
            pos[5][0]=x;
            pos[6][0]=x;
        }
        if(y==0){
            pos[4][1]=y;
            pos[6][1]=y;
            pos[8][1]=y;
        }
        else if(y== width-1){
            pos[3][1]=y;
            pos[5][1]=y;
            pos[7][1]=y;
        }

        int ind= (int) (Math.random()* pos.length);

        if (t.meetingtile.tileon!=null){
            t.meetingtile.tileon.popoccupants(t);}
        Tile tile=getcoor(pos[ind][0],pos[ind][1]);
        //for (Tile tile: Tlist) {
        //if (tile.coor_x == pos[ind][0] && tile.coor_y == pos[ind][1]){
        //System.out.println("udfhdffdhdghdgifh");

        t.meetingtile.tileon=tile;
        tile.add(t.meetingtile);
        //tile.setwhite();
        tile.addoccupants(t);
        int xt= tile.coor_x;
        int yt =tile.coor_y;
        t.meetingtile.coor_x=xt;
        t.meetingtile.coor_y=yt;
        frame.repaint(); frame.revalidate();
        int[][] pos2={{xt,yt},{xt+1,yt},{xt+(-1),yt},{xt,yt+1},{xt,yt+(-1)},{xt+1,yt+1},{xt+1,yt+(-1)},{xt+(-1),yt+1},{xt+(-1),yt+(-1)}};
        return pos2;
        //}
        //}
        //return pos;
    }
    public  synchronized void slowdown(){
        int[] f=new int[4];
        f[0]=1;
        f[2]=1;
        f[3]=1;
        f[1]=1;

    }
    public void initialize(int fm,int fw,int sm,int sw){
        clockTile clock=new clockTile(8,8,this);
        Thread clkThread= new Thread(clock);
        clkThread.start();
        System.out.println("4");
        for (int i = 0; i < fm; i++) {
            Man m=new Man(0,0,this);
            ArrayList<Boolean> g = new ArrayList<>();
            g.add(true);
            g.add(true);
            ArrayList<Boolean>gen=setDominant(g);
            m.genes=gen;
            //m.fast=true;
            m.fenotipo();
            m.birthday = timepassed;
            Thread thread=new Thread(m);
            m.runningon=thread;
            m.runningon.start();
            Alive.add(m);
        }
        for (int i = 0; i < sm; i++) {
            Man m=new Man(0,0,this);
            ArrayList<Boolean> g = new ArrayList<>();
            g.add(false);
            g.add(false);
            ArrayList<Boolean>gen=setDominant(g);
            m.genes=gen;
           // m.fast=false;
            m.fenotipo();
            m.birthday = timepassed;
            Thread thread=new Thread(m);
            m.runningon=thread;
            m.runningon.start();
            Alive.add(m);

        }
        for (int i = 0; i < fw; i++) {
            Woman w=new Woman(29,29,this);
            ArrayList<Boolean> g = new ArrayList<>();
            g.add(true);
            g.add(true);
            ArrayList<Boolean>gen=setDominant(g);
            w.genes=gen;
           // w.fast=true;
            w.fenotipo();
            w.birthday = timepassed;
            Thread thread=new Thread(w);
            w.runningon=thread;
            w.runningon.start();
            Alive.add(w);


        }
        for (int i = 0; i < sw; i++) {
            Woman w=new Woman(width -1,height -1,this);
            ArrayList<Boolean> g = new ArrayList<>();
            g.add(false);
            g.add(false);
            ArrayList<Boolean>gen=setDominant(g);
            w.genes=gen;
            //w.fast=false;
            w.fenotipo();
            w.birthday = timepassed;
            Thread thread=new Thread(w);
            w.runningon=thread;
            w.runningon.start();
            Alive.add(w);


        }
        frame.repaint(); frame.revalidate();
    }

    public void meet(Tile t){
        Man first;
        Woman second;
        if(t.occupants.size()>=2){
            try {
            String genderfirst=t.occupants.get(1).getgender();
            String gendersecond = t.occupants.get(0).getgender();
            //System.out.println(t.occupants.get(0)+" "+t.occupants.get(1));
            //t.occupants.get(1).getFrame().slowdown();
            //t.occupants.get(0).getFrame().slowdown();

            if(genderfirst.equals("male") && gendersecond.equals("female")){
                //System.out.println("all good");
                first= (Man) t.occupants.get(1);
                second =(Woman) t.occupants.get(0);
            }
            else if(gendersecond.equals("male") && genderfirst.equals("female")){
               // System.out.println("all good");
                first= (Man) t.occupants.get(0);
                second =(Woman) t.occupants.get(1);
            }
            else { //System.out.println("all good");
                return;}

            if (!first.single || !second.single){
                return;
            }
            if ((55 > first.getage() && first.getage() < 12) || (55 > second.getage() && second.getage()<12)){
                return;
            }
            if(Compatibility(first.type(),second.type())){
                gettogether(first, second);
            }
            else return;
            }
            catch (java.lang.IndexOutOfBoundsException e){
                System.out.println("out of bound");
            }
            catch (java.lang.NullPointerException e){
            System.out.println("null pointer");
            }}
        else{
            return;}

    }
    public boolean Compatibility(boolean type_a, boolean type_b){
        if(type_a==true && type_b == false){
            return false;
        }
        else return true;




    }
    public int[] Costs(boolean type_a, boolean type_b){
        int[] costs= new int[4];
        int costm;
        int costw;
        if(type_a){
            costm=0;
            costw=0+b;
        }
        else{
            if(type_b){
                costm=0+b/2;
                costw=0+b/2;
            }
            else{
                costm=c+b/2;
                costw=c+b/2;
            }
        }
        costs[0]=costm;
        costs[1]=costw;


        return costs;
    }
    public Tile getcoor(int x, int y){
        //assert (x< width && y< height );

        int z = (x)*width + (y);
        //System.out.println(x+" "+width+" "+y);
        //System.out.println(z);
        return Tlist.get(z);
    }

    public void localmeet(int[][] coor){
        meet(getcoor(coor[0][0],coor[0][1]));
        //Tile[] t=new Tile[8];
        /*for (int i = 0; i <9; i++) {
            for (Tile tile:Tlist) {
                if(tile.coor_x==coor[0][0] && tile.coor_y==coor[0][1]){
                    meet(tile);
                }

            }

        }*/


    }
    public void gettogether(Man m, Woman w){
            int[]pausetime=Costs(m.type(),w.type());
            m.Pause(timepassed,pausetime[0]);
            w.Pause(timepassed,pausetime[1]);
            //System.out.println("io");
            if(!Forbidden.contains(m.meetingtile.tileon))
                Forbidden.add(m.meetingtile.tileon);
            m.Partner=w;
            w.Partner = m;
            Court(true,m);
            Court(true,w);
          //System.out.println(Prison.size());
    }
    public ArrayList<Boolean> inheritance(Man m, Woman w){
        int x= (int) (Math.random()*2);
        int y= (int) (Math.random()*2);
        ArrayList<Boolean> disp=new ArrayList<>();
        disp.add(m.genes.get(x));
        disp.add(w.genes.get(y));

        return disp;

    }
    synchronized public static void warten(){
        for (Person i : Alive){
            try {
                i.getRunningon().wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public Person God(Man parent1, Woman parent2, int x,int y){
        String[] gender={"male","female"};
        int k=(int)(Math.random()*2);
        if(gender[k].equals("male")){
            Man m=new Man(x,y,this);
            m.genes=setDominant(inheritance(parent1,parent2));
            m.fenotipo();
            m.birthday = timepassed;
            //Thread thread=new Thread(m);
            //m.runningon=thread;
            Alive.add(m);
            //thread.start();
            return m;
        }
        else {
            Woman w=new Woman(x,y,this);
            w.genes=inheritance(parent1,parent2);
            w.fenotipo();
            w.birthday = timepassed;
            //Thread thread=new Thread(w);
            //w.runningon=thread;
            Alive.add(w);
            //thread.start();
            return w;

        }

    }

    public void rem(Man p){
        p.meetingtile.setwhite();
        p.meetingtile.color= lightGray;
        //this.graveyard.add(p.meetingtile);
        p.meetingtile.tileon.remove(p.meetingtile);
        System.out.println(p.getage());
        p.meetingtile.revalidate();

    }
    public void rem(Woman p){
        p.meetingtile.color= lightGray;
        //this.graveyard.add(p.meetingtile);
        p.meetingtile.tileon.remove(p.meetingtile);
        System.out.println(p.getage());
        p.meetingtile.revalidate();


    }
    public void makebabies(Man one, Woman two) {
        int max = a / 10;
        int x = two.meetingtile.coor_x;
        int y = two.meetingtile.coor_y;
        int amount = rd.nextInt(max);
        for (int ja = 1;ja <amount; ja++){
            God(one,two,x,y);
        }
        System.out.println(Integer.toString(amount)+"new babies");
    }
    public void Court(Boolean v,Person p){
        if(v){
            if(!Prison.contains(p))
                Prison.add(p);
            //System.out.println(Prison.size());
        }
        else {
            Prison.remove(p);
        }
    }
    public  void GrimReaper(Man p){
        Alive.remove(p);
        p.stop=true;
        this.rem(p);
        //System.out.println("eo");
    }
    public  void GrimReaper(Woman p){
        //System.out.println("eo");
        Alive.remove(p);
        p.stop=true;
        this.rem(p);
    }
    public ArrayList<Boolean> setDominant(ArrayList<Boolean> g){
        if(dominantgene){
            if(g.contains(true)){
                Collections.swap(g,g.indexOf(true),0);
            }

        }
        else {
            if(g.contains(false)){
                Collections.swap(g,g.indexOf(false),0);
            }
        }
        return g;
    }



    public static void main(String[] args) throws InterruptedException {
        Main timer = new Main();
        int[] f= new int[2];
      /*  while(true){
            if(timer.Prison.size()>0)
                System.out.println(timer.Prison.size());
            try {
                //System.out.println(timer.Prison.size());
                for (Person p:timer.Prison) {
                    System.out.println("sdsd");
                    p.Startagain(timer.timepassed);
                    //System.out.println(timepassed);
                }
            }catch (java.util.ConcurrentModificationException e){
                System.out.println("error");
            }*/

    }

    public ArrayList<int[]> growthPopulation(int time, ArrayList<int[]> popTime) {
        timepassed = time;
        popGrowth = popTime;
        if(timepassed % 20 == 0) {
            int[] x = new int[2];
            x[0] = timepassed;
            x[1] = 20;
            popGrowth.add(x);
        }
        return popGrowth;
    }

    public float[][] statsPopulation(float totPeople, float fmen, float fwomen, float smen, float swomen){
        float [][] statsPop = new float[3][4];  //general overview , fast/slow, men/women
        statsPop [0][0] = (float) ((float) (Math.round((fmen/totPeople) * 10000.0)/100.0));   //fmen
        statsPop [0][1] = (float) ((float) (Math.round((fwomen/totPeople) * 10000.0)/100.0)); //fwomen
        statsPop [0][2] = (float) ((float) (Math.round((smen/totPeople) * 10000.0)/100.0));   //smen
        statsPop [0][3] = (float) ((float) (Math.round((swomen/totPeople) * 10000.0)/100.0));  //swomen

        statsPop [1][0] = (float) ((float) (Math.round((fmen+fwomen)/totPeople) * 10000.0)/100.0);  //fast people
        statsPop [1][1] = (float) ((float) (Math.round((smen+swomen)/totPeople) * 10000.0)/100.0);  //slow people

        statsPop [2][0] = (float) ((float) (Math.round((fmen+smen)/totPeople) * 10000.0)/100.0);  //men
        statsPop [2][1] = (float) ((float) (Math.round((fwomen+swomen)/totPeople) * 10000.0)/100.0);  //women

        return statsPop;
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    } @Override public void mousePressed(MouseEvent e) {} @Override public void mouseReleased(MouseEvent e) {

    } @Override public void mouseEntered(MouseEvent e) {

    } @Override public void mouseExited(MouseEvent e) {

    }



}