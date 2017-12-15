package classloader;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by ChengCe on 2017/12/11.
 */
public class MLoader extends ClassLoader{


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String classPath = MLoader.class.getResource("/").getPath(); //得到classpath  
        String fileName = name.replace(".", "/") + ".class" ;
        File classFile = new File(classPath , fileName);
        if(!classFile.exists()){
            throw new ClassNotFoundException(classFile.getPath() + " 不存在") ;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        ByteBuffer bf = ByteBuffer.allocate(1024) ;
        FileInputStream fis = null ;
        FileChannel fc = null ;
        try {
            fis = new FileInputStream(classFile) ;
            fc = fis.getChannel() ;
            while(fc.read(bf) > 0){
                bf.flip() ;
                bos.write(bf.array(),0 , bf.limit()) ;
                bf.clear() ;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fis.close() ;
                fc.close() ;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return defineClass(bos.toByteArray() , 0 , bos.toByteArray().length) ;
    }


}
