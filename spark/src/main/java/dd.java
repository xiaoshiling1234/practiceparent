import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class dd {
    /**
     * lru design
     * @param operators int整型二维数组 the ops
     * @param k int整型 the k
     * @return int整型一维数组
     */
    public int[] LRU (int[][] operators, int k) {
        // write code here
        Map<Integer,Integer> map=new LinkedHashMap<>();
        ArrayList<Integer> result=new ArrayList<>();
        for(int[] op:operators){
            switch(op[0]){
                case 1:
                    if(!map.containsKey(op[1])&&map.size()==k){
                        map.remove(map.keySet().iterator().next());
                    }else{
                        map.remove(op[1]);
                    }
                    map.put(op[1],op[2]);
                    continue;
                case 2:
                    if(map.containsKey(op[1])){
                        int val=map.get(op[1]);
                        map.remove(op[1]);
                        map.put(op[1],val);
                    }
                    result.add(map.getOrDefault(op[1],-1));
                    continue;
            }
        }
        int[] out= new int[result.size()];
        for(int i=0;i<result.size();i++){
            out[i]=result.get(i);
        }
        return out;
    }

    public static void main(String[] args) {
        int[][] operators={{1,1,1},{1,2,2},{1,3,2},{2,1},{1,4,4},{2,2}};
        new dd().LRU(operators,3);
    }
}