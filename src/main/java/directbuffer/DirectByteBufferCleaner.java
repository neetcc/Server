package directbuffer;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;

/**
 * Created by ChengCe on 2017/12/15.
 */
// method to clean directbytebuffer
public class DirectByteBufferCleaner {
    public static void clean(final ByteBuffer buffer){
        if(buffer.isDirect()){
            ((DirectBuffer)buffer).cleaner().clean();
        }
    }
    
    public static void sleep(long time){
        try {
            Thread.currentThread().sleep(time);
        }catch (Exception e){
            
        }
    }
    
    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024*1024*100);
        System.out.println("start");
        sleep(10000);
        clean(buffer);
        System.out.println("end");
        sleep(10000);
    }
}
