package models.behavioral.visitor;

import java.util.Random;
import java.util.Vector;

//结构对象角色
public class ObjectStructure {
    private Vector<Element> elements;

    public ObjectStructure() {
        elements = new Vector<Element>();
    }

    public void action(Visitor vi) {
        for (Element e : elements) {
            e.accept(vi);
        }
    }

    public void add(Element e) {
        elements.add(e);
    }

    //元素生成器，通过一个工厂方法进行模拟
    public void createElements() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            if (rand.nextInt(100) > 50) {
                this.add(new ConcreateElement1());
            } else {
                this.add(new ConcreateElement2());
            }
        }
    }
}
