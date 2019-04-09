import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test_noly_for_github_you_connot_run_just_now {
    public static void main(String []args){
        //创建可回收的线程池
        ExecutorService executorService= Executors.newCachedThreadPool();
        int sum =5;//初始化哲学家和筷子的人数的人数
        Chopstick[] chopsticks=new Chopstick[sum];
        for (int i=0;i<sum;i++){
            chopsticks[i]= new Chopstick(i);
        }
        for (int i=0;i<sum;i++){
            executorService.execute(new Philosopher(chopsticks[i],chopsticks[(i+1)%sum]));
        }

    }
}
//构造筷子类
class Chopstick{
    //状态
    private int id;
    //创建构造器
    public Chopstick(int id){
        this.id=id;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
}
class Philosopher implements Runnable{
    private Chopstick left;
    private Chopstick right;
    public Philosopher(Chopstick left,Chopstick right){
        if (left.getId() < right.getId()) {
            this.left=left;
            this.right=right;
        }
        else{
            this.left=right;
            this.right=left;
        }
    }

    @Override
    public void run() {
        try {
            while (true){
                //思考一段时间
                //Thread.sleep(2000);
                //让哲学家不在进行思考，模拟极端情况，测试是否出现死锁
                synchronized (left){
                    synchronized (right){
                        //进餐
                        Thread.sleep(2000);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
