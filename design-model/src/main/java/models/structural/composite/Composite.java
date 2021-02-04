package models.structural.composite;

import java.util.ArrayList;

//树枝构件
public class Composite implements Component{
    //构件容器
    private ArrayList<Component> componentArrayList=new ArrayList<Component>();
    //添加构件
    public void add(Component component){
        this.componentArrayList.add(component);
    }
    //删除构件
    public void remove(Component component){
        this.remove(component);
    }
    //获取子构件
    public ArrayList<Component> getChild(){
        return this.componentArrayList;
    }
    @Override
    public void operation() {
        System.out.println("树枝构件");
    }
}
