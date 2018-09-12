package file;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * Created by rcp on 2018/9/12.
 */
public class DirList {
    public static void main(String[] args){
        File path = new File("c:/");
        ert(path);
    }

    static void ert(File path){
        if(path.isDirectory()){
            for(File file : path.listFiles()){
                ert(file);
            }
        }

        if (path.getName().endsWith(".git")){
            System.out.println(path.getPath());
        }

//        String[] list;
//        list = path.list(new DirFilter("read"));
//        Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
//        for(String dirItem:list){
//            System.out.println(dirItem);
//        }
    }
}



class DirFilter implements FilenameFilter{
    private Pattern pattern;
    public DirFilter(String regex){
        pattern = Pattern.compile(regex);
    }
    public boolean accept(File dir, String name){
        return pattern.matcher(name).matches();
    }
}
