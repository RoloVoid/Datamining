import java.io.*;
import java.util.*;

public class testFile {
    static double min_support=0.2;
    //设置最小支持度阈值为0.2

    static double min_sup=2;
    //设置最小支持度计数为2

    static LinkedList<LinkedList<String>> database=new LinkedList<>();
    //database文件

    public static LinkedList<LinkedList<String>> getSubset(LinkedList<String> table, int size){
        LinkedList<LinkedList<String>> result=new LinkedList<>();
        if(size==2){
            int head=0;
            int tail=1;
            while(head!=table.size()-1&&tail<table.size()){
                while(head%2==0&&tail!=table.size()){
                    LinkedList<String> temp=new LinkedList<>();
                    temp.add(table.get(head));
                    temp.add(table.get(tail));
                    result.add(temp);
                    tail++;
                }
                head++;
                tail--;
                while(head%2==1&&tail!=head){
                    LinkedList<String> temp=new LinkedList<>();
                    temp.add(table.get(head));
                    temp.add(table.get(tail));
                    result.add(temp);
                    tail--;
                }
                head++;
                tail+=2;
            }
            return result;
        }else{
            for(int i=table.size()-1;i>size-1;i--){
                LinkedList<String> fur=new LinkedList<>();
                for(int j=0;j<i+1;j++){
                    fur.add(table.get(j));
                }
                LinkedList<LinkedList<String>> m=getSubset(fur,size-1);
                for(LinkedList<String> n:m){
                    n.add(table.get(i));
                    result.add(n);
                }
            }
        }
        return result;
    }
    //构造子集的方法1:从频繁项子集中组合
    

    public static HashMap<String[],Integer> searchFor(){
        return null;
    }
    //寻找频繁项的方法


    public static void main(String[] args) throws Exception {

        File a = new File("C:\\Users\\86158\\Desktop\\ShoppingCart_dataset.csv");
        FileInputStream q = new FileInputStream(a);
        FileOutputStream p=new FileOutputStream("C:\\Users\\86158\\Desktop\\result.txt");

        InputStreamReader m=new InputStreamReader(q);
        OutputStreamWriter n=new OutputStreamWriter(p);

        BufferedReader test=new BufferedReader(m);
        BufferedWriter test2=new BufferedWriter(n);

        //读取文件的io流方法

        while(test.readLine()!=null) {
            String mn=test.readLine();
            if(mn!=null) {
                LinkedList<String> temp=new LinkedList<>();
                String[] recordTable=mn.split(",");
                for(int i=1;i<recordTable.length;i++){
                    temp.add(recordTable[i]);
                }
                database.add(temp);
            }
        }
        //导入数据到数据库

        q.close();
        p.close();

        //从csv文件中读出数据，储存为linkedlist表，二维数组的首项为购买时间,已去除

        HashSet<String> items=new HashSet<>();

        for(LinkedList<String> record:database){
            for(String item:record){
                items.add(item);
            }
        }
        //items项集


        HashMap<String,Integer> freOneItem=new HashMap<>();
        LinkedList<String> oneItemTable=new LinkedList<>();

        Iterator<String> itemsIter= items.iterator();

        while(itemsIter.hasNext()){
            String item1=itemsIter.next();
            int counter=0;
            for(LinkedList<String> record:database){
                for(String item:record){
                    if (item.equals(item1)) {
                        counter++;
                    }
                }
            }
            if(counter>=min_sup) {
                freOneItem.put(item1, counter);
                oneItemTable.add(item1);
            }
        }
        //获得频繁一项集

        LinkedList<LinkedList<String>> freTwoItem=getSubset()
    }
}
