import java.io.*;
import java.util.*;

public class testFile {
    static double min_support=0.8;
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

    public static String toString(LinkedList<String> a){
        String b="";
        for(int i=0;i<a.size()-1;i++){
            b=b.concat(a.get(i)+",");
        }
        b=b.concat(a.get(a.size()-1)+"\t");
        return b;
    }
    //写一个toString方法，便于最后写入文件


    public static void main(String[] args) throws Exception {

        File a = new File("C:\\Users\\86158\\Desktop\\ShoppingCart_dataset.csv");
        FileInputStream q = new FileInputStream(a);
        FileOutputStream p=new FileOutputStream("C:\\Users\\86158\\Desktop\\result.csv");

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
                    if(recordTable[i]!=""&&recordTable[i]!=" "&&recordTable[i].length()>1) {
                        temp.add(recordTable[i]);
                    }
                }

                database.add(temp);
            }
        }
        //导入数据到数据库

        q.close();

        //从csv文件中读出数据，储存为linkedlist表，二维数组的首项为购买时间,已去除

        HashSet<String> items=new HashSet<>();

        for(LinkedList<String> record:database){
            for(String item:record){
                items.add(item);
            }
        }
        //items项集


        HashMap<String,Double> freOneItem=new HashMap<>();
        LinkedList<String> oneItemTable=new LinkedList<>();

        Iterator<String> itemsIter= items.iterator();

        while(itemsIter.hasNext()){
            String item1=itemsIter.next();
            double counter=0;
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

        HashMap<LinkedList<String>,double[]> freTwoItems=new HashMap<>();
        //从二项频繁项开始，字典里要同时有支持度和支持度计数
        LinkedList<LinkedList<String>> twoItemsTable=new LinkedList<>();

        LinkedList<LinkedList<String>> PfreTwoItem=getSubset(oneItemTable,2);
        System.out.println(PfreTwoItem.get(5).get(1));
        //获得可能的频繁二项集

        LinkedList<LinkedList<String>> twoItem=new LinkedList<>();
        for(LinkedList<String> op:database){
            LinkedList<LinkedList<String>> ed=getSubset(op,2);
            for(LinkedList<String> oe:ed){
                twoItem.add(oe);
            }
        }
        //获得所有二项集

        for(LinkedList<String> pointer: PfreTwoItem){
            int counter=0;
            for(LinkedList<String> pointer2:twoItem){
                if(pointer2.containsAll(pointer)){
                    counter++;
                }
            }
            if(counter>=min_sup){
                for(String pointer3:pointer){
                    double support=counter/(freOneItem.get(pointer3));
                    if(support>min_support&&support<=1.0){
                        LinkedList<String> out=new LinkedList<>();
                        for(String z:pointer){
                            out.add(z);
                        }
                        out.add(pointer3);
                        double[] data=new double[]{counter,support};
                        freTwoItems.put(out, data);
                        test2.write(toString(out)+",");
                        System.out.println(toString(out));
                        test2.write(counter+"\t"+",");
                        test2.write(String.valueOf(support)+"\n");
                        test2.flush();
                    }
                }
            }
        }
        try{
            test2.close();
        }catch (IOException e){
            System.out.println("已经关闭");
        }


        //获得全部的频繁二项集，并输出

    }
}
