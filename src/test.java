import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
奇数号的哲学家先拿起左边的筷子，在拿起右边的筷子。
偶数号的哲学家先拿起右边的筷子，再拿起左边的筷子，则以就变成，
只有1号和2号哲学家会同时竞争1号的筷子，
3号和4四号的哲学家会同时竞争3号的筷子，
即5位哲学家会先竞争奇数号的筷子，
再去竞争偶数号的筷子
 */
public class test {
    static Semaphore[]semaphores=new Semaphore[5];
    public static void main(String[]args){
        //创建一个5线程的进程西
        ExecutorService executorService= Executors.newFixedThreadPool(4);
        for (int i=0;i<4;i++){
            semaphores[i]=new Semaphore(1,true);
        }
        //创建5个哲学家，
        for (int i=0;i<4;i++){
            executorService.execute(new ActionRunnable(i));
        }
    }
    //创建哲学家吃饭的行为
    static class ActionRunnable implements Runnable{
        private int id=0;
        ActionRunnable(int id){
            this.id=id;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()){
                try {
                    if((id+1)%2!=0){
                        semaphores[id].acquire();
                        semaphores[(id+1)%4].acquire();
                    }else{
                        semaphores[(id+1)%4].acquire();
                        semaphores[id].acquire();
                    }
                    System.out.println("我是"+(id+1)+"号在吃饭");

                    if ((id+1)%2!=0){
                        semaphores[id].release();
                        semaphores[(id+1)%4].release();
                    }else{
                        semaphores[(id+1)%4].release();
                        semaphores[id].release();
                    }
                    System.out.println("我是"+(id+1)+"号我要开始思考了");
                    Thread.yield();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
