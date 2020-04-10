import java.util.concurrent.*;
public class Cat_And_Mice  
{
  
  public static void main(String args[]) 
  { 
 
   Thread cat=new Thread( new Cat() );
   Thread mice=new Thread( new Mice());
   
   cat.start(); 
   mice.start();  ;
        
  } //main
} //B class
 
class Food  
{
   
  volatile boolean dish1 = true ;
  volatile boolean dish2 = true ;
  volatile String name ;
    
  Food(String n) 
  
  {
    name = n;
  }
  Food() {}
 
  synchronized public void run() 
  {
   // if dish1() is occupied by any thread, even the thread from the same object cannot enter dish1() .  
    // same for  dish2()    ***not working fine ****
    if( dish1 )              
       dish1(name); 
    else if( dish2 )
       dish2(name);
  }
   
  void dish1(String name)  
  {
    //synchronized (this)
       try {
               dish1= false;
               System.out.println( name +" eating from dish 1");
               Thread.sleep(1000);
               System.out.println( name+" done eating");
               dish1 = true;
             } catch (Exception e )  { }
      
  }
 
 
  void  dish2 (String name)  
  {
    // synchronized (this)      
        try {
               dish2 = true;
               System.out.println(name + " eating from dish 2");
               Thread.sleep(2000);
               System.out.println( name +" done eating");
                dish2 = true;
              } catch (Exception e )  { }
       
  }   
 
}
 
class Cat implements Runnable {
 volatile  int catcount=0;
  String[] cats = {"cat1","cat2","cat3","cat4","cat5","cat6"};
  public void run() {
    Food food = new Food("cat");   //for the sake of simplicity I am using the "cat" only, not selecting different names
    ExecutorService  executer = Executors.newFixedThreadPool(2);  // maximum two threads can work simultanously 
 
    while( (catcount<6) )
       {  try{
                executer.submit( new Runnable() {
                                    public void run() {
                                        food.run();   
                System.out.println( );  // a blank line after every two cats finishes food ** not working fine**
                                       }
                                      });
                catcount++;
                 
              } catch(Exception e ) { }
       }
      executer.shutdown();
}
}
 
class Mice implements Runnable {
 
  volatile int micecount = 0;
  String[] mice = {"mice1","mice2"};
  public void run() {
     int i = 0;
     Food food = new Food("mice");   //for the sake of simplicity I am using the "mice" only, not selecting different names
     ExecutorService  executer = Executors.newFixedThreadPool(2);  // maximum two threads can work simultanously
 
     while( (micecount<2)  )
       {  try {
                 executer.submit( new Runnable() {
                                    public void run() {
                                        food.run();  
                System.out.println( ); // a blank line after every two mice finishes food ** not working fine**
                                       }
                                       });
                  
                 micecount++;
              } catch(Exception e ) { }
       }
       executer.shutdown();
  }
}