package parse.executor;

import org.reflections.Reflections;
import parse.utils.MyAdjGraphic;

import java.util.*;

public class Dag {
    public void runTask() {
        System.out.println("调用血缘任务");
    }

    public static boolean dagParse() {
        HashSet<Object> classSet = new HashSet<>();
        //扫描需要执行的包
        try {
            //入参 要扫描的包名
            Reflections f = new Reflections("parse.executor.task");
            //入参 目标注解类
            Set<Class<?>> set = f.getTypesAnnotatedWith(JoinRunDag.class);
            for (Class<?> c : set) {
                JoinRunDag annotation = c.getAnnotation(JoinRunDag.class);
                if (annotation.enable()) {
                    classSet.add(c.newInstance());
                }
            }
            //构建图
            MyAdjGraphic adjGraphic = buildGraph(classSet.toArray());
            for (Object o : adjGraphic.vertices) {
                System.out.print(o.getClass().getSimpleName() + "\t");
            }
            System.out.println();
            adjGraphic.print();
            //拓扑排序执行任务
            return runDagTask(adjGraphic);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean runDagTask(MyAdjGraphic adjGraphic) {
        ArrayList vertices = adjGraphic.vertices;
        int[] doneLogo = new int[vertices.size()];
        while (true) {
            ArrayList<Task> tasks = new ArrayList<>();
            for (int i = 0; i < doneLogo.length; i++) {
                if (doneLogo[i] == 0) {
                    //找入度为0的节点
                    if (adjGraphic.getInDegreeOfVertice(vertices.get(i)) == 0) {
                        tasks.add((Task) vertices.get(i));
                        doneLogo[i] = 1;
                    }
                }
            }
            //执行任务
            for (Task task : tasks) {
                task.run();
                //将节点相关的出度设为0
                int i = vertices.indexOf(task);
                adjGraphic.deleteOutDegree(i);
                adjGraphic.print();
            }

            //如果没有入度为0的，则可能任务执行结束或有环路
            if (tasks.size() == 0) {
                int s = 0;
                for (int logo : doneLogo) {
                    s += logo;
                }
                if (s < doneLogo.length) {
                    System.out.println("任务出错，存在环路");
                }
                break;
            }
        }
        return false;
    }

    private static MyAdjGraphic buildGraph(Object[] vertices) throws Exception {
        int n = vertices.length;
        MyAdjGraphic g = new MyAdjGraphic(n);
        //初始化图节点
        for (Object vertice : vertices) {
            g.insertVertice(vertice);
        }
        //查找起始节点和终止节点index
        for (Object vertice : vertices) {
            SqlTask sqlTask = (SqlTask) vertice;
            int ounIndex = -1;
            for (int i = 0; i < n; i++) {
                if (vertice.getClass().isInstance(vertices[i])) {
                    ounIndex = i;
                }
            }

            for (Task task : sqlTask.getParents()) {
                int inIndex = -1;
                for (int i = 0; i < n; i++) {
                    if (task.getClass().isInstance(vertices[i])) {
                        inIndex = i;
                    }
                }
                if (inIndex == -1) {
                    System.out.println("父节点:" + task.getClass().getName() + "没有注册或不存在！！");
                } else {
                    g.insertEdges(inIndex, ounIndex, 1);
                }
            }
        }
        return g;
    }

    public static void main(String[] args) {
        Dag.dagParse();
    }
}
